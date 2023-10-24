package com.example.brisbaneeco;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
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

import com.example.brisbaneeco.BLE.DataEnCryptDecrypt;
import com.google.android.material.snackbar.Snackbar;

import java.util.UUID;

public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    TextView tvDeviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

    private void startScanning() {

        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        // Define a ScanCallback to handle scan results
        ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                Log.d(TAG, String.valueOf(result.toString()));
                if (result.getScanRecord().getServiceUuids() != null) {
                    tvDeviceData.setText(DataEnCryptDecrypt.decryptFromUUID(result.getScanRecord().getServiceUuids().get(0).getUuid()));
                }

            }
            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Toast.makeText(MapActivity.this, "Scan failed: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        };

        // Check if this is the desired device based on its name or other criteria
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
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
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
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

//    private void sendDataToServer() {
//        new SendDataRequest().execute("Temperature: [20, 25, 40]");
//    }
//
//    private static class SendDataRequest extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... params) {
//            if (params.length == 0) return null;
//            String data = params[0];
//            try {
//                URL url = new
//                        URL("http://192.168.1.100:4567/receiveData");
//                HttpURLConnection conn = (HttpURLConnection)
//                        url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setDoOutput(true);
//                OutputStream os = conn.getOutputStream();
//                os.write(data.getBytes());
//                os.flush();
//                int responseCode = conn.getResponseCode();
//                System.out.println("Response Code: " + responseCode);
//                String responseMessage = conn.getResponseMessage();
//                System.out.println("Response Message: " + responseMessage);
//                // Read the response
//                BufferedReader in = new BufferedReader(new
//                        InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = in.readLine()) != null) {
//                    response.append(line);
//                }
//                in.close();
//                System.out.println("Response Message: " + response.toString());
//                conn.disconnect();
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Response Code: " + e);
//            }
//            return null;
//        }
//    }
}