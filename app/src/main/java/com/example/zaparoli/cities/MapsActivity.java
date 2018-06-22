package com.example.zaparoli.cities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "Error Message";
    private DatabaseReference mDatabase;
    private DatabaseReference dDatabase;
    private DatabaseReference vDatabase;
    private DatabaseReference lDatabase;
    private Marker currentLocationMarker;
    private LatLng latLng;
    private View view;
    private Button Buraco, Dengue, Vazamento, Lixo;
    private Map<String, Marker> mMarkerMap = new HashMap<>();
    private ArrayList<String> mKeys = new ArrayList<>();


    public static MapsActivity newInstance() {
        MapsActivity fragment = new MapsActivity();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getActivity().setTitle("Mapa");


        Buraco = (Button) view.findViewById(R.id.Buraco);
        Dengue = (Button) view.findViewById(R.id.Dengue);
        Vazamento = (Button) view.findViewById(R.id.Vazamento);
        Lixo = (Button) view.findViewById(R.id.Lixo);

        Buraco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Buraco();

                Toast.makeText(getActivity(), "Marcação Adicionada", Toast.LENGTH_SHORT).show();

            }
        });

        Dengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dengue();

                Toast.makeText(getActivity(), "Marcação Adicionada", Toast.LENGTH_SHORT).show();

            }
        });

        Vazamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vazamento();

                Toast.makeText(getActivity(), "Marcação Adicionada", Toast.LENGTH_SHORT).show();

            }
        });

        Lixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lixo();

                Toast.makeText(getActivity(), "Marcação Adicionada", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            LatLng latLng = null;


            @Override
            public void onMarkerDragEnd(Marker marker) {

                LatLng latLng = marker.getPosition();

                String id = marker.getId();

                Toast.makeText(getActivity(), ""+latLng+id, Toast.LENGTH_LONG).show();

                Marker previousMarker = mMarkerMap.get(marker.getId());

                if (previousMarker != null) {
                    marker.remove();
                    mMap.addMarker(new MarkerOptions().draggable(true).position(latLng).title("Buraco").icon(BitmapDescriptorFactory.fromResource(R.drawable.road)));
                } else {
                    marker.remove();
                    mDatabase.push().setValue(latLng);

                }

            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                LatLng latLng = marker.getPosition();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                LatLng latLng = marker.getPosition();
                marker.setPosition(latLng);
            }
        });

        GPS();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Locais").child("Buraco");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                LatLng location = new LatLng(
                        dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class)

                );
                mMap.addMarker(new MarkerOptions().draggable(true).position(location).title("Buraco").icon(BitmapDescriptorFactory.fromResource(R.drawable.road)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                String key = dataSnapshot.getKey();

                    Log.i(TAG,key);
                    LatLng location = new LatLng(
                            dataSnapshot.child("latitude").getValue(double.class),
                            dataSnapshot.child("longitude").getValue(double.class)
                    );

                    mMap.addMarker(new MarkerOptions().draggable(true).position(location).title("Buraco").icon(BitmapDescriptorFactory.fromResource(R.drawable.road)));

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dDatabase = FirebaseDatabase.getInstance().getReference().child("Locais").child("Dengue");

        dDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                LatLng location = new LatLng(
                        dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class)
                );
                mMap.addMarker(new MarkerOptions().draggable(true).position(location).title("Dengue").icon(BitmapDescriptorFactory.fromResource(R.drawable.dengue)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        vDatabase = FirebaseDatabase.getInstance().getReference().child("Locais").child("Vazamento");

        vDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                LatLng location = new LatLng(
                        dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class)
                );
                mMap.addMarker(new MarkerOptions().draggable(true).position(location).title("Vazamento").icon(BitmapDescriptorFactory.fromResource(R.drawable.vazamento)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        lDatabase = FirebaseDatabase.getInstance().getReference().child("Locais").child("Lixo");

        lDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                LatLng location = new LatLng(
                        dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class)
                );
                mMap.addMarker(new MarkerOptions().draggable(true).position(location).title("Lixo").icon(BitmapDescriptorFactory.fromResource(R.drawable.lixeira)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void GPS() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50));


            }


        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }
    }

    public void Buraco() {
        try {
            locationManager = (LocationManager) getActivity(). getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                mDatabase.push().setValue(latLng);

            }

        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }
    }

    public void Dengue(){
        try {
            locationManager = (LocationManager) getActivity(). getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                dDatabase.push().setValue(latLng);

            }

        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }
    }

    public void Vazamento(){
        try {
            locationManager = (LocationManager) getActivity(). getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                vDatabase.push().setValue(latLng);

            }

        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }
    }

    public void Lixo() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                lDatabase.push().setValue(latLng);

            }

        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }

    }

}
