package com.kimtis.navigation;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimtis.MainActivity;
import com.kimtis.R;
import com.kimtis.data.CachedData;
import com.kimtis.data.EstmData;
import com.ppsoln.commons.position.LatLngAlt;

import java.util.LinkedList;

public class NavigationActivity extends AppCompatActivity {

    MapView mMapView;
    GoogleMap mMap;
    Marker myPositionMarker, destinationMarker;
    LatLng myPosition, destPosition;
    MarkerOptions mOption, destinationOption;
    TextView btn_add_dest;
    Location start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // For showing a move to my location button
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                mMap.getUiSettings().setTiltGesturesEnabled(false);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                myPosition = new LatLng(
                        CachedData.getInstance().getCurrentPosition().getLatitude(),
                        CachedData.getInstance().getCurrentPosition().getLongitude()
                );
                mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(myPosition, 16));
                mOption = new MarkerOptions();
                mOption.position(myPosition);
                mOption.anchor(0.5f, 0.5f);
                mOption.flat(true);
                mOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.mymarker_default));

                myPositionMarker = mMap.addMarker(mOption);


            }
        });

        btn_add_dest = (TextView) findViewById(R.id.btn_add_dest);
        btn_add_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                destinationOption = new MarkerOptions();
                destPosition = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                destinationOption.position(destPosition);
                destinationOption.anchor(0.5f, 0.5f);
                destinationOption.flat(true);
                destinationOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.destination));


                destinationMarker.setDraggable(true);
                destinationMarker.setTitle("Destination");

                destinationMarker = mMap.addMarker(destinationOption);
                btn_add_dest.setVisibility(TextView.GONE);
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (marker.getTitle().equals("Destination")) {
//                    end = new Location();
//                    end.setmarker.getPosition().
                }
            }
        });


    }

    int a = 0;
    MainActivity.OnNmeaCPCalculatedListener nListener = new MainActivity.OnNmeaCPCalculatedListener() {
        @Override
        public void onNmeaCPCalculated() {

            if (a-- > 0) {
                Log.e("position changed", "position changed");
                LatLngAlt modified_position = ((EstmData) (((LinkedList) (CachedData.getInstance().getEstmDataList())).getLast())).getModified_latLngAlt();
                myPosition = new LatLng(
                        modified_position.getLongitude(),
                        modified_position.getLatitude());
                mOption = new MarkerOptions();
                mOption.position(myPosition);
                mOption.anchor(0.5f, 0.5f);
                mOption.flat(true);
                // TODO : change icon
//                mOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.mymarker_positioning));
                mOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.mymarker_default));
                myPositionMarker.remove();
                myPositionMarker = mMap.addMarker(mOption);
                mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(myPosition, 16));
                a = 5;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.registerOnNmeaCPCalculatedListener(nListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.unregisterOnNmeaCPCalculatedListener(nListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
