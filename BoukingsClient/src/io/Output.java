package io;

import model.Ticket;
import model.Travel;

public class Output {
	public static void say(String message) {
		System.out.println(message);
	}
	
	public static void newLine() {
		System.out.println();
	}
	
	public static void displaySuccess(String message) {
		System.out.println(message);
	}
	
	public static void displayError(String message) {
		System.out.println(message);
	}
	
	public static void displayLogo() {
		Output.newLine();
		System.out.println("Boukings.com");
		Output.newLine();
	}
	
	public static void ask(String label) {
		System.out.print(label + " : ");
	}

	public static void displayTicket(Ticket t) {
		Output.say("---------------------------------");
		Output.say(t.getDepartureStation() + "\t==>\t" + t.getArrivalStation());
		Output.say(t.getFormattedDepartureDate() + "\t==>\t" + t.getFormattedArrivalDate());
		Output.newLine();
		Output.say(t.getPrice() + "€");
		if(t.isFlexible()) Output.say("Billet flexible");
		Output.say("---------------------------------");
	}

	public static void displayTravel(int i, Travel t) {
		Output.say("-- Choix " + i + " ----------------------");
		Output.say(t.getCompany());
		Output.say(t.getDepartureStation() + "\t==>\t" + t.getArrivalStation());
		Output.say(t.getFormattedDepartureDate() + "\t==>\t" + t.getFormattedArrivalDate());
		Output.newLine();
		Output.say("Supplément flexible : " + t.getFlexibleExtraPrice() + "€");
		Output.say("Première classe : " + t.getFirstClassPrice() + "€\tFlexible : " + t.getFlexibleFirstClassPrice() + "\tPlaces : " + t.getNumberOfTicketsFirst());
		Output.say("Classe business : " + t.getBusinessClassPrice() + "€\tFlexible : " + t.getFlexibleBusinessClassPrice() + "\tPlaces : " + t.getNumberOfTicketsBusiness());
		Output.say("Classe standard : " + t.getStandardClassPrice() + "€\tFlexible : " + t.getFlexibleStandardClassPrice() + "\tPlaces : " + t.getNumberOfTicketsStandard());
		Output.say("-------------------------------");
	}
}
