package eu.nets.portal;

import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class IdmContext {
    private final JSONObject jsonObject;

    public IdmContext(String jsonString) {
        if (StringUtils.isBlank(jsonString)) { jsonString="{}"; }
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    public Object getValue(String keyPath) {
        String[] path = keyPath.split("\\.");
        Object currentJson = jsonObject;
        for (String entry: path) {
            currentJson = ((JSONObject) currentJson).get(entry);
        }
        return currentJson;
    }
    @Override
    public String toString() {
        return jsonObject.toString();
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String keyPath) {
        return (List<Object>) getValue(keyPath);
    }

	public boolean subsetOf(IdmContext ctx2) {
		return subset(this.jsonObject, ctx2.jsonObject);
	}

	private boolean subset(Object o1, Object o2) {
		if (wildChar(o2)) { return true; }
		else if (o1 instanceof JSONObject && o2 instanceof JSONObject) {
			return objectSubset((JSONObject) o1, (JSONObject) o2);
		} else if (o1 instanceof String && o2 instanceof String) {
			return StringUtils.equals((String) o1, (String) o2);
		} else if (o1 instanceof Number && o2 instanceof Number) {
			return o1.equals(o2);
		} else if (o1 instanceof JSONArray && o2 instanceof JSONArray) {
			return listSubset((JSONArray) o1, (JSONArray) o2);
		}
		return false;
	}

	private boolean wildChar(Object object) {
		return (object instanceof String) && "*".equals(object);
	}

	private boolean objectSubset(JSONObject o1, JSONObject o2) {
		for (Object oEntry: o1.entrySet()) {
			@SuppressWarnings("unchecked")
			Entry<String,Object> entry = (Entry<String, Object>) oEntry;
			String key = entry.getKey();
			Object value2 = o2.get(key);
			if (! subset(entry.getValue(), value2)) return false;
		}
		return true;
	}

	// Difficult
	private boolean listSubset(JSONArray o1, JSONArray o2) {
		return false;
	}


}
