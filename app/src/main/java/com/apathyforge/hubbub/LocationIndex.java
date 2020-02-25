package com.apathyforge.hubbub;

import com.google.android.gms.maps.model.LatLng;

public class LocationIndex
{
    private LatLng AU;
    private LatLng Hub1;
    private LatLng Hub2;
    private LatLng Hub3;
    private LatLng Hub4;

    public LocationIndex()
    {
        //Ashland University
        AU = new LatLng(40.8614888818401, -82.3217368125916);
        //Hub1 = new LatLng();
        //Hub2 = new LatLng();
        //Hub3 = new LatLng();
        //Hub4 = new LatLng();
    }

    public LatLng getAU()
    {
        return AU;
    }

    public LatLng getHub1()
    {
        return Hub1;
    }
    public LatLng getHub2()
    {
        return Hub2;
    }
    public LatLng getHub3()
    {
        return Hub3;
    }
    public LatLng getHub4()
    {
        return Hub4;
    }

}
