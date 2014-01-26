package com.example.iou;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iou.VenmoLibrary.VenmoResponse;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private final static int IOU = 0;
	private final static int UOME = 1;
	private final static int ADD_DEBTOR_ACTIVITY = 1;
	private final static int VENMO_ACTIVITY = 2;
	private final String DATA_LIST = "data_list";
	
	public static ArrayList<DebtEntry> debt_data_list = new ArrayList<DebtEntry>(); //IOU
	public static ArrayList<DebtEntry> debtors_data_list = new ArrayList<DebtEntry>(); //UOME
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		debt_data_list.add(new DebtEntry("Melissa", "9739309163", "5", "Target"));
		debtors_data_list.add(new DebtEntry("Madison", "5037522173", "20", "Dinner"));

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the two
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			args.putSerializable(DATA_LIST, debt_data_list);
			
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.iou).toUpperCase(l);
			case 1:
				return getString(R.string.uome).toUpperCase(l);
			}
			return null;
		}
		
	}
	

	
	
	/*
	 * Data class for storing a debt
	 */
	
	public static class DebtEntry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String person;
		private String phone;
		private String amount;
		private String description;
		
		// constructor
		public DebtEntry(String p, String ph, String a, String d){
			person = p;
			phone = ph;
			amount = a;
			description = d;
		}
		
		public String getPerson() {
			return person;
		}
		
		public String getPhone() {
			return phone;
		}
		
		public String getAmount() {
			return amount;
		}
		
		public String getDescription() {
			return description;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		DebtAdapter debtAdapter;
		ArrayList<DebtEntry> debt_list;

		public DummySectionFragment() {
			//this.setRetainInstance(true);  //should keep the fragment working on a phone rotation
			Log.d("IOU", "trying to implement serializable");
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			
			Context context = getActivity();
			
			int tab = getArguments().getInt(ARG_SECTION_NUMBER);
			if (tab == 1) {
				dummyTextView.setText("IOU");
				debt_list = debt_data_list;
			} else if (tab == 2) {
				dummyTextView.setText("UOMe");
				debt_list = debtors_data_list;
			}
			debtAdapter = new DebtAdapter(debt_list, context, tab);
			
			ListView lv = (ListView) rootView.findViewById(R.id.debt_list);
			lv.setItemsCanFocus(true);
			lv.setAdapter(debtAdapter);
			
			
			return rootView;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);

		 }
		
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.add_debtor_button, menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.action_add_debtor:
		        	Intent intent = new Intent(getActivity(), AddDebtor.class);
		        	int debtType = (getArguments().getInt(ARG_SECTION_NUMBER) == 1)? IOU : UOME;
		        	intent.putExtra("debt_type", debtType);
	                startActivityForResult(intent, ADD_DEBTOR_ACTIVITY);
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data){
			// returned from add debtor activity
			if (requestCode == ADD_DEBTOR_ACTIVITY && resultCode == RESULT_OK){
				Log.i("onActivityResult","returned from adding debtor");
				
				/* add some data to our data structure */
				if (data.getIntExtra("debt_type", 0) == IOU){
					debt_data_list.add((DebtEntry) data.getSerializableExtra("new_debt"));
				} else if (data.getIntExtra("debt_type", 0) == UOME){
					debtors_data_list.add((DebtEntry) data.getSerializableExtra("new_debt"));
				}
				
				/* notify adapter of data set changed */
				debtAdapter.notifyDataSetChanged();
			} 
			// returned from venmo activity
			else if (requestCode == VENMO_ACTIVITY) {
				Log.i("onActivityResult", "returned from venmo");
				if(resultCode == RESULT_OK) {
	                String signedrequest = data.getStringExtra("signedrequest");
	                if(signedrequest != null) {
	                    VenmoResponse response = (new VenmoLibrary()).validateVenmoPaymentResponse(signedrequest, "J7mezsbJSANyk28VvxPV5aUQZugzMfTG");
	                    if(response.getSuccess().equals("1")) {
	                        //Payment successful.  Use data from response object to display a success message
	                        String description = response.getNote();
	                        String amount = response.getAmount();
	                        String id = response.getPaymentId();
	                        Toast.makeText(getActivity(), id + ": Paid " + amount + " for " + description, Toast.LENGTH_SHORT).show();
	                        // TODO remove corresponding item from listview
	                    }
	                }
	                else {
	                    String error_message = data.getStringExtra("error_message");
	                    //An error occurred.  Make sure to display the error_message to the user
	                    Toast.makeText(getActivity(), "Error occurred during transaction: " + error_message, Toast.LENGTH_SHORT).show();
	                    // don't delete item
	                }
				} else if(resultCode == RESULT_CANCELED) {
	                //The user cancelled the payment
					Toast.makeText(getActivity(), "Transaction cancelled", Toast.LENGTH_SHORT).show();
					// don't delete item
	            }
			}
		}
	}

}
