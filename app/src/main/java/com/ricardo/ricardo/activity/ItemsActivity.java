package com.ricardo.ricardo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardo.ricardo.R;
import com.ricardo.ricardo.adapter.AdapterHome;
import com.ricardo.ricardo.models.ItemsCategory;
import com.ricardo.ricardo.models.ResponseList;
import com.ricardo.ricardo.pageadapter.PagerAdapterItem;
import com.ricardo.ricardo.service.SpaceService;
import com.ricardo.ricardo.utils.UtilConnexion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private Integer positionItem;
    private String titleItem;

    private ViewPager viewPagerFragments;
    private TabLayout tabLayoutItems;
    private ImageView imageViewBack, imageViewImage;
    private ProgressBar progressBar;
    private TextView textViewLoad;
    private RatingBar ratingBar;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        getSupportActionBar().hide();

        viewPagerFragments = findViewById(R.id.vpItem);
        tabLayoutItems = findViewById(R.id.tlItem);
        imageViewBack = findViewById(R.id.ivItemBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageViewImage = findViewById(R.id.ivItemImage);
        progressBar = findViewById(R.id.pbItem);
        textViewLoad = findViewById(R.id.tvItemLoad);
        ratingBar = findViewById(R.id.rbItem);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getApplicationContext(), R.string.item_change_rate,Toast.LENGTH_LONG).show();
                ratingBar.setRating(v);
            }
        });

        Bundle extras = getIntent().getExtras();
        positionItem = extras.getInt("level");
        titleItem = extras.getString("gallery");
        BindRequest();
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
                if(response.isSuccessful()){
                    List<ResponseList> responseListList = response.body();

                    int i = 0;
                    for (ResponseList responseList : responseListList){
                        if( i == positionItem){
                            for(ItemsCategory itemsCategory : responseList.getItems()){
                                tabLayoutItems.addTab(tabLayoutItems.newTab().setText(itemsCategory.getTitle()));
                            }

                            PagerAdapterItem adapter = new PagerAdapterItem(getSupportFragmentManager(), tabLayoutItems.getTabCount(),responseList.getItems(),titleItem );
                            viewPagerFragments.setAdapter(adapter);
                            viewPagerFragments.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutItems));
                            tabLayoutItems.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    viewPagerFragments.setCurrentItem(tab.getPosition());
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });
                            BindPhotoStudent( responseList.getItems().get(0).getGalery()[0]);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.erro ,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void BindPhotoStudent(final String urlImage) {

        new Thread(){
            public void run(){
                Bitmap img = null;
                try {
                    URL newUrl = new URL(urlImage);
                    HttpURLConnection conexao = (HttpURLConnection) newUrl.openConnection();
                    InputStream inputStream = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap imgAux = img;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        textViewLoad.setVisibility(View.GONE);
                        imageViewImage.setVisibility(View.VISIBLE);
                        imageViewImage.setImageBitmap(imgAux);
                    }
                });
            }
        }.start();
    }
}
