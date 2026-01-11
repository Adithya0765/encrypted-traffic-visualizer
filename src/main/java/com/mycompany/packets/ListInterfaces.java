package com.mycompany.packets;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.List;

public class ListInterfaces {
	public static void main(String [] args) throws Exception {
		List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
		
		if(devices == null || devices.isEmpty()) {
			System.out.println("No Network Interfaces found!");
			System.out.println("Try running ecplise ad admin");
			return;
		}
		
		System.out.println("Available Network Interfaces:");
		int index = 0;
		for(PcapNetworkInterface dev : devices) {
			System.out.println("["+index+"]"+dev.getName()+" - "+dev.getDescription());
			index = index+1;
		}
	}

}
