import static spark.Spark.*;

public class DataRequestReciever {

    public static void main(String[] args) {
        // Define the port for your server
        int port = 4567;
        port(port);
        // Define an endpoint to receive data via POST request
        post("/receiveData", "application/json", (request, response) -> {
        // Handle the received data
            String data = request.body();
            System.out.println("Received request: " + data);
            // You can process the data further or save it to a database
            // processing and sending response back
            if (data.contains("Give_Location")){
                return "The nearby bins are at ";
            }else {
                return "We do not take this kind of request";
            }
        });
    }
}
