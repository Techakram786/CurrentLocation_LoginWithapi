package com.techakram.loginretrofit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.techakram.loginretrofit.R;

public class MainActivity extends AppCompatActivity {
   Button btnLocation;
   TextView textView1,textView2;
   FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=findViewById(R.id.tv11);
        textView2=findViewById(R.id.tv22);
        btnLocation=findViewById(R.id.btn1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this );
       btnLocation.setOnClickListener(new View.OnClickListener( ) {
           @Override
           public void onClick(View view) {
               if(ActivityCompat.checkSelfPermission(MainActivity.this,
                       Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                       Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
               {
                 getCurrentLocation();
               }
               else
               {
                   //when permission not granted
                   //request for permission
                   ActivityCompat.requestPermissions(MainActivity.this,
                           new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                   ,Manifest.permission.ACCESS_COARSE_LOCATION},100);
               }
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100 && grantResults.length>0 && (grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED))
        {
          //when permission granted
            //call method..
            getCurrentLocation();
        }
        else
        {
            //permission are denied..
            Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show( );
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
           fusedLocationProviderClient.getLastLocation()
                   .addOnCompleteListener(new OnCompleteListener<Location>( ) {
               @Override
               public void onComplete(@NonNull Task<Location> task) {
                   final Location location=task.getResult();
                   if(location!=null)
                   {
                       textView1.setText(String.valueOf(location.getLatitude()));
                       textView2.setText(String.valueOf(location.getLongitude()));
                   }
                   else
                   {
                       LocationRequest locationRequest=new LocationRequest()
                               .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                               .setInterval(10000)
                               .setFastestInterval(10000)
                               .setNumUpdates(1);
                       LocationCallback locationCallback=new LocationCallback( ) {
                           @Override
                           public void onLocationResult(@NonNull LocationResult locationResult) {
                              Location location1=locationResult.getLastLocation();
                              textView1.setText(String.valueOf(location1.getLatitude()));
                              textView2.setText(String.valueOf(location1.getLongitude()));
                           }
                       };
                       //request loacation update..
                       fusedLocationProviderClient.requestLocationUpdates(locationRequest
                       ,locationCallback, Looper.myLooper());
                   }
               }
           });
        }
        else {
            //when location service is not enabled..
            //open location setting..
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}