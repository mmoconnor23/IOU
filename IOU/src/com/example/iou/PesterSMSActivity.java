package com.example.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iou.MainActivity.DebtEntry;

public class PesterSMSActivity extends Activity {

	int pos;
	
	EditText msg;
	TextView phone;
	
	private static int PESTER = 0;
	private static int IRRITATE = 1;
	private static int CUT_OFF = 2;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pester_sms);
		
		Intent i = getIntent();
		DebtEntry debt = (DebtEntry) i.getSerializableExtra("debt");
		pos = i.getIntExtra("id_num", -1);
		
		String name = debt.getPerson();
		String ph = debt.getPhone();
		String amount = debt.getAmount(); 
		String note = debt.getDescription(); 
		int pesters = debt.getPesters();
		
		msg = (EditText) findViewById(R.id.pester_sms_message);
		Button send = (Button) findViewById(R.id.button_send_sms);
		
		send.setText("Pester " + name + "!");
		
		Log.i("PESTER", ""+pesters);
		
		if (pesters >= CUT_OFF){
			if (note.equals("")){
				msg.setText("You owe me $" + amount + " and you haven't paid. I'm cutting you off. Bye bye forever.");
			} else {
				msg.setText("You owe me $" + amount + " for "+ note + " and you haven't paid. I'm cutting you off. Bye bye forever.");
			}
		} else if (pesters >= IRRITATE) {
			if (note.equals("")){
				msg.setText("So, you still haven't paid me my $" + amount + ". Pay up son!");
			} else {
				msg.setText("So, you still haven't paid me my $" + amount + " for " + note + ". Pay up son!");
			}
		} else {
			if (note.equals("")){
				msg.setText("Hey! You owe me $" + amount + ". Please pay me soon!");
			} else {
				msg.setText("Hey! You owe me $" + amount + " for " + note + ". Please pay me soon!");
			}
		}
		
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("PSTR", "pesterrr");
				SmsManager sm = SmsManager.getDefault();
				
				//sm.sendTextMessage(phone.getText().toString(), null, msg.getText().toString(), null, null); // first param should be phone
				sm.sendTextMessage("5558", null, msg.getText().toString(), null, null); // first param should be phone
				
				/* tell user of success/failure */
				Toast.makeText(PesterSMSActivity.this, "Pester Message Sent!", Toast.LENGTH_LONG).show();
				
				/* return to previous activity, update desired internal state */
				Intent returnIntent = new Intent();
				
				//TODO: check here for errors in sending sms 
				//and put extra if we want to update internal state (e.g. about num pesters sent) in prev activity
				// TODO: make toasts to tell user if message failed (do this before returning)
				//returnIntent.putExtra("new_debt", debt);
				returnIntent.putExtra("id_num", pos);
				setResult(RESULT_OK,returnIntent); //TODO: set to not ok if nothing sent  
				finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pester_activity_settings_menu, menu);
		return true;
	}
	
}
