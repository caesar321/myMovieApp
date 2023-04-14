package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrendingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private myAdapter myapter;
    private ArrayList<ModelClass1> modelClass2;
    private RequestQueue requestQueue;
    private SearchView searchView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        recyclerView= findViewById(R.id.recyclerViewTred);
        dialog = new ProgressDialog(this);
        modelClass2= new ArrayList<>();
        //searchView= findViewById(R.id.searchViewTred);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
      //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        requestQueue= Volley.newRequestQueue(this);
        parseJsonTred();
    }
    private void parseJsonTred() {
        dialog.setMessage("Loading....");
        dialog.show();
        String url1="https://api.themoviedb.org/3/trending/all/day?api_key=3d5997641d1f4cfed4f532e5d7df75e3";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String ReleasedDate = object.optString("release_date");
                                String Title = object.optString("original_title");
                                String overView= object.getString("overview");
                                String Image= object.getString("poster_path");
                                modelClass2.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            myapter= new myAdapter(getApplicationContext(),modelClass2);
                            recyclerView.setAdapter(myapter);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });requestQueue.add(jsonObjectRequest);
    }

}