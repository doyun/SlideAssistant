package ua.nure.doyun.slideassistant.entities;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;



public class SlidePackage {

	private String type;
	private String name;
	private String slideNumber;
	private String connectionsNumber;
	private String clear;
	private String question;
	JSONObject slidePackage;

	public SlidePackage(String json){
		try {
			slidePackage = new JSONObject(json);
			type = slidePackage.getString("type");
			name = slidePackage.getString("name");
			slideNumber = slidePackage.getString("slideNumber");
			connectionsNumber = slidePackage.getString("connectionsNumber");
			clear = slidePackage.getString("clear");
			question = slidePackage.getString("question");
		} catch (JSONException e) {
		}
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getSlideNumber() {
		return slideNumber;
	}

	public String getConnectionsNumber() {
		return connectionsNumber;
	}

	public String getClear() {
		return clear;
	}

	public String getQuestion() {
		return question;
	}
	
	public String toString(){
		return slidePackage.toString();
	}

}
