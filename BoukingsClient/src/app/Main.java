package app;

import controller.Account;
import controller.Bouking;
import io.Input;
import io.Output;
import model.BodyParser;
import model.Ticket;
import model.Travel;
import model.UserInfo;
import utils.DateConvert;

public class Main {
	private static BuyReturnMessage buyTicket() {
		BodyParser bp;
		String res;
		String choice;
		String gd = "";
		String ga = "";
		long dd = -1;
		long da = -1;
		int nbpc = -1;
		int nbbc = -1;
		int nbsc = -1;
		
		while(true) {
			Output.say("Affinez votre recherche");
			Output.say("gareDepart <gare>" + (gd.equals("") ? "" : " (" + gd + ")"));
			Output.say("gareArrivee <gare>" + (ga.equals("") ? "" : " (" + ga + ")"));
			Output.say("dateDepart" + (dd < 0 ? "" : " (" + DateConvert.toString(dd) + ")"));
			Output.say("dateArrivee" + (da < 0 ? "" : " (" + DateConvert.toString(da) + ")"));
			Output.say("nbPlacesPremiereClasse <nombre>" + (nbpc < 0 ? "" : " (" + nbpc + ")"));
			Output.say("nbPlacesClasseBusiness <nombre>" + (nbbc < 0 ? "" : " (" + nbbc + ")"));
			Output.say("nbPlacesClasseStandard <nombre>" + (nbsc < 0 ? "" : " (" + nbsc + ")"));
			Output.say("ok : lancer la recherche");
			Output.ask("Que souhaitez-vous faire ?");
			
			choice = Input.readString();
			int index = choice.indexOf(' ');

			if(choice.startsWith("ok"))
				break;
			
			if(choice.startsWith("dateDepart")) {
				dd = Input.askDate();
				continue;
			}

			if(choice.startsWith("dateArrivee")) {
				da = Input.askDate();
				continue;
			}
			
			if(index < 0 || index == choice.length() - 1)
				continue;
			
			String action = choice.substring(0, index);
			String value = choice.substring(index + 1);
			
			switch(action) {
				case "gareDepart":
					gd = value;
					break;
				case "gareArrivee":
					ga = value;
					break;
				case "dateDepart":
					dd = Input.askDate();
					break;
				case "dateArrivee":
					da = Input.askDate();
					break;
				case "nbPlacesPremiereClasse":
					nbpc = (value.equals("") ? -1 : Integer.parseInt(value));
					break;
				case "nbPlacesClasseBusiness":
					nbbc = (value.equals("") ? -1 : Integer.parseInt(value));
					break;
				case "nbPlacesClasseStandard":
					nbsc = (value.equals("") ? -1 : Integer.parseInt(value));
					break;
				default:
					continue;
			}
		}

		//System.out.println(dd+" / "+da);
		res = Bouking.getTravels(gd, ga, dd, da, nbpc, nbbc, nbsc);
		
		if(res == null) {
			Output.displayError("Impossible de communiquer avec les services de Boukings.com");
			System.exit(1);
		}
		
		bp = new BodyParser(res);
		bp.next();
		
		if(bp.get("success") != null && bp.get("success").equals("false")) {
			Output.displayError("Impossible de poursuivre : " + bp.get("reason"));
			return null;
		}
		
		Travel[] travels = new Travel[bp.count()];
		for(int i = 0; i < travels.length; i++, bp.next())
			travels[i] = new Travel(bp);
		
		Output.say("Train disponibles :");
		if(travels.length > 0) {
			for(int i = 0; i < travels.length; i++) {
				Output.displayTravel(i + 1, travels[i]);
				Output.newLine();
			}
			
			Output.newLine();
			Output.ask("Quel ticket voulez-vous acheter ?");
			choice = Input.readString();
			
			if(choice.equals(""))
				return null;
			
			int c = Integer.parseInt(choice) - 1;
			
			if(c < 0 || c >= travels.length)
				return null;
			
			return new Main.BuyReturnMessage(travels[c].getWebService(), travels[c].getTrainID());
		} else {
			Output.say("Aucun train trouvé...");
			//Wait
			Output.say("Appuyez sur ENTREE pour revenir au menu principal");
			Input.readString();
			return null;
		}
	}
	
	private static int[] askNumberOfTickets() {
		int[] numbers = new int[6];
		
		Output.ask("Acheter combien de ticket première classe ?");
		String res = Input.readString();
		if(res.equals(""))
			return null;
		
		numbers[0] = Integer.parseInt(res);
		if(numbers[0] < 0)
			return null;
		
		if(numbers[0] > 0) {
			Output.ask("Prendre combien de ticket première classe flexibles ?");
			res = Input.readString();
			if(res.equals(""))
				return null;
			
			numbers[1] = Integer.parseInt(res);
			if(numbers[1] < 0 || numbers[1] > numbers[0])
				return null;
		} else
			numbers[1] = 0;

		Output.ask("Acheter combien de ticket classe business ?");
		res = Input.readString();
		if(res.equals(""))
			return null;
		
		numbers[2] = Integer.parseInt(res);
		if(numbers[2] < 0)
			return null;
		
		if(numbers[2] > 0) {
			Output.ask("Prendre combien de ticket classe business flexibles ?");
			res = Input.readString();
			if(res.equals(""))
				return null;
			
			numbers[3] = Integer.parseInt(res);
			if(numbers[3] < 0 || numbers[3] > numbers[2])
				return null;
		} else
			numbers[3] = 0;

		Output.ask("Acheter combien de ticket classe standard ?");
		res = Input.readString();
		if(res.equals(""))
			return null;
		
		numbers[4] = Integer.parseInt(res);
		if(numbers[4] < 0)
			return null;
		
		if(numbers[4] > 0) {
			Output.ask("Prendre combien de ticket classe standard flexibles ?");
			res = Input.readString();
			if(res.equals(""))
				return null;
			
			numbers[5] = Integer.parseInt(res);
			if(numbers[5] < 0 || numbers[5] > numbers[4])
				return null;
		} else
			numbers[5] = 0;
		
		return numbers;
	}
	
	public static void main(String[] args) {
		String res = null;
		String mail = null;
		BodyParser bp;
		
		Output.displayLogo();
		Output.say("Connectez-vous !");
		
		while(true) {
			Output.ask("Addresse E-mail");
			mail = Input.readString();
			Output.ask("Mot de passe");
			res = Account.connect(mail, Input.readString());
			
			if(res != null)
				break;
			
			Output.displayError("La connexion a échouée");
		}
		
		res = Account.getUserInfo(mail);
		
		if(res == null) {
			Output.displayError("Impossible de communiquer avec les services de Boukings.com");
			System.exit(1);
		}
		
		bp = new BodyParser(res);
		bp.next();
		
		if(bp.get("success") != null && bp.get("success").equals("false")) {
			Output.displayError("Impossible de poursuivre :" + bp.get("reason"));
			System.exit(1);
		}
			
		UserInfo user = new UserInfo(bp);
		
		while(true) {
			Output.displayLogo();
			Output.say("Bienvenue " + user.getFirstName() + " " + user.getLastName() + " !");
			Output.newLine();
			Output.say("1 : Acheter un billet de train");
			Output.say("2 : Consulter mes achats");
			Output.ask("Que souhaitez-vous faire ?");
			String choice = Input.readString();

			if(choice.equals("1")) {
				Main.BuyReturnMessage brm = Main.buyTicket();
				
				if(brm == null)
					continue;
				
				int[] outbound = Main.askNumberOfTickets();
				
				if(outbound == null)
					continue;

				Output.newLine();
				Output.say("Voulez-vous acheter un billet retour ?");
				Output.say("oui/non");
				Output.ask("Prendre un billet retour ?");
				
				if(!Input.readString().equals("oui")) {
					if(Bouking.buy(brm.getWebService(), brm.getTrainID(), outbound[0], outbound[1], outbound[2], outbound[3], outbound[4], outbound[5], mail) == null) {
						Output.displayError("Impossible de communiquer avec les services de Boukings.com");
						System.exit(1);
					}
					
					Output.say("Billet aller simple acheté !");
					//Wait
					Output.say("Appuyez sur ENTREE pour revenir au menu principal");
					Input.readString();
					continue;
				}
				
				Main.BuyReturnMessage brm2 = Main.buyTicket();
				
				if(brm2 == null)
					continue;
				
				int[] returning = Main.askNumberOfTickets();
				
				if(returning == null)
					continue;
				
				if(Bouking.buy(brm.getWebService(), brm.getTrainID(), outbound[0], outbound[1], outbound[2], outbound[3], outbound[4], outbound[5], mail) == null || Bouking.buy(brm2.getWebService(), brm2.getTrainID(), returning[0], returning[1], returning[2], returning[3], returning[4], returning[5], mail) == null) {
					Output.displayError("Impossible de communiquer avec les services de Boukings.com");
					System.exit(1);
				}

				Output.say("Billets aller et retour achetés !");
				//Wait
				Output.say("Appuyez sur ENTREE pour revenir au menu principal");
				Input.readString();
			} else if(choice.equals("2")) {
				res = Account.getUserTickets(mail);
				
				if(res == null) {
					Output.displayError("Impossible de communiquer avec les services de Boukings.com");
					System.exit(1);
				}
				
				bp = new BodyParser(res);
				bp.next();
				
				if(bp.get("success") != null && bp.get("success").equals("false")) {
					Output.displayError("Impossible de poursuivre : " + bp.get("reason"));
					continue;
				}
				
				Ticket[] tickets = new Ticket[bp.count()];
				for(int i = 0; i < tickets.length; i++, bp.next())
					tickets[i] = new Ticket(bp);
				
				Output.say("Vos achats :");
				if(tickets.length > 0) {
					for(Ticket t : tickets) {
						Output.displayTicket(t);
						Output.newLine();
					}
				} else
					Output.say("Vous n'avez acheté aucun ticket...");
				
				//Wait
				Output.say("Appuyez sur ENTREE pour revenir au menu principal");
				Input.readString();
			}
		}
	}
	
	private static class BuyReturnMessage {
		private String webService;
		private int trainID;
		
		public BuyReturnMessage(String webService, int trainID) {
			this.webService = webService;
			this.trainID = trainID;
		}
		
		public String getWebService() {
			return this.webService;
		}
		
		public int getTrainID() {
			return this.trainID;
		}
	}
}	
