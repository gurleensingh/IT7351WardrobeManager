package com.example.wardrobemanager;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class Inventory extends Activity implements  CustomLayout.Listener
{
	TextView tvClothes, tvFootwear , tvAccessories,tvBags,tvAddItem;

	GridView gridClothes , gridBags , gridFootwear , gridAccesories;

	DatabaseHandler database;

	SharedPreferences myPrefs; 

	SharedPreferences.Editor prefsEditor;

	GridAdapter gAdapter;

	LinearLayout llForDisplay ,llBelow;

	ScrollView llForAdding;
	
	CheckBox rainySeason;

	TextView edDateOfPurchase,edLastAccessedDate;

	EditText edTitle ,    edBrand , edColor ;

	Spinner spinnerSeason , spinnerItem , spinnerdressCode , spinnerlocation;

	public String[] seasonArray = new String[]{"Winter","Summer","Spring","Autumn"}; //array to show on spinner click

	public String[] seasonItemType = new String[]{"Clothes","Bags","Accessories","Footwear"};

	public String[] seasonDressCode = new String[]{"Casual","Formal"};

	public String[] seasonlocation = new String[]{"Wardrobe1","Wardrobe2"};

	ArrayList<HashMap<String,String>> listForClothes = new ArrayList<HashMap<String,String>>(); //arraylist to store data returning from database

	ArrayList<HashMap<String,String>> listForBags = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForAccesories = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForfootwear = new ArrayList<HashMap<String,String>>();

	Calendar myCalendar = Calendar.getInstance(); //Calendar instance for date

	Calendar myCalendar1 = Calendar.getInstance();

	CustomLayout  customLayout;

	String forItemCode="S000";

	static int count=0;

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

		setContentView(R.layout.inventory);

		initialise();

		listner();

		try
		{
			listForClothes = database.getItems("Clothes"); // database method to fetch result for clothes section

			gAdapter = new GridAdapter(listForClothes); // adapter to show the result

			gridClothes.setAdapter(gAdapter); // gridview to set adapter

			listForAccesories = database.getItems("Accessories");

			gAdapter = new GridAdapter(listForAccesories);

			gridAccesories.setAdapter(gAdapter);

			listForBags = database.getItems("Bags");

			gAdapter = new GridAdapter(listForBags);

			gridBags.setAdapter(gAdapter);

			listForfootwear = database.getItems("Footwear");

			gAdapter = new GridAdapter(listForfootwear);

			gridFootwear.setAdapter(gAdapter);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void listner() //listener to work on different tap's
	{
		edDateOfPurchase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				new DatePickerDialog( Inventory.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		edLastAccessedDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				new DatePickerDialog( Inventory.this, date1, myCalendar1
						.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
						myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		tvAccessories.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				gridAccesories.setVisibility(View.VISIBLE);
				gridBags.setVisibility(View.GONE);
				gridFootwear.setVisibility(View.GONE);
				gridClothes.setVisibility(View.GONE);


				try
				{
					listForAccesories = database.getItems("Accessories");

					gAdapter = new GridAdapter(listForAccesories);

					gridAccesories.setAdapter(gAdapter);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});	

		tvClothes.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				gridAccesories.setVisibility(View.GONE);
				gridBags.setVisibility(View.GONE);
				gridFootwear.setVisibility(View.GONE);
				gridClothes.setVisibility(View.VISIBLE);


				try
				{
					listForClothes = database.getItems("Clothes");

					gAdapter = new GridAdapter(listForClothes);

					gridClothes.setAdapter(gAdapter);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});	

		tvBags.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				gridAccesories.setVisibility(View.GONE);
				gridBags.setVisibility(View.VISIBLE);
				gridFootwear.setVisibility(View.GONE);
				gridClothes.setVisibility(View.GONE);


				try
				{ 

					listForBags = database.getItems("Bags");

					gAdapter = new GridAdapter(listForBags);

					gridBags.setAdapter(gAdapter); 

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});	
		tvFootwear.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				gridAccesories.setVisibility(View.GONE);
				gridBags.setVisibility(View.GONE);
				gridFootwear.setVisibility(View.VISIBLE);
				gridClothes.setVisibility(View.GONE);


				try
				{ 
					listForfootwear = database.getItems("Footwear");

					gAdapter = new GridAdapter(listForfootwear);

					gridFootwear.setAdapter(gAdapter);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
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

	private void initialise() //we have to initialize each and everything we use from layout in order to use them
	{
		customLayout=(CustomLayout)findViewById(R.id.parentLayout);

		customLayout.setListener(this);
		
		rainySeason = (CheckBox) findViewById(R.id.rainySeason);

		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit(); 

		llBelow = (LinearLayout) findViewById(R.id.llBelow);

		database = new DatabaseHandler(getApplicationContext());

		tvAccessories = (TextView) findViewById(R.id.tvAccessories);

		tvClothes = (TextView) findViewById(R.id.tvClothes);

		tvBags = (TextView) findViewById(R.id.tvBags);

		tvFootwear = (TextView) findViewById(R.id.tvFootwear);

		gridAccesories = (GridView) findViewById(R.id.gridAccesories);

		gridBags = (GridView) findViewById(R.id.gridBags);

		gridClothes = (GridView) findViewById(R.id.gridClothes);

		gridFootwear = (GridView) findViewById(R.id.gridFootwear);

		llForDisplay = (LinearLayout) findViewById(R.id.llForDisplay);

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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonArray );

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerSeason.setAdapter(adapter);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonDressCode );

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerdressCode.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonItemType );

		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerItem.setAdapter(adapter2);

		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,seasonlocation);

		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerlocation.setAdapter(adapter3);
	}

	public void Home(View v) 
	{
		Intent i = new Intent(this,Home.class);

		startActivity(i);

		finish();
	}

	public void AddItemToDb(View v) //this is the method to add record's to database
	{
		if(!edTitle.getText().toString().equals("") && !edBrand.getText().toString().equals("") 

				&& !edDateOfPurchase.getText().toString().equals("") 

				&& !edLastAccessedDate.getText().toString().equals("")

				&&!edColor.getText().toString().equals(""))
		{
			if(!base64.equals(""))
			{
				database.open();

				database.createEntry_for_addItem(edTitle.getText().toString(), 

						spinnerSeason.getSelectedItem().toString(),

						spinnerItem.getSelectedItem().toString(), edDateOfPurchase.getText().toString(),

						edBrand.getText().toString(), spinnerdressCode.getSelectedItem().toString(),

						spinnerlocation.getSelectedItem().toString(), edColor.getText().toString(), 
 
						edLastAccessedDate.getText().toString(),base64,forItemCode+myPrefs.getInt("count", 0),
						rainySeason.isChecked());

				database.close();

				System.out.println("CHAKK "+ rainySeason.isChecked());
				
				SuccesfullMessage(forItemCode+myPrefs.getInt("count", 0)+" Item Code Succesfully added to the inventory");
 			
			}
		}
		else
		{
			createErrorDialog("All Fields manadatory");
		}
	}

	public void backToDisplay(View v) //work's on back click
	{
		if(llForAdding.getVisibility() == View.VISIBLE)
		{
			llForAdding.setVisibility(View.GONE);

			llForDisplay.setVisibility(View.VISIBLE);

			tvAddItem.setVisibility(View.VISIBLE);
		}
	}

	public void AddAnItem(View v) 
	{
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); //for camera to open

		startActivityForResult(cameraIntent, CAMERA_REQUEST); 	
	}

	public void Inventory(View v) 
	{

	}

	public void Calendar(View v) 
	{
		Intent i = new Intent(this,MyCalendarActivity.class);

		startActivity(i);
	}

	public void StyleExpert(View v) 
	{

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

	public void Picture(View v)
	{
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 

		startActivityForResult(cameraIntent, CAMERA_REQUEST); 	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) //after we get an image from camera
	{  
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) 
		{  
			Bitmap photo = (Bitmap) data.getExtras().get("data"); 

			base64 = convertBitMapToBase64(photo);

			//System.out.println("  "+base64);

			if(llForDisplay.getVisibility() == View.VISIBLE)
			{
				llForAdding.setVisibility(View.VISIBLE);

				llForDisplay.setVisibility(View.GONE);

				tvAddItem.setVisibility(View.GONE);
			}
		}  
	}

	public String convertBitMapToBase64(Bitmap bm)
	{ 
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  

		bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

		byte[] byteArray = byteArrayOutputStream .toByteArray();

		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return encoded;
	}

	class GridAdapter extends BaseAdapter
	{
		ArrayList<HashMap<String, String>> userArray = new  ArrayList<HashMap<String,String>>(); 

		public GridAdapter(ArrayList<HashMap<String, String>> userArrayList ) 
		{
			this.userArray  = userArrayList; 
		}

		@Override
		public int getCount() 
		{
			return userArray.size();
		}

		@Override
		public Object getItem(int arg0) 
		{
			return userArray.get(arg0);
		}

		@Override
		public long getItemId(int arg0) 
		{
			return 0;
		}

		@Override
		public View getView(final int position, View cView, ViewGroup arg2) 
		{
			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if(cView==null)
			{
				cView = inflater.inflate(R.layout.grid_adapter,null); // layout for adapter
			}

			final ImageView ivPic = (ImageView) cView.findViewById(R.id.ivPic); 

			try
			{
				String userImage = userArray.get(position).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				ivPic.setImageBitmap(bitmap);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			cView.setOnClickListener(new OnClickListener() // on picture click 
			{
				@Override
				public void onClick(View arg0) 
				{
					try
					{
						Intent i = new Intent(Inventory.this,View_Item.class);

						i.putExtra("item_information", userArray.get(position));

						startActivity(i);

					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			});

			return cView;
		}
	}

	protected Bitmap convertBase64ToBitmap(String base64) 
	{
		byte[] decodedString =  android.util.Base64.decode(base64, android.util.Base64.DEFAULT);

		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

		return decodedByte;

	}

	public void SuccesfullMessage(String errMsg) //Alert used to show error
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
				
				count = myPrefs.getInt("count", 0);
				
				count = count+1;

				prefsEditor.putInt("count", count).commit();

				System.out.println("COUNT "+forItemCode+count);

				base64="";

				if(llForAdding.getVisibility() == View.VISIBLE)
				{
					listForClothes = database.getItems("Clothes"); // database method to fetch result for clothes section

					gAdapter = new GridAdapter(listForClothes); // adapter to show the result

					gridClothes.setAdapter(gAdapter); 
					
					llForAdding.setVisibility(View.GONE);

					llForDisplay.setVisibility(View.VISIBLE);

					tvAddItem.setVisibility(View.VISIBLE);
				}
				
				edTitle.setText("");
				
				edColor.setText("");
		 		
				edDateOfPurchase.setText("");
				
				edLastAccessedDate.setText("");
				
				edBrand.setText("");
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
	
	public void createErrorDialog(String errMsg) //Alert used to show error
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

