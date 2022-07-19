import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        //start receiving messages
        try(ServerSocket serverSocket = new ServerSocket(8080)){
            System.out.println("server started.");
            while (true) {
                //handle a new incoming message
                try (Socket client = serverSocket.accept()) {
                    System.out.println("debug message " + client.toString());
                    System.out.println("\n");

                    //read the request
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    //get each line of the message from the stream
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder request = new StringBuilder();
                    //once we read off the buffered reader,
                    // we will need a place to store each line
                    String line;
                    line = br.readLine(); // reads a single line each time
                    while (!line.isBlank()){ // if that line is not blank
                        request.append(line + "\r\n"); // stick it in our request
                        line = br.readLine();
                    }
                    System.out.println("--request--");
                    System.out.println(request + "\n");
                    //decide how we respond

                    FileInputStream image = new FileInputStream("src/selfie.png");
                    System.out.println(image.toString());

                    //send back a message
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes(StandardCharsets.UTF_8));
                    clientOutput.write(("\n").getBytes(StandardCharsets.UTF_8));
                    clientOutput.write(image.readAllBytes());
                    clientOutput.flush();
                    //send image ?

                    //change response on route ?

                    //send a response

                    //get ready for next message

                    client.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
