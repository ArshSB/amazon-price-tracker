# Amazon price tracker
a simple java price tracking program

## Purpose
this program continously monitors an amazon product and sends an email to the user if the item hits their desired price plus a few other neat additions

## Getting started
1. on your Google account, allow access to less secure apps in order to send emails to yourself or others. [Tutorial here](https://support.google.com/accounts/answer/6010255) 
***make sure you turn it off after exiting the program***
2. insert your Gmail address and password for authentication in *sendEmail()* method of *Monitor* class
 ```
String gmail = "your@gmail.com"; 
String gmailPass = "password";
```
3. now run *Main.java*, input the necessary information and start tracking!

## Built With
* [JSoup 1.13.1](https://jsoup.org/download) - for HTML parsing
* [JavaMail 1.6.2](https://javaee.github.io/javamail) - for sending emails
* [JAF](https://docs.oracle.com/javase/8/docs/api/javax/activation/package-summary.html) - supplementary for JavaMail
