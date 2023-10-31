import java.net.*;
import java.io.*;

class Server {
  private Socket socket = null;
  private ServerSocket server = null;

  public Server(int port, int backlog, String IP) {
    
    
    //try + catch for server
    try {
      //ServerSocket serverSocket = new ServerSocket(port number, backlog, IP address);
      server = new ServerSocket(port, backlog, InetAddress.getByName(IP));

      server.setSoTimeout(0); // 0 means no timeout

      System.out.println("Server started.");

      socket = server.accept();

      System.out.println("Connection made!");
    

      //try + catch for the input - reads from socket
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
           String input;

          while ((input = reader.readLine()) != null) {
             if (input.equals("over")) {
               break;
              }
           System.out.println(input);
          }

        } catch (Exception e) {
          System.out.println(e);
        }



     

        //try + catch for socket
        try {
          if (socket != null) {
             socket.close(); }
          } catch (Exception e) {
            System.out.println(e);
          }


      }  catch (Exception e) {
       System.out.println(e);
      }
  }
  
 
  


  public static void main(String args[]) {
    Server server = new Server(5000, 0, "127.0.0.1"); //change port and IP numbers when actually testing
  }
}