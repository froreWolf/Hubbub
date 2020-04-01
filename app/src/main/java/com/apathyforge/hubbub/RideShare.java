package com.apathyforge.hubbub;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class RideShare extends AppCompatActivity {

    private Button location1, location2, location3,
            location4, location5, location6;
    private LatLng[] locRanks;
    private String[] locRankNames;
    private LocationIndex index;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //create the location index and get user location
        index = new LocationIndex();
        locationManager = (LocationManager) getSystemService(Context.
                LOCATION_SERVICE);
        getLocation();
        //attach buttons to objects
        location1 = findViewById(R.id.location1_btn);
        location2 = findViewById(R.id.location2_btn);
        location3 = findViewById(R.id.location3_btn);
        location4 = findViewById(R.id.location4_btn);
        location5 = findViewById(R.id.location5_btn);
        location6 = findViewById(R.id.location6_btn);
        //sort locations by distance
        sortLocationsByDistance();
        labelButtons();
        //create onclickListeners
        location1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[0]);
            }
        });
        location2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[1]);
            }
        });
        location3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[2]);
            }
        });
        location4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[3]);
            }
        });
        location5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[4]);
            }
        });
        location6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendToChatRoom(locRankNames[5]);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
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
            Location location = locationManager.
                    getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                startActivity(new Intent(this,
                        Welcome.class));
            }
        }
    }


    private void sortLocationsByDistance()
    {
        //array that holds results
        locRanks = new LatLng[index.getNumLocations()];
        locRankNames = new String[index.getNumLocations()];

        //calculate places
        placeNewLocation(index.getlocation1(), index.getLocation1Name());
        placeNewLocation(index.getlocation2(), index.getLocation2Name());
        placeNewLocation(index.getlocation3(), index.getLocation3Name());
        placeNewLocation(index.getlocation4(), index.getLocation4Name());
        placeNewLocation(index.getlocation5(), index.getLocation5Name());
        placeNewLocation(index.getlocation6(), index.getLocation6Name());
    }

    private void placeNewLocation(LatLng newLocation, String locationName)
    {
        //keep track of where in the array you are
        int i = 0;

        while(newLocation != null)
        {
            //if the location is null then place the location, otherwise swap
            if(locRanks[i] == null)
            {
                locRanks[i] = newLocation;
                locRankNames[i] = locationName;
                newLocation = null;
            }
            else
            {
                //if newLocation is closer swap for
                // current rankings[i] then continue
                if(calculateDistance(locRanks[i]) >
                        calculateDistance(newLocation))
                {
                    //swap out the LatLng
                    LatLng temp = locRanks[i];
                    locRanks[i] = newLocation;
                    newLocation = temp;
                    //swap out the Names
                    String tempName = locRankNames[i];
                    locRankNames[i] = locationName;
                    locationName = tempName;
                    //increment the counter
                    i++;
                }
                //if the distance is more to the new location, test next space
                else if(calculateDistance(locRanks[i]) <
                        calculateDistance(newLocation)
                || calculateDistance(locRanks[i]) ==
                        calculateDistance(newLocation))
                {
                    i++;
                }
            }
        }
    }

    private double calculateDistance(LatLng loc)
    {
        Location location1 = new Location("");
        Location location2 = new Location("");
        location1.setLatitude(index.getUserLoc().latitude);
        location1.setLongitude(index.getUserLoc().longitude);
        location2.setLatitude(loc.latitude);
        location2.setLongitude(loc.longitude);
        return location1.distanceTo(location2);
    }

    private void labelButtons()
    {
        //set the button names based on the rankings
        location1.setText(locRankNames[0]);
        location2.setText(locRankNames[1]);
        location3.setText(locRankNames[2]);
        location4.setText(locRankNames[3]);
        location5.setText(locRankNames[4]);
        location6.setText(locRankNames[5]);

    }

    private void sendToChatRoom(String locName)
    {
        Intent intent = new Intent(RideShare.this, chatRoom.class);

        if(locName.equals(index.getLocation1Name()))
        {
            intent.putExtra("location", "1");
            startActivity(intent);
        }
        else if(locName.equals(index.getLocation2Name()))
        {
            intent.putExtra("location", "2");
            startActivity(intent);
        }
        else if(locName.equals(index.getLocation3Name()))
        {
            intent.putExtra("location", "3");
            startActivity(intent);
        }
        else if(locName.equals(index.getLocation4Name()))
        {
            intent.putExtra("location", "4");
            startActivity(intent);
        }
        else if(locName.equals(index.getLocation5Name()))
        {
            intent.putExtra("location", "5");
            startActivity(intent);
        }
        else
        {
            intent.putExtra("location", "6");
            startActivity(intent);
        }
    }
}
