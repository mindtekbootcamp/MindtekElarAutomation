package elar.api.utils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {

    public static String getJson(String jsonName) {
        String path = "src/test/resources/testdata/"+jsonName+".json";
        JSONParser jsonParser = new JSONParser();
        String data;
        try {
            FileReader reader = new FileReader(path);
            data = jsonParser.parse(reader).toString();
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
