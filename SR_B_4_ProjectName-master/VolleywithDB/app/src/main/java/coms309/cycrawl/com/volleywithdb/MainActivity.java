package coms309.cycrawl.com.volleywithdb;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String JSON_URL = "http://proj-309-sr-b-4.cs.iastate.edu/RetrieveCT.php";

    private Button buttonGet;
    private ListView listView;
    private Button buttonSubmit;
    private EditText userName;
    private EditText userPassword;
    private String server_url = "http://proj-309-sr-b-4.cs.iastate.edu/InsertingIntoCT.php";
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        userName = (EditText) findViewById(R.id.editUserName);
        userPassword = (EditText) findViewById(R.id.edituserPassword);
        builder = new AlertDialog.Builder(MainActivity.this);

        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                        final String user_Name, user_Password;
                        user_Name = userName.getText().toString();
                        user_Password = userPassword.getText().toString();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        builder.setTitle("server Response");
                                        builder.setMessage("Response"+response);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,"Error...",Toast.LENGTH_LONG).show();
                                error.printStackTrace();



                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("userName", user_Name);
                                params.put("userPassword",user_Password);
                                return params;
                            }
                        };


                        PrepareRequest.getmInstance(MainActivity.this).addToRequestQue(stringRequest);





            }

        });
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            showJSON(response);
                        }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        myApplication ma = new myApplication(this, ParseJSON.accountID, ParseJSON.userName, ParseJSON.userPassword, ParseJSON.adminID);
        listView.setAdapter(ma);
    }

    @Override
    public void onClick(View v){

        switch(v.getId()) {
            case R.id.buttonGet:
                sendRequest();
                break;
        }
    }

}
