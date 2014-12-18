package ua.nure.doyun.slideassistant.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import ua.nure.doyun.slideassistant.entities.SlidePackage;
import ua.nure.doyun.slideassistant.util.JSONParser;

public class DAManager {
	
	private static DAManager instance;
	
	public static synchronized DAManager newInstance(){
		if(instance == null){
			instance = new DAManager();
		}
		return instance;
	}
	
	public void createPresentation(SlidePackage pack){
		JSONParser jsonParser = new JSONParser();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", pack.getName()));
		params.add(new BasicNameValuePair("slideNumber", pack.getSlideNumber()));
		JSONObject json = jsonParser
				.makeHttpRequest(
						"http://localhost:8080/DAServer/create",
						"POST", params);
		try {
			pack.setId(json.getString("id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
