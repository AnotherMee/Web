package com.web.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Interfaces 
{
	String addEvent(String eventIDs, String eventType, int bookCap);
	String removeEvent(String eventID, String eventType);
	String listEventAvailability(String eventType);
	String getOwnlistEevntAvailability(String eventType);
	String bookEvent (String customerID, String eventID, String eventType);
	String getBookingSchedule (String customerID);
	String cancelEvent(String customerID, String eventID, String eventType);
	String swapEvent(String customerID, String newEventID, String newEventType, String oldEventID, String oldEventType);
}
