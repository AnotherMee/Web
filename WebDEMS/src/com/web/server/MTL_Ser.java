package com.web.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.xml.ws.Endpoint;

import com.web.service.MTLimp;

public class MTL_Ser 
{
	public static void main(String str[]) 
	{
		MTLimp mtl_obj = new MTLimp();
		Endpoint endpoint = Endpoint.publish("http://localhost:8081/mtlserver", mtl_obj);
		System.out.println("MTL Server start");
		
		String getInfo = null;
		String sendInfo = null;
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6001);
	        	while(true)
		        {
		        	byte[] buffer = new byte[1000];
		        	byte [] m;
	        		System.out.println("MTL UDP");
	        		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		System.out.println("reach MTL list");
		        		String[] info = getInfo.split(",");
		        		String eventType = info[0];
		        		sendInfo = mtl_obj.getOwnlistEevntAvailability(eventType);
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = mtl_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		System.out.println(customerID + " MTLgetinfo");
		        		sendInfo = mtl_obj.getBookingSchedule(customerID);
		        		System.out.println(sendInfo + " sendInfo");
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = mtl_obj.cancelEvent(customerID, eventID, eventType);
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
