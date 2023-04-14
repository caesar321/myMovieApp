package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView recyclerView,recyclerView2,recyclerView3;
    private ArrayList<ModelClass1> modelClass1;
    private RequestQueue requestQueue;
    private myAdapter adapter;
    private SearchView searchView;
     private ProgressDialog progressDialog;



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        };
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout= findViewById(R.id.drawer);
        navigationView= findViewById(R.id.nav_view);
        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        progressDialog = new ProgressDialog(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        modelClass1= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerView1);
        recyclerView3= findViewById(R.id.recyclerView3);
        recyclerView2= findViewById(R.id.recyclerView2);
        searchView= findViewById(R.id.searchView);
        adapter= new myAdapter(getApplicationContext(),modelClass1);
        requestQueue= Volley.newRequestQueue(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(gridLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        parseJson1();
        parseJson2();
        parseJson3();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.seeMyList:{
                        Toast.makeText(MainActivity.this, "MY Collection", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(MainActivity.this,DisplayActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.tvShow:{
                        Toast.makeText(MainActivity.this, "TV clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,TvActivity.class);
                        startActivity(intent);
                        break;
                    }case R.id.trending:{
                        Toast.makeText(MainActivity.this, "Trending clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,TrendingActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.movie:{
                        Toast.makeText(MainActivity.this, "Movies", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    }

                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

    }
    private void parseJson3() {
         progressDialog.setMessage("Loading....");
         progressDialog.show();
        String url2="https://api.themoviedb.org/3/movie/155/recommendations?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=4";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.getString("original_title");
                                String ReleasedDate = object.getString("release_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass1.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass1);
                            recyclerView2.setAdapter(adapter);
                            progressDialog.dismiss();
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

    private void parseJson2() {
        String url1="https://api.themoviedb.org/3/movie/top_rated?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=4";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.getString("original_title");
                                String ReleasedDate = object.getString("release_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass1.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass1);
                            recyclerView3.setAdapter(adapter);
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


    private void parseJson1() {
        String url="https://api.themoviedb.org/3/movie/popular?api_key=3d5997641d1f4cfed4f532e5d7df75e3&language=en-US&page=15";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject object= array.getJSONObject(i);
                                String Title = object.getString("original_title");
                                String ReleasedDate = object.getString("release_date");
                                String Image= object.getString("poster_path");
                                String overView= object.getString("overview");
                                modelClass1.add(new ModelClass1(Image,Title,ReleasedDate,overView));
                            }
                            adapter= new myAdapter(getApplicationContext(),modelClass1);
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
    public void searchList(String text){
        ArrayList<ModelClass1> search= new ArrayList<>();
        for(ModelClass1 modalclass:modelClass1){
            if(modalclass.getmTitle().toLowerCase().contains(text.toLowerCase())){
                search.add(modalclass);
            }
        }
        adapter.searchmyModalClass(search);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}
