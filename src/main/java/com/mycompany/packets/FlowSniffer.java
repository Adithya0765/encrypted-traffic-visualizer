package com.mycompany.packets;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FlowSniffer {

    private final Map<String, Flow> flows = new HashMap<>();

    public void startCapturing(java.util.function.Consumer<Map<String, Flow>> uiUpdater) {
        try {
            int index = 4; // your WiFi / NIC index

            PcapNetworkInterface nif = Pcaps.findAllDevs().get(index);
            if (nif == null) return;

            PcapHandle handle = new PcapHandle.Builder(nif.getName())
                    .snaplen(65536)
                    .promiscuousMode(PromiscuousMode.NONPROMISCUOUS)
                    .timeoutMillis(50)
                    .immediateMode(true)
                    .build();

            Thread refresher = new Thread(() -> {
                while (true) {
                    try { Thread.sleep(3000); } catch (Exception ignored) {}
                    flows.values().forEach(this::classifyFlow);
                    uiUpdater.accept(flows);
                }
            });
            refresher.setDaemon(true);
            refresher.start();

            PacketListener listener = packet -> {
                try {
                    IpV4Packet ipv4 = packet.get(IpV4Packet.class);
                    if (ipv4 == null) return;

                    TcpPacket tcp = packet.get(TcpPacket.class);
                    if (tcp == null) return;

                    String dst = ipv4.getHeader().getDstAddr().getHostAddress();
                    int dstPort = tcp.getHeader().getDstPort().valueAsInt();
                    int length = packet.length();

                    String key = dst + ":" + dstPort;
                    long now = System.currentTimeMillis();

                    Flow f = flows.get(key);
                    if (f == null) {
                        f = new Flow(dst, dstPort);
                        flows.put(key, f);
                        enrichIP(f);
                    }

                    f.update(length, now);

                } catch (Exception ignored) {}
            };

            handle.loop(-1, listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enrichIP(Flow f) {
        if (f.dstIP.startsWith("172.") || f.dstIP.startsWith("192.") || f.dstIP.startsWith("10.")) {
            f.org = "Local Network";
            f.asn = "Private";
            f.country = "LAN";
            return;
        }

        try {
            URL url = new URL("http://ip-api.com/json/" + f.dstIP);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1500);
            conn.setReadTimeout(1500);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) json.append(line);

            String txt = json.toString();
            f.org = extract(txt, "\"org\":\"", "\"");
            f.asn = extract(txt, "\"as\":\"", "\"");
            f.country = extract(txt, "\"country\":\"", "\"");

        } catch (Exception e) {
            f.org = "Unknown";
            f.asn = "Unknown";
            f.country = "Unknown";
        }
    }

    private void classifyFlow(Flow f) {
        String asn = (f.asn == null ? "" : f.asn.toLowerCase());
        long bytes = f.totalBytes;
        int p = f.dstPort;

        boolean cloud = asn.contains("amazon") || asn.contains("aws") ||
                        asn.contains("google") || asn.contains("gcp") ||
                        asn.contains("microsoft") || asn.contains("azure") ||
                        asn.contains("digitalocean") || asn.contains("linode") ||
                        asn.contains("ovh") || asn.contains("hetzner") ||
                        asn.contains("m247");

        if (f.dstIP.startsWith("172.") || f.dstIP.startsWith("192.") || f.dstIP.startsWith("10.")) {
            f.category = "Local";
            return;
        }

        boolean vpnPort = p == 1194 || p == 51820 || p == 500 || p == 4500 || p == 1701;
        boolean vpnTraffic = cloud && (vpnPort || (p == 443 && bytes > 5_000_000));

        if (vpnTraffic) {
            f.category = "VPN Tunnel";
            return;
        }

        if (asn.contains("cloudflare")) {
            if (p == 443 && bytes < 250_000) {
                f.category = "DoH Candidate";
            } else {
                f.category = "CDN / Discord / Cloudflare";
            }
            return;
        }

        if (asn.contains("google")) {
            if (bytes < 250_000) f.category = "Chrome/Google API";
            else f.category = "Browser Traffic";
            return;
        }

        if (asn.contains("microsoft")) {
            f.category = "Enterprise (Teams/Office)";
            return;
        }

        if (p == 443) {
            f.category = cloud ? "Cloud Service" : "Encrypted HTTPS";
            return;
        }

        if (p == 853) {
            f.category = "DNS-over-TLS";
            return;
        }

        f.category = "Other";
    }

    private String extract(String json, String start, String end) {
        int s = json.indexOf(start);
        if (s == -1) return "Unknown";
        s += start.length();
        int e = json.indexOf(end, s);
        if (e == -1) return "Unknown";
        return json.substring(s, e);
    }
}
