package edu.etzion.koletzion.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.models.SuggestedContent;

//todo test all the update methods
public class DataDAO {

    private static DataDAO instance;

    private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";

    //posts,profiles,suggested content , users
    private final String TEXT_API_KEY = "alfuldstonglareaderignot";
    private final String TEXT_API_SECRET = "550b2221df5ab5695899f57f26b0ef76550e9fb3";
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

    private DataDAO(){}


    public static DataDAO getInstance() {
        if (instance == null)
            instance = new DataDAO();
        return instance;
    }


    // this method writes Profile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void writeMyProfile(Profile profile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                profile.setTimeStamp(new Date().getTime());
                db.save(profile);
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
                broadcastPost.setTimeStamp(new Date().getTime());
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
                suggestedContent.setTimestamp(new Date().getTime());
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





    // this method updates Profile Object to the server
    @SuppressLint("StaticFieldLeak")
    public void updateMyProfile(Profile profile) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);
                db.update(profile);
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

//todo delete readers from here when donr writing them.(YOSSI)

    // this method returns list of all the broadcasts from server.
    @SuppressLint("StaticFieldLeak")
    public void getAllPosts() {
        new AsyncTask<Void, Void, List<BroadcastPost>>() {

            @Override
            protected List<BroadcastPost> doInBackground(Void... voids) {
                List<BroadcastPost> postsList = new ArrayList<>();
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(POSTS_API_KEY)
                        .password(POSTS_API_SECRET)
                        .build();

                Database db = client.database("posts", false);

                List<BroadcastPost> list = db.findByIndex("{\n" +
                        "   \"selector\": {\n" +
                        "      \"_id\": {\n" +
                        "         \"$gt\": \"0\"\n" +
                        "      }\n" +
                        "   }\n" +
                        "}", BroadcastPost.class);

                for (BroadcastPost item : list) {
                    Log.e("check", "checkResult: "+item.toString());
                    postsList.add(item);
                }
                Log.e("check", list.toString());
                Collections.sort(postsList);
                return postsList;
            }

            @Override
            protected void onPostExecute(List<BroadcastPost> posts) {

            }
        }.execute();
    }

    // this method returns a list with all the broadcasters.
    @SuppressLint("StaticFieldLeak")
    public void getBroadcasters() {
        new AsyncTask<Void, Void, List<Profile>>() {

            @Override
            protected List<Profile> doInBackground(Void... voids) {
                List<Profile> profiles = new ArrayList<>();
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database("profiles", false);

                List<Profile> list = db.findByIndex("{\n"+
                        "   \"selector\": {\n"+
                        "      \"isBroadcaster\": true\n"+
                        "   }\n"+
                        "}", Profile.class);
                for (Profile item : list) {
                    Log.e("check", "checkResult: "+item.toString());
                    profiles.add(item);
                }
                Log.e("check", list.toString());
                Collections.sort(profiles);
                return profiles;
            }

            @Override
            protected void onPostExecute(List<Profile> profiles) {

            }
        }.execute();

    }


    //this method returns a Profile object
    @SuppressLint("StaticFieldLeak")
    public void getProfileByUserName(String userName) {


        new AsyncTask<Void, Void, Profile>() {

            @Override
            protected Profile doInBackground(Void... voids) {
                Profile profile = null;
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database("profiles", false);

                List<Profile> list = db.findByIndex("{\n" +
                        "   \"selector\": {\n" +
                        "      \"username\": \""+userName+"\"\n" +
                        "   }\n" +
                        "}", Profile.class);
                for (Profile item : list) {
                    Log.e("check", "checkResult: "+item.toString());
                    profile=item;
                }
                Log.e("check", list.toString());
                return profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {

            }
        }.execute();
    }

    public BroadcastPost getBroadcastPostById(String id){
        BroadcastPost broadcastPost = null;
        CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                .username(POSTS_API_KEY)
                .password(POSTS_API_SECRET)
                .build();

        Database db = client.database(POSTS_DB, false);

        List<BroadcastPost> list = db.findByIndex("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": \""+id+"\"\n" +
                "   }\n" +
                "}", BroadcastPost.class);
        for (BroadcastPost item : list) {
            Log.e("check", "checkResult: "+item.toString());
            broadcastPost=item;
        }
        Log.e("check", list.toString());
        return broadcastPost;

    }
}