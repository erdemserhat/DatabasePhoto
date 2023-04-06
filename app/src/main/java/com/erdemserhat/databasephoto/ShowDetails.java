package com.erdemserhat.databasephoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.erdemserhat.databasephoto.databinding.ActivityMainBinding;
import com.erdemserhat.databasephoto.databinding.ActivityShowDetailsBinding;

public class ShowDetails extends AppCompatActivity {
    //Declaring binding reference
    private ActivityShowDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        Vocabulary vocabulary = (Vocabulary) intent.getSerializableExtra("vocabulary");
        String name = vocabulary.getName();
        String meaning = vocabulary.getMeaning();
        byte[] photo = vocabulary.getPhoto();

        binding.vNameShower.setText(name);
        binding.vMeaningShower.setText(meaning);
        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        binding.vImageShower.setImageBitmap(bitmap);
        

    }
}