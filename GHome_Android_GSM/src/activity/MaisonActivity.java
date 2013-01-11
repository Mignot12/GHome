package activity;

import java.util.ArrayList;

import communication.ConnexionServeur;

import mignot.ghome_android_gsm.R;
import bean.Conteneur;
import bean.Interrupteur;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MaisonActivity extends Activity {
	
	private TextView textViewTitreMaison;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maison);
		
		ConnexionServeur.sendMessage("azzerert");
		
		textViewTitreMaison=(TextView)findViewById(R.id.textViewTitreMaison);
		ArrayList<Interrupteur> interrupteurs = Conteneur.getInstance().getInterrupteurs();
		if(interrupteurs.size()>0){
			textViewTitreMaison.setText(interrupteurs.get(0).getNom());
		}

	}
}
