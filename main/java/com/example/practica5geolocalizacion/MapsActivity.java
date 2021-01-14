package com.example.practica5geolocalizacion;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private LocationListener listener;
    private LocationListener listener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button ruta = findViewById(R.id.btnempezar);
        Button mark = findViewById(R.id.btnmarkers);

        ruta.setOnClickListener(MapsActivity.this);
        mark.setOnClickListener(MapsActivity.this);




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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);



    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnmarkers){

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(  MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);






            } else {

                mMap.setMyLocationEnabled(true);
                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        LatLng position = new LatLng(latitude, longitude);

               /* mMap.addMarker(new MarkerOptions()
                        .position(position) //setting position
                        .draggable(true) //Making the marker draggable
                        .title("Mi Ubicacion")); //Adding a title
*/
                        //Moving the camera
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

                        Toast.makeText(getApplicationContext(), "Latitud: " + latitude + "Longitud: " + longitude, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                listener2 = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        LatLng position_ini = new LatLng(latitude, longitude);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position_ini, 7));

                        for (int i = 1; i < 4; i++) {

                            double randX = Math.random();
                            double randY = Math.random();
                            LatLng position = new LatLng(latitude + (randX), longitude - (randY));

                            mMap.addMarker(new MarkerOptions()
                                    .position(position) //setting position
                                    .draggable(true) //Making the marker draggable
                                    .title("Mi Ubicacion")); //Adding a title

                            //Moving the camera
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,7));

                            Toast.makeText(getApplicationContext(), "Latitud: " + latitude + "Longitud: " + longitude, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                LocationManager manager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);
                List<String> misProveedores = manager.getAllProviders();
                for (String miProveedor: misProveedores){
                    LocationProvider info = manager.getProvider(miProveedor);
                    Log.i("GEO", miProveedor);
                    Log.i("GEO", "REQUIERE SATÉLITE:" + info.requiresSatellite());
                }

                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5,
                        listener2);

            }
        }

        if(v.getId()==R.id.btnempezar) {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);


            } else {

                mMap.setMyLocationEnabled(true);
                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        LatLng position = new LatLng(latitude, longitude);

               /* mMap.addMarker(new MarkerOptions()
                        .position(position) //setting position
                        .draggable(true) //Making the marker draggable
                        .title("Mi Ubicacion")); //Adding a title
*/
                        //Moving the camera
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

                        Toast.makeText(getApplicationContext(), "Latitud: " + latitude + "Longitud: " + longitude, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                listener2 = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        LatLng position_ini = new LatLng(latitude, longitude);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position_ini, 7));

                        for (int i = 1; i < 4; i++) {

                            double randX = Math.random();
                            double randY = Math.random();
                            LatLng position = new LatLng(latitude + (randX), longitude - (randY));

                            mMap.addMarker(new MarkerOptions()
                                    .position(position) //setting position
                                    .draggable(true) //Making the marker draggable
                                    .title("Mi Ubicacion")); //Adding a title

                            //Moving the camera
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,7));

                            Toast.makeText(getApplicationContext(), "Latitud: " + latitude + "Longitud: " + longitude, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                LocationManager manager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);
                List<String> misProveedores = manager.getAllProviders();
                for (String miProveedor : misProveedores) {
                    LocationProvider info = manager.getProvider(miProveedor);
                    Log.i("GEO", miProveedor);
                    Log.i("GEO", "REQUIERE SATÉLITE:" + info.requiresSatellite());
                }

                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5,
                        listener);

            }
        }
    }
}