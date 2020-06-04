package com.example.vikashkumar.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikashkumar.R;
import com.example.vikashkumar.Utils.Constant;
import com.example.vikashkumar.Utils.Preferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.vikashkumar.Utils.Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class GoogleMapActivtiy extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {
    private static final String TAG = "MapActivity";

    private int cart_count = 0;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean mLocationPermissionGranted = false;

    String address_line,city , state , country, postalCode,landmark="";
    String address_line2;


    Geocoder geocoder;
    List<Address> addresses = new ArrayList<>();
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    TextView textViewLocation, textViewSearchingCity;
    private GoogleMap mMap;
    String action_Type = "";
    String address_ID = "";
    LinearLayout radioGroupType;
    LinearLayout homeCheckBoxLayout, workCheckBoxLayout, othersCheckBoxLayout;
    String address_Type = "";
    String address = "";

    String house_no = "";
    EditText fullAddressEt,shopNoEt;
    Double latitude;
    Double longitude;

    Preferences preferences;
    private static final float DEFAULT_ZOOM = 15f;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    TextInputLayout textInputLayoutHouse, textInputLayoutLandmark;
    MaterialButton materialButtonSaveAddress;

    Fragment mapFragment;
    TextView addressSaveAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_activtiy);

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.map);
        preferences= new Preferences(GoogleMapActivtiy.this);


        getLocationPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationPermissionGranted) {
            getLastLocation();
        }
    }

    private void getLocationPermission() {

        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {


        if (mLocationPermissionGranted) {
            if (isLocationEnabled()) {
                initMap();
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    Log.d(TAG, "onComplete: " + location);
                                    movecamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM);
                                    getAddressUsingLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                                }
                            }
                        }
                );
            } else {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setInterval(0);
                mLocationRequest.setFastestInterval(0);
                mLocationRequest.setNumUpdates(1);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                SettingsClient settingsClient = LocationServices.getSettingsClient(GoogleMapActivtiy.this);
                Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
                task.addOnSuccessListener(GoogleMapActivtiy.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (mLocationPermissionGranted) {
                            initMap();
                            requestNewLocationData();

                        }
                    }
                });
                task.addOnFailureListener(GoogleMapActivtiy.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            try {
                                resolvableApiException.startResolutionForResult(GoogleMapActivtiy.this, 40);
                            } catch (IntentSender.SendIntentException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        } else {
            getLocationPermission();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.d(TAG, "onLocationResult: " + mLastLocation);
            if (mLastLocation != null) {
                movecamera(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), DEFAULT_ZOOM);
                getAddressUsingLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            }
        }
    };

    public void getAddressUsingLatLng(LatLng latLng) {
        if (latLng != null) {
            geocoder = new Geocoder(GoogleMapActivtiy.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addresses.get(0) != null) {

                    latitude = latLng.latitude;
                    longitude=latLng.longitude;


                    address_line = addresses.get(0).getAddressLine(0);
                    address = addresses.get(0).getAddressLine(0);
                    address_line2 = "";
                    if (addresses.get(0).getMaxAddressLineIndex() > 2) {
                        address_line2 = addresses.get(0).getAddressLine(1);
                    }
                    // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    Log.d(TAG, "getAddressUsingLatLng: " + addresses.toString());


                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    landmark = addresses.get(0).getLocality();
                    String[] parts = address_line.split(",");


                    preferences.setAddress(address_line);



                    Toast.makeText(this, "" + address_line, Toast.LENGTH_SHORT).show();
//                TextView cityName = findViewById(R.id.cityName);
//
//                cityName.setText(state);


//                saveDefaultAddresss(address, address_line, city,
//                        postalCode, landmark, String.valueOf(mLastKnownsLocation.getLatitude()), String.valueOf(mLastKnownsLocation.getLongitude()), "Home", 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void movecamera(LatLng latLng, float zoom) {
        Log.d(TAG, "movecamera: moving the camera to Lat :" + latLng.latitude + ", lng :" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (mMap != null) {
            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (mLocationPermissionGranted) {
                        getLastLocation();

                    }
                    return true;
                }
            });
            mMap.setOnCameraMoveListener(this);
            mMap.setOnCameraIdleListener(this);
            mMap.setOnCameraMoveStartedListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map

                }
        }
    }

    @Override
    public void onCameraMove() {

//        double CameraLat = mMap.getCameraPosition().target.latitude;
//        double CameraLong = mMap.getCameraPosition().target.longitude;
//        getAddressUsingLatLng(new LatLng(CameraLat, CameraLong));

    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onCameraIdle() {
        double CameraLat = mMap.getCameraPosition().target.latitude;
        double CameraLong = mMap.getCameraPosition().target.longitude;
        getAddressUsingLatLng(new LatLng(CameraLat, CameraLong));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}