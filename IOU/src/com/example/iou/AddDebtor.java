package com.example.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iou.MainActivity.DebtEntry;

public class AddDebtor extends Activity {

	private final static int IOU = 0;
	private final static int UOME = 1;
	
	EditText name;
	EditText phone;
	EditText amt;
	EditText description;
	
	Button button_finish;
	int debtType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_debtor);
		
		Intent intent = getIntent();
		debtType = intent.getIntExtra("debt_type", 0);
		
		name = (EditText) this.findViewById(R.id.debtor_name);
		if (debtType == IOU) {
			// put in iou msg
			Log.i("ADD_DEBTOR", "in iou adder");
			name.setHint(R.string.message_iou);
		} else {
			// put in uome msg
			Log.i("ADD_DEBTOR", "in uome adder");
			name.setHint(R.string.message_uome);
		}
		
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
		
		phone = (EditText) this.findViewById(R.id.debtor_phone);
		amt = (EditText) this.findViewById(R.id.amt_dollars);
		description = (EditText) this.findViewById(R.id.debt_description);
		
		DebtEntry debt = new DebtEntry(name.getText().toString(), phone.getText().toString(), 
				amt.getText().toString(), description.getText().toString());
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("debt_type", debtType);
		returnIntent.putExtra("new_debt", debt);
		setResult(RESULT_OK,returnIntent);   
		finish();
	}
}
