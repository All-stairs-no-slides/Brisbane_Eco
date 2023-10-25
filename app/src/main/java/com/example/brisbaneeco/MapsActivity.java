package com.example.brisbaneeco;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    double[] RubbishXY = {-27.4773131, 153.022925};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        updateLongTextView(RubbishXY[0]);
        updateLatTextView(RubbishXY[1]);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng bin = new LatLng(RubbishXY[0], RubbishXY[1]);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(bin)
                .title("Rubbish Bin"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bin));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setTrafficEnabled(true);
    }

    public void updateLongTextView(double toThis){
        TextView textView = (TextView) findViewById(R.id.LongitudeText);
        textView.setText("X: " + toThis);
    }
    public void updateLatTextView(double toThis){
        TextView textView = (TextView) findViewById(R.id.LatitudeText);
        textView.setText("Y: " + toThis);
    }

}