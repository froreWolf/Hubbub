package com.apathyforge.hubbub;

import androidx.annotation.RequiresApi;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Navigation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean updateLocation;
    private LocationManager locationManager;
    private Location location;
    private LocationIndex index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //check if the the GPS_PROVIDER or NETWORK_PROVIDER services are enabled
        boolean locationEnabled = isLocationEnabled();
        if (!locationEnabled) {
            showLocationAlert();
        }
        //holds the LatLng data for map points
        index = new LocationIndex();
        //sets location tracking off initially
        updateLocation = false;
        //create Location manager to be able to get user locations
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        //clear all previous markers
        mMap.clear();
        //add all hub map markers as well as a marker for Ashland University
        mMap.addMarker(new MarkerOptions().position(index.getAU()).title("Ashland University"));

        //get the user's location to be used to position the camera
        getLocation();
        //fffLatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
        //set camera position and animate into it
        CameraPosition initialPosition = CameraPosition.builder()
                .target(index.getAU())
                .zoom(10)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(initialPosition)
                , 5000, null);
        //marker click listeners
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker){
                LatLng position = marker.getPosition();
                //link to google maps application?
                //possibly another activity that will handle navigation?
                return false;
            }
        });
    }


    private void showLocationAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your locations settings is turned 'off'.\n" +
                        "Please enable location to use this feature of Hubbub")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //open up the location settings
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the activity and return to the welcome screen
                        startActivity(new Intent(Navigation.this, Welcome.class));
                    }
                });
        dialog.show();
    }


    private boolean isLocationEnabled() {
        Context context = getApplicationContext();
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private final LocationListener locationListenerNetwork = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            if(updateLocation)
            {

            }
        }

        // remaining three methods remain unused for this particular application
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){}

        @Override
        public void onProviderEnabled(String provider){}

        @Override
        public void onProviderDisabled(String provider){}
    };
}
