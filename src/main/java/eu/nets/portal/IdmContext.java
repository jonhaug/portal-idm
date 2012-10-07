package eu.nets.portal;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IdmContext {
    private final JSONObject jsonObject;

    public IdmContext(String jsonString) {
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

}
