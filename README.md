# Encrypted Traffic Visualizer (SOC Dashboard)

A JavaFX-based encrypted traffic analysis tool that captures live network traffic via Pcap4J and classifies encrypted flows (VPN, DNS-over-HTTPS, Browser, Cloud Services, Enterprise, Local LAN). Data is enriched with ASN, organization, and country information to give SOC analysts context.

This project is designed as a blue-team/red-team learning tool to visualize modern encrypted traffic without decrypting payloads.

---

## ✨ Features

- Real-time Packet Capture using Pcap4J
- Encrypted Traffic Classification:
  - VPN Tunnels (OpenVPN, WireGuard, IPSec)
  - DNS-over-HTTPS (DoH)
  - Enterprise traffic (Teams/Office 365)
  - Browser traffic (Chrome/Google)
  - Cloud Services (AWS, Azure, GCP, OVH, Linode, etc.)
  - Local LAN traffic
- ASN & Organization Enrichment (IP → Geolocation metadata)
- Country Origin Lookup
- JavaFX SOC Dashboard UI
- Flow Table with Live Updates
- Cross-platform support: Windows + Java 17+

---

## 🛠 Technology Stack

| Component | Used For |
|---|---|
| Java 17+ / JDK 25 | Core Language |
| Maven | Dependency & Build Mgmt |
| pcap4j | Packet Capture Layer |
| JavaFX | GUI Rendering |
| ip-api.com | IP ASN/Geo Lookup |
| Npcap (WinPcap) | Native Packet Driver |

---

## 📦 Installation

### 1. Install Required Dependencies

✔ **Npcap** (Packet driver for Windows):   ```https://npcap.com/```  
✔ **Java JDK 17+ or JDK 25**  
✔ **Maven 3.8+**

---

### 2. Clone the Repository

```bash
git clone https://github.com/Adithya0765/encrypted-traffic-visualizer.git
cd encrypted-traffic-visualizer
```

