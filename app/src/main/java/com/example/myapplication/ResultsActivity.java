package com.example.myapplication;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.ImageType;
import com.tom_roush.pdfbox.rendering.PDFRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
// PDFBox Link : https://github.com/TomRoush/PdfBox-Android

public class ResultsActivity extends AppCompatActivity {
    ImageButton im;
    Button sr;
    File root;
    AssetManager assetManager;
    ImageView fp,sp;
    ProgressBar p;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Find the root of the external storage.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        fp = findViewById(R.id.first_page);
        sp = findViewById(R.id.second_page);
        im=findViewById(R.id.imageButton);
        sr=findViewById(R.id.send_req);
        root = getApplicationContext().getCacheDir();
        assetManager = getAssets();
        p=findViewById(R.id.progressBar);
        PDFBoxResourceLoader.init(getApplicationContext());

        sr.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle((R.string.send_request));
            builder.setMessage(R.string.message);
            builder.setPositiveButton(getString(R.string.send), (dialogInterface, i) -> {
                finish();
            });
            builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                dialogInterface.dismiss();
            }).show();
        });
        im.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String uni = intent.getStringExtra("uni");
        int page1= Integer.parseInt(uni);
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("exams/AI midterm 2021-2022.pdf");
        try {

            final File local_file = File.createTempFile("temp","pdf");
            mStorageReference.getFile(local_file)
                    .addOnSuccessListener(taskSnapshot -> {
                        try {
                            PDDocument document = PDDocument.load(local_file);
                            // Create a renderer for the document
                            PDFRenderer renderer = new PDFRenderer(document);
                            int pages = document.getNumberOfPages();
                            if(page1<pages-1) {
                                // Render the image to an RGB Bitmap
                                Bitmap pageImage1 = renderer.renderImage(page1 - 1, 1, ImageType.RGB);
                                Bitmap pageImage2 = renderer.renderImage(page1, 1, ImageType.RGB);
                                //---- will add the equation to get 2 pages for student ----//
                            /*
                                Suppose first student code = "200" -> x
                                each exam papers = 2;
                                input-> 201
                                y = (201 - 200)+1= 2;
                                so we will display  page (2*y) , (2*y)-1
                                                ==> pages( 4 , 3 );
                             */
                                // Save the render result to an image
                                String path = root.getAbsolutePath() + "/render.jpg";
                                File renderFile = new File(path);
                                FileOutputStream fileOut = new FileOutputStream(renderFile);
                                pageImage1.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
                                pageImage2.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
                                fileOut.close();
                                // Optional: display the render result on screen
                                fp.setImageBitmap(pageImage1);
                                sp.setImageBitmap(pageImage2);
                                p.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),ResultsActivity.class);
                                startActivity(i);
                            }
                            } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}