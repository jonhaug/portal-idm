package eu.nets.portal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class IdmContext {
	private final JSONObject jsonObject;

	public IdmContext(String jsonString) {
		jsonObject = (JSONObject) JSONValue.parse(jsonString);
	}

	public Object getValue(String keyPath) {
		return jsonObject.get(keyPath);
	}
	@Override
	public String toString() {
		return jsonObject.toString();
	}

	public Object[] getArray(String keyPath) {
		JSONArray arr = (JSONArray) getValue(keyPath);
		return arr.toArray();
	}

}
