package com.example.wardrobemanager;

import com.example.wardrobemanager.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Style_Expert extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);

		setContentView(R.layout.style_expert);
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
		Intent i = new Intent(this,Help.class);

		startActivity(i);
	}
}
