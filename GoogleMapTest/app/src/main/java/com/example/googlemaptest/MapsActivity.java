package com.example.googlemaptest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaptest.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private int MY_PERMISSIONS_REQUEST_LOCATION = 10;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final LatLng SEOUL = new LatLng(37.566535, 126.977969);
    private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng BUSAN = new LatLng(35.179554, 129.075642);

    private Marker mSeoul;
    private Marker mDaejeon;
    private Marker mBusan;

    Marker car;

    //이동된 장소 변수
    Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //위도 정보 가져오기
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            //위치 변경된 정보 넘어옴
            public void onLocationChanged(Location location) {
                LatLng newlatLng = new LatLng(location.getLatitude(), location.getLongitude());


                if (lastKnownLocation != null){
                    LatLng lastlatlng = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                    PolylineOptions options = new PolylineOptions().add(lastlatlng).add(newlatLng);
                    mMap.addPolyline(options);
                    car.remove();


                }
                //현재 위치 변수
                lastKnownLocation = location;
                car = mMap.addMarker(new MarkerOptions().position(newlatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));

            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        //각종 정보 추가 한다.

        mSeoul = mMap.addMarker(new MarkerOptions().position(SEOUL).title("SEOUL"));
        mSeoul.setTag(0);

        mDaejeon = mMap.addMarker(new MarkerOptions()
                .position(DAEJEON).title("Daejeon")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)));
        mDaejeon.setTag(0);

        mBusan = mMap.addMarker(new MarkerOptions().position(BUSAN).title("Busan"));
        mBusan.setTag(0);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        //폴리라인 추가
        mMap.addPolyline((new PolylineOptions()).add(SEOUL, BUSAN, DAEJEON));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Integer clickCount = (Integer) marker.getTag();
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " 가 클릭되었음, 클릭횟수: " + clickCount,
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}