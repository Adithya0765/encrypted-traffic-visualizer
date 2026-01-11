package com.mycompany.packets;

public class Flow {

    public String dstIP;
    public int dstPort;
    public String asn = "Unknown";
    public String org = "Unknown";
    public String country = "Unknown";
    public String category = "Unclassified";

    public int packetCount = 0;
    public long totalBytes = 0;
    public long firstSeen;
    public long lastSeen;

    public Flow(String dstIP, int dstPort) {
        this.dstIP = dstIP;
        this.dstPort = dstPort;
        this.firstSeen = System.currentTimeMillis();
        this.lastSeen = this.firstSeen;
    }

    public void update(int bytes, long now) {
        this.packetCount++;
        this.totalBytes += bytes;
        this.lastSeen = now;
    }

    // JavaFX uses these getters!
    public String getDstIP() { return dstIP; }
    public int getDstPort() { return dstPort; }
    public String getAsn() { return asn; }
    public String getOrg() { return org; }
    public String getCountry() { return country; }
    public String getCategory() { return category; }
    public int getPacketCount() { return packetCount; }
    public long getTotalBytes() { return totalBytes; }
}
