package com.example.brisbaneeco;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.brisbaneeco.BLE.DataEnCryptDecrypt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    double[] RubbishXY = {-27.4773131, 153.022925};

    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        updateLongTextView(RubbishXY[0]);
        updateLatTextView(RubbishXY[1]);

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Bluetooth is not enabled");
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                requestBluetoothPermissions();
            }
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                requestBluetoothPermissions();
            }
        }
    }

//    the ble code
private void startScanning() {

    bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

    // Define a ScanCallback to handle scan results
    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            Log.d(TAG, String.valueOf(result.toString()));

            if (result.getScanRecord().getServiceUuids() != null) {
//                    tvDeviceData.setText(DataEnCryptDecrypt.decryptFromUUID(result.getScanRecord().getServiceUuids().get(0).getUuid()));

                Log.d(TAG, "results: " + DataEnCryptDecrypt.decryptFromUUID(result.getScanRecord().getServiceUuids().get(0).getUuid()));

                Log.d(TAG, DataEnCryptDecrypt.decryptFromUUID(result.getScanRecord().getServiceUuids().get(0).getUuid()));

            }

        }
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Toast.makeText(MapsActivity.this, "Scan failed: " + errorCode, Toast.LENGTH_SHORT).show();
        }
    };

    // Check if this is the desired device based on its name or other criteria
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
        requestBluetoothPermissions();
    }else {
        // Start scanning for nearby BLE devices
        bluetoothLeScanner.startScan(scanCallback);
    }
}

    private void requestBluetoothPermissions() {
        String[] permissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissions = new String[]{
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            };
        } else {
            permissions = new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
        }

        ActivityCompat.requestPermissions(this, permissions, REQUEST_BLUETOOTH_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            int grandetPermission = 0;
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    // Handle accordingly
                    grandetPermission++;

                    // Checking whether the number of granted permission is equal to the number or permission we requested.
                    if (grandetPermission == permissions.length) {
                        startScanning();
                    }
                } else if (grantResult == PackageManager.PERMISSION_DENIED) {
                    // Permission denied
                    // Handle denial (explain, request again, etc.)
                    if (!shouldShowRequestPermissionRationale(permission)) {
                        // User denied without asking again
                        // Explain why the permission is needed and prompt to go to settings
                        showPermissionExplanationDialog();
                    } else {
                        // User denied but can be asked again
                        // Explain why the permission is needed
                        showPermissionExplanationSnackbar();
                    }
                }
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showPermissionExplanationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required")
                .setMessage("This app needs Bluetooth permissions for BLE communication. You can grant these permissions in the app settings.")
                .setPositiveButton("Go to Settings", (dialog, which) -> openAppSettings())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void showPermissionExplanationSnackbar() {
        Snackbar.make(findViewById(android.R.id.content), "Bluetooth permissions are needed for BLE communication.", Snackbar.LENGTH_LONG)
                .setAction("Grant", view -> requestBluetoothPermissions())
                .show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng bin = new LatLng(RubbishXY[0], RubbishXY[1]);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(bin)
                .title("Rubbish Bin"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bin));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setTrafficEnabled(true);
    }

    public void updateLongTextView(double toThis){
        TextView textView = (TextView) findViewById(R.id.LongitudeText);
        textView.setText("X: " + toThis);
    }
    public void updateLatTextView(double toThis){
        TextView textView = (TextView) findViewById(R.id.LatitudeText);
        textView.setText("Y: " + toThis);
    }

}