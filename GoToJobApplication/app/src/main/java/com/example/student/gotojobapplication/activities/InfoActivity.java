package com.example.student.gotojobapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.gotojobapplication.models.Result;
import com.example.student.gotojobapplication.utils.DataProvider;
import com.example.student.gotojobapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class InfoActivity extends AppCompatActivity {

    private ImageView infoImage;
    private TextView infoName;
    private TextView infoPhone;
    private TextView infoEmail;
    private TextView infoGender;
    private TextView infoNation;
    private TextView infoUserName;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        foundViews();
        setInfo();
        getMapWithLocation();
    }

    private void foundViews() {
        infoImage = findViewById(R.id.info_image);
        infoName = findViewById(R.id.info_name);
        infoPhone = findViewById(R.id.info_phone);
        infoEmail = findViewById(R.id.info_email);
        infoGender = findViewById(R.id.info_gender);
        infoNation = findViewById(R.id.info_national);
        infoUserName = findViewById(R.id.info_username);
        floatingActionButton = findViewById(R.id.fab);
    }

    private void setInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Picasso.get().load(getListItem().getPicture().getLarge()).into(infoImage);
            infoName.setText(getListItem().getName().getFirst());
            infoPhone.setText(getListItem().getPhone());
            infoEmail.setText(getListItem().getEmail());
            infoGender.setText(getListItem().getGender());
            infoNation.setText(getListItem().getNat());
            infoUserName.setText(getListItem().getLogin().getUsername());

        }
    }

    private void getMapWithLocation() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(InfoActivity.this, MapsActivity.class);
                intent.putExtra(DataProvider.MAP_KEY, Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    private Result getListItem() {
        return DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY));
    }
}
