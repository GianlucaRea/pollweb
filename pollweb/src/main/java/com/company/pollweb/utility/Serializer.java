package com.company.pollweb.utility;

import org.json.*;

/**
 * @author gianlucarea
 */

public class Serializer {

    public static JSONObject testobreveToJSON(int max_number, String pattern){
        String JSON = "{\"max_lenght\" :"+max_number+",\"pattern\":"+pattern+"}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
    public static JSONObject testolungoToJSON(int max_number, int min_number, String pattern){
        String JSON = "{\"min_lenght\" :"+min_number+",\"max_lenght\":"+max_number+",\"pattern\":"+pattern+"}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
    public static JSONObject numeroToJSON(int max_num,int min_num){
        String JSON = "{\"min_num\" :"+min_num+",\"max_num\":"+max_num+"}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
    public static JSONObject sceltaSingolaToJSON(String chooses){
        String JSON = "{\"chooses\" :["+chooses+"]}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
    public static JSONObject sceltaMultiplaToJSON(String chooses, int min_chooses, int max_chooses){
        String JSON = "{\"chooses\": ["+chooses+"],\"min_chooses\": "+min_chooses+",\"max_chooses\": "+max_chooses+"}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
    public static JSONObject dataToJSON(int data){
        String JSON = "{\"date\" :["+data+"]}";
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }

    public static JSONObject StringToJSON(String JSON){
        JSONObject obj = new JSONObject(JSON);
        return obj;
    }
}
