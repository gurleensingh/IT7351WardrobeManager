package com.example.wardrobemanager;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Sign_Up extends Activity
{
	EditText email , password,confirmPassword;
	
	DatabaseHandler database;
	
	SharedPreferences myPrefs; 

	SharedPreferences.Editor prefsEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sign_up);
		
		initialise();
	}
	private void initialise() 
	{
		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit(); 
		
		database = new DatabaseHandler(getApplicationContext());

		email = (EditText) findViewById(R.id.edEmail);
		
		confirmPassword = (EditText)findViewById(R.id.edConfirmPassword);
		
		password = (EditText) findViewById(R.id.edPassword);
	}

	public void SignUP(View v) 
	{
		if(!email.getText().toString().equals(""))
		{
			if(checkEmailNewUI(email.getText().toString()))
			{
			
			if(!password.getText().toString().equals(""))
			{
				if(password.getText().toString().equals(confirmPassword.getText().toString()))
				{
					 database.open();
					 
					 database.createEntry_for_email_password
					 (email.getText().toString(), password.getText().toString());
					 
					 database.close();
					 
					 finish();
				}
				else
				{
					createErrorDialog("Password do not match!");
				}
			}
			else
			{
				createErrorDialog("Please Enter your Password");
			}
			
			}
			else
			{
				createErrorDialog("Kindly enter your valid Email address.");
			}
		}
		else
		{
			createErrorDialog("Please Enter your Email");
		}
	}
	
	private boolean checkEmailNewUI(String email)
	{ 
		return Pattern.matches("[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,4}", email);
	}
	
	public void createErrorDialog(String errMsg) 
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
}
