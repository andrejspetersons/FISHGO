package com.example.fishgomobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.fishgomobile.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;


public class Loma_register_activity extends AppCompatActivity {
    private Button confirm_btn;
    private ImageView fish_picture;

    String[] fish_array = {"Forele", "Lusis", "Lidaka", "Rauda", "Bute"};
    String[] fishing_instruments = {"Makšķere", "Makšķerešanas tīkls", "Others"};
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loma_register);

        Button camera = findViewById(R.id.camera_btn);
        Button gallery = findViewById(R.id.gallery_btn);
        Button predict = findViewById(R.id.predict_btn);
        Button submit = findViewById(R.id.iesniegt_btn);
        EditText predicted_fish = findViewById(R.id.rezultats);
        fish_picture=findViewById(R.id.camera);


        getPermission();


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 12);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);

            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    try {
                        img = Bitmap.createScaledBitmap(img, 224, 224, true);
                    }catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"MAKE FISH SCREENSHOT!!",Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                        return;
                    }


                try {

                    ModelUnquant model = ModelUnquant.newInstance(Loma_register_activity.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);



                    TensorImage timage = new TensorImage(DataType.FLOAT32);
                    timage.load(img);
                    ByteBuffer byteBuffer = timage.getBuffer();


                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    ModelUnquant.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    float[] confidences = outputFeature0.getFloatArray();
                    // find the index of the class with the biggest confidence.
                    int maxPos = 0;
                    float maxConfidence = 0;
                    for (int i = 0; i < confidences.length; i++) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i];
                            maxPos = i;
                        }
                    }
                    String[] labels = new String[5];
                    int cnt = 0;
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            labels[cnt] = line;
                            cnt++;
                            line = bufferedReader.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//
                    predicted_fish.setText(labels[maxPos]);

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {

                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Loma_register_activity.this,Lomu_Vesture.class);
                intent.putExtra("nosaukums",predicted_fish.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });


    }


    public void  getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Loma_register_activity.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    img= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    fish_picture.setImageBitmap(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            img = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(img.getWidth(), img.getHeight());
            img = ThumbnailUtils.extractThumbnail(img, dimension, dimension);
            fish_picture.setImageBitmap(img);
            img = Bitmap.createScaledBitmap(img, 244, 244, false);
        }
        super.onActivityResult(requestCode, resultCode, data);




    }



}

