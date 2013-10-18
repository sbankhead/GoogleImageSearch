package com.example.googleimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	Button btSearch;
	GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdaptor imagesAdaptor;
	
	String img_size = "";
	String img_type = "";
	String color_filter = "";
	String site_restrict = "";
	String query = "";
	
	AsyncHttpClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		SetupViews();
		imagesAdaptor = new ImageResultsArrayAdaptor(this, imageResults);
		gvResults.setAdapter(imagesAdaptor);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				i.putExtra("url", imageResult.getFullURL());
				startActivity(i);
			}
		});
		gvResults.setOnScrollListener(new EndlessScrollListener(3,1) {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {				
				MakeRequest(client, totalItemsCount, query);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void SetupViews() {
		etQuery = (EditText)findViewById(R.id.etQuery);
		btSearch = (Button)findViewById(R.id.btSearch);
		gvResults = (GridView)findViewById(R.id.gvResults);
	}
	
	public void onImageSearch(View v) {
		query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		
		client = new AsyncHttpClient();
		imageResults.clear();
		MakeRequest(client, 0, query);
	}
	
	public void MakeRequest(AsyncHttpClient a_Client, int a_start, String a_query) {
		a_Client.get(	"https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + Uri.encode(a_query) + "&imgcolor=" + color_filter + 
				"&imgsz=" + img_size + "&imgtype=" + img_type + "&as_sitesearch=" + site_restrict + "&rsz=4&start=" + a_start, new JsonHttpResponseHandler() {			
		@Override
		public void onSuccess(JSONObject response) {
			JSONArray imageJSONResults = null;
			try{
				if (response != null)
				{
				imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
				
				imagesAdaptor.addAll(ImageResult.fromJSONArray(imageJSONResults));
				
				Log.d("DEBUG", imageResults.toString());
				}else
				{
					Log.d("DEBUG", "NULL RESPONSE");
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	});
	}
	
	public void onTest(MenuItem mi) {
		Intent i = new Intent(this, AdvancedSearchOptionsActivity.class);
		startActivityForResult(i, 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if (resultCode == RESULT_OK) {
		   img_size = data.getStringExtra("imgsize");
		   img_type = data.getStringExtra("imgtype");
		   color_filter = data.getStringExtra("colorfilter");
		   site_restrict = data.getStringExtra("siterestrict");
		   Log.d("DEBUG", "Image Size: " + img_size);
		   Log.d("DEBUG", "Image Type: " + img_type);
		   Log.d("DEBUG", "Color Filter: " + color_filter);
		   Log.d("DEBUG", "Site Restrict: " + site_restrict);
       }
	}
}
