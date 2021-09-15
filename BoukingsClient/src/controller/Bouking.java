package controller;

import java.rmi.RemoteException;

import model.BodyParser;
import tps.ws.deployment.BookingsStub;
import tps.ws.deployment.BookingsStub.Buy;
import tps.ws.deployment.BookingsStub.Travel;
import tps.ws.deployment.BookingsStub.Travels;

public class Bouking {
	public static String getTravels(String departureStation, String arrivalStation, long departureDate, long arrivalDate, int nbFirstClass, int nbBusinessCLass, int nbStandardClass) {		
		String res = null;
		try {
			BookingsStub stub = new BookingsStub();
			Travels resource = new Travels();

			
			if(departureStation != null) resource.setDepartureStation(departureStation);
			if(arrivalStation != null) resource.setArrivalStation(arrivalStation);
			if(departureDate >= 0) resource.setDepartureDate(departureDate);
			else resource.setDepartureDate(0);
			if(arrivalDate >= 0) resource.setArrivalDate(arrivalDate);
			else resource.setArrivalDate(0);
			if(nbFirstClass >= 0) resource.setNbFirstCLass(nbFirstClass);
			else resource.setNbFirstCLass(0);
			if(nbBusinessCLass >= 0) resource.setNbBusinessClass(nbBusinessCLass);
			else resource.setNbBusinessClass(0);
			if(nbStandardClass >= 0) resource.setNbStandardClass(nbStandardClass);
			else resource.setNbStandardClass(0);
			
			res = stub.travels(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		if(res == null)
			return null;
		
		return res;
	}
	
	public static String getTravel(String webService, int trainID) {		
		String res = null;
		try {
			BookingsStub stub = new BookingsStub();
			Travel resource = new Travel();
			
			resource.setTrainID(trainID);
			resource.setWebService(webService);
			
			res = stub.travel(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		if(res == null)
			return null;
		
		return res;
	}
	
	public static String buy(String webService, int trainID, int nbFirstClass, int nbFlexibleFirstClass, int nbBusinessClass, int nbFlexibleBusinessClass, int nbStandardClass, int nbFlexibleStandardClass, String mail) {
		String res = null;
		try {
			for(int i = 0; i < nbFirstClass - nbFlexibleFirstClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("false");
				resource.setTicketClass("FIRST");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			for(int i = 0; i < nbFlexibleFirstClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("true");
				resource.setTicketClass("FIRST");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			for(int i = 0; i < nbBusinessClass - nbFlexibleBusinessClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("false");
				resource.setTicketClass("BUSINESS");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			for(int i = 0; i < nbFlexibleBusinessClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("true");
				resource.setTicketClass("BUSINESS");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		try {
			for(int i = 0; i < nbStandardClass - nbFlexibleStandardClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("false");
				resource.setTicketClass("STANDARD");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			for(int i = 0; i < nbFlexibleStandardClass; i++) {
				BookingsStub stub = new BookingsStub();
				Buy resource = new Buy();
				res = null;
				
				resource.setTrainID(trainID);
				resource.setFlexible("true");
				resource.setTicketClass("STANDARD");
				resource.setWebService(webService);
				resource.setMail(mail);
				
				res = stub.buy(resource).get_return();

				//System.out.println(res);
				if(res == null)
					return null;
				
				BodyParser bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false"))
					return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
			
		return "ok";
	}
}
