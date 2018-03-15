### **Taxi Booking Service**

### **Package Structure**
1. lib: contains all the libraries and jars necessary for the web service to run
2. src: contains the java source code/classes that enable different functionality of Taxi Booking Service
    1. api: contains all the apis - book, tick and reset
    2. helpers: contains the helper classes used for response/request in APIs
    3. model: contains the main models for the service - Car, Customer, LocationMap
3. SimpleService.java - the start point of the application
4. TaxiBookingService.java - the main class that builds the inventory, has the three REST APIs, its parameters and
    uses the api helper classes (book, tick and reset)
5. tst: contains all the unit tests
6. web: contains the standard web xml file and a server page to bring up the API's response
7. Taxi Booking Service API documentation.pdf - contains the API documentation for Taxi Booking Service with inputs,
outputs and examples

### **How to Run**
1. Bring up the glassfish server
    1. The glassfish open-source library is part of the lib folder
    2. Command to bring up the server: ./lib/glassfish5/glassfish/bin/asadmin start-domain
    3. Command to shutdown the server: ./lib/glassfish5/glassfish/bin/asadmin stop-domain
2. Bring up the TaxiBookingService application
    1. ./lib/glassfish5/glassfish/bin/asadmin deploy ~/out/artifacts/TaxiBookingService_war_exploded
3. Runs at port 8080
4. Use the following curl commands to execute the API calls:

#### Book API:

curl -i -H "Content-Type: application/x-www-form-urlencoded" \
 -X POST "http://localhost:8080/TaxiBookingService_war_exploded/TaxiBookingService/api/book?source=0,1&destination=1,1"

#### Tick API:

curl -i -X POST http://localhost:8080/TaxiBookingService_war_exploded/TaxiBookingService/api/tick

#### Reset API:

curl -i -X POST http://localhost:8080/TaxiBookingService_war_exploded/TaxiBookingService/api/reset