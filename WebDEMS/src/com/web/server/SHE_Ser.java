package com.web.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.xml.ws.Endpoint;

import com.web.service.SHEimp;

public class SHE_Ser 
{
	public static void main(String str[]) 
	{
		SHEimp she_obj = new SHEimp();
		Endpoint endpoint = Endpoint.publish("http://localhost:8082/sheserver", she_obj);
		System.out.println("QUE Server start");
		
		
		String getInfo;
		String sendInfo = null;
		
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6002);
	        	while(true)
		        {
		        	byte[] buffer = new byte[1000];
		        	byte [] m;
	        		System.out.println("SHE_UDP");
	        		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String eventType = info[0];
		        		sendInfo = she_obj.getOwnlistEevntAvailability(eventType);	
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = she_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		sendInfo = she_obj.getBookingSchedule(customerID);
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = she_obj.cancelEvent(customerID, eventID, eventType);
		        	}
		        	m = sendInfo.getBytes();
		        	DatagramPacket reply = new DatagramPacket(m, m.length, request.getAddress(), request.getPort());
		        	aSocket.send(reply);
		        }
	        }
	        catch(SocketException e){
	        	System.out.println("Socket: " + e.getMessage());
	        }
	        catch(IOException e) {
	        	System.out.println("IO: " + e.getMessage());
	        }
	}
}
