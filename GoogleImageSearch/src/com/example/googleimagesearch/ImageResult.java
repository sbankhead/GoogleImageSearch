package com.example.googleimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -276541659807300434L;
	private String FullURL;
	private String ThumbURL;
	
	public ImageResult(JSONObject json) {
		try {
			this.FullURL = json.getString("url");
			this.ThumbURL = json.getString("tbUrl");
		} catch (JSONException e) {
			this.FullURL = null;
			this.ThumbURL = null;
		}
		
	}
	
	public String getFullURL() {
		return FullURL;
	}
	
	public String getThumbURL() {
		return ThumbURL;
	}
	
	public String toString() {
		return this.ThumbURL;
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x=0; x<array.length(); x++) {
			try {
				results.add(new ImageResult(array.getJSONObject(x)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
