import org.jsoup.*;
import org.jsoup.nodes.*;
import java.net.MalformedURLException;
import java.util.*;
import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.activation.*;


public class Monitor {
	private double price, priceTrack, priceDifference;
	private String email, title, url;
	private boolean checkEmailSentOrError = false;
	//checkEmailSentOrError is to help stop program if an email is sent or an error occurs, it is false by default as no email is sent when the program starts
	//price is the current price of the product, priceTrack is the price the user wants
	//title is short description of the product
	
	
	public Monitor(double priceTrack, String link, String email) {
		setPriceTrack(priceTrack);
		setUrl(link);
		setEmail(email);
	}
	
	public double getPrice() { return price; } //no setter for price as that is independent of the program
	
	public double getPriceTrack() { return priceTrack; }
	public void setPriceTrack(double priceTrack) { this.priceTrack = priceTrack; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public boolean getCheckEmailSentOrError() { return checkEmailSentOrError; }
	public void setCheckEmailSentOrError(boolean b) { checkEmailSentOrError = b; }
	
	public String status() { 
		return String.format("<html>Tracking '%s' <br> Current price is CAD %.2f <br> Date: %s <html>", title, price, new Date()); //HTML <br> used in place of \n 
	}
	
	public String track() {
		try {
			Document d = Jsoup.connect(url).get(); //connect to the product page and get its entire HTML text
			Element prodPrice = d.getElementById("priceblock_ourprice"); //find the product's price using the HTML ID associated with it
			title = d.getElementById("productTitle").text(); 
			price = stringToDouble(prodPrice.text());  
			if (price <= priceTrack) {
				priceDifference = Math.abs(price - priceTrack);
				sendEmail();
				setCheckEmailSentOrError(true);  //set to true if email is sent so the program can be stopped
				return String.format("<html>Match found! Email sent to %s at $%.2f<br>Date: %s<br><br>Program stopped<html>", email, price, new Date());
			} else
				return status();
		
		} 
		catch (MessagingException e) { setCheckEmailSentOrError(true); e.printStackTrace(); return ("Program aborted. Messaging Error"); } //set to true if error occurs so program is stopped successfully
		catch(MalformedURLException e) { setCheckEmailSentOrError(true); return ("Program aborted. Malformed URL Error, please check your link"); }
		catch (NumberFormatException e) { setCheckEmailSentOrError(true); return ("Program aborted. Internal Error"); }
		catch (HttpStatusException e) { setCheckEmailSentOrError(true); return ("Program aborted. HTTP 404 error, please check your link"); }
		catch (Exception e) { setCheckEmailSentOrError(true); e.printStackTrace(); return ("Program aborted. Unknown Error"); }
	}
	
	private double stringToDouble(String str) throws NumberFormatException { //convert the price string to a double 
		String temporary = "";
		
		for (int i = 0; i < str.length(); i++) {        //check every character in string and only add digits and decimal point to temporary 
			char ch = str.charAt(i);
			if ((Character.isDigit(ch)) || (ch == '.')) 
					temporary += ch;
		}
		
		return Double.parseDouble(temporary);
	}
	
	private void sendEmail() throws MessagingException{ 
		Properties prop = new Properties(); 
		
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587"); //keep 587 as port if using GMail
		
		String gmail = "your@gmail.com"; 
		String gmailPass = "password"; //your GMail password here for authentication
		
		Session session = Session.getInstance(prop, new Authenticator() { //make sure your google account has 'less secure app access" allowed
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmail, gmailPass);
				}
			}
		);
		
		Message msg = new MimeMessage(session);
			
		msg.setFrom(new InternetAddress(gmail)); 
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); //email you would like to send the price alert to (could be yourself or someone else)
		msg.setSubject("Price drop detected!");
		msg.setText(String.format("'%s'\nis at or below your desired price of $%.2f \n\nPrice difference: $%1.2f\n\nLink: %s", title, priceTrack, priceDifference, url));
		
		Transport.send(msg); //may throw exception
		
	}

}
