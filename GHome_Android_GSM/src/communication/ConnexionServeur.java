package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import bean.Conteneur;
import bean.Interrupteur;


public class ConnexionServeur {

	   private static Socket socket;
	   private static boolean connected;

	   private final static int portDefaut = 2009;
	   private final static String ipDefaut = "109.0.210.65";
	   //private final static String ipDefaut = "192.168.0.1";

	   private static String ip = ipDefaut;
	   private static int port = portDefaut;

	   private static int timeout = 10000;
	   

	   private static ConnexionServeur instance = null;
	   
	   protected ConnexionServeur() {
	   }
	   
	   public static ConnexionServeur getInstance() {
	      if(instance == null) {
	         instance = new ConnexionServeur();
	      }
	      return instance;
	   }

	   private static void initBeans() {
		   ConnexionServeur.sendMessage(OutputMessageType.INIT);

			BufferedReader in;
			try {
				in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		        String message_distant = "";
				message_distant = in.readLine();
				if(message_distant!=null){
					String[] mess = message_distant.split(" ");
					if(mess[0].equals(InputMessageType.INTERRUPTEUR)){
						Conteneur.getInstance().add(new Interrupteur(Integer.parseInt(mess[1]), mess[2]));
		                return;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	
	   public static void deconnexion(){
		   try {
			socket.close();
			connected=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	   public static void connexion(){
		   socket=new Socket();
			try {
				socket.connect(new InetSocketAddress(ip, port), timeout);
				connected=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				connected=false;
			}
			initBeans();
	   }
	   
	   public static Socket getSocket() {
		return socket;
	}
	   
	   public static void sendMessage(String s){
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

	public static void setSocket(Socket asocket) {
		socket = asocket;
	}

	public static int  getTimeout() {
		return timeout;
	}

	public static void setTimeout(int atimeout) {
		timeout = atimeout;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		ConnexionServeur.ip = ip;
	}

	public static void setConnected(boolean aconnected) {
		connected = aconnected;
	}

	public static boolean getConnected(){
		   return connected;
	   }

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		ConnexionServeur.port = port;
	}

	public static int getPortdefaut() {
		return portDefaut;
	}

	public static String getIpdefaut() {
		return ipDefaut;
	}
}
