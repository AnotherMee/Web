
package com.web.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.xml.ws.Endpoint;

import com.web.service.QUEimp;

public class QUE_Ser 
{
	public static void main(String str[]) 
	{
		QUEimp que_obj = new QUEimp();
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/queserver", que_obj);
		System.out.println("QUE Server start");
		
		String getInfo;
		String sendInfo = null;
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6000);
	        	while(true)
		        {
	        		System.out.println("QUE UDP start");
	        		byte[] buffer = new byte[1000];
	        		byte [] m;
		        	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		String[] info = getInfo.split(",");
		        		System.out.println(getInfo);
		        		String eventType = info[0];
		        		System.out.println(eventType);
		        		sendInfo = que_obj.getOwnlistEevntAvailability(eventType);	
		        		System.out.println(sendInfo);
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = que_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		sendInfo = que_obj.getBookingSchedule(customerID);
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = que_obj.cancelEvent(customerID, eventID, eventType);
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
