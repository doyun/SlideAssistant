package ua.nure.doyun.slideassistant.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class SlidePackage {

	private String id;
	private String type;
	private String name;
	private String slideNumber;
	private String connectionsNumber;
	private String isClear;
	private String clear;
	private String question;
	private String wasSetClear;

	JSONObject slidePackage;

	public SlidePackage(String json) {
		try {
			slidePackage = new JSONObject(json);
			System.out.println("SlidePackage" + slidePackage.toString());
			id = slidePackage.getString("id");
			type = slidePackage.getString("type");
			name = slidePackage.getString("name");
			slideNumber = slidePackage.getString("slideNumber");
			connectionsNumber = slidePackage.getString("connectionsNumber");
			isClear = slidePackage.getString("isClear");
			clear = slidePackage.getString("clear");
			question = slidePackage.getString("question");
			wasSetClear = slidePackage.getString("wasSetClear");
			System.out.println("SlidePackage " + isClear + " " + wasSetClear);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setId(String id) {
		try {
			slidePackage.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.id = id;
	}

	public String getId() {
		return id;
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

	public void setConnectionsNumber(String connectionsNumber) {
		try {
			slidePackage.put("connectionsNumber", connectionsNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.connectionsNumber = connectionsNumber;
	}

	public String getIsClear() {
		return isClear;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		try {
			slidePackage.put("clear", clear);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.clear = clear;
	}

	public String getQuestion() {
		return question;
	}

	public String getWasSetClear() {
		return wasSetClear;
	}

	public String toString() {
		return slidePackage.toString();
	}

}
