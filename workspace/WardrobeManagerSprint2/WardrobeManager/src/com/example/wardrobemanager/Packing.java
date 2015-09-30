package com.example.wardrobemanager;

import java.util.ArrayList;
import java.util.HashMap; 

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Packing extends Activity
{
	RelativeLayout rlDefaultName;

	LinearLayout llForDisplay,linearHr1,linearHr2,linearHr3,linearHr4;

	ScrollView LinearSummary;

	GridView gridClothes , gridBags , gridFootwear , gridAccesories;

	DatabaseHandler database;

	SharedPreferences myPrefs; 

	Button mainComplete;

	TextView backFinal;

	SharedPreferences.Editor prefsEditor;

	GridAdapter gAdapter;

	Button bDoneSelecting;

	EditText edTitle;

	PackingList packingList;

	ListView listPacking;

	ArrayList<HashMap<String,String>> listForPacking = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForPackingDetail = new ArrayList<HashMap<String,String>>();

	TextView tvClothes, tvFootwear , tvAccessories,tvBags,tvAddItem;

	ArrayList<HashMap<String,String>> listForClothes = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForBags = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForAccesories = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> listForfootwear = new ArrayList<HashMap<String,String>>();

	ArrayList<CheckBox> cbArray = new ArrayList<CheckBox>();

	ArrayList<HashMap<String,String>> selectedlistForClothes = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForBags = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForAccesories = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForfootwear = new ArrayList<HashMap<String,String>>();


	ArrayList<HashMap<String,String>> selectedlistForClothes1 = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForBags1 = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForAccesories1 = new ArrayList<HashMap<String,String>>();

	ArrayList<HashMap<String,String>> selectedlistForfootwear1 = new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.packing);

		initialise();

		listner();

		try
		{
			listForPacking = database.getpackingList();

			System.out.println("PACKING LIST "+ listForPacking );

			packingList = new PackingList(listForPacking);

			listPacking.setAdapter(packingList);

			if( listForPacking.size() > 0 )
			{
				llForDisplay.setVisibility(View.GONE);

				listPacking.setVisibility(View.VISIBLE);

				rlDefaultName.setVisibility(View.GONE);
			}

			listForClothes = database.getItems("Clothes");

			gAdapter = new GridAdapter(listForClothes,"Clothes");

			gridClothes.setAdapter(gAdapter);

			listForAccesories = database.getItems("Accessories");

			gAdapter = new GridAdapter(listForAccesories,"Accessories");

			gridAccesories.setAdapter(gAdapter);

			listForBags = database.getItems("Bags");

			gAdapter = new GridAdapter(listForBags,"Bags");

			gridBags.setAdapter(gAdapter);

			listForfootwear = database.getItems("Footwear");

			//System.out.println("LIST "+listForfootwear);

			gAdapter = new GridAdapter(listForfootwear,"Footwear");

			gridFootwear.setAdapter(gAdapter);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void initialise() 
	{ 
		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit();  

		rlDefaultName = (RelativeLayout) findViewById(R.id.rlDefaultName);

		llForDisplay = (LinearLayout) findViewById(R.id.llForDisplay);

		mainComplete = (Button) findViewById(R.id.mainComplete);

		backFinal = (TextView) findViewById(R.id.backFinal);

		database = new DatabaseHandler(getApplicationContext()); 

		tvAccessories = (TextView) findViewById(R.id.tvAccessories);

		tvClothes = (TextView) findViewById(R.id.tvClothes);

		tvBags = (TextView) findViewById(R.id.tvBags);

		tvFootwear = (TextView) findViewById(R.id.tvFootwear);

		tvAddItem = (TextView) findViewById(R.id.tvAddItem);

		gridAccesories = (GridView) findViewById(R.id.gridAccesories);

		gridBags = (GridView) findViewById(R.id.gridBags);

		gridClothes = (GridView) findViewById(R.id.gridClothes);

		gridFootwear = (GridView) findViewById(R.id.gridFootwear); 

		bDoneSelecting =(Button) findViewById(R.id.bDoneSelecting);

		LinearSummary = (ScrollView) findViewById(R.id.LinearSummary);

		linearHr1 = (LinearLayout) findViewById(R.id.linearHr1);

		linearHr2 = (LinearLayout) findViewById(R.id.linearHr2);

		linearHr3 = (LinearLayout) findViewById(R.id.linearHr3);

		linearHr4 = (LinearLayout) findViewById(R.id.linearHr4);

		edTitle = (EditText) findViewById(R.id.edTitle);

		listPacking = (ListView) findViewById(R.id.listPacking);
	}

	private void listner() 
	{ 
		tvAccessories.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				gridAccesories.setVisibility(View.VISIBLE);
				gridBags.setVisibility(View.GONE);
				gridFootwear.setVisibility(View.GONE);
				gridClothes.setVisibility(View.GONE);

				bDoneSelecting.setVisibility(View.GONE);

				try
				{
					listForAccesories = database.getItems("Accessories");

					gAdapter = new GridAdapter(listForAccesories,"Accessories");

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

				bDoneSelecting.setVisibility(View.GONE);

				try
				{
					listForClothes = database.getItems("Clothes");

					gAdapter = new GridAdapter(listForClothes,"Clothes");

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

				bDoneSelecting.setVisibility(View.VISIBLE);

				try
				{ 

					listForBags = database.getItems("Bags");

					gAdapter = new GridAdapter(listForBags,"Bags");

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

				bDoneSelecting.setVisibility(View.GONE);

				try
				{ 
					listForfootwear = database.getItems("Footwear");

					gAdapter = new GridAdapter(listForfootwear,"Footwear");

					gridFootwear.setAdapter(gAdapter);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});	
	}

	@SuppressLint("InlinedApi")
	public void DoneSelecting(View v) 
	{
		System.out.println("LIST BAGS "+selectedlistForBags.size());
		System.out.println("LIST Accesories "+selectedlistForAccesories.size());
		System.out.println("LIST Clothes "+selectedlistForClothes.size());
		System.out.println("LIST Footwear "+selectedlistForfootwear.size());

		linearHr1.removeAllViews();

		linearHr2.removeAllViews();

		linearHr3.removeAllViews();

		linearHr4.removeAllViews();

		for (int i = 0; i < selectedlistForClothes.size(); i++)
		{
			try
			{
				ImageView imageView = new ImageView(this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = selectedlistForClothes.get(i).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr1.addView(imageView);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
		for (int q = 0; q < selectedlistForBags.size(); q++)
		{
			try
			{
				ImageView imageView = new ImageView(this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = selectedlistForBags.get(q).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr2.addView(imageView);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
		for (int j = 0; j < selectedlistForfootwear.size(); j++)
		{
			try
			{
				ImageView imageView = new ImageView(this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = selectedlistForfootwear.get(j).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr3.addView(imageView);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
		for (int ii = 0; ii < selectedlistForAccesories.size(); ii++)
		{
			try
			{
				ImageView imageView = new ImageView(this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = selectedlistForAccesories.get(ii).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr4.addView(imageView);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		LinearSummary.setVisibility(View.VISIBLE);

		mainComplete.setVisibility(View.VISIBLE);

		edTitle.setEnabled(true);

		llForDisplay.setVisibility(View.GONE);

		bDoneSelecting.setVisibility(View.GONE);

		backFinal.setVisibility(View.INVISIBLE);
	}

	public void BackClickFromFinal(View v)
	{
		LinearSummary.setVisibility(View.GONE);

		llForDisplay.setVisibility(View.GONE);

		bDoneSelecting.setVisibility(View.GONE);

		listPacking.setVisibility(View.VISIBLE);

		listForPackingDetail.clear();

	}
	int strCount=0;

	public void DoneSelectingForDB(View v)
	{
		if(!edTitle.getText().toString().equals(""))
		{
			LinearSummary.setVisibility(View.GONE);

			llForDisplay.setVisibility(View.VISIBLE);

			bDoneSelecting.setVisibility(View.GONE);

			for (int i = 0; i < selectedlistForClothes.size(); i++)
			{
				try
				{				
					database.open();

					database.createEntry_for_packing(selectedlistForClothes.get(i).get("item_code"),
							selectedlistForClothes.get(i).get("pic"), "Clothes", edTitle.getText().toString(),
							myPrefs.getString("auto_id", String.valueOf(strCount))); 

					System.out.println("GOING DATA CLOTHES"+ selectedlistForClothes.get(i).get("item_code") );

					database.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}	
			for (int q = 0; q < selectedlistForBags.size(); q++)
			{
				try
				{				
					database.open();

					database.createEntry_for_packing(selectedlistForBags.get(q).get("item_code"),
							selectedlistForBags.get(q).get("pic"), "Bags", 
							edTitle.getText().toString(),myPrefs.getString("auto_id", String.valueOf(strCount)));


					System.out.println("GOING DATA Bags"+ selectedlistForBags.get(q).get("item_code") );

					database.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}	
			for (int j = 0; j < selectedlistForfootwear.size(); j++)
			{
				try
				{				
					database.open();

					database.createEntry_for_packing(selectedlistForfootwear.get(j).get("item_code"),
							selectedlistForfootwear.get(j).get("pic"), "Footwear",
							edTitle.getText().toString(),myPrefs.getString("auto_id", String.valueOf(strCount)));

					System.out.println("GOING DATA Footwear "+ selectedlistForfootwear.get(j).get("item_code") );

					database.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}	
			for (int ii = 0; ii < selectedlistForAccesories.size(); ii++)
			{
				try
				{				
					database.open();

					database.createEntry_for_packing(selectedlistForAccesories.get(ii).get("item_code"),
							selectedlistForAccesories.get(ii).get("pic"), "Accesories", 

							edTitle.getText().toString(),myPrefs.getString("auto_id", String.valueOf(strCount)));

					System.out.println("GOING DATA Accesories "+ selectedlistForAccesories.get(ii).get("item_code") );


					database.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}	 

			database.open(); 

			database.createEntry_for_PACKING_LIST_NAME(edTitle.getText().toString(),
					myPrefs.getString("auto_id", String.valueOf(strCount)));

			database.close();

			strCount=strCount+1;

			prefsEditor.putString("auto_id", String.valueOf(strCount)).commit();

			listForPacking.clear();

			listForPacking = database.getpackingList();

			System.out.println("PACKING LIST "+ listForPacking.size());

			packingList = new PackingList(listForPacking);

			listPacking.setAdapter(packingList);

			listPacking.setVisibility(View.VISIBLE);

			llForDisplay.setVisibility(View.GONE);
		}
		else
		{
			createErrorDialog("Please Enter Packing List name");
		}
	}

	public void Home(View v) 
	{
		Intent i = new Intent(this,Home.class);

		startActivity(i);

		finish();
	}

	public void Inventory(View v) 
	{
		Intent i = new Intent(this,Inventory.class);

		startActivity(i);

		finish();
	}

	public void Calendar(View v) 
	{

	}

	public void CreateNew(View v) 
	{
		rlDefaultName.setVisibility(View.GONE);

		llForDisplay.setVisibility(View.VISIBLE);

		listPacking.setVisibility(View.GONE);

		LinearSummary.setVisibility(View.GONE);

		selectedlistForAccesories.clear();

		selectedlistForBags.clear();

		selectedlistForClothes.clear();

		selectedlistForfootwear.clear();

		selectedlistForAccesories1.clear();

		selectedlistForBags1.clear();

		selectedlistForClothes1.clear();

		selectedlistForfootwear1.clear();

		System.out.println("LIST BAGS "+selectedlistForBags.size());
		System.out.println("LIST Accesories "+selectedlistForAccesories.size());
		System.out.println("LIST Clothes "+selectedlistForClothes.size());
		System.out.println("LIST Footwear "+selectedlistForfootwear.size());
	}

	public void Packing(View v) 
	{

	}
	public void Help(View v) 
	{

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

	class GridAdapter extends BaseAdapter
	{
		ArrayList<HashMap<String, String>> userArray = new  ArrayList<HashMap<String,String>>(); 

		String from="";

		public GridAdapter(ArrayList<HashMap<String, String>> userArrayList,String frm ) 
		{
			this.userArray  = userArrayList; 

			this.from=frm;
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
				cView = inflater.inflate(R.layout.packing_grid_adapter,null);
			}

			final ImageView ivPic = (ImageView) cView.findViewById(R.id.ivPic); 

			CheckBox checkBox = (CheckBox) cView.findViewById(R.id.checkbox);

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


			if(from.equals("Clothes"))
			{
				HashMap<String, String> hmap = new HashMap<String, String>();

				hmap.put("item_code", String.valueOf(listForClothes.get(position).get("item_code")));

				if(selectedlistForClothes1.contains(hmap))
				{
					checkBox.setChecked(true);
				}
				else
				{
					checkBox.setChecked(false);
				}
			}

			else if(from.equals("Accessories"))
			{
				HashMap<String, String> hmap = new HashMap<String, String>();

				hmap.put("item_code", String.valueOf(listForAccesories.get(position).get("item_code")));



				if(selectedlistForAccesories1.contains(hmap))
				{
					checkBox.setChecked(true);
				}
				else
				{
					checkBox.setChecked(false);
				}
			}

			else if(from.equals("Bags"))
			{
				HashMap<String, String> hmap = new HashMap<String, String>();

				hmap.put("item_code", String.valueOf(listForBags.get(position).get("item_code")));

				if(selectedlistForBags1.contains(hmap))
				{
					checkBox.setChecked(true);
				}
				else
				{
					checkBox.setChecked(false);
				}
			}
			else
			{
				HashMap<String, String> hmap = new HashMap<String, String>();

				hmap.put("item_code", String.valueOf(listForfootwear.get(position).get("item_code")));

				if(selectedlistForfootwear1.contains(hmap))
				{
					checkBox.setChecked(true);
				}
				else
				{
					checkBox.setChecked(false);
				}
			}

			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
				{
					System.out.println("IN VIEW Adapter BEFORE CONDITION");

					if(isChecked)
					{
						System.out.println("IN VIEW Adapter IS CHECKED");

						HashMap<String, String> hmap1 = new HashMap<String, String>();

						hmap1.put("item_code", String.valueOf(userArray.get(position).get("item_code")));

						HashMap<String, String> hmap = new HashMap<String, String>();

						hmap.put("item_code", String.valueOf(userArray.get(position).get("item_code")));

						hmap.put("pic", String.valueOf(userArray.get(position).get("pic")));

						if(from.equals("Accessories"))
						{
							selectedlistForAccesories.add(hmap);

							selectedlistForAccesories1.add(hmap1);
						}
						else if(from.equals("Bags"))
						{
							selectedlistForBags.add(hmap);

							selectedlistForBags1.add(hmap1);
						}
						else if(from.equals("Clothes"))
						{
							selectedlistForClothes.add(hmap);

							selectedlistForClothes1.add(hmap1);
						}
						else
						{
							selectedlistForfootwear.add(hmap);

							selectedlistForfootwear1.add(hmap1);
						}
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

	class PackingList extends BaseAdapter
	{
		ArrayList<HashMap<String, String>> userArray = new  ArrayList<HashMap<String,String>>();

		public PackingList(ArrayList<HashMap<String, String>> userArrayList ) 
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
				cView = inflater.inflate(R.layout.packing_list_adapter,null);
			}

			final TextView tv = (TextView) cView.findViewById(R.id.tv);

			try
			{
				tv.setText(userArray.get(position).get("packing_list_name").trim()); 

				cView.setOnClickListener(new OnClickListener() 
				{	
					@Override
					public void onClick(View arg0)
					{ 
						getpackingListForID(userArray.get(position).get("id"),
								userArray.get(position).get("packing_list_name"));
					}

				});
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			return cView;
		}

	}

	@SuppressLint("InlinedApi")
	private void getpackingListForID(String id,String pckingListName) 
	{  
		System.out.println("LIST BEFORE"+ listForPackingDetail.size());

		linearHr1.removeAllViews();

		linearHr2.removeAllViews();

		linearHr3.removeAllViews();

		linearHr4.removeAllViews();

		listForPackingDetail = database.getpackingListDetail(id);

		System.out.println("LIST "+ listForPackingDetail.size());

		for (int i = 0; i < listForPackingDetail.size(); i++)
		{ 
			String type = listForPackingDetail.get(i).get("type_of_item"); 

			if(type.equals("Bags"))
			{
				ImageView imageView = new ImageView(Packing.this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = listForPackingDetail.get(i).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr2.addView(imageView);
			}
			else if(type.equals("Clothes"))
			{
				ImageView imageView = new ImageView(Packing.this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = listForPackingDetail.get(i).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr1.addView(imageView);
			}
			else if(type.equals("Accesories"))
			{
				ImageView imageView = new ImageView(Packing.this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = listForPackingDetail.get(i).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr4.addView(imageView);
			}
			else
			{
				ImageView imageView = new ImageView(Packing.this);	

				LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams
						(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				imageView.setLayoutParams(vp);

				String userImage = listForPackingDetail.get(i).get("pic");

				Bitmap bitmap = convertBase64ToBitmap(userImage);

				imageView.setImageBitmap(bitmap);

				linearHr3.addView(imageView);
			}
		}

		LinearSummary.setVisibility(View.VISIBLE);

		edTitle.setText(pckingListName);

		edTitle.setEnabled(false);

		mainComplete.setVisibility(View.GONE);

		listPacking.setVisibility(View.GONE);

		backFinal.setVisibility(View.VISIBLE);
	}

}
