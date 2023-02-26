# Points Reward API
This API is based on reward program which calculate points against each purchase 
of customer.
## Getting Started
Clone the repository and follow the instructions below.
## To run the service directly
Adjust the data source properties if needed in application-default.properties.

### For Windows
```
install Java 17
https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi
---------------------
install maven
https://maven.apache.org/download.cgi
----------------------------
mvn clean spring-boot:run
```
### For Api specification use this endpoint
```
http://localhost:8090/swagger-ui/index.html
```
### For Application Health use this endpoint
```
http://localhost:8090/actuator/
```