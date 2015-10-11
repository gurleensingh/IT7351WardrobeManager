package com.example.wardrobemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

public class Home extends Activity {

	DatabaseHandler database;

	SharedPreferences myPrefs; 

	SharedPreferences.Editor prefsEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home); //define layout for this class

		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit(); 

		database = new DatabaseHandler(getApplicationContext()); //initialize 

		exportDatabse("Wardrobe_db"); //to export database from device 
	}

	@SuppressWarnings("resource")
	public void exportDatabse(String databaseName)
	{
		try 
		{
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();    

			if (sd.canWrite()) 
			{
				String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
				String backupDBPath = "HOME.db";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) 
				{
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Localized messages  "+e.getLocalizedMessage());
		}
	}

	public void Home(View v) //function called on Home click
	{

	}

	public void StyleExpert(View v) //function called on Home click
	{
		Intent i = new Intent(this,Style_Expert.class);

		startActivity(i);
	}
	
	public void Inventory(View v)  //function called on Inventory click
	{
		Intent i = new Intent(this,Inventory.class);

		startActivity(i);
	}

	public void Calendar(View v) //function called on Calendar click
	{
		Intent i = new Intent(this,MyCalendarActivity.class);

		startActivity(i);
	}


	public void Packing(View v) //function called on Packing click
	{
		Intent i = new Intent(this,Packing.class);

		startActivity(i);

	}
	public void Help(View v) //function called on Help click
	{
		Intent i = new Intent(this,Help.class);

		startActivity(i);
	}
	public void Logout(View v) //function called on Logout click
	{
		String username = myPrefs.getString("email", "");  //get username from shared Preference's

		String rememberMe =myPrefs.getString("remember_me", ""); //get remember Me if checked from shared Preference's

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
}
