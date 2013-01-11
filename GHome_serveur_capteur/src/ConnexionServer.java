/**
 * 
 */

/**
 * @author Sébastien
 *
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class ConnexionServer {
	
	private static ServerSocket socketServer;
	private static Socket socketS;
	private static PrintWriter send;
	final static int PORT = 5000;
	
	/**
	 * @param args
	 */
	public static void main(String[] zero) throws IOException {  
		//ServerSocket socketServer;
		//Socket socketS;
		
		try {
		socketServer = new ServerSocket(PORT);
		socketS = socketServer.accept();
		System.out.println("Connection réussie");
		
		send = new PrintWriter(socketS.getOutputStream());
		//send = new PrintWriter(socketS.getOutputStream(), true);
		
		EnvoiTrame();	
				
		
		socketServer.close();
		socketS.close();
		}
		catch (IOException e){
			System.err.println("could not listen on port : 5000");
			System.exit(-1);
		}
	}
	
	public static void EnvoiTrame() {
		send.println("A55A0B05100000000021CC31306E");
		send.println("B66B0B05100000000021CC31306E");
		send.println("STOP");
		//send.write("A55A0B05100000000021CC31306E");
		//System.out.println("Trame envoyée");
		send.flush();
	}

}
