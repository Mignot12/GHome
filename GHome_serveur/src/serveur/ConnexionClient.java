package serveur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import serveur.Log.logType;

public class ConnexionClient {

	private static PrintWriter send_out;
	private static Socket socket;
	private Log log;
	private Fenetre fenetre;
	
	
	
		public ConnexionClient(Log alog, Fenetre afentre){
			log=alog;
			fenetre=afentre;
		
			//socket = new Socket("134.214.105.28",5000);	
			//send_out = new PrintWriter(socket.getOutputStream(), true);

			ServerSocket socket;
			try {				
				socket = new ServerSocket(2009);
				Thread threadAcceptClient = new Thread(new AccepterClients(socket, log));
				threadAcceptClient.start();
				System.out.println("En attente de clients");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		public static void ActionneurON() {
			
			send_out.println("A55A3B050B0100FFF9F1E043088");
		  
			  //   SYNC H_SEQ   length    RPS    ON     mode learn       id          status    checksum
			  //  A55A  3       B         05    0B010    0F            FF9F1E04       30         88
			
			//H_SEQ=3 = transmit radio telegram
		  
			send_out.flush();
		}
		
		public static void ActionneurOFF() {
			
			send_out.println("A55A3B050B0110FFF9F1E043088");
			  
			   //   SYNC H_SEQ   length    RPS    OFF     mode learn       id          status    checksum
			   //  A55A  3       B         05    0B010    0F            FF9F1E04       30          88
				
			//H_SEQ=3 = transmit radio telegram
		
			send_out.flush();
		}
		
		public static void closeSocket() {
			try {
			        socket.close();
			}catch (IOException e) {

				e.printStackTrace();
			}
	}

}

/**
 * 
 * @author Andreas
 * Classe qui boucle et accepte les connexions clients
 *
 */
class AccepterClients implements Runnable {

	   private ServerSocket socketserver;
	   private Socket socket;
	   private int nbrclient = 1;
	   private Log log;
	   private ArrayList<Thread> threadsLectureClient;

	   public AccepterClients(ServerSocket s, Log alog){
			socketserver = s;
			log=alog;
			threadsLectureClient=new ArrayList<Thread>();
		}
		
		public void run() {
	        try {
	        	while(true){
	        		socket = socketserver.accept(); // Un client se connecte on l'accepte
	        		//On lance un thread qui check les messages pour chaque socket
					Thread threadLectureClient = new Thread(new LectureClient(socket, log, nbrclient));
					threadsLectureClient.add(threadLectureClient);
					threadLectureClient.start();
	                log.ecrire("Le client n°"+nbrclient+" s'est connecte", logType.EVENT);
	                nbrclient++;
	        	}  
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
	}