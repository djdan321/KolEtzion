package edu.etzion.koletzion.database;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvBroadcastersAdapter;
import edu.etzion.koletzion.Adapters.rvFeedAdapter;
import edu.etzion.koletzion.models.Profile;

public class BroadcastersDataSource extends AsyncTask<Void,Void,List<Profile>> {
    private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
    private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
    private final String PROFILES_DB = "profiles";
    private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";

    private boolean isGrid;
    private WeakReference<RecyclerView> rv;

    public BroadcastersDataSource(RecyclerView rv,boolean isGrid) {
        this.rv =new WeakReference<>(rv);
        this.isGrid=isGrid;
    }

    @Override
    protected List<Profile> doInBackground(Void... voids) {
        return getBroadcastersFromServer();
    }

    private List<Profile> getBroadcastersFromServer() {
        List<Profile> profiles = new ArrayList<>();
        CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                .username(PROFILES_API_KEY)
                .password(PROFILES_API_SECRET)
                .build();

        Database db = client.database(PROFILES_DB, false);

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
        //todo set another xml that shows broadcaster profile in a bigger size and to make rvBroadcasteradapter gets bool and then display the relevant xml(by thye size of the view)
        RecyclerView rv = this.rv.get();
        if(isGrid)
            rv.setLayoutManager(new GridLayoutManager(this.rv.get().getContext(), 2));
        else
            rv.setLayoutManager(new LinearLayoutManager(this.rv.get().getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new rvBroadcastersAdapter(profiles,rv.getContext()));
    }
}
