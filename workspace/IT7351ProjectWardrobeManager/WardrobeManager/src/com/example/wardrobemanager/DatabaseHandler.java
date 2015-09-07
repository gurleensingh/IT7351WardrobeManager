package com.example.wardrobemanager;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME="Wardrobe_db";	

	public static final int DATABASE_VERSION=1;

	DatabaseHandler ourHelper;

	SQLiteDatabase ourDB;

	Context c;

	public static final String KEY_EMAIL="email";

	public static final String KEY_PASSWORD="password";

	public static final String DATABASE_TABLE_LOGIN="login_table";
	
	
	//add item
	public static final String KEY_TITLE="title";

	public static final String KEY_SEASON="season";
	
	public static final String KEY_TYPE_OF_ITEM="type_of_item";
	
	public static final String KEY_Date_Purchase="date_purchase";
	
	public static final String KEY_BRAND="brand";
	
	public static final String KEY_DRESS_CODE="dress_code";
	
	public static final String KEY_LOCATION="location";
	
	public static final String KEY_COLOR="color";
	
	public static final String KEY_LAST_ACCESSED_DATE="last_accessed_date";
	
	public static final String KEY_PIC="pic";

	public static final String DATABASE_TABLE_ADD_ITEM="items_table";
	

	public DatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		c =context;
	}

	public DatabaseHandler open() throws SQLException
	{
		ourHelper=new DatabaseHandler(c);

		ourDB=this.getWritableDatabase();

		return this;
	}

	public void close() 
	{
		if(ourHelper!=null)
			ourHelper.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String table1 =" CREATE TABLE " + DATABASE_TABLE_LOGIN + "(" + KEY_EMAIL +", "+ KEY_PASSWORD +")";

		System.out.println("Table 1 "+table1);

		db.execSQL(table1);
		
		String table =" CREATE TABLE " + DATABASE_TABLE_ADD_ITEM + "(" + KEY_TITLE +", "+ KEY_SEASON+", "+ 
		
				KEY_TYPE_OF_ITEM +", "+ KEY_Date_Purchase+", "+ KEY_BRAND +", "+ KEY_DRESS_CODE +", "+ 
				
				KEY_LOCATION +", "+ KEY_COLOR +", "+ KEY_LAST_ACCESSED_DATE +", "+ KEY_PIC+")";

		
		System.out.println("Table  "+table);

		db.execSQL(table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{

	}

	public long createEntry_for_email_password(String email ,String password)
	{
		ourDB=this.getWritableDatabase();

		ContentValues cv= new ContentValues();

		cv.put(KEY_EMAIL, email);

		cv.put(KEY_PASSWORD, password); 

		return ourDB.insert(DATABASE_TABLE_LOGIN, null, cv) ;
	}
	
	public long createEntry_for_addItem
	
	(String title ,String season,String typeOfItem,String datePurchase,
			
			String brand,String dressCode,String location,String color,String lastAccesssedDate,String pic)
	{
		ourDB=this.getWritableDatabase();

		ContentValues cv= new ContentValues();

		cv.put(KEY_TITLE, title);

		cv.put(KEY_SEASON, season); 
		
		cv.put(KEY_TYPE_OF_ITEM, typeOfItem); 
		
		cv.put(KEY_Date_Purchase, datePurchase); 
		
		cv.put(KEY_BRAND, brand); 
		
		cv.put(KEY_DRESS_CODE, dressCode); 
		
		cv.put(KEY_LOCATION, location); 
		
		cv.put(KEY_COLOR, color); 
		
		cv.put(KEY_LAST_ACCESSED_DATE, lastAccesssedDate); 
		
		cv.put(KEY_PIC, pic); 

		return ourDB.insert(DATABASE_TABLE_ADD_ITEM, null, cv) ;
	}
	
	public ArrayList<HashMap<String, String>> getItems(String type) 
	{
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String,String>>();

		try
		{
			String query = "SELECT * FROM items_table  where type_of_item like  '%"+type+"%'";
			
			
			System.out.println(query);

			SQLiteDatabase db2 = this.getWritableDatabase();

			Cursor c = db2 .rawQuery(query, null);

			while (c.moveToNext())
			{
				HashMap<String, String> v=new HashMap<String, String>();

				String[] names=c.getColumnNames();

				for (int j = 0; j < names.length; j++) 
				{
					String value = c.getString(c.getColumnIndex(names[j]));

					v.put(names[j], value);
				}

				contactList.add(v);
			}

			System.out.println("items  "+contactList.size());
		}
		catch (Exception e)
		{
		}
		return contactList;
	}

	public boolean check_login(String email ,String password)
	{
		String query = "SELECT * FROM login_table WHERE email = '"+email+"' AND password='"+password+"'";

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor c = db.rawQuery(query, null);

		if(c!=null)
		{
			if(c.getCount() > 0)
			{
				System.out.println("NOT NULL");
				
				return true;
			}
		}
		System.out.println(" NULL");

		return false;
	}
}
