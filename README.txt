# transfer-money

Transfer money is a Java standalone project with embeded Tomcat, using Maven to build and manager dependencies.

To execute in debug mode run the maven goal: clean install tomcat7:run

To create the executable jar file run the maven goal: clean install tomcat7:exec-war-only

Before running the project make sure you have Java 8 or greater installed.

With executable jar file created go to target folder and run the following command to execute the program: java -jar executable.jar

The URL to access the test page is:
http://localhost:8080/transfermoney/hello.jsp