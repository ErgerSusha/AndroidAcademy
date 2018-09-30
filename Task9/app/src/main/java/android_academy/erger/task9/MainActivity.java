package android_academy.erger.task9;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int PERMISSIONS_REQUEST_CODE = 1;
    public static final String URL = "https://www.sample-videos.com/img/Sample-jpg-image-1mb.jpg";

    public static void startActivity(Context context, Bundle extras) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFab();

        checkForIntentExtras();
    }

    private void checkForIntentExtras() {
        String filePath = getIntent().getStringExtra(DownloadService.FILE_PATH);
        if (filePath != null) {
            Log.d("TAG", "MainActivity # checkForIntentExtras, filePath: " + filePath);
            showImage(filePath);
        }
    }

    private void showImage(String filePath) {
        ImageView imageView = findViewById(R.id.picImageView);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bitmap);
    }

    private void setFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick();
            }
        });
    }

    private void onFabClick() {
        if (isPermissionGranted()) {
            startDownloadService();
        } else {
            requestPermission();
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    private void startDownloadService() {
        Log.d("TAG", "startDownloadService");
        DownloadService.startService(this, URL);
        onBackPressed();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
            showExplainingRationaleDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showExplainingRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the contacts-related task you need to do.
                Log.d("TAG", "onRequestPermissionsResult # Permission granted");
                startDownloadService();
            } else {
                // permission denied, boo! Disable the functionality that depends on this permission.
                Log.d("TAG", "onRequestPermissionsResult # Permission denied");
            }
        }
    }
}