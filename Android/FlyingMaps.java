// This is the MainActivity.java file from a flying maps application written for my mobile applications class.
// A user is able to explore a map using the Google Maps API.
// The user can tilt their phone in any direction to move the maps.
// Application was called "FlyingMaps"

package flyingmaps.coreymccown.com.flyingmaps_cnm;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements SensorEventListener {
    private double oldxr, oldyr;
    private SensorManager mSensorManager;
    private Sensor rSensor;
    private Sensor aSensor;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private boolean madeLocationPoint = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, rSensor, SensorManager.SENSOR_DELAY_NORMAL);
        setUpMapIfNeeded();
    }

    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void getLocation () {
        LocationManager locationManager;
        LocationListener gpsListener;

        try {
            locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE); //1
            gpsListener = new LocationListener() { //2
                public void onLocationChanged(Location location) {
                    if(!madeLocationPoint) {
                        //txtLongitude.setText(String.valueOf(location.getLongitude()));
                        //txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                .title("YOU!")
                                .snippet("Found you :]")
                        );
                        Log.d("MapsActivity", "Got GPS location. Lat: " + location.getLatitude() + " Lon: " + location.getLongitude());
                        madeLocationPoint = true;
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled (String provider){}
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,gpsListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,gpsListener);
        }
            catch (Exception e) {
                Toast. makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show(); //4
                Log.d("MapsActivity", "Failed to get GPS location.");
            }
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng startPos = new LatLng(34.5283695, -83.9861997);
        mMap.addMarker(new MarkerOptions()
                .position(startPos)
                .title("Drill Field")
                .snippet("Drill Field on the Dahlonega Campus of UNG")
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPos, 7));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        getLocation();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float zoom = mMap.getCameraPosition().zoom;
        LatLng old = mMap.getCameraPosition().target;

        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            if(oldxr == 0)
                oldxr = event.values[0];

            if(oldyr == 0)
                oldyr = event.values[1];

            if(Math.abs(oldxr - event.values[0]) > 0.03)
            {
                double lat = 0;
                if(oldxr < event.values[0]) {
                    lat = old.latitude - (24 / (Math.pow(2, zoom - 3)));
                } else {
                    lat = old.latitude + (24 / (Math.pow(2, zoom - 3)));
                }
                LatLng newLatLng = new LatLng(lat, old.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
                oldxr = event.values[0];
            }
            if(Math.abs(oldyr - event.values[1]) > 0.03)
            {
                Log.d("MapsActivity", "Y: " + event.values[1] + " (" + (oldyr - event.values[1]) + ")");
                double lon = 0;
                if(oldyr < event.values[1]) {
                    lon = old.longitude + (24 / (Math.pow(2, zoom - 3)));
                } else {
                    lon = old.longitude - (24 / (Math.pow(2, zoom - 3)));
                }
                LatLng newLatLng = new LatLng(old.latitude, lon);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
                oldyr = event.values[1];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
