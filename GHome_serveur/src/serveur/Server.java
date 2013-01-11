package serveur;

import java.util.Timer;

public class Server
{
	private final long PERIOD_METEO_TASK = 2880000; // duration before reading again data web (ms). Currently 8h
	
	public Server()
	{
		initServer();
	}
	
	private void initServer()
	{
		Timer meteoTimer = new Timer(true);
		meteoTimer.schedule(new MeteoWebReader(), 0, PERIOD_METEO_TASK);
		
		
		
		// make sure that initialisation is achieved
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
