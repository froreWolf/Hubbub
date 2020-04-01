package com.apathyforge.hubbub;

import com.google.android.gms.maps.model.LatLng;

class LocationIndex
{
    private LatLng AU;
    private LatLng userLoc;
    private LatLng Mansfield;
    private LatLng Ontario;
    private LatLng Wooster;
    private LatLng Mifflin;
    private LatLng LoudonVille;
    private int numLocations;

    LocationIndex()
    {
        //Ashland University
        AU = new LatLng(40.8614888818401, -82.3217368125916);
        Mansfield = new LatLng(40.758416, -82.515276 );
        Ontario = new LatLng(40.762495, -82.593522);
        Wooster = new LatLng(40.809482, -81.935286);
        Mifflin = new LatLng(40.780358, -82.376628);
        LoudonVille = new LatLng(40.634109,-82.233968);
        numLocations = 6;
    }

    LatLng getlocation1()
    {
        return AU;
    }
    LatLng getlocation2()
    {
        return Mansfield;
    }
    LatLng getlocation3()
    {
        return Ontario;
    }
    LatLng getlocation4()
    {
        return Wooster;
    }
    LatLng getlocation5()
    {
        return Mifflin;
    }
    LatLng getlocation6()
    {
        return LoudonVille;
    }

    String getLocation1Name(){return "Ashland University";}
    String getLocation2Name(){return "Mansfield";}
    String getLocation3Name(){return "Ontario";}
    String getLocation4Name(){return "Wooster";}
    String getLocation5Name(){return "Mifflin";}
    String getLocation6Name(){return "LoudonVille";}


    int getNumLocations(){return numLocations;}

    //setter and getter for the user's current location
    void setUserLoc(LatLng newLoc){
        userLoc = newLoc;
    }

    LatLng getUserLoc(){
        return userLoc;
    }

}
