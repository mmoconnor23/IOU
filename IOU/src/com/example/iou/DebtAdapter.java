package com.example.iou;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.iou.MainActivity.DebtEntry;
import com.example.iou.MainActivity.SectionsPagerAdapter;


/*
 * Custom ViewHolder for ListView
 */
class ViewHolder {

    TextView person;
    TextView description;
    ImageButton venmo;
    Button pester;
    
 }


	/*
	 * Custom Adapter class for debt list
	 */
	public class DebtAdapter extends BaseAdapter {
		private ArrayList<DebtEntry> debts;
		private final LayoutInflater mInflater;
		final ViewHolder holder;
		private Context c;
		
		private int tab;
		
		private static int TAB_IOU = 1;
		private static int TAB_UOME = 2;
		
		
		private static int PESTER_SMS_ACTIVITY = 3;
		
		public DebtAdapter(ArrayList<DebtEntry> debt_entries, Context context, int curTab){
			c = context;
			debts = debt_entries;
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			tab = curTab;
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
            holder.description.setText((CharSequence) ("$" + debts.get(position).getAmount() 
            											+ " - " + debts.get(position).getDescription()));
            
            holder.venmo = (ImageButton) convertView.findViewById(R.id.venmo);
        	// on Add button click: remove event from user events (since it was added before)
            holder.venmo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
               	 Log.d("venmo button", "clicked on venmo button!");
               	 
               	 doVenmo(view);
               }

            });
            
            holder.pester = (Button) convertView.findViewById(R.id.button_pester_or_remind);
            if (tab == TAB_IOU){
            	holder.pester.setText(R.string.remind);
            	holder.pester.setBackgroundColor(Color.parseColor("#00cc00"));
            } else {
            	holder.pester.setText(R.string.pester);
            	holder.pester.setBackgroundColor(Color.parseColor("#"));

            }
            
            holder.pester.setOnClickListener(new OnClickListener() {
            	@Override
            	public void onClick(View view) {
            		Log.d("pester button", "clicked on pester button!");

            		//TODO: find out if pester or remind!
            		Intent i = new Intent((Activity) c, PesterSMSActivity.class);
            		
            		int tag = (Integer) view.getTag();
        			DebtEntry debt = debts.get(tag);
            		i.putExtra("debt", debt);
            		view.getContext().startActivity(i);
            	}
            });
            
            convertView.setTag(position);
            
            //updates tag of the button view as we scroll
            holder.person.setTag(position);
            holder.description.setTag(position);
            holder.venmo.setTag(position);
            holder.pester.setTag(position);
            
            holder.person.setFocusable(true);
            holder.description.setFocusable(true);
            holder.person.requestFocus();
            holder.description.requestFocus();
			
            return convertView;
		}
		
		
		private void doVenmo(View v) {
			final int VENMO_ACTIVITY = 2;
			Activity a = (Activity) c;
			
			int tag = (Integer) v.getTag();
			DebtEntry debt = debts.get(tag);
			
			String app_id = "1563";
			String app_name = "IOU";
			String recipient = debt.getPhone(); // needs username, phone number, or email; let's assume phone
			String amount = debt.getAmount(); 
			String note = debt.getDescription(); 
			String txn = "pay"; // either "pay" or "charge"; let's say we can only pay others in this app
			
			try { 
				Intent venmoIntent = VenmoLibrary.openVenmoPayment(app_id, app_name, recipient, amount, note, txn);
				a.startActivityForResult(venmoIntent, VENMO_ACTIVITY); //1 is the requestCode we are using for Venmo. Feel free to change this to another number. 
			} catch (android.content.ActivityNotFoundException e) { //Venmo native app not install on device, so let's instead open a mobile web version of Venmo in a WebView 
				Intent venmoIntent = new Intent(c, VenmoWebViewActivity.class); 
				String venmo_uri = VenmoLibrary.openVenmoPaymentInWebView(app_id, app_name, recipient, amount, note, txn); 
				venmoIntent.putExtra("url", venmo_uri); 
				a.startActivityForResult(venmoIntent, VENMO_ACTIVITY); 
			}
				
		}
		
		
		

	}