package com.example.iou;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iou.MainActivity.DebtEntry;


/*
 * Custom ViewHolder for ListView
 */
class ViewHolder {

    TextView person;
    TextView description;
    Button venmo;
    
 }


	/*
	 * Custom Adapter class for debt list
	 */
	public class DebtAdapter extends BaseAdapter {
		private ArrayList<DebtEntry> debts;
		private final LayoutInflater mInflater;
		final ViewHolder holder;
		
		public DebtAdapter(ArrayList<DebtEntry> debt_entries, Context context){
			debts = debt_entries;
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
		}
		
		public int getCount() {
			return debts.size();
		}
		
		public DebtEntry getItem(int position){
			return debts.get(position);
		}

		@Override
		public long getItemId(int position){
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			//LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.debt, null);
           	//holder = new ViewHolder();      
            holder.person = (TextView)convertView.findViewById(R.id.debt_name);
            holder.person.setText((CharSequence) (debts.get(position).getPerson()));
            holder.description = (TextView) convertView.findViewById(R.id.debt_description);
            holder.description.setText((CharSequence) (debts.get(position).getAmount() 
            											+ " - " + debts.get(position).getDescription()));
            
            holder.venmo = (Button) convertView.findViewById(R.id.venmo);
            holder.venmo.setText("VENMO");
        	// on Add button click: remove event from user events (since it was added before)
            holder.venmo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
               	 Log.d("venmo button", "clicked on venmo button!");
               }
            });
			
            return convertView;
		}
	}