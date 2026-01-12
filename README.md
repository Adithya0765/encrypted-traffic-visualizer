<div align="center">

# 🕶️ **Encrypted Traffic Visualizer**
### *Real-Time SOC Dashboard for Encrypted Network Flows*

<img src="https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square">
<img src="https://img.shields.io/badge/Language-Java%2017+-blue?style=flat-square">
<img src="https://img.shields.io/badge/UI-JavaFX-purple?style=flat-square">
<img src="https://img.shields.io/badge/Packet%20Capture-Pcap4j-orange?style=flat-square">
<img src="https://img.shields.io/badge/Platform-Windows-lightgrey?style=flat-square">
<img src="https://img.shields.io/badge/License-MIT-green?style=flat-square">

<br>

**Encrypted Traffic Visualizer** is a modern JavaFX-based cyber tool that captures live traffic and classifies encrypted flows (VPN, DoH, Enterprise, Browser, Cloud Services, Local LAN) *without decrypting payloads.*

Built as a mini-SOC dashboard for learning & analysis by blue teamers, red teamers, SOC analysts, and students.

</div>

---

## 🛰️ **What This Tool Does**

✔ Captures raw network packets in real time  
✔ Groups them into encrypted flows  
✔ Enriches IPs with ASN + Country metadata  
✔ Classifies traffic into meaningful categories  
✔ Displays everything in a clean SOC dashboard  

---

## ✨ **Feature Highlights**

- Real-time Packet Capture using Pcap4J
- Encrypted Traffic Classification:
  - VPN Tunnels (OpenVPN, WireGuard, IPSec)
  - DNS-over-HTTPS (DoH)
  - Enterprise traffic (Teams/Office 365)
  - Browser traffic (Chrome/Google)
  - Cloud Services (AWS, Azure, GCP, OVH, Linode, etc.)
  - Local LAN traffic
- ASN & Organization Enrichment (IP → Metadata)
- Country Origin Lookup
- JavaFX SOC Dashboard UI
- Flow Table with Live Updates
- Cross-platform Support: Windows + Java 17+

---

## 🔍 **Detection Categories**

Detection uses:

➤ Cloud ASN recognition  
➤ VPN port heuristics  
➤ TLS+Google eco recognition  
➤ DoH signatures  
➤ LAN vs Internet split  

---

## 🎛️ **Architecture**

```text
┌───────────────────────────────┐
│ Pcap4J Packet Capture         │
└──────────────┬────────────────┘
│ Packets
┌──────────────▼────────────────┐
│ FlowSniffer                   │
│ • TCP flow assembly           │
│ • GEO/ASN enrichment          │
│ • Classification engine       │
└──────────────┬────────────────┘
│ Classified flows
┌──────────────▼────────────────┐
│ JavaFX GUI                    │
│ • Live SOC dashboard          │
│ • Table-based visualization   │
└───────────────────────────────┘
```

---

## 🧩 **Tech Stack**

| Component | Purpose |
|---|---|
| 🟦 **Java 17+ / JDK 25** | Core runtime |
| 🟥 **Maven** | Dependency management |
| 🟧 **pcap4j** | Packet capture layer |
| 🟪 **JavaFX** | Dashboard UI |
| 🌐 **ip-api.com** | IP metadata enrichment |
| 🎯 **Npcap/WinPcap** | Network driver |

---

## 📦 **Installation**

### **1️⃣ Install Required Dependencies**

✔ Npcap (Windows Driver):  
https://npcap.com/  
✔ Java JDK 17+ (or JDK 25)  
✔ Maven 3.8+

---

### **2️⃣ Clone the Repo**

```bash
git clone https://github.com/<your-name>/<your-repo>.git
cd <your-repo>
```
### **3️⃣ Run the Tool**

```bash
mvn clean install
mvn javafx:run
```

## 📊 Output Columns Explained
| Column          | Meaning                  |
| --------------- | ------------------------ |
| **Destination** | Destination IP           |
| **Port**        | Target TCP port          |
| **ASN**         | Autonomous System Number |
| **Org**         | Owning organization      |
| **Country**     | Geo origin               |
| **Category**    | Classification result    |
| **Packets**     | Flow packet count        |
| **Bytes**       | Total flow size          |


## 🧠 Classification Logic Overview
Traffic is classified without decrypting, using metadata such as:

- IP ASN (AWS, GCP, Azure, Cloudflare, etc.)

- TLS ports (443, 853)

- VPN ports (51820, 1194, 500, 4500)

- Bytes transferred

- Remote org identity

- Cloud/enterprise ownership

Examples

🟣 VPN Tunnel
→ Cloud ASN + VPN ports OR long-lived TLS

🔵 Browser Traffic
→ Google ASN + TLS

🟢 Enterprise
→ Microsoft ASN (Teams/Office)

🟠 DoH Candidate
→ Cloudflare ASN + small TLS 443 responses

⚪ Local
→ RFC1918 destinations (172.x, 192.168.x, 10.x)

> No DPI or MITM is performed.


## 🧪 Best Results Tips

✔ Run on a real Wi-Fi/Ethernet network
✔ Keep outbound HTTP allowed (for ASN lookup)
✔ Try using VPN or browsers for visible categories

## ⚠️ Legal Use Notice

This tool is intended for:

- Research 🧪

- Education 🎓

- SOC Practice 🛡️

- Red/Blue Teaming ⚔️

> Do NOT capture traffic on networks you do not own or have permission to inspect.

## 🤝 Contributions Welcome

Potential future upgrades:

- JA3 TLS fingerprinting

- C2 Beacon detection

- GeoIP heatmaps

- CSV/JSON export

- Threat intel integration

- UI side-panels + filters

- Plug-in architecture

Submit PRs or Issues 🙌

## 🌟 Why This Project Matters

Encrypted traffic dominates the modern internet — this tool demonstrates how much intel can be extracted through:

✔ Metadata
✔ ASN + cloud mapping
✔ Protocol heuristics

— without ever decrypting a packet.

This makes it a great portfolio project for:

- 🧑‍💻 Cybersecurity Students

- 🎯 SOC Analysts

- 🛡️ Blue Teamers

- ⚔️ Red Teamers

- 🕵️ Malware Analysts

- 🌐 Network Engineers

<div align="center">

Made for learning. Built for hacking your understanding of encryption.

🕶️ Observe the encrypted world — without breaking it.

</div> 
