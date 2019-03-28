package edu.etzion.koletzion.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyData {

    public String myName;
    public String mySurName;
    public int myAge;
    public String myAddress;
    public String myPhone;
    public long myGpsLat;
    public long myGpsLan;
    public Context context;
    public MyData(){}

    public MyData(String myName, String mySurName, int myAge, String myAddress,
                  String myPhone, long myGpsLat, long myGpsLan, Context context) {
        this.myName = myName;
        this.mySurName = mySurName;
        this.myAge = myAge;
        this.myAddress = myAddress;
        this.myPhone = myPhone;
        this.myGpsLat = myGpsLat;
        this.myGpsLan = myGpsLan;
        this.context=context;
    }

    @SuppressLint("StaticFieldLeak")
    public void writeDB() {

        final String TEXT_API_KEY = "alfuldstonglareaderignot";
        final String TEXT_API_SECRET = "550b2221df5ab5695899f57f26b0ef76550e9fb3";
        final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
        final String DB_NAME_TEXT = "demo";
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(TEXT_API_KEY)
                        .password(TEXT_API_SECRET)
                        .build();

                //return databases.toString();
                Database db = client.database("demo", false);
                // A Java type that can be serialized to JSON

                MyData myData = new MyData();
                db.save(myData);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(context, "Data Ok", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
    @SuppressLint("StaticFieldLeak")
    public void readDb() {

        final String TEXT_API_KEY = "alfuldstonglareaderignot";
        final String TEXT_API_SECRET = "550b2221df5ab5695899f57f26b0ef76550e9fb3";
        final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
        final String DB_NAME_TEXT = "demo";

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(TEXT_API_KEY)
                        .password(TEXT_API_SECRET)
                        .build();

                //return databases.toString();
                Database db = client.database("demo", false);
                String myJson = "";
                try {
                    // A Java type that can be serialized to JSON
                    JSONObject myQuery = new JSONObject();

                    //get the query conditions
                    JSONObject myQueryField = new JSONObject();
                    myQueryField.put("$eq", "נסיון");
                    JSONObject myData = new JSONObject();
                    myData.put("data", myQueryField);
                    myQuery.put("selector", myData);

                    //get the fields
                    myQuery.put("fields", "[_id,Latitude,Longitude,data]");
                    myQuery.put("sort", "[{_id,asc}]");
                    Log.e("JSON", "myJson: " + myQuery.toString());
                    myJson = myQuery.toString();

                } catch (JSONException e) {

                }

                List<MyData> test = db.findByIndex(myJson, MyData.class);
                for (MyData item : test) {
                    Log.e("check", "checkResult: " + item.myName);
                }
                Log.e("check", test.toString());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(context, "Data OK", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

}
