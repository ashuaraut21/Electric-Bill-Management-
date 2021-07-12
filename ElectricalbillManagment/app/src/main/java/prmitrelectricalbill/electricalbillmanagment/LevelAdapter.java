package prmitrelectricalbill.electricalbillmanagment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LevelAdapter extends ArrayAdapter<String> {
	private final Activity context;
	
	private final String[] name;
	
	private final String[] date;
	public LevelAdapter(Activity context, String[] gname, String[] gdate) {
		super(context, R.layout.itemlist, gname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.name=gname;
		this.date=gdate;
		
	}
	
		
		// TODO Auto-generated constructor stub
	
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.itemlist, null,true);
		
		TextView txtparkingname = (TextView) rowView.findViewById(R.id.name);
		TextView txtparkingdate = (TextView) rowView.findViewById(R.id.time);
		txtparkingname.setText(name[position]);
		txtparkingdate.setText(date[position]);
		
		return rowView;
		
	};
}
