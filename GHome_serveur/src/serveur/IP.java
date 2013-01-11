package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import java.util.Scanner; 

public class IP 
{     
    /** 
     * @param site l'adresse internet d'un site donnant l'adresse IP 
     * @param prefixe le code HTML précédant la valeur de l'adresse IP 
     * @param suffixe le code HTML succédant la valeur de l'adresse IP 
     * @return l'adresse IP trouvée ou null sinon 
     */ 
    public static String findIP(String site, String prefixe, String suffixe) throws Exception 
    {         
        //Scanner sc = new Scanner(new URL(site).openStream());
        URL url = new URL(site);
    	
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
//		Thread.sleep(5000);
		httpcon.addRequestProperty("User-Agent", "Mozilla/4.76"); 
		BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
	    String line="";
        while((line=in.readLine())!=null) 
        {              
        	//System.out.println(line);
//            int a = line.indexOf(prefixe); 
//          if (a!=-1) 
//          { 
//              int b = line.indexOf(suffixe,a); 
//              if (b!=-1) 
//              { 
//              	in.close();
//                  //sc.close(); 
//                  return line.substring(a+prefixe.length(),b); 
//              } 
//          } 
        	
        	if (line.equals(prefixe)) 
	        { 	
        		String rep = in.readLine();
        		in.close();
	            return rep; 
	        }
        } 
         
        //sc.close(); 
    	in.close();
    	
        return null; 
    } 
}
