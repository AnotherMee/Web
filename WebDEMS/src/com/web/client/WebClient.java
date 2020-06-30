package com.web.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.web.server.QUE_Ser;
import com.web.service.Interfaces;








public class WebClient 
{
	static Interfaces inter_obj;
	public static void main(String[] args) throws MalformedURLException 
	{
		Thread threadOne = new Thread(new Runnable() 
		{
	        public void run() 
	        {
	        	try {
					operation(args);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
		     
	    Thread threadTwo = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	try {
					operation(args);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    Thread threadThree = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	try {
					operation(args);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    Thread threadFour = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	try {
					operation(args);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    Thread threadFive = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	try {
					operation(args);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    Thread threadTest1 = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	test1(args);
	        }
	    });
	    Thread threadTest2 = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	        	test2(args);
	        }
	    });
	    System.out.println("How many clients do you need?");
	    Scanner scan=new Scanner(System.in);
		int num = scan.nextInt();
		if(num == 1)
		{
			threadOne.start();
		}
		else if(num == 2)
		{
			threadOne.start();
		    threadTwo.start();
		}
		else if(num == 3)
		{
			threadOne.start();
		    threadTwo.start();
		    threadThree.start();
		}
		else if(num == 4)
		{
			threadOne.start();
		    threadTwo.start();
		    threadThree.start();
		    threadFour.start();
		}
		else if(num == 5)
		{
			threadOne.start();
		    threadTwo.start();
		    threadThree.start();
		    threadFour.start();
		    threadFive.start();
		}
		else
		{
			threadTest1.start();
		    threadTest2.start();
		}
	}
	public static void operation(String[] args) throws MalformedURLException
    {
		while(true)
		{
			System.out.println("Please enter your UserID or enter 0 to exit");
			Scanner scan=new Scanner(System.in);
			String userID = scan.nextLine();
		    
			String user = userID.substring(3, 4);
			String serverNam = userID.substring(0, 3);
			String result = null;
			// Created User file
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			sf.setLenient(false);
			String fileName = userID;
			File file = new File("D:\\test_files\\" + fileName);
			
			if (file.exists() == false)
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Created QUE file
			String QUE_fileName = "QUE_Server";
			File QUE_file = new File("D:\\test_files\\" + QUE_fileName);
			
			if (QUE_file.exists() == false)
			{
				try {
					QUE_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Created MTL file
			String MTL_fileName = "MTL_Server";
			File MTL_file = new File("D:\\test_files\\" + MTL_fileName);
			
			if (MTL_file.exists() == false)
			{
				try {
					MTL_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Created SHE file
			
			String SHE_fileName = "SHE_Server";
			File SHE_file = new File("D:\\test_files\\" + SHE_fileName);
			
			if (SHE_file.exists() == false)
			{
				try {
					SHE_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (user.equals("M"))
			{
				
				if (serverNam.equals("QUE"))
				{
					URL que_url = new URL("http://localhost:8080/queserver?wsdl");
					QName que_QName = new QName("http://service.web.com/", "QUEimpService");
					Service que_service = Service.create(que_url, que_QName);
					inter_obj = que_service.getPort(Interfaces.class);
					
					    System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
					    String choice = scan.nextLine();
					
						if (choice.equals("a"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();						
							int cap = Integer.parseInt(bookCap);
							result = inter_obj.addEvent(eventID, eventType, cap);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + cap + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = inter_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = inter_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// book event
						else if (choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.print(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// get book events
						else if (choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// cancel event
						else if (choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
					}
				}
				
				else if (serverNam.equals("MTL"))
				{
					try
					{

						URL mtl_url = new URL("http://localhost:8081/mtlserver?wsdl");
						QName mtl_QName = new QName("http://service.web.com/", "MTLimpService");
						Service mtl_service = Service.create(mtl_url, mtl_QName);
						inter_obj = mtl_service.getPort(Interfaces.class);
					
						System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
						String mtl_choice = scan.nextLine();
						if (mtl_choice.equals("a"))
						{
							
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();
							int bc = Integer.parseInt(bookCap);
							result = inter_obj.addEvent(eventID, eventType, bc);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}				
						}
						else if (mtl_choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = inter_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = inter_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (mtl_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (mtl_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);	
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
				
				else if (serverNam.equals("SHE"))
				{
					try
					{
						URL she_url = new URL("http://localhost:8082/sheserver?wsdl");
						QName she_QName = new QName("http://service.web.com/", "SHEimpService");
						Service she_service = Service.create(she_url, she_QName);
						inter_obj = she_service.getPort(Interfaces.class);
				
						System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
						String she_choice = scan.nextLine();
				
						if (she_choice.equals("a"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();
							int bc = Integer.parseInt(bookCap);
							result = inter_obj.addEvent(eventID, eventType, bc);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = inter_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = inter_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);
	
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (she_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
			}
			// Customer Client///////////////////////////////////////////////////////////////////////////////////////////
			else
			{
				// QUE
				if(serverNam.equals("QUE"))
				{
					try
					{
						URL que_url = new URL("http://localhost:8080/queserver?wsdl");
						QName que_QName = new QName("http://service.web.com/", "QUEimpService");
						Service que_service = Service.create(que_url, que_QName);
						inter_obj = que_service.getPort(Interfaces.class);
					
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event, enter s to swap events");
						String choice = scan.nextLine();
					
						// book event
						if (choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.print(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// get book events
						else if (choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// cancel event
						else if (choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}	
				else if (serverNam.equals("MTL"))
				{
					
					try 
					{
						URL mtl_url = new URL("http://localhost:8081/mtlserver?wsdl");
						QName mtl_QName = new QName("http://service.web.com/", "MTLimpService");
						Service mtl_service = Service.create(mtl_url, mtl_QName);
						inter_obj = mtl_service.getPort(Interfaces.class);
					
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event.");
						String mtl_choice = scan.nextLine();
					
						if (mtl_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (mtl_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);	
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}		
				else if (serverNam.equals("SHE"))
				{
					try 
					{
						URL she_url = new URL("http://localhost:8082/sheserver?wsdl");
						QName she_QName = new QName("http://service.web.com/", "SHEimpService");
						Service she_service = Service.create(she_url, she_QName);
						inter_obj = she_service.getPort(Interfaces.class);
				
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event.");
						String she_choice = scan.nextLine();
				
						if (she_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.getBookingSchedule(customerID);
							System.out.println(result);
	
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = inter_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (she_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = inter_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
			}
			try 
			{
				FileWriter fw = new FileWriter(file,true);
				fw.write(result);
				fw.flush();
				fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void test1(String[] args)
    {
    	String result1 = null;
    	
    	try
		{
    		URL que_url = new URL("http://localhost:8080/queserver?wsdl");
			QName que_QName = new QName("http://service.web.com/", "QUEimpService");
			Service que_service = Service.create(que_url, que_QName);
			inter_obj = que_service.getPort(Interfaces.class);
		    
		    result1 = inter_obj.bookEvent("SHEC1234", "", "");
		}
    	catch (Exception e) {
	          System.out.println("Client exception: " + e);
		  e.printStackTrace();
	       }
    	
    	
    	System.out.println(result1);;
    	
    }
    
    public static void test2(String[] args)
    {
    	String result2 = null;
    	try
		{
    		URL she_url = new URL("http://localhost:8082/sheserver?wsdl");
			QName she_QName = new QName("http://service.web.com/", "SHEimpService");
			Service she_service = Service.create(she_url, she_QName);
			inter_obj = she_service.getPort(Interfaces.class);
		    
		    result2 = inter_obj.bookEvent("SHEC1234", "", "");
		    
		    
		}
    	catch (Exception e) {
	          System.out.println("Client exception: " + e);
		  e.printStackTrace();
	       }
    	System.out.println(result2);
    }
	
	
}

