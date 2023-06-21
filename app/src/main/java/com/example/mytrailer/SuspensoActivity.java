package com.example.mytrailer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
public class SuspensoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspenso);
    }

    public void abrirVideo1(View view) {
        // Abre el video 1 en YouTube
        String videoId = "9e_Ij0S-sBw";
        reproducirVideoYouTube(videoId);
    }

    public void abrirVideo2(View view) {
        // Abre el video 2 en YouTube
        String videoId = "1FRtbDz6U1Y";
        reproducirVideoYouTube(videoId);
    }

    private void reproducirVideoYouTube(String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
        startActivity(intent);
    }
}