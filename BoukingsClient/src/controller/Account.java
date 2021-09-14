package controller;

import java.rmi.RemoteException;

import model.BodyParser;
import tps.ws.deployment.UsersStub;
import tps.ws.deployment.UsersStub.Connect;
import tps.ws.deployment.UsersStub.User;
import tps.ws.deployment.UsersStub.UserTickets;

public class Account {
	public static String connect(String mail, String password) {		
		String res = null;
		try {
			UsersStub stub = new UsersStub();
			Connect resource = new Connect();
			
			resource.setMail(mail);
			resource.setPassword(password);
			
			res = stub.connect(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
		if(res == null)
			return null;
		
		BodyParser bp = new BodyParser(res);
		bp.next();
		
		return bp.get("token");
	}
	
	public static String getUserInfo(String mail) {		
		String res = null;
		try {
			UsersStub stub = new UsersStub();
			User resource = new User();
			
			resource.setMail(mail);
			
			res = stub.user(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		if(res == null)
			return null;
		
		return res;
	}
	
	public static String getUserTickets(String mail) {		
		String res = null;
		try {
			UsersStub stub = new UsersStub();
			UserTickets resource = new UserTickets();
			
			resource.setMail(mail);
			
			res = stub.userTickets(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		if(res == null)
			return null;
		
		return res;
	}
}