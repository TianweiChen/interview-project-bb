# interview-project-bb

To run the API, TrainingSearchControllerSpec is a spring-test mocMVC test running against the training search controller with the navigation.json; There are a couple of unit tests in SearchServiceImplSpec with mock data to test the main search logic; There are also some mocMVC tests for the exception handling; and loading json data to pojo; The dependency jars are managed by gradle; The web app is based on springframework; Development environment is Intellij IDEA 13.1.4; Server Tomcat 7.0.55.

Architectural Design key points:
- The proejct is architectured in layers: controller and service;
- The navigation data is loaded during startup of the web app, and stored in a context object. The app is initialized with Java Config; Upon arrival of each request, the data does not need to be loaded from DAO;
- The search logic is at the service layer. To improvement performance, an cache can be added at the service layer with ID as key, and search result as value;
- Controller advice and exception handler are used for error handling;
- Tests include mocMVC as integeration test; and spock unit tests.
