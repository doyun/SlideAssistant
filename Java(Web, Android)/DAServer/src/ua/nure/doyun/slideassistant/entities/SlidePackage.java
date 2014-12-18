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
	JSONObject slidePackage;
	private String wasSetClear;

	public SlidePackage() {
		slidePackage = new JSONObject();
	}

	public SlidePackage(String json) {
		try {
			slidePackage = new JSONObject(json);
			id = slidePackage.getString("id");
			type = slidePackage.getString("type");
			name = slidePackage.getString("name");
			slideNumber = slidePackage.getString("slideNumber");
			connectionsNumber = slidePackage.getString("connectionsNumber");
			isClear = slidePackage.getString("isClear");
			clear = slidePackage.getString("clear");
			question = slidePackage.getString("question");
			wasSetClear = slidePackage.getString("wasSetClear");
		} catch (JSONException e) {
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

	public void setType(String type) {
		try {
			slidePackage.put("type", type);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.type = type;
	}

	public void setName(String name) {
		try {
			slidePackage.put("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.name = name;
	}

	public void setSlideNumber(String slideNumber) {
		try {
			slidePackage.put("slideNumber", slideNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.slideNumber = slideNumber;
	}

	public void setConnectionsNumber(String connectionsNumber) {
		try {
			slidePackage.put("connectionsNumber", connectionsNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.connectionsNumber = connectionsNumber;
	}

	public void setIsClear(String isClear) {
		try {
			slidePackage.put("isClear", isClear);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.isClear = isClear;
	}
	
	public String getWasSetClear() {
		return wasSetClear;
	}


	public void setWasSetClear(String wasSetClear) {
		try {
			slidePackage.put("wasSetClear", wasSetClear);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.wasSetClear = wasSetClear;
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

	public String getIsClear() {
		return isClear;
	}

	public String getClear() {
		return clear;
	}

	public String getQuestion() {
		return question;
	}

	public String toString() {
		return slidePackage.toString();
	}

	public void setClear(String clear) {
		try {
			slidePackage.put("clear", clear);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.clear = clear;
	}

	public void setQuestion(String question) {
		try {
			slidePackage.put("question", question);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.question = question;
	}

}
