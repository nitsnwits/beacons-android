package cmpe295.sjsu.edu.salesman;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Rucha on 6/17/15.
 */
public class MyDeserializer implements JsonDeserializer<Name> {

    @Override
    public Name deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "name" element from the parsed JSON
        JsonElement name = json.getAsJsonObject().get("name");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(name, Name.class);

    }
}
