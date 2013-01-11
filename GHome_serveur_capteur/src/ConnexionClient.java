/**
 * 
 */

/**
 * @author Sébastien
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ConnexionClient {
	
	
	private static Socket socket;
	private static BufferedReader received;
	final static int PORT = 5000;
	final static String TRAME = "A55A";

	/**
	 * @param args
	 */
	public static void main(String[] zero) throws UnknownHostException, IOException {
		
		try{
			
			socket = new Socket(InetAddress.getLocalHost(),PORT);	
			System.out.println("Demande de connexion");
			
			received = new BufferedReader(new InputStreamReader (socket.getInputStream()));
			String idTrame;	
			
			do{ 
			String msg = received.readLine();	
			System.out.println(received.ready());
			System.out.println(msg);
			//String idTrame = msg.substring(0,4);
			idTrame = msg.substring(0,4);
			idTrame = msg.substring(0,4);
			System.out.println(idTrame);
			
			if (idTrame.compareTo(TRAME) == 0){
				System.out.println("La trame reçue est celle de notre capteur");
			}
			else{
				System.out.println("La trame reçue n'est pas celle de notre capteur");
			}
			} while(idTrame.compareTo("STOP") != 0);
			
			
			socket.close();
		}
		catch (IOException e){
			System.err.println("could not connect on port : 5000");
			System.exit(-1);
		}
		
		
		

	}

}
