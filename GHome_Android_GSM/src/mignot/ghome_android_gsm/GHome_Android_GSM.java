package mignot.ghome_android_gsm;

import java.net.Socket;
import view.MenuAdapter;
import communication.ConnexionServeur;
import communication.LectureServeur;

import activity.*;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ToggleButton;


public class GHome_Android_GSM extends Activity {
	private ToggleButton toggleButtonConnecte;
	private Socket socket;
	private GridView gridViewMenu;	
	private ProgressDialog progressDialogConnexion;
	
	/*
	 * Nous permet de modifier notre UI via un thread. En effet on ne peut pas modifier 
	 * l'UI directement à partir d'un thread, pourtant indispensable à notre connexion avec le serveur.
	 * On passe donc par un handler
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//0. La connexion a réussit
			if(msg.what == 0) {
				toggleButtonConnecte.setChecked(true);
			}
			else if(msg.what == 1) {
				toggleButtonConnecte.setChecked(false);
			}
	};

};

	@Override
	protected void onResume() {
		super.onResume();
		//textv.setText("lolito");
		if(ConnexionServeur.getConnected()){
			toggleButtonConnecte.setChecked(true);
		}
		else{
			toggleButtonConnecte.setChecked(false);
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		toggleButtonConnecte.setText("duuhuh");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread th = new Thread(new LectureServeur());
		th.start();
		gridViewMenu = (GridView)findViewById(R.id.gridViewMenu);
		String[] items = new String[] { 
				"Maison", "Modes", "Paramètres Ghome", "Paramètres réseau" 
		};
 
		gridViewMenu.setAdapter(new MenuAdapter(this, items));
 
		gridViewMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				if(position==3){
					Intent parametresActivite = new Intent(GHome_Android_GSM.this, ParametresReseauActivity.class);
			        startActivity(parametresActivite);
				}
				else if(position==0){
					Intent parametresActivite = new Intent(GHome_Android_GSM.this, MaisonActivity.class);
			        startActivity(parametresActivite);
				}
				else if(position==2){
					
				}
			}
		});

		toggleButtonConnecte = (ToggleButton)findViewById(R.id.toggleButtonConnecte);
		toggleButtonConnecte.setClickable(false);
		toggleButtonConnecte.setTextOn("Con.");
		toggleButtonConnecte.setTextOff("Déc.");
		toggleButtonConnecte.setChecked(false);
		
		/*Si nous ne sommes pas connecté au serveur, on affiche un
		 * message popup pendant que la connexion s'opère
		 */
		
		if(!ConnexionServeur.getConnected()){
			progressDialogConnexion = new ProgressDialog(this);
			progressDialogConnexion.setMessage("Connexion au serveur en cours");
			progressDialogConnexion.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					ConnexionServeur.connexion();
					
			// A la fin du traitement, on fait disparaitre notre message
					if(ConnexionServeur.getConnected()){
						handler.sendEmptyMessage(0);
					}
					else{
						handler.sendEmptyMessage(1);
					}
					progressDialogConnexion.dismiss();
				}
			}).start();
		}
		else{
			toggleButtonConnecte.setChecked(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

