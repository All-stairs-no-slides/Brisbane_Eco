package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileNotFoundException;

public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOpenCamera;
    TextView txtResultBody;

    private BarcodeDetector detector;
    private Uri imageUri;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;
    private static final String TAG = "API123";
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcodecamera);

        initViews();

        /*
        if (savedInstanceState != null) {
            if (imageUri != null) {
                imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
                txtResultBody.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
            }
        }
        */

        // THIS LINE CRASHES THE CAMERA SCREEN FOR SOME REASON ?????
        // Tutorial: https://www.digitalocean.com/community/tutorials/qr-code-barcode-scanner-android
        detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        /*
        if (!detector.isOperational()) {
            txtResultBody.setText("Detector initialisation failed");
            return;
        }
        */
    }

    private void initViews() {
        txtResultBody = findViewById(R.id.txtResultsBody);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        txtResultBody = findViewById(R.id.txtResultsBody);
        btnOpenCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


    }

}