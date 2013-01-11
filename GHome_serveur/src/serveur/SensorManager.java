package serveur;

import serveur.SensorCategory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Handler for sensors and motors
 * @author Yoann
 *
 */
public class SensorManager
{
	private class Sensor { 
	public SensorCategory cat; public ArrayList<Integer> groups; public Sensor(){ groups = new ArrayList<Integer>(); } }
	
	private final String CONF_PATH = "conf_sensors";
	
	private static SensorManager instance;
	private Hashtable<String, Sensor> sensorList; // String = ID, Sensor = sensor associated
	private Hashtable<String, Sensor> motorList; // idem
	
	
	
	
	/**
	 * 
	 * @return Gets the instance of <code>SensorManager</code> handling all the sensors referenced by the user
	 */
	public static SensorManager getInstance()
	{
		if(instance == null){
			instance = new SensorManager();
		}
		return instance;
	}
	
	
	/**
	 * References a new sensor
	 * @param id hardware ID of the sensor to be referenced
	 * @param cat category of the sensor
	 * @see SensorCategory
	 */
	public void addSensor(String id, String cat)
	{
		Sensor sensor = new Sensor();
		sensor.cat = SensorCategory.valueOf(cat);
		sensorList.put(id, sensor);
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CONF_PATH)));
			out.println("S "+id+" "+cat);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean assignGroupToSensor(String id, int group)
	{
		Sensor sensor = sensorList.get(id);
		if(sensor == null){
			return false;
		}
		sensor.groups.add(group);
		addGroupInConf(id, group);
		
		return true;
	}
	
	public boolean removeGroupToSensor(String id, int group){
		Sensor sensor = sensorList.get(id);
		if(sensor == null){
			return false;
		}
		if(!sensor.groups.remove((Integer)group)){
			return false;
		}
		eraseGroupInConf(id, group);
		
		return true;
	}
	
	/**
	 * Removes a sensor from the list referencing all the sensors handled
	 * @param id hardware ID of the sensor to be removed
	 */
	public void removeSensor(String id){
		sensorList.remove(id);
		eraseLineInConf(id);
	}
	
	/**
	 * Checks whether a sensor is referenced or not in the list referencing all the sensors handled
	 * @param id hardware ID of the sensor to check
	 * @return TRUE if the sensor is ours,
	 * <br>FALSE otherwise
	 */
	public boolean isInSensorList(int id){
		return sensorList.containsKey(id);
	}
	
	/**
	 * References a new motor
	 * @param id hardware ID of the motor to be referenced
	 * @param cat category of the motor
	 * @see SensorCategory
	 */
	public void addMotor(String id, String cat)
	{
		Sensor sensor = new Sensor();
		sensor.cat = SensorCategory.valueOf(cat);
		sensorList.put(id, sensor);
		
		try
		{
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CONF_PATH)));
			out.println("M "+id+" "+cat);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean assignGroupToMotor(String id, int group){
		Sensor sensor = motorList.get(id);
		if(sensor == null){
			return false;
		}
		sensor.groups.add(group);
		addGroupInConf(id, group);
		
		return true;
	}
	
	public boolean removeGroupToMotor(String id, int group){
		Sensor sensor = motorList.get(id);
		if(sensor == null){
			return false;
		}
		if(!sensor.groups.remove((Integer)group)){
			return false;
		}
		eraseGroupInConf(id, group);
		
		return true;
	}
	
	
	public void removeMotor(String id){
		motorList.remove(id);
		eraseLineInConf(id);
	}
	
	/**
	 * Gets all the motors of the different groups assigned to the sensor
	 * @param id hardware ID of the sensor
	 * @return map of couples (ID motor - category)
	 */
	public Hashtable<String, SensorCategory> getMotorBoundToSensor(String id){
		
		Hashtable<String, SensorCategory> list = new Hashtable<String, SensorCategory>();
		
		Sensor sensor = sensorList.get(id);
		if(sensor == null){
			return null;
		}
		
		for(int group : sensor.groups)
		{
			Set<Entry<String,Sensor>> test = motorList.entrySet();
			Iterator<Entry<String, Sensor>> it = test.iterator();
			
			while(it.hasNext())
			{
				Entry<String,Sensor> couple = it.next();
				if(couple.getValue().groups.contains(group)){
					list.put(couple.getKey(), couple.getValue().cat);
				}
			}
		}
		
		
		return list;
	}
	
	
	private void addGroupInConf(String id, int group){
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(CONF_PATH));
			
			StringBuilder temp = new StringBuilder();
			String line;
			while((line = in.readLine()) != null)
			{
				temp.append(line);
				
				if(!line.isEmpty() && line.charAt(0) != '#')
				{
					String[] data = line.split(" ");
					if(data[1].equals(id))
					{
						temp.append(" "+group);
					}
				}
				
				temp.append("\n");
			}
			in.close();
			
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CONF_PATH)));
			out.println(temp);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void eraseGroupInConf(String id, int group){
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(CONF_PATH));
			
			StringBuilder temp = new StringBuilder();
			String line;
			while((line = in.readLine()) != null)
			{
				if(!line.isEmpty() && line.charAt(0) != '#')
				{
					String[] data = line.split(" ");
					if(!data[1].equals(id))
					{
						temp.append(line+"\n");
					}
					else{
						StringBuilder tmp_data = new StringBuilder();
						for(int i=0; i<data.length; i++){
							if(!data[i].equals(group)){
								tmp_data.append(data[i]);
							}
						}
						temp.append(tmp_data);
					}
				}
				else
				{
					temp.append(line+"\n");
				}
			}
			in.close();
			
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CONF_PATH)));
			out.println(temp);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void eraseLineInConf(String id){
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(CONF_PATH));
			
			StringBuilder temp = new StringBuilder();
			String line;
			while((line = in.readLine()) != null)
			{
				if(!line.isEmpty() && line.charAt(0) != '#')
				{
					String[] data = line.split(" ");
					if(!data[1].equals(id))
					{
						temp.append(line+"\n");
					}
				}
				else
				{
					temp.append(line+"\n");
				}
			}
			in.close();
			
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CONF_PATH)));
			out.println(temp);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// singleton
	private SensorManager(){
		sensorList = new Hashtable<String, Sensor>();
		motorList = new Hashtable<String, Sensor>();
		
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(CONF_PATH));
			
			String line;
			while((line = in.readLine()) != null)
			{
				// do not process empty lines or comments ( # )
				if(!line.isEmpty() && line.charAt(0) != '#'){
					String[] data = line.split(" ");
					// 3 args min (type id cat)
					if(data.length < 3){
						System.out.println("Error in file '"+CONF_PATH+"': line without all necessary values");
					}
					else
					{
						Sensor sensor = new Sensor();
						sensor.cat = SensorCategory.valueOf(data[2]);
						// possible group(s)
						for(int i=3; i<data.length; i++){
							sensor.groups.add(Integer.parseInt(data[i]));
						}
						
						switch(data[0])
						{
							case "S":
								sensorList.put(data[1], sensor);
								break;
							case "M":
								motorList.put(data[1], sensor);
								break;
							default:
								System.out.println("Error in file '"+CONF_PATH+"': line starting with '"+data[0]+"'");
						}
					}
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
