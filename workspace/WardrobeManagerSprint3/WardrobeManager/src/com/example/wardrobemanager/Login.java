package com.example.wardrobemanager;

import java.util.regex.Pattern; 

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Activity
{
	EditText email , password;

	CheckBox remeberMeCB;

	SharedPreferences myPrefs; 

	SharedPreferences.Editor prefsEditor;

	DatabaseHandler database;

	SQLiteDatabase database2;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);	

		initialise();

		database = new DatabaseHandler(getApplicationContext());

		database2 = database.getWritableDatabase();

		System.out.println("LOGIN "+ getSharedPreferences("myPrefs", MODE_PRIVATE).getString("email", ""));

		if(!myPrefs.getString("email", "").equals("") && !myPrefs.getString("password", "").equals("") ) // if already login move to home screen 
		{
			Intent i = new Intent(this,Home.class);;

			startActivity(i);
		}
	}

	private void initialise() 
	{
		myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

		prefsEditor = myPrefs.edit();

		remeberMeCB = (CheckBox) findViewById(R.id.remeberMeCB);

		email = (EditText) findViewById(R.id.edEmail);

		password = (EditText) findViewById(R.id.edPassword);

		if(myPrefs.getString("remember_me", "").equals("true"))
		{
			email.setText(myPrefs.getString("email", ""));
		}
	}

	public void ForgotPassword(View v) 
	{

	}

	public void LoginClick(View v) 
	{
		if(!email.getText().toString().equals(""))
		{
			if(checkEmailNewUI(email.getText().toString()))
			{
				if(!password.getText().toString().equals(""))
				{
					boolean check = database.check_login(email.getText().toString(), password.getText().toString());

					if(check==true)
					{
						prefsEditor.putString("remember_me",""+remeberMeCB.isChecked()).commit();

						prefsEditor.putString("email",email.getText().toString()).commit();

						prefsEditor.putString("password",password.getText().toString()).commit();

						Intent i = new Intent(this,Home.class);

						startActivity(i);

						finish();
					}
					else
					{
						createErrorDialog("Wrong Sign up credential's.!!");
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

	public void SignUp(View v) 
	{
		Intent i = new Intent(this,Sign_Up.class);

		startActivity(i);
	}

	private boolean checkEmailNewUI(String email) // to check whether it's a email format or not
	{ 
		return Pattern.matches("[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,4}", email);
	}

}
