# Amazon price tracking windows application
a GUI version of my price tracking program

## Purpose
this application continously monitors an amazon product and sends an email to the user if the item hits their desired price. Also shows the user real-time updates

## Getting started
1. on your Google account, allow access to less secure apps in order to send emails to yourself or others. 
[Tutorial here](https://support.google.com/accounts/answer/6010255) 
***make sure you turn it off after exiting the program***
2. insert your Gmail address and password for authentication in *sendEmail()* method of *Monitor* class. Do not share your source code with anyone as it includes your Gmail information
 ```
String gmail = "your@gmail.com"; 
String gmailPass = "password";
```
3. if you want to change how often the program retrieves the product information from Amazon, change the delay on line *161* of *GUI* class. Default is 1 hour
```
timer = new Timer(3600000, new ActionListener() //delay is in milliseconds
```
4. either run *Main.java* or export the project as a .JAR file to start the application
5. input the required information (the Amazon product link, the email you want to receive the updates on, and your tracking price) and start tracking!

## Built With
* [JSoup 1.13.1](https://jsoup.org/download) - for HTML parsing
* [JavaMail 1.6.2](https://javaee.github.io/javamail) - for sending emails
* [JAF](https://docs.oracle.com/javase/8/docs/api/javax/activation/package-summary.html) - supplementary for JavaMail
* [Java Swing](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html) - for building the GUI
* [Java AWT](https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html) - supplementary for Java Swing

## Attribution
* [Icon image from Vecteezy.com](https://www.vecteezy.com/vector-art/554990-shopping-cart-vector-icon) - used under free license
