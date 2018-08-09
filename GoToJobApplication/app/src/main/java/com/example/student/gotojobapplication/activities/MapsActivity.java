package com.example.student.gotojobapplication.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.student.gotojobapplication.R;
import com.example.student.gotojobapplication.utils.DataProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        final Double lat = Double.valueOf(DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.MAP_KEY)).getLocation().getCoordinates().getLatitude());
        final Double lng = Double.valueOf(DataProvider.list.get(getIntent().getExtras().getInt(DataProvider.MAP_KEY)).getLocation().getCoordinates().getLongitude());
        LatLng user = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(user).title("User Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(user));
    }
}
