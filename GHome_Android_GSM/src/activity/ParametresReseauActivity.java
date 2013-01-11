package activity;

import android.os.Bundle;
 

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import mignot.ghome_android_gsm.GHome_Android_GSM;
import mignot.ghome_android_gsm.R;

import communication.ConnexionServeur;
import communication.OutputMessageType;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ParametresReseauActivity extends Activity {
	private ArrayList<HashMap<String, String>> listItem;
	private ListView listViewReseau;
	private Button buttonHome;
	private TextView textViewTitreParametresReseau;
	
	public void maj(){
		    //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
		    SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.item_perso,
		           new String[] {/*"img",*/ "titre", "description"}, new int[] {/*R.id.img,*/ R.id.titre, R.id.description});

		    //On attribut à notre listView l'adapter que l'on vient de créer
		    listViewReseau.setAdapter(mSchedule);
	 }
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametres_reseau);
		
		textViewTitreParametresReseau=(TextView)findViewById(R.id.textViewTitreParametresReseau);
		textViewTitreParametresReseau.setText(getString(R.string.title_activity_parametres_reseau));
		buttonHome = (Button)findViewById(R.id.buttonHome);
		buttonHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent parametresActivite = new Intent(ParametresReseauActivity.this, GHome_Android_GSM.class);
		        startActivity(parametresActivite);
			}
		});
		listViewReseau = (ListView)findViewById(R.id.listViewReseau);
		String[] listeStrings = {"Etat de la connexion","IP du serveur"};
		listViewReseau.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));

        //Création de la ArrayList qui nous permettra de remplire la listView
		listItem = new ArrayList<HashMap<String, String>>();
 
        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Création d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        map.put("titre", "Etat de la connexion");
        //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
        if(!ConnexionServeur.getConnected()){
        	map.put("description", "Déconnecté");
        }
        else{
        	map.put("description", "Connecté");
        }
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        //map.put("img", String.valueOf(R.drawable.word));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView

        map = new HashMap<String, String>();
        map.put("titre", "IP du serveur");
        map.put("description", ConnexionServeur.getIp());
        //map.put("img", String.valueOf(R.drawable.excel));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", "Port");
        map.put("description", ""+ConnexionServeur.getPort());
        //map.put("img", String.valueOf(R.drawable.excel));
        listItem.add(map);
 
        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.item_perso,
               new String[] {/*"img",*/ "titre", "description"}, new int[] {/*R.id.img,*/ R.id.titre, R.id.description});
 
        //On attribut à notre listView l'adapter que l'on vient de créer
        listViewReseau.setAdapter(mSchedule);
 
        //Enfin on met un écouteur d'évènement sur notre listView
        listViewReseau.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
        		ListView listViewReseau = (ListView)findViewById(R.id.listViewReseau);
        		HashMap<String, String> map = (HashMap<String, String>) listViewReseau.getItemAtPosition(position);
        		

        		//Se connecter
        		if(id==0){
            		//on créer une boite de dialogue
            		AlertDialog.Builder adb = new AlertDialog.Builder(ParametresReseauActivity.this);
            		//on attribut un titre à notre boite de dialogue
            		adb.setTitle(map.get("titre"));
            		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
            		adb.setMessage(map.get("description"));
            		//on indique que l'on veut le bouton ok à notre boite de dialogue
            		//on affiche la boite de dialogue
            		
            		final AlertDialog.Builder adb2 = adb;
            		final HashMap<String, String> mapConn = (HashMap<String, String>)listViewReseau.getItemAtPosition(0);
                    if(!ConnexionServeur.getConnected()){
	            		adb.setPositiveButton("Se connecter", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
			    		    	ConnexionServeur.connexion();
			    		    	if(ConnexionServeur.getConnected()){
			    		    		mapConn.put("description", "Connecté");
			    		    	}
			    		    	else{
			    		    		mapConn.put("description", "Déconnecté");
			    		    	}
			    		    	maj();
							}
						});
                    }
                    else{
	            		adb.setPositiveButton("Se déconnecter", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
									try {
										ConnexionServeur.sendMessage(OutputMessageType.DECO);
										ConnexionServeur.getSocket().close();
				    		    		mapConn.put("description", "Déconnecté");
				    		    		maj();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			    		    	maj();
							}
						});
                    }
            		adb.setNegativeButton("Annuler", null);
            		adb.show();
        		}
        		//modif de l'ip du serveur
        		if(id==1){
            		//on créer une boite de dialogue
            		AlertDialog.Builder adb = new AlertDialog.Builder(ParametresReseauActivity.this);
            		//on attribut un titre à notre boite de dialogue
            		adb.setTitle(map.get("titre"));
            		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
            		adb.setMessage("Adresse ip actuel du serveur : "+map.get("description")+
            				"\n(par défaut l'adresse ip du serveur est "+ConnexionServeur.getIpdefaut()+")");
            		//on indique que l'on veut le bouton ok à notre boite de dialogue
            		//on affiche la boite de dialogue
            		final EditText input = new EditText(ParametresReseauActivity.this);
            		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            		        LinearLayout.LayoutParams.FILL_PARENT,
            		        LinearLayout.LayoutParams.FILL_PARENT);
            		input.setLayoutParams(lp);
            		final AlertDialog.Builder adb2 = adb;
            		final HashMap<String, String> mapIp = map;
            		adb.setPositiveButton("Ok", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
		    		    	String ip = input.getText().toString();
		    		    	ConnexionServeur.setIp(ip);
		    		    	mapIp.put("description", ip);
		    		    	maj();
						}
					});
            		adb.setNegativeButton("Annuler", null);
            		adb.setView(input);
            		adb.show();
        		}
        		//modif du port du serveur
        		if(id==2){
            		//on créer une boite de dialogue
            		AlertDialog.Builder adb = new AlertDialog.Builder(ParametresReseauActivity.this);
            		//on attribut un titre à notre boite de dialogue
            		adb.setTitle(map.get("titre"));
            		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
            		adb.setMessage("Port actuel : "+map.get("description")+
            				" \n(par défaut le port est "+ConnexionServeur.getPortdefaut()+")");
            		//on indique que l'on veut le bouton ok à notre boite de dialogue
            		//on affiche la boite de dialogue
            		final EditText input = new EditText(ParametresReseauActivity.this);
            		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            		        LinearLayout.LayoutParams.FILL_PARENT,
            		        LinearLayout.LayoutParams.FILL_PARENT);
            		input.setLayoutParams(lp);
            		final AlertDialog.Builder adb2 = adb;
            		final HashMap<String, String> mapPort = map;
            		adb.setPositiveButton("Ok", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
		    		    	int port = Integer.parseInt(input.getText().toString());
		    		    	ConnexionServeur.setPort(port);
		    		    	mapPort.put("description", ""+port);
		    		    	maj();
						}
					});
            		adb.setNegativeButton("Annuler", null);
            		adb.setView(input);
            		adb.show();
        		}
        	}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_parametres, menu);
		return true;
	}
}