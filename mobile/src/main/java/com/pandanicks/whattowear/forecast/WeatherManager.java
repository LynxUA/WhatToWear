package com.pandanicks.whattowear.forecast;

import android.content.Context;

import com.pandanicks.whattowear.R;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by denysburlakov on 22.03.15.
 */
public class WeatherManager {
    Context context;
    public WeatherManager(Context context){
        this.context = context;
    }
    public String getCurrentTemprature(double lat, double lng) throws WheatherNotFoundException, IOException {
        String temprature = null;
        String url = "http://api.worldweatheronline.com/free/v2/weather.ashx?q="+Math.round(lat)+
                    "%20"+Math.round(lng)+"&format=json&num_of_days=5&key="+context.getString(R.string.world_wheather_key);
        System.out.println(url);
        JSONParser jParser = new JSONParser();
// getting JSON string from URL
        JSONObject json = null;
        try {
            json = (JSONObject) jParser.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            // Getting Array of Contacts
            temprature = (String) ((JSONObject)(json.getJSONObject("data").getJSONArray("current_condition").get(0))).get("temp_C");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (temprature == null){
            throw new WheatherNotFoundException();
        }else
            return temprature;
    }

    public class WheatherNotFoundException extends Throwable {
    }
}
