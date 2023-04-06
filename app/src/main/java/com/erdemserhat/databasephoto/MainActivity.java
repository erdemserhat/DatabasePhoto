package com.erdemserhat.databasephoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.erdemserhat.databasephoto.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    //Declaring binding reference
    private ActivityMainBinding binding;
    private static final int PICK_IMAGE = 100;
    public static Vocabulary vocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //End of binding process.

        //Creating a database
        SQLiteDatabase database = this.openOrCreateDatabase("Vocabularies", MODE_PRIVATE, null);
        //Creating a table and columns
        database.execSQL("CREATE TABLE IF NOT EXISTS vocabularies (name TEXT, meaning TEXT, photo BLOB)");
        //Creating a cursor reference
        Cursor cursor = database.rawQuery("SELECT * FROM vocabularies ", null);
        //Defining column indexes

        int nameIx = cursor.getColumnIndex("name");
        int meaningIx = cursor.getColumnIndex("meaning");
        int photoIx = cursor.getColumnIndex("photo");

        //Reading data

        while (cursor.moveToNext()) {
            String name = cursor.getString(nameIx);
            String meaning = cursor.getString(meaningIx);
            byte[] photo = cursor.getBlob(photoIx);
            vocabulary = new Vocabulary(name, meaning, photo);
            System.out.println(vocabulary.getName());
            System.out.println(vocabulary.getMeaning());

        }

        /**
         * Save button
         */
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.vName.getText().toString();
                String meaning = binding.vMeaning.getText().toString();

                // Drawable nesnesini Bitmap nesnesine dönüştürme
                BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.vImage.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                // Byte dizisine dönüştürme
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] photo = stream.toByteArray();
                //Executing sql statement
                String sql = "INSERT INTO vocabularies (name, meaning, photo) VALUES (?,?,?)";
                SQLiteStatement statement = database.compileStatement(sql);
                statement.bindString(1, name);
                statement.bindString(2, meaning);
                statement.bindBlob(3, photo);
                statement.executeInsert();


            }
        });
        /**
         * Select button
         */

        binding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });


        /**
         * show button
         */

        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("vocabulary", vocabulary);
                startActivity(intent);
            }
        });


    }


    /**
     * Picking a image from gallery
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            binding.vImage.setImageURI(imageUri);
        }
    }
}