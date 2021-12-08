package com.aait.oms.orders;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.aait.oms.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String address ;
    List<Address> addresses = new ArrayList<Address>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
           address = extras.getString("address");
        }
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
    @Override
    public void onMapReady(GoogleMap gMap) {
        mMap = gMap;



        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        Geocoder geo = new Geocoder(this);
        try {
            addresses= geo.getFromLocationName(address, 5);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        double lat=addresses.get(0).getLatitude();
        double lng=addresses.get(0).getLongitude();

        LatLng point = new
                LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(point).title(address).snippet(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
    }
}