package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.ImageType;
import com.tom_roush.pdfbox.rendering.PDFRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultsActivity extends AppCompatActivity {
    ImageButton im;
    File root;
    AssetManager assetManager;
    ImageView fp,sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Find the root of the external storage.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        fp = findViewById(R.id.first_page);
        sp = findViewById(R.id.second_page);
        im=findViewById(R.id.imageButton);
        root = getApplicationContext().getCacheDir();
        assetManager = getAssets();
        PDFBoxResourceLoader.init(getApplicationContext());

        im.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String uni = intent.getStringExtra("uni");
        int page1= Integer.parseInt(uni);
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("exams/Maths Summary.PDF");
        try {

            final File localfile = File.createTempFile("temp","pdf");
            mStorageReference.getFile(localfile)
                    .addOnSuccessListener(taskSnapshot -> {
                        try {
                            PDDocument document = PDDocument.load(localfile);
                            // Create a renderer for the document
                            PDFRenderer renderer = new PDFRenderer(document);
                            // Render the image to an RGB Bitmap
                            Bitmap pageImage1 = renderer.renderImage(page1, 1, ImageType.RGB);
                            Bitmap pageImage2 = renderer.renderImage(page1+1, 1, ImageType.RGB);
                            //---- will add the equation to get 2 pages for student ----//
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
                            } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).addOnFailureListener(e -> {

                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}