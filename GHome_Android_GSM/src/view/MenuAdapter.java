package view;
	 
	import mignot.ghome_android_gsm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

	public class MenuAdapter extends BaseAdapter {
		private Context context;
		private final String[] menuValues;
	 
		public MenuAdapter(Context context, String[] menuValues) {
			this.context = context;
			this.menuValues = menuValues;
		}
	 
		public View getView(int position, View convertView, ViewGroup parent) {
	 
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
			if (convertView == null) {
	 
				gridView = new View(context);
	 
				// get layout from mobile.xml
				gridView = inflater.inflate(R.layout.item_menu, null);
	 
				// set value into textvie
				TextView textView = (TextView) gridView
						.findViewById(R.id.textViewMenu);
				textView.setText(menuValues[position]);
	 
				// set image based on selected text
				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.imageViewMenu);
	 
				String item = menuValues[position];
	 
				if (item.equals("Paramètres réseau")) {
					imageView.setImageResource(R.drawable.settings);
				} else if (item.equals("Entrées")) {
					imageView.setImageResource(R.drawable.thermometer);
				} else if (item.equals("Sorties")) {
					imageView.setImageResource(R.drawable.light);
				}
	 
			} else {
				gridView = (View) convertView;
			}
	 
			return gridView;
		}
	 
		@Override
		public int getCount() {
			return menuValues.length;
		}
	 
		@Override
		public Object getItem(int position) {
			return null;
		}
	 
		@Override
		public long getItemId(int position) {
			return 0;
		}
	 
	}