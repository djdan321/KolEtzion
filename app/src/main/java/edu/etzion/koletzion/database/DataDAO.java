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

import edu.etzion.koletzion.authentication.User;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.MyProfile;
import edu.etzion.koletzion.models.StudentProfile;
import edu.etzion.koletzion.models.SuggestedContent;
import edu.etzion.koletzion.models.TextPost;
//todo test all the write/update methods
public class DataDAO {

    //posts,profiles,suggested content , users
    private final String TEXT_API_KEY = "alfuldstonglareaderignot";
    private final String TEXT_API_SECRET = "550b2221df5ab5695899f57f26b0ef76550e9fb3";
    private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
    private final String DB_NAME = "demo";

     // profiles db credentials
    private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
    private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
    private final String PROFILES_DB = "profiles";

     // posts db credentials
    private final String POSTS_API_KEY = "mitereeneringledituriess";
    private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
    private final String POSTS_DB = "posts";

     // suggestedcontent db credentials
    private final String SC_API_KEY = "tstaroartindedgerfachoun";
    private final String SC_API_SECRET = "b873ae0e2398ad46034ed603fe12c721c1b90b5b";
    private final String SC_DB = "suggested_content";

 // users db credentials
    private final String USERS_API_KEY = "heyrenthoughessecelecief";
    private final String USERS_API_SECRET = "1ade9e9b6fe0a6928b2031803f6d54ea68c37ae8";
    private final String USERS_DB = "users";

    public Context context;
    public DataDAO(){}

    public DataDAO(Context context) {

        this.context=context;
    }


    //todo delete this method writeDB()


    @SuppressLint("StaticFieldLeak")
    public void writeDB() {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(TEXT_API_KEY)
                        .password(TEXT_API_SECRET)
                        .build();

                //return databases.toString();
                Database db = client.database(DB_NAME, false);
                // A Java type that can be serialized to JSON
                DataDAO myData = new DataDAO();
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


    // this method writes MyProfile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeMyProfile(MyProfile myProfile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                db.save(myProfile);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }

    // this method writes StudentProfile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeStudentProfile(StudentProfile studentProfile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                db.save(studentProfile);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method writes BroadcastPost Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeBroadcastPost(BroadcastPost broadcastPost) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(POSTS_API_KEY)
                        .password(POSTS_API_SECRET)
                        .build();

                Database db = client.database(POSTS_DB, false);
                db.save(broadcastPost);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }



    // this method writes TextPost Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeTextPost(TextPost textPost) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(POSTS_API_KEY)
                        .password(POSTS_API_SECRET)
                        .build();

                Database db = client.database(POSTS_DB, false);
                db.save(textPost);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method writes SuggestedContent Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeSuggestedContent(SuggestedContent suggestedContent) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(SC_API_KEY)
                        .password(SC_API_SECRET)
                        .build();

                Database db = client.database(SC_DB, false);
                db.save(suggestedContent);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method writes User Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeUser(User user) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(USERS_API_KEY)
                        .password(USERS_API_SECRET)
                        .build();

                Database db = client.database(USERS_DB, false);
                db.save(user);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method updates MyProfile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateMyProfile(MyProfile myProfile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                db.update(myProfile);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }

    // this method updates StudentProfile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateStudentProfile(StudentProfile studentProfile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                db.update(studentProfile);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method updates BroadcastPost Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateBroadcastPost(BroadcastPost broadcastPost) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(POSTS_API_KEY)
                        .password(POSTS_API_SECRET)
                        .build();

                Database db = client.database(POSTS_DB, false);
                db.update(broadcastPost);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }



    // this method updates TextPost Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateTextPost(TextPost textPost) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(POSTS_API_KEY)
                        .password(POSTS_API_SECRET)
                        .build();

                Database db = client.database(POSTS_DB, false);
                db.update(textPost);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }


    // this method updates User Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateUser(User user) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(USERS_API_KEY)
                        .password(USERS_API_SECRET)
                        .build();

                Database db = client.database(USERS_DB, false);
                db.update(user);
                Log.e("TAG", "doInBackground: cloudant data was saved.... ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("TAG","onPostExecute");
            }
        }.execute();
    }
    /*

    todo add readers

    smart and generic readers using find querys and bringing only relevant data.

     */



}
