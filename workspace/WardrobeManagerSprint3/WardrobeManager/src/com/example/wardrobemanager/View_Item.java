package com.example.wardrobemanager;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class View_Item extends Activity implements  CustomLayout.Listener
{
	TextView tvClothes, tvFootwear , tvAccessories,tvBags,tvAddItem; 

	DatabaseHandler database;

	SharedPreferences myPrefs; 

	SharedPreferences.Editor prefsEditor;  

	ScrollView llForAdding;

	LinearLayout llBelow;

	TextView edDateOfPurchase,edLastAccessedDate;
	
	CheckBox rainySeason;

	EditText edTitle ,    edBrand , edColor ;

	Spinner spinnerSeason , spinnerItem , spinnerdressCode , spinnerlocation;

	public String[] seasonArray = new String[]{"Winter","Summer","Rainy","Autumn"};

	public String[] seasonItemType = new String[]{"Clothes","Bags","Accessories","Footwear"};

	public String[] seasonDressCode = new String[]{"Casual","Formal"};

	public String[] seasonlocation = new String[]{"Wardrobe1","Wardrobe2"};

	HashMap<String,String>  items = new  HashMap<String,String> (); 

	Calendar myCalendar = Calendar.getInstance();

	Calendar myCalendar1 = Calendar.getInstance();

	CustomLayout  customLayout;

	Button bDone,bDelete,bEdit,bUndo;

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

			myCalendar.set(Calendar.YEAR, year);

			myCalendar.set(Calendar.MONTH, monthOfYear);

			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			updateLabel();
		}
	};

	DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

			myCalendar1.set(Calendar.YEAR, year);

			myCalendar1.set(Calendar.MONTH, monthOfYear);

			myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			updateLabel1();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_item);

		initialise();

		listner();

		try
		{
			items =    (HashMap<String, String>) getIntent().getSerializableExtra("item_information");//get intent from item click to show particular result

			System.out.println("HERE "+ items);

			displayResult(); // method to display result for particular item

		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
 

	private void displayResult() 
	{
		if(items.get("rainy_season").equals("1"))
		{
			rainySeason.setChecked(true);
		}
		else
		{
			rainySeason.setChecked(false);
		}
		
		edTitle.setText(items.get("title"));
		
		tvAddItem.setText("Item Code :"+items.get("item_code"));

		edDateOfPurchase.setText(items.get("date_purchase"));

		edLastAccessedDate.setText(items.get("last_accessed_date"));

		edColor.setText(items.get("color"));

		edBrand.setText(items.get("brand"));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonArray );

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerSeason.setAdapter(adapter);

		int spinnerPosition = adapter.getPosition(items.get("season"));

		spinnerSeason.setSelection(spinnerPosition);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonDressCode );

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerdressCode.setAdapter(adapter1);

		int spinnerPosition1 = adapter1.getPosition(items.get("dress_code"));

		spinnerdressCode.setSelection(spinnerPosition1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonItemType );

		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerItem.setAdapter(adapter2);

		int spinnerPosition12 = adapter2.getPosition(items.get("type_of_item"));

		spinnerItem.setSelection(spinnerPosition12);

		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonlocation);

		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerlocation.setAdapter(adapter3);

		int spinnerPosition123 = adapter3.getPosition(items.get("location"));

		spinnerlocation.setSelection(spinnerPosition123);

	}

	private void listner() 
	{
		edDateOfPurchase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				new DatePickerDialog( View_Item.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		edLastAccessedDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				new DatePickerDialog( View_Item.this, date1, myCalendar1
						.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
						myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	private void updateLabel() 
	{
		String myFormat = "MM/dd/yy"; //In which you need put here

		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		edDateOfPurchase.setText(sdf.format(myCalendar.getTime()));
	}

	private void updateLabel1() 
	{
		String myFormat = "MM/dd/yy"; //In which you need put here

		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		edLastAccessedDate.setText(sdf.format(myCalendar1.getTime()));
	}

	private void initialise() 
	{
		customLayout=(CustomLayout)findViewById(R.id.parentLayout);

		customLayout.setListener(this);

		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit();  

		bDone = (Button)findViewById(R.id.bDone);

		bDelete = (Button)findViewById(R.id.bDelete);

		bEdit = (Button)findViewById(R.id.bEdit);

		bUndo = (Button)findViewById(R.id.bUndo);

		llBelow = (LinearLayout) findViewById(R.id.llBelow);

		database = new DatabaseHandler(getApplicationContext());

		tvAccessories = (TextView) findViewById(R.id.tvAccessories);

		tvClothes = (TextView) findViewById(R.id.tvClothes);

		tvBags = (TextView) findViewById(R.id.tvBags);

		tvFootwear = (TextView) findViewById(R.id.tvFootwear); 

		tvAddItem = (TextView) findViewById(R.id.tvAddItem);

		llForAdding = (ScrollView) findViewById(R.id.llForAdding);

		edBrand = (EditText) findViewById(R.id.edBrand);

		edColor = (EditText) findViewById(R.id.edColor);

		edDateOfPurchase = (TextView) findViewById(R.id.edDateOfPurchase);

		edLastAccessedDate = (TextView) findViewById(R.id.edLastAccessedDate);

		edTitle = (EditText) findViewById(R.id.edTitle);

		spinnerdressCode = (Spinner) findViewById(R.id.spinnerdressCode);

		spinnerItem= (Spinner) findViewById(R.id.spinnerItem);

		spinnerlocation = (Spinner) findViewById(R.id.spinnerlocation);

		spinnerSeason = (Spinner) findViewById(R.id.spinnerSeason);

		rainySeason =(CheckBox) findViewById(R.id.rainySeason);
	}

	public void Home(View v) 
	{
		Intent i = new Intent(this,Home.class);

		startActivity(i);

		finish();
	}

	public void AddItemToDb(View v ) 
	{
		if(!edTitle.getText().toString().equals("") && !edBrand.getText().toString().equals("") 

				&& !edDateOfPurchase.getText().toString().equals("") 

				&& !edLastAccessedDate.getText().toString().equals("")

				&&!edColor.getText().toString().equals(""))
		{

			database.open();

			database.update_(edTitle.getText().toString(), 

					spinnerSeason.getSelectedItem().toString(),

					spinnerItem.getSelectedItem().toString(), edDateOfPurchase.getText().toString(),

					edBrand.getText().toString(), spinnerdressCode.getSelectedItem().toString(),

					spinnerlocation.getSelectedItem().toString(), edColor.getText().toString(), 

					edLastAccessedDate.getText().toString(),items.get("item_code"),rainySeason.isChecked());

			database.close(); 

			bDelete.setVisibility(View.VISIBLE);

			bDone.setVisibility(View.GONE);

			bEdit.setVisibility(View.VISIBLE);

			bUndo.setVisibility(View.GONE);

			setEnabled(false);

		}
		else
		{
			createErrorDialog("All Fields manadatory","");
		}
	}

	public void backToDisplay(View v) 
	{
		Intent i = new Intent(View_Item.this,Inventory.class);

		startActivity(i);

		finish();
	}



	public void Inventory(View v) 
	{
		Intent i = new Intent(View_Item.this,Inventory.class);

		startActivity(i);
	}

	public void Calendar(View v) 
	{
		Intent i = new Intent(this,MyCalendarActivity.class);

		startActivity(i);
	}

	public void Edit(View v) 
	{ 
		bDelete.setVisibility(View.GONE);

		bDone.setVisibility(View.VISIBLE);

		bEdit.setVisibility(View.GONE);

		bUndo.setVisibility(View.VISIBLE);

		setEnabled(true);
	}

	public void Undo(View v)
	{
		bDelete.setVisibility(View.VISIBLE);

		bDone.setVisibility(View.GONE);

		bEdit.setVisibility(View.VISIBLE);

		bUndo.setVisibility(View.GONE);

		setEnabled(false);

		displayResult();
	}

	private void setEnabled(boolean b) 
	{
		rainySeason.setEnabled(b);
		
		edTitle.setEnabled(b);

		edBrand.setEnabled(b);

		edColor.setEnabled(b);

		edDateOfPurchase.setEnabled(b);

		edLastAccessedDate.setEnabled(b);

		spinnerdressCode.setClickable(b);

		spinnerItem.setClickable(b);

		spinnerlocation.setClickable(b);

		spinnerSeason.setClickable(b);
	}

	public void Delete(View v) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage("Are you sure you wish to delete this item ?")

		.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
				
				database.open();

				database.delete_byID(items.get("item_code"));

				database.close(); 
				
				createErrorDialog("You have successfully deleted Item Code :"+items.get("item_code"),"finish");
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
			}
		});

		AlertDialog settingsErrDialog = builder.create();

		try
		{
			settingsErrDialog.show();
		}
		catch(Exception e)
		{}
	}

	public void Packing(View v) 
	{
		Intent i = new Intent(this,Packing.class);

		startActivity(i);
	}

	public void Help(View v) 
	{
		Intent i = new Intent(this,Help.class);

		startActivity(i);
	}
	public void Logout(View v) 
	{
		String username = myPrefs.getString("email", "");

		String rememberMe =myPrefs.getString("remember_me", "");

		prefsEditor.clear();

		prefsEditor.commit();

		if(rememberMe.equals("true"))
		{
			prefsEditor.putString("email", username).commit();

			prefsEditor.putString("remember_me", "true").commit();
		}

		Intent i = new Intent(this,Login.class);

		startActivity(i);

		finish();

	}

	private static final int CAMERA_REQUEST = 1888; 

	String base64="";


	public String convertBitMapToBase64(Bitmap bm)
	{ 
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  

		bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

		byte[] byteArray = byteArrayOutputStream .toByteArray();

		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return encoded;
	}


	protected Bitmap convertBase64ToBitmap(String base64) 
	{
		byte[] decodedString =  android.util.Base64.decode(base64, android.util.Base64.DEFAULT);

		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

		return decodedByte;

	}

	public void createErrorDialog(String errMsg,final String err) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(errMsg)

		.setCancelable(false);

		builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
				
				if (err.equals("finish")) 
				{
					finish();
				}
			}
		});

		AlertDialog settingsErrDialog = builder.create();

		try
		{
			settingsErrDialog.show();
		}
		catch(Exception e)
		{}
	}

	@Override
	public void onSoftKeyboardShown(boolean isShowing) 
	{
		if (isShowing==true)
		{
			System.out.println("key :    keyboard is open");
			llBelow.setVisibility(View.GONE);

		}		
		else {

			llBelow.setVisibility(View.VISIBLE);
			System.out.println("key :    keyboard is close");
		}

	}


}

