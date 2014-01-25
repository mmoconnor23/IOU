package com.example.iou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.iou.MainActivity.DebtEntry;

/*
 * Custom ViewHolder for ListView
 */
class ViewHolder {

    TextView person;
    TextView description;
    
 }


	/*
	 * Custom Adapter class for debt list
	 */
	public class DebtAdapter extends BaseAdapter {
		private DebtEntry[] debts;
		private final LayoutInflater mInflater;
		final ViewHolder holder;
		
		public DebtAdapter(DebtEntry[] debt_entries, Context context){
			debts = debt_entries;
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
		}
		
		public int getCount() {
			return debts.length;
		}
		
		public DebtEntry getItem(int position){
			return debts[position];
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
            holder.person.setText((CharSequence) (debts[position].getPerson()));
            holder.description = (TextView) convertView.findViewById(R.id.debt_description);
            holder.description.setText((CharSequence) (debts[position].getAmount() 
            											+ " - " + debts[position].getDescription()));
			
            return convertView;
		}
	}