package com.example.puzzlegame;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ImageView camera;
    CircleImageView photo;
    EditText etName;
    Button submit;

    ActivityResultLauncher<Intent> cameraLauncher;
    int REQUEST_CAMERA_PERMISSION = 101;
    File CurentPhotoFile ;
    String currentPhotoPath ="";

    MyDataBaseHelper dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera=findViewById(R.id.camera);
        photo=findViewById(R.id.photo);
        etName=findViewById(R.id.etName);
        submit=findViewById(R.id.submit);
        dbHandler = new MyDataBaseHelper(MainActivity.this);





        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    currentPhotoPath="";
                    if (checkCameraPermission()) {
                        openCamera();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    String photoPath = currentPhotoPath;

                    dbHandler.addNewUser(etName.getText().toString(),photoPath);
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("name", etName.getText().toString());
                    intent.putExtra("image", currentPhotoPath);
                    startActivity(intent);
                    finish();
                } else {
                    if (etName.getText().toString().isEmpty()) {
                        etName.setError("Please enter a name");
                        etName.requestFocus();
                    }

                    if (currentPhotoPath.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please capture a photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Camera capture was successful, handle the result.
                displayImage(MainActivity.this,photo,currentPhotoPath);
            }else {
                Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
            }
        });




    }






    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.puzzlegame",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }

    }

    private void displayImage(Context context, ImageView imageView, String currentPhotoPath) {

        Glide.with(context)

                .load(currentPhotoPath)
                .placeholder(R.drawable.man)
                .error(R.drawable.ic_launcher_background)
                .into(photo);

    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        CurentPhotoFile=image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean isFormValid() {
        return !etName.getText().toString().isEmpty() && !currentPhotoPath.isEmpty();
    }


}