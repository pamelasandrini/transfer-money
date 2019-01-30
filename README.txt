# transfer-money

Transfer money is a Java standalone project that uses embedded Tomcat, H2 db in memory and Maven.

Before running the project make sure you have Java 8 or greater installed.

To execute in debug mode run the maven goal: clean install tomcat7:run

To create a executable jar file run the maven goal: clean install tomcat7:exec-war-only

After the executable jar file creation go to target folder and run the following command to execute it: java -jar executable.jar

Account services urls:

get
http://localhost:8080/transfermoney/account/{accountNo}

get
http://localhost:8080/transfermoney/account/{accountNo}/balance

get
http://localhost:8080/transfermoney/account/all

put
http://localhost:8080/transfermoney/account/create
json param: {
"customerName": "name",
    	"balance": 0.0}


delete
http://localhost:8080/transfermoney/account/{accountNo}

Transfer money services urls:

get
http://localhost:8080/transfermoney/transfer/all

post
http://localhost:8080/transfermoney/transfer
json param: {
"accountFrom": 0,
 "accountTo": 1,
"value" : 450.90
}