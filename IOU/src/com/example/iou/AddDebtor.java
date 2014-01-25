package com.example.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iou.MainActivity.DebtEntry;

public class AddDebtor extends Activity {

	EditText name;
	EditText phone;
	EditText amt;
	EditText description;
	
	Button button_finish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_debtor);
		
		button_finish = (Button) this.findViewById(R.id.button_finish_add_debt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_debtor_page_main, menu);
		return true;
	}

	public void onFinishButtonPressed(View v){
		/* grab data entered and save it as debt entry */
		name = (EditText) this.findViewById(R.id.debtor_name);
		phone = (EditText) this.findViewById(R.id.debtor_phone);
		amt = (EditText) this.findViewById(R.id.amt_money_owed);
		description = (EditText) this.findViewById(R.id.debt_description);
		
		DebtEntry debt = new DebtEntry(null, null, null);
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("new_debt", debt);
		setResult(RESULT_OK,returnIntent);     
		finish();
	}
}
