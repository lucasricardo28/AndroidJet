package com.ricardo.ricardo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ricardo.ricardo.R;
import com.ricardo.ricardo.adapter.AdapterFavorites;
import com.ricardo.ricardo.adapter.AdapterHome;
import com.ricardo.ricardo.models.ResponseList;
import com.ricardo.ricardo.service.SpaceService;
import com.ricardo.ricardo.utils.UtilConnexion;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesActivity extends AppCompatActivity {
    //ACTION BAR
    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;

    //RETROFIT
    private Retrofit retrofit;

    //LISTA DE ITENS
    private ListView listViewItems;

    //LOAD VIEW
    private LinearLayout llLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        listViewItems = findViewById(R.id.lvIncludeFavorite);
        llLoad = findViewById(R.id.LoadFavorites);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.dlFavorites);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();

        BindActionBar();

        NavigationView mNavigationView = findViewById(R.id.nvFavorites);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.itemMenuHome) {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
                else if (id == R.id.itemMenuFavorites) {
                    startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dlFavorites);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        BindRequest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(nToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void BindActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4a90e2'>Favorites</font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_action_bar_white));
        nToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.menu_hamburger));
    }

    private void BindRequest(){
        //INTERCEPTOR PRA SUA REQUISICAO
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(loggingInterceptor);

        UtilConnexion utilConnexion = new UtilConnexion();
        String url = utilConnexion.getUrl();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpaceService spaceService = retrofit.create(SpaceService.class);

        Call<List<ResponseList>> requestSpaceService = spaceService.GetDayByDayClasses();
        requestSpaceService.enqueue(new Callback<List<ResponseList>>() {
            @Override
            public void onResponse(Call<List<ResponseList>> call, Response<List<ResponseList>> response) {
                listViewItems.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    List<ResponseList> responseListList = response.body();

                    int i = 0;
                    for (ResponseList responseList: responseListList){
                        if(i == 0){
                            AdapterFavorites adapter = new AdapterFavorites(responseList, getLayoutInflater());
                            listViewItems.setAdapter(adapter);
                        }
                        i++;
                    }
                }

                llLoad.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ResponseList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.erro ,Toast.LENGTH_LONG).show();
            }
        });
    }
}
