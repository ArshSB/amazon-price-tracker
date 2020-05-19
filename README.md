# amazon-price-tracker
a simple program that monitors a product's price on amazon and sends an email to the user if the product hits the desired price

### Prerequisites
1. on your Google account, allow access to less secure apps in order to send emails to yourself or others. [Tutorial here](https://support.google.com/accounts/answer/6010255) 
***make sure you turn it off after exiting the program***
2. input your Gmail address and password for authentication in *sendEmail()* method of *Monitor* class
 ```
String gmail = "your@gmail.com"; 
String gmailPass = "password";
```
3. now run *Main.java*, input the necessary information and start tracking!
### Built With
* [JSoup](https://jsoup.org/download) - used for HTML parsing
* [JavaMail](https://javaee.github.io/javamail) - used for sending emails
* [JAF](https://docs.oracle.com/javase/8/docs/api/javax/activation/package-summary.html) - supplementary to JavaMail
