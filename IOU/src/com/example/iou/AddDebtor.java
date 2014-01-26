package com.example.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.iou.MainActivity.DebtEntry;

public class AddDebtor extends Activity{

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
		
		name = (EditText) findViewById(R.id.debtor_name);
		
		Spinner spinner = (Spinner) findViewById(R.id.debt_type_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.debt_types_array, 
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,
					long id) {
				// An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
		    	String p = (String) parent.getItemAtPosition(pos);
		    	Log.i("ADDDEBTOR", ""+ pos);
		    	//Object item = av.getSelectedItem();
		    	Log.i("ADD_DEBTOR", p);
		    	if (pos == IOU){
		    		name.setHint(R.string.message_iou);
		    		debtType = IOU;
		    	} else {
		    		name.setHint(R.string.message_uome);
		    		debtType = UOME;
		    	}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
