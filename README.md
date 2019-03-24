# CSV Parser
A Servlet web application with Spring MVC and Tomcat. The web service and client application are used to parse CSVs and return the median value from the CSV grouped by a 'label' field. 

### Endpoint
The application exposes the following endpoint:
```/parseFile```(POST) - accepts the CSV file as a multi-form POST request and parses it to product a JSON response containing median values grouped by labels.

### File Formats
- Input: CSVs adhering to the format from [this](src/main/resources/iris.csv) example.
- Output: JSON file like [this](src/main/resources/iris.csv_medians.json) example.

### Run
- Pull the docker image from docker hub with
`` docker pull registry.hub.docker.com/eshmeister/csv-parser:latest``
- Run the service with
`` docker run -d  -p 8080:8080 <IMAGE_NAME> ``
- Navigate to ``localhost:8080`` on the browser to view the client application.
- Upload a CSV file adhering to the constraints of the application and hit Submit.

That's it! The result of the Median computation is downloaded as a file automatically.

### Run Locally
- Clone the repo https://github.com/emushtaq/csv-parser
- ``cd csv-parser``
- Create a WAR with ``mvnw package``
- Run the application using ``java -jar target/csv-parser-0.0.1-SNAPSHOT.war``
- Navigate to ``localhost:8080`` on the browser to view the client application.
- Upload a CSV file adhering to the constraints of the application and hit Submit.

That's it! The result of the Median computation is downloaded as a file automatically.

### Assumptions
- ';' is the default and only allowed delimiter
- The first row in the CSV is expected to be the header
- A 'label' field is expected in the CSV
- The application does not validate the format of the CSV. It is expected that the user provides a valid CSV file
- When computing the median for text, the value from one of the rows will be displayed

### TODO
- Add more and better test cases
- Validate input for type and format
- Improve response error messaging based on validation

