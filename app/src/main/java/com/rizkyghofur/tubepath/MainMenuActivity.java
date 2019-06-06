package com.rizkyghofur.tubepath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MainMenuActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.logofront, R.drawable.images2, R.drawable.images3, R.drawable.images4, R.drawable.images5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_navigation);

        //Memberikan listener saat menu item di bottom navigation diklik
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        Toast.makeText(MainMenuActivity.this, "Anda sedang berada di beranda", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_jadwal:
                        Toast.makeText(MainMenuActivity.this, "Jadwal clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_akun:
                        Intent intent0 = new Intent(MainMenuActivity.this, ProfileActivity.class);
                        startActivity(intent0);
                        break;
                }
                return true;
            }
        });

            Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                carouselView = findViewById(R.id.carouselView);
                carouselView.setPageCount(sampleImages.length);
                carouselView.setImageListener(imageListener);
            }

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    imageView.setImageResource(sampleImages[position]);
                }
            };

    public void Tugas(View view) {
        Intent intent = new Intent(MainMenuActivity.this, TugasActivity.class);
        startActivity(intent);
    }

    public void Alarm(View view) {
        Intent intent = new Intent(MainMenuActivity.this, AlarmActivity.class);
        startActivity(intent);
    }

    public void Belajar(View view) {
        Intent intent = new Intent(MainMenuActivity.this, BelajarActivity.class);
        startActivity(intent);
    }

    public void Tentang(View view) {
        Intent intent = new Intent(MainMenuActivity.this, TentangActivity.class);
        startActivity(intent);
    }

    public void Rapat(View view) {
        Intent intent = new Intent(MainMenuActivity.this, RapatActivity.class);
        startActivity(intent);
    }
}