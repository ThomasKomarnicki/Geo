package doglandia.com.server.model;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

/**
 * Created by Thomas on 6/5/2015.
 */

@Parcel
public class Place {

    public Place(){

    }

    int id;

    LatLng latLng;

    public Place(LatLng latLng){
        setLatLng(latLng);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
