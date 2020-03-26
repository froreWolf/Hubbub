package com.apathyforge.hubbub;

import com.google.android.gms.maps.model.LatLng;

public class LocationIndex
{
    private LatLng AU;
    private LatLng userLoc;
    private LatLng Mansfield;
    private LatLng Ontario;
    private LatLng Wooster;
    private LatLng Mifflin;
    private LatLng LoudonVille;

    public LocationIndex()
    {
        //Ashland University
        AU = new LatLng(40.8614888818401, -82.3217368125916);
        Mansfield = new LatLng(40.758416, -82.515276 );
        Ontario = new LatLng(40.762495, -82.593522);
        Wooster = new LatLng(40.809482, -81.935286);
        Mifflin = new LatLng(40.780358, -82.376628);
    }

    public LatLng getAU()
    {
        return AU;
    }
    public LatLng getMansfield()
    {
        return Mansfield;
    }
    public LatLng getOntario()
    {
        return Ontario;
    }
    public LatLng getWooster()
    {
        return Wooster;
    }
    public LatLng getMifflin(){return Mifflin;}
    public LatLng getLoudonVille(){return LoudonVille;}

    public void setUserLoc(LatLng newLoc){
        userLoc = newLoc;
    }

    public LatLng getUserLoc(){
        return userLoc;
    }

}
