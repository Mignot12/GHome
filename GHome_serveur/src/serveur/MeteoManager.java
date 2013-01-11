package serveur;

import java.util.ArrayList;

/**
 * Saves weather forecast (read from web)
 * @author Yoann
 *
 */
public class MeteoManager {
	
	private static MeteoManager instance;
	
	private ArrayList<ArrayList<ArrayList<String>>> forecast;
	//  days: 0 = today, 1 = tomorrow, 2 = in 2 days, ...
	//	--> time of day: 0 = morning, 1 = noon, 2 = afternoon, 3 = night
	//	------> data: 0 = temperature, 1 = weather condition 
	
	public static MeteoManager getInstance()
	{
		if(instance == null){
			instance = new MeteoManager();
		}
		return instance;
	}
	
	
	public void setNewForecast(ArrayList<ArrayList<ArrayList<String>>> newForecast){
		forecast = newForecast;
	}
	
	public ArrayList<ArrayList<ArrayList<String>>> getForecast(){
		return forecast;
	}
	
	// singleton
	private MeteoManager() {
		forecast = new ArrayList<ArrayList<ArrayList<String>>>();
		
		ArrayList<String> data = new ArrayList<String>();
		ArrayList<ArrayList<String>> time = new ArrayList<ArrayList<String>>();
		
		time.add(data);
		forecast.add(time);
	}

}
