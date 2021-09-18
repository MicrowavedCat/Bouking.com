package controller;

import java.rmi.RemoteException;

import model.BodyParser;
import tps.ws.deployment.UsersStub;
import tps.ws.deployment.UsersStub.Connect;
import tps.ws.deployment.UsersStub.User;
import tps.ws.deployment.UsersStub.UserTickets;

public class Account {
	private String mail;
	private String accessToken;
	
	public String getMail() {
		return this.mail;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public String connect(String mail, String password) {		
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
		
		if(bp.get("success") == null || bp.get("success").equals("false"))
			return null;
		
		this.mail = mail;
		this.accessToken = bp.get("reason");
		return this.accessToken;
	}
	
	public String getUserInfo() {		
		String res = null;
		try {
			UsersStub stub = new UsersStub();
			User resource = new User();

			resource.setAccessToken(this.accessToken);
			resource.setMail(this.mail);
			
			res = stub.user(resource).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

		if(res == null)
			return null;
		
		return res;
	}
	
	public String getUserTickets() {		
		String res = null;
		try {
			UsersStub stub = new UsersStub();
			UserTickets resource = new UserTickets();

			resource.setAccessToken(this.accessToken);
			resource.setMail(this.mail);
			
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