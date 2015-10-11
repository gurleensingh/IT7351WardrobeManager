package com.example.wardrobemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Help extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.help);
	}

	public void Inventory(View v) 
	{
		Intent i = new Intent( this,Inventory.class);

		startActivity(i);
	}

	public void Calendar(View v) 
	{
		Intent i = new Intent(this,MyCalendarActivity.class);

		startActivity(i);
	}

	public void Home(View v) 
	{
		Intent i = new Intent(this,Home.class);

		startActivity(i);

		finish();
	}

	public void Packing(View v) 
	{
		Intent i = new Intent(this,Packing.class);

		startActivity(i);
	}
	public void Help(View v) 
	{ 
	}
}
