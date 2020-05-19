import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.activation.*;


public class Monitor {
	private double price, priceTrack, priceDifference;
	private String email, title, urlShort;
	//price is the current price of the product, priceTrack is the price the user wants
	//title is short description of the product
	//urlShort is the shortened version of the product url with user parameters/references removed
	
	public Monitor(double priceTrack, String link, String email) {
		setPriceTrack(priceTrack);
		urlShort = urlShortener(link);
		setEmail(email);
		track();
	}
	
	public double getPrice() { return price; } //no setter for price as that is independent of the program
	
	public double getPriceTrack() { return priceTrack; }
	public void setPriceTrack(double priceTrack) { this.priceTrack = priceTrack; }
	
	public String getUrlShort() { return urlShort; }
	public void setUrlShort(String url) { urlShort = urlShortener(url); }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String status() { 
		return String.format("Tracking '%s' at CAD %.2f for %s\nCurrent price is CAD %.2f\nLink: %s", title, priceTrack, email, price, urlShort);
	}
	
	public void track() {
		try {
			Document d = Jsoup.connect(urlShort).get(); //connect to the product page and get its entire HTML text
			Element prodPrice = d.getElementById("priceblock_ourprice"); //find the product's price using the HTML ID associated with it
			title = d.getElementById("productTitle").text(); 
			price = stringToDouble(prodPrice.text());  
			if (price <= priceTrack) {
				priceDifference = Math.abs(price - priceTrack);
				sendEmail();
				Date date = new Date();
				System.out.printf("\nEmail sent to %s at $%.2f", email, price);
				System.out.println("\nDate: " + date + "\n");
			}
		
		} 
		catch (MessagingException e) { System.out.println(e.toString()); }
		catch(MalformedURLException e) { System.out.println(e.toString()); }
		catch (NumberFormatException e) { System.out.println(e.toString()); }
		catch (IOException e) { System.out.println(e.toString()); }
	}
	
	private double stringToDouble(String str) throws NumberFormatException { //convert the price string to a double 
		String temp = "";
		
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if ((Character.isDigit(ch)) || (ch == '.')) 
					temp += ch;
		}
		
		return Double.parseDouble(temp);
	}
	
	private void sendEmail() throws MessagingException{ 
		Properties prop = new Properties(); 
		//courtesy of stackoverflow.com/questions/32284898/sending-email-using-java-authentication-error
		
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587"); //keep 587 as port if using gmail
		
		String gmail = "your@gmail.com"; 
		String gmailPass = "password"; //your gmail password here for authentication
		
		Session session = Session.getInstance(prop, new Authenticator() { //make sure your google account has 'less secure app access" allowed
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmail, gmailPass);
				}
			}
		);
		
		Message msg = new MimeMessage(session);
			
		msg.setFrom(new InternetAddress("your@gmail.com")); 
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); //email you would like to send the price alert to (could be yourself or someone else)
		msg.setSubject("Price drop detected!");
		msg.setText(String.format("'%s'\nis at or below your desired price of $%.2f \n\nPrice difference: $%1.2f\n\nLink: %s", title, priceTrack, priceDifference, urlShort));
		
		Transport.send(msg); //may throw exception
		
	}
	
	private String urlShortener(String url) {
		String urlShort = "";
		int count = 0; int i = 0;
		
		//count represents number of "/" in the amazon url, after 6 slashes are the user parameters
		//so the url until the 6th slash is copied
		
		while (!(count == 6)) {  
			char ch = url.charAt(i);
			if (ch == '/')
				count++;
			i++;
			urlShort += ch;
		}
		return urlShort;
	}

}
