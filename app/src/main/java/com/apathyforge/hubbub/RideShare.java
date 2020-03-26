package com.apathyforge.hubbub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.maps.model.LatLng;

public class RideShare extends AppCompatActivity {

    private Button location1, location2, location3,
        location4, location5, location6;

    private LocationIndex index;
    private LocationManager locationManager;
    private DistanceRanker rankings;

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        //attach buttons to objects
        location1 = findViewById(R.id.location1_btn);
        location2 = findViewById(R.id.location2_btn);
        location3 = findViewById(R.id.location3_btn);
        location4 = findViewById(R.id.location4_btn);
        location5 = findViewById(R.id.location5_btn);
        location6 = findViewById(R.id.location6_btn);
        //create onclickListeners
        location1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });
        location2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });
        location3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });
        location4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });
        location5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });
        location6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //code to take the user to this chatroom
            }
        });

        sortLocationsByDistance();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
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
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //check permission for ACCESS_COARSE_LOCATION
            if(shouldShowRequestPermissionRationale(Manifest.permission_group.LOCATION))
            {
                Toast.makeText(this, "Your location is " +
                        "needed for this part of the application to " +
                        "function as intended", Toast.LENGTH_LONG).show();
                //get the permissions
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        else
        {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            index.setUserLoc(new LatLng(location.getLatitude(),location.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    startActivity(new Intent(this, Welcome.class));
                }
                return;
            }
        }
    }


    private final LocationListener locationListenerNetwork = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            getLocation();
        }

        // remaining three methods remain unused for this particular application
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){}

        @Override
        public void onProviderEnabled(String provider){}

        @Override
        public void onProviderDisabled(String provider){}
    };

    private void sortLocationsByDistance()
    {
        //this method will sort the locations by distance from the user's current
        //location and then list the closest locations first
    }

    private double calculateDistance(LatLng loc1, LatLng loc2)
    {
        Location location1 = new Location("");
        Location location2 = new Location("");
        location1.setLatitude(loc1.latitude);
        location1.setLongitude(loc1.longitude);
        location2.setLatitude(loc2.latitude);
        location2.setLongitude(loc2.longitude);
        return location1.distanceTo(location2);
}

class DistanceRanker
{
    private Node firstNode, lastNode;

    public DistanceRanker()
    {
        firstNode = lastNode = null;
    }

    public void addNode(String name, double distance)
    {
        Node newNode = new Node(name, distance);
        //first case if nodeCount == 0
        if(firstNode == null)
        {
            firstNode = lastNode = newNode;
        }
        else
        {
            lastNode.nextNode = newNode;
            lastNode = newNode;
        }
    }

    public void sortNodes()
    {

    }

    class Node
    {
        private String name;
        private double distance;
        private Node nextNode;

        public Node(String name, double distance)
        {
            this.name = name;
            this.distance = distance;
        }

        public String getName()
        {
            return name;
        }

        public double getDistance()
        {
            return distance;
        }

        public void setNextNode(Node next)
        {
            nextNode = next;
        }
    }
}
}
