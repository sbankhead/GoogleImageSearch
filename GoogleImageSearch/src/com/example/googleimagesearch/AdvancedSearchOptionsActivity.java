package com.example.googleimagesearch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AdvancedSearchOptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search_options);
		AddImageSize();
		AddImageType();
		AddColorFilter();
		
		Button SaveButton = (Button) findViewById(R.id.button1);
		SaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SendResult();
			}
		});
	}
	
	public void SendResult() {
		Spinner spinner1 = (Spinner) findViewById(R.id.Spinner1);
		Spinner spinner2 = (Spinner) findViewById(R.id.Spinner2);
		Spinner spinner3 = (Spinner) findViewById(R.id.Spinner3);
		EditText edit1 = (EditText) findViewById(R.id.EditText04);
		
		Intent i = getIntent();
		i.putExtra("imgsize", (String)spinner1.getSelectedItem());
		i.putExtra("imgtype", (String)spinner3.getSelectedItem());
		i.putExtra("colorfilter", (String)spinner2.getSelectedItem());
		i.putExtra("siterestrict", edit1.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void AddColorFilter() {
		Spinner spinner = (Spinner) findViewById(R.id.Spinner2);
		List<String> list = new ArrayList<String>();
		list.add("black");
		list.add("blue");
		list.add("brown");
		list.add("gray");
		list.add("green");
		list.add("orange");
		list.add("pink");
		list.add("purple");
		list.add("red");
		list.add("teal");
		list.add("white");
		list.add("yellow");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
	
	public void AddImageType() {
		Spinner spinner = (Spinner) findViewById(R.id.Spinner3);
		List<String> list = new ArrayList<String>();
		list.add("face");
		list.add("photo");
		list.add("clipart");
		list.add("lineart");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
	
	public void AddImageSize() {
		Spinner spinner = (Spinner) findViewById(R.id.Spinner1);
		List<String> list = new ArrayList<String>();
		list.add("small");
		list.add("medium");
		list.add("large");
		list.add("xlarge");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	  }
	
	public void onSave() {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_search_options, menu);
		return true;
	}

}
