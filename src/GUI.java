import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.Timer;
import javax.swing.JScrollPane;


public class GUI {

	protected JFrame frame;
	private JLabel lblTitle, lblLicense, lblGithub, lblProductLink,
	lblYourEmail, lblPriceToTrack, lblEmailError, lblPriceError, 
	lblLinkError, lblIcon, lblUpdates;
	private JTextField emailField, priceField, linkField;
	private JButton trackButton, endButton;
	private JScrollPane scrollPane;
	private String userLink, userEmail, oldUpdate, newUpdate;
	private double userPrice;
	private Timer timer;
	private Monitor monitor;
	boolean noInputError = true;
	//set to true as default so that if no input error occurs, we don't have to change its value to true for the program to run
	
	
	//Create the application
	 
	public GUI() {
		program();
	}
	 
	private void program() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 806, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblTitle = new JLabel("AMAZON PRICE TRACKING PROGRAM");
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setBounds(111, 19, 257, 29);
		frame.getContentPane().add(lblTitle);
		
		lblLicense = new JLabel("GNU GPLv3 License");
		lblLicense.setForeground(Color.RED);
		lblLicense.setFont(new Font("Verdana", Font.BOLD, 7));
		lblLicense.setBounds(249, 41, 92, 14);
		frame.getContentPane().add(lblLicense);
		
		lblGithub = new JLabel("Github.com/ArshSB");
		lblGithub.setForeground(Color.RED);
		lblGithub.setFont(new Font("Verdana", Font.BOLD, 7));
		lblGithub.setBounds(111, 41, 98, 14);
		frame.getContentPane().add(lblGithub);
		
		scrollPane = new JScrollPane();          //for the user to be able to see continuous tracking updates 
		scrollPane.setBounds(378, 0, 416, 333);
		frame.getContentPane().add(scrollPane);
		
		lblUpdates = new JLabel("");
		scrollPane.setViewportView(lblUpdates);
		
		lblProductLink = new JLabel("PRODUCT URL (case sensitive)");
		lblProductLink.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblProductLink.setBounds(31, 97, 165, 23);
		frame.getContentPane().add(lblProductLink);
		
		lblYourEmail = new JLabel("YOUR EMAIL (case sensitive)");
		lblYourEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblYourEmail.setBounds(31, 151, 178, 23);
		frame.getContentPane().add(lblYourEmail);
		
		lblPriceToTrack = new JLabel("PRICE TO TRACK ");
		lblPriceToTrack.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblPriceToTrack.setBounds(31, 203, 133, 23);
		frame.getContentPane().add(lblPriceToTrack);
		
		lblEmailError = new JLabel("Invalid email");
		lblEmailError.setForeground(new Color(255, 0, 0));
		lblEmailError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmailError.setVisible(false);  //set all error label visibility off so that they only come on when there is an input error
		lblEmailError.setBounds(31, 275, 289, 23);
		frame.getContentPane().add(lblEmailError);
		
		lblPriceError = new JLabel("Invalid tracking price, please enter a number greater than 0");
		lblPriceError.setForeground(Color.RED);
		lblPriceError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPriceError.setVisible(false);
		lblPriceError.setBounds(31, 301, 289, 23);
		frame.getContentPane().add(lblPriceError);
		
		lblLinkError = new JLabel("Invalid Amazon link");
		lblLinkError.setForeground(Color.RED);
		lblLinkError.setVisible(false);
		lblLinkError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLinkError.setBounds(31, 256, 133, 14);
		frame.getContentPane().add(lblLinkError);
		
		lblIcon = new JLabel("");
		ImageIcon img = new ImageIcon(getClass().getResource("/images/icon.jpg"));
		lblIcon.setIcon(new ImageIcon(img.getImage().getScaledInstance(110, 75, Image.SCALE_SMOOTH))); //resize picture to fit frame nicely
		frame.setIconImage(img.getImage());
		lblIcon.setBounds(0, 11, 110, 75);
		frame.getContentPane().add(lblIcon);
		
		linkField = new JTextField();
		linkField.setBounds(31, 120, 324, 20);
		frame.getContentPane().add(linkField);
		linkField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(31, 172, 324, 20);
		frame.getContentPane().add(emailField);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(31, 225, 324, 20);
		frame.getContentPane().add(priceField);
		
		endButton = new JButton("STOP TRACKING");
		endButton.setOpaque(true);
		endButton.setForeground(Color.WHITE);
		endButton.setFont(new Font("Verdana", Font.BOLD, 11));
		endButton.setBorderPainted(false);
		endButton.setBackground(Color.BLACK);
		endButton.setBounds(378, 332, 416, 36);
		frame.getContentPane().add(endButton);
		
		endButton.addActionListener(new ActionListener() {   //stop track updates by ending the timer and turn on text fields for possible new input 
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				priceField.setEditable(true);
				linkField.setEditable(true);
				emailField.setEditable(true);
			}
		});
		
		trackButton = new JButton("TRACK");
		trackButton.setForeground(Color.WHITE);
		trackButton.setBackground(Color.RED);
		trackButton.setOpaque(true);
		trackButton.setBorderPainted(false);
		trackButton.setFont(new Font("Verdana", Font.BOLD, 11));
		trackButton.setBounds(0, 332, 379, 36);
		frame.getContentPane().add(trackButton);
	
		
		timer = new Timer(3600000, new ActionListener() {   //timer continuously runs the tracking updates every 1 hour until either an email is sent,
			public void actionPerformed(ActionEvent e) {	//an error occurs or "stop tracking" button is enabled
				newUpdate = monitor.track();
				oldUpdate = "<html>" + lblUpdates.getText() + "<br><br>";  //using HTML <br> in place of \n 
				lblUpdates.setText(oldUpdate + newUpdate + "<html>");  
				//adding previous update texts to the new one so that scroll panel can show all updates instead of only the new update each time
				
				if (monitor.getCheckEmailSentOrError() == true) //if email is sent or an error occurs, stop the program as if the endButton is clicked
					endButton.doClick();
			}
		});
		

		trackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Reset error labels and boolean every time track button is enabled 
				//so that if the user fixes an input error, its respective error label doesn't stay visible
				
				noInputError = true;  
				lblEmailError.setVisible(false);
				lblPriceError.setVisible(false);
				lblLinkError.setVisible(false);
				
				try {
					userPrice = Double.parseDouble(priceField.getText().replaceAll("\\s", "")); 
					//replace whitespaces so error isn't thrown because of it
					
					if ( (userPrice <= 0)) { 
						lblPriceError.setVisible(true); 
						noInputError = false; 
						//assuming the user enters a number but not
						//set to false so that the program doesn't start tracking until every user input is correct
					}
					
				} catch(NumberFormatException exc) { lblPriceError.setText("Please enter a valid price number");  lblPriceError.setVisible(true); noInputError = false; }
				  //if the user did not enter a number, exception is thrown from line 186 to notify them 
				
				
				userLink = linkField.getText().replaceAll("\\s", "");
				userEmail = emailField.getText().replaceAll("\\s", "");
				lblUpdates.setText("");
				
				if ( !userLink.contains("amazon.") ) { //check if an amazon URL is provided
					lblLinkError.setVisible(true);
					noInputError = false;
				}
				
				if ( (!userEmail.contains("@")) || (!userEmail.contains(".")) ) { //check if a valid email is provided
					lblEmailError.setVisible(true);
					noInputError = false;
				}
					
				
				if (noInputError == true) {
					monitor = new Monitor(userPrice, userLink, userEmail); 
					//create (or override) monitor object every time track button is enabled and no invalid user inputs 
					
					priceField.setEditable(false); 
					linkField.setEditable(false); // turn off text fields while tracking is underway
					emailField.setEditable(false);
					
					timer.setInitialDelay(0);     
					//since the timer only runs after waiting 1 hour, we don't want the user to wait that period of time when they press "track" button
					//hence the initial delay is set at 0 so that the program can run its first task immediately and then run every 1 hour
					//or however long you have the delay set up as
					
					timer.start();
					//start tracking
				}

			}
		});		
	}
}
