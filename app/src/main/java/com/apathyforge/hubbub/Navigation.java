package com.apathyforge.hubbub;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Navigation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationIndex index;
    private MarkerOptions userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //check if the the GPS_PROVIDER or NETWORK_PROVIDER services are enabled
        boolean locationEnabled = isLocationEnabled();
        if (!locationEnabled)
        {
            showLocationAlert();
        }
        //create Location manager to be able to get user locations
        locationManager = (LocationManager) getSystemService(Context.
                LOCATION_SERVICE);
        //holds the LatLng data for map points
        index = new LocationIndex();
        //get the user's location and ensure that the
        // correct permissions are taken
        getLocation();
        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move
     * the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user
     * will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered
     * once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //clear all previous markers
        mMap.clear();
        //add all hub map markers as well as a marker for Ashland University
        mMap.addMarker(new MarkerOptions().position(index.getlocation1())
                .title(index.getLocation1Name()));
        mMap.addMarker(new MarkerOptions().position(index.getlocation2())
                .title(index.getLocation2Name()));
        mMap.addMarker(new MarkerOptions().position(index.getlocation3())
                .title(index.getLocation3Name()));
        mMap.addMarker(new MarkerOptions().position(index.getlocation4())
                .title(index.getLocation4Name()));
        mMap.addMarker(new MarkerOptions().position(index.getlocation5())
                .title(index.getLocation5Name()));
        mMap.addMarker(new MarkerOptions().position(index.getlocation6())
                .title(index.getLocation6Name()));

        //add the user's location
        userMarker = new MarkerOptions().position(
                index.getUserLoc()).title("YOU ARE HERE!");
        mMap.addMarker(userMarker);
        //setup the camera
        setCamera();
    }


    private void showLocationAlert() {
        final AlertDialog.Builder dialog = new
                AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your locations settings is turned 'off'.\n" +
                        "Please enable location to use this feature of Hubbub")
                .setPositiveButton("Location Settings",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //open up the location settings
                        startActivity(new Intent(Settings.
                                ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the activity and return to the welcome screen
                        startActivity(new Intent(Navigation.
                                this, Welcome.class));
                    }
                });
        dialog.show();
    }


    private boolean isLocationEnabled()
    {
        Context context = getApplicationContext();
        LocationManager lm = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager
                .PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //check permission for ACCESS_COARSE_LOCATION
            if(shouldShowRequestPermissionRationale(Manifest.permission_group.
                    LOCATION))
            {
                Toast.makeText(this, "Your location is " +
                        "needed for this part of the application to " +
                        "function as intended", Toast.LENGTH_LONG).show();
                //get the permissions
                requestPermissions(new String[]{Manifest.permission.
                        ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.
                        ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
        else
        {
            Location location = locationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            assert location != null;
            index.setUserLoc(new LatLng(location.getLatitude(),
                    location.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.
                    PERMISSION_GRANTED) {
                getLocation();
            } else {
                startActivity(new Intent(Navigation.this,
                        Welcome.class));
            }
        }
    }


    public void setCamera(){
        //get the new location for the camera and marker
        getLocation();
        //move location map marker to new location
        userMarker.position(index.getUserLoc());
        //set the camera
        CameraPosition initialPosition = CameraPosition.builder()
                .target(index.getUserLoc())
                .zoom(10)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(initialPosition)
                , 5000, null);
    }
}
