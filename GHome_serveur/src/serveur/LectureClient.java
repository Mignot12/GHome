package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import bean.Interrupteur;

import serveur.Log.logType;

/**
 * 
 * @author Andreas
 * Classe qui boucle et en lisant les messages des terminaux android connectés
 * qui lui arrivent
 *
 */
public class LectureClient implements Runnable {

	private Socket socket;
	private Log log;
	private int noClient;

	   
	   public void sendMessage(String s){
		   	PrintWriter out;
			try {
				out = new PrintWriter(socket.getOutputStream());
		        out.println(s);
		        out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	
	   public LectureClient(Socket s, Log alog, int anoClient){
		   socket = s;
		   log=alog;
		   noClient=anoClient;
	   }
		
		public void run() {
			BufferedReader in;
			try {
				in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
				while(true){
					if(socket.isClosed()){
					    log.ecrire("Le client n°"+noClient+" s'est deconnecte", logType.EVENT);
						return;
					}
					else{
					    String message_distant = "";
						message_distant = in.readLine();
						if(message_distant!=null){
					        log.ecrire("client n°"+noClient+" : "+message_distant, logType.MESSAGE);
							if(message_distant.equals(InputMessageType.DECO)){
								socket.close();
					            log.ecrire("Le client n°"+noClient+" s'est deconnecte", logType.EVENT);
					            return;
							}
							else if(message_distant.equals(InputMessageType.INIT)){
								Interrupteur i=new Interrupteur(3, "interrulol");
								String mess = OutputMessageType.INTERRUPTEUR+" "+i.toString();
								System.out.println(mess);
					            sendMessage(mess);
					            
					            return;
							}
						}
						else{
							socket.close();
				            log.ecrire("Le client n°"+noClient+" s'est deconnecte", logType.EVENT);
				            return;
						}
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
