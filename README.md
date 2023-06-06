Github Repository Checker

The Github Repository Checker is a service that allows you to retrieve information about a user's repositories from the GitHub API.

API Endpoints

The API provides the following endpoint:

Retrieve User Repositories

    URL: /api/repositories/{username}
    Method: GET
    Headers:
        Accept: Specify the media type of the response (supported value: application/json)
    Parameters:
        username (path variable): The GitHub username for which to retrieve the repositories.
    Response:
        If successful, the API will return a JSON response containing information about the user's repositories.
        If the user is not found, a JSON response with an appropriate error message will be returned.
        If the Accept header is not set to application/json, a JSON response with an error message indicating an unsupported media type will be returned.

How It Works

    The user makes a GET request to the /api/repositories/{username} endpoint, providing the GitHub username and the Accept header.
    The request is handled by the GithubRepositoryController class, which is responsible for calling the appropriate service method.
    The fetchDataFromApi method in the GithubRepositoryService class is invoked.
    The service checks if the Accept header is set to application/json. If not, it returns a response with an unsupported media type error.
    If the Accept header is valid, the service constructs the URL for the GitHub API endpoint to retrieve the user's repositories.
    The service makes an HTTP GET request to the GitHub API using the RestTemplate class.
    If the user is not found, a response with a user not found error is returned.
    If the user is found, the service retrieves the repositories from the response.
    For each non-fork repository, the service retrieves the branches associated with the repository by making another API request.
    The retrieved branches are set on the respective repository objects.
    Finally, the service returns a response with the user's repositories, including the associated branches (if any).

GlobalExceptionHandler.java

    This class is a Spring ControllerAdvice that handles exceptions thrown during request processing. It provides exception handling methods for HttpClientErrorException.NotFound and HttpMediaTypeNotAcceptableException. These methods create JSON error responses with appropriate status codes and messages.



To use the project, you can follow these steps:

    Make sure you have Java 17 and Maven installed on your system.
    Clone the project repository.
    Build the project using Maven: mvn clean install.
    Run the project using an appropriate Java runtime environment.
