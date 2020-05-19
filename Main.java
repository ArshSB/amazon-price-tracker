import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		try (Scanner in = new Scanner(System.in)) {
			
			System.out.println("[Amazon price monitoring program]\n");
			
			System.out.println("Please enter the Amazon URL of the product you want to monitor: ");
			String link = in.next();
			
			System.out.println("What price would you like to track? ");
			double priceTrack = in.nextDouble();
			while(priceTrack <= 0) {  //check for a valid tracking price and keep asking until a valid value is received
				System.out.println("Please enter a value greater than 0: ");
				priceTrack = in.nextDouble();
			}
			
			System.out.println("Please enter the email you would like to receive updates to: ");
			String email = in.next();
			while(true) { 
				if ( (email.contains("@")) && (email.contains(".")) )
					break;
				System.out.println("Invalid email format, please try again: ");
				email = in.next();
			}
			
			Monitor monitor = new Monitor(priceTrack, link, email);
			while (true) { //never-ending loop to monitor prices continuously 
				System.out.println("\n" + monitor.status());
				System.out.println("\n*Monitoring*\n");
				Thread.sleep(3600000); //pause program for 1 hour, meaning price check is performed once every hour	
				monitor.track();
			}	
		} 
		
		
		catch(InputMismatchException e) { System.out.println(e.toString()); }
		catch(InterruptedException e) { System.out.println(e.toString()); }
		catch(Exception e) { System.out.println(e.toString()); }
		
		finally { System.out.println("\n*Program stopped*"); }
	}

}
