package coms309.cycrawl.com.volleywithdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Nate on 10/6/2017.
 */

public class ParseJSON {
    public static String[] accountID;
    public static String[] userName;
    public static String[] userPassword;
    public static String[] adminID;
    public static final String KEY_ACCOUNTID = "accountID";
    public static final String KEY_USERNAME="userName";
    public static final String KEY_USERPASSWORD="userPassword";
    public static final String KEY_ADMINID = "adminID";

    private JSONArray users;
    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try{
            users =new JSONArray(json);
            accountID = new String[users.length()];
            userName = new String[users.length()];
            userPassword = new String[users.length()];
            adminID = new String[users.length()];

            for(int i=0;i<users.length(); i++){
                JSONObject jo = users.getJSONObject(i);
                accountID[i] = jo.getString(KEY_ACCOUNTID);
                userName[i] = jo.getString(KEY_USERNAME);
                userPassword[i]=jo.getString(KEY_USERPASSWORD);
                adminID[i] = jo.getString(KEY_ADMINID);

            }

        }catch(JSONException e){
            e.printStackTrace();;
        }
    }



}