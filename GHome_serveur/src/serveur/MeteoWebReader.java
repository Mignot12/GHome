package serveur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * To be used as a periodic task: will get the weather data from Web
 * @author Yoann
 *
 */
public class MeteoWebReader extends TimerTask
{
	// postal code has to be added at the end of the URL
	private final String METEO_URL = "http://api.meteorologic.net/forecarss?p=";
	private final String CONF_PATH = "conf_postalcode";
	
	
	@Override
	public void run()
	{
		String complete_url = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(CONF_PATH));
			String code = in.readLine();
			// TODO traiter erreur code pas bon
			
			complete_url = METEO_URL + code;
			
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		parseMeteoDataXML(complete_url);
	}
	
	private void parseMeteoDataXML(String url)
	{
		ArrayList<ArrayList<ArrayList<String>>> report = new ArrayList<ArrayList<ArrayList<String>>>();
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());
			 
			NodeList forecastList = doc.getElementsByTagName("meteo:weather");
			for(int i=0; i<forecastList.getLength(); i++)
			{
				ArrayList<ArrayList<String>> day = new ArrayList<ArrayList<String>>();
				
				NamedNodeMap forecastDay = forecastList.item(i).getAttributes();
				
				ArrayList<String> dataMorning = new ArrayList<String>();
				dataMorning.add(forecastDay.getNamedItem("tempe_matin").getNodeValue());
				dataMorning.add(forecastDay.getNamedItem("namepictos_matin").getNodeValue());
				day.add(dataMorning);
				
				ArrayList<String> dataNoon = new ArrayList<String>();
				dataNoon.add(forecastDay.getNamedItem("tempe_midi").getNodeValue());
				dataNoon.add(forecastDay.getNamedItem("namepictos_midi").getNodeValue());
				day.add(dataNoon);
				
				ArrayList<String> dataAfternoon = new ArrayList<String>();
				dataAfternoon.add(forecastDay.getNamedItem("tempe_apmidi").getNodeValue());
				dataAfternoon.add(forecastDay.getNamedItem("namepictos_apmidi").getNodeValue());
				day.add(dataAfternoon);
				
				ArrayList<String> dataNight = new ArrayList<String>();
				dataNight.add(forecastDay.getNamedItem("tempe_soir").getNodeValue());
				dataNight.add(forecastDay.getNamedItem("namepictos_soir").getNodeValue());
				day.add(dataNight);
				
				report.add(day);
			}
			
			// save forecast read
			MeteoManager.getInstance().setNewForecast(report);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
