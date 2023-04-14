package com.example.mymovie;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvActivity extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerViewTv2,recyclerViewTv3,recyclerViewTv4;
    private ArrayList<ModelClass1> modelClass3;
    private RequestQueue requestQueue;
    private myAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        modelClass3= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerViewTv);
        recyclerViewTv3= findViewById(R.id.recyclerViewTv3);
        recyclerViewTv2= findViewById(R.id.recyclerViewTv2);
        recyclerViewTv4= findViewById(R.id.recyclerViewTv4);
        requestQueue= Volley.newRequestQueue(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewTv4.setHasFixedSize(true);
        recyclerViewTv3.setLayoutManager(gridLayoutManager);
        recyclerViewTv2.setLayoutManager(linearLayoutManager2);
        recyclerViewTv4.setLayoutManager(linearLayoutManager3);
        TvShows1();
        TvShows2();
        TvShows3();
        TvShows4();

    }
    private void TvShows4() {
        String url4=  "https://api.themoviedb.org/3/tv/on_the_air?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url4, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.optString("original_name");
                                String ReleasedDate = object.optString("first_air_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass3.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass3);
                            recyclerViewTv4.setAdapter(adapter);
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
    private void TvShows3() {
        String url3="https://api.themoviedb.org/3/tv/top_rated?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.optString("original_name");
                                String ReleasedDate = object.optString("first_air_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass3.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass3);
                            recyclerViewTv3.setAdapter(adapter);
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
    private void TvShows2() {
        String url2="https://api.themoviedb.org/3/tv/latest?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.optString("original_name");
                                String ReleasedDate = object.optString("first_air_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass3.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass3);
                            recyclerViewTv2.setAdapter(adapter);
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

    private void TvShows1() {
        String url1="https://api.themoviedb.org/3/tv/popular?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=4";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.optString("original_name");
                                String ReleasedDate = object.optString("first_air_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass3.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass3);
                            recyclerView.setAdapter(adapter);
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