package com.example.appplaymusic;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private List<Song> lstSongs = new ArrayList<Song>();
    private MediaPlayer mediaPlayer;
    private ImageView btnPrev, btnNext, btnPlay, btnRepeat, btnRandom;
    private TextView txtSongName, txtTimeStart, txtTimeEnd;
    private SeekBar seekBarSong;
    private int currentSong = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLstSongs();
        getAllId();
        start();
        setStatePlay();
        autoNextSong();
        setStateChangeSong();

        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void start(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, lstSongs.get(this.currentSong).getMp3File());
        mediaPlayer.start();
        setStartTime();
        setEndTime();
        txtSongName.setText(lstSongs.get(this.currentSong).getSongName());
    }

    private void autoNextSong(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentSong = currentSong + 1 > lstSongs.size() - 1 ? 0 : currentSong + 1;
                start();
                btnPlay.setImageResource(R.drawable.pause);
            }
        });
    }

    private void setStateChangeSong(){
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                currentSong = currentSong - 1 < 0 ? lstSongs.size() - 1 : currentSong - 1;
                start();
                btnPlay.setImageResource(R.drawable.pause);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                currentSong = currentSong + 1 > lstSongs.size() - 1 ? 0 : currentSong + 1;
                start();
                btnPlay.setImageResource(R.drawable.pause);
            }
        });
    }

    private void setStatePlay(){
        if (mediaPlayer.isPlaying()){
            btnPlay.setImageResource(R.drawable.pause);
        }else {
            btnPlay.setImageResource((R.drawable.play));
        }

        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                } else {
                    btnPlay.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });
    }

    private void setEndTime(){
        int timeSong = mediaPlayer.getDuration();
        String time = String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(timeSong),
                TimeUnit.MILLISECONDS.toSeconds(timeSong) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSong)));
        txtTimeEnd.setText(time);
        seekBarSong.setMax(timeSong/1000);
    }

    private void setStartTime(){
        handler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int currentTime = 0;
                if (mediaPlayer != null) {
                    currentTime = mediaPlayer.getCurrentPosition() / 1000;
                    seekBarSong.setProgress(currentTime);
                }
                handler.postDelayed(this, 1000);
                String time = String.format("%d:%d",
                        TimeUnit.SECONDS.toMinutes(currentTime),
                        TimeUnit.SECONDS.toSeconds(currentTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(currentTime)));
                txtTimeStart.setText(time);
            }
        });
    }

    private void getAllId(){
        this.btnRandom = findViewById(R.id.btnRandom);
        this.btnPrev = findViewById(R.id.btnPrev);
        this.btnPlay = findViewById(R.id.btnPlay);
        this.btnNext = findViewById(R.id.btnNext);
        this.btnRandom = findViewById(R.id.btnRepeat);
        this.txtTimeEnd = findViewById(R.id.txtTimeEnd);
        this.txtTimeStart = findViewById(R.id.txtTimeStart);
        this.txtSongName = findViewById(R.id.songName);
        this.seekBarSong = findViewById(R.id.seekBar);
    }

    private void initLstSongs(){
        Song song1 = new Song("Beautiful in white", R.raw.beautifulinwhite);
        Song song2 = new Song("Take me to your heart", R.raw.takemetoyourheart);
        Song song3 = new Song("Yêu từ đâu mà ra orinn remix", R.raw.yeutudaumaraorinnremix);
        Song song4 = new Song("Ignite", R.raw.ignite);
        Song song5 = new Song("Reality", R.raw.reality);
        Song song6 = new Song("Phoenix", R.raw.phoenix);
        lstSongs.add(song1);
        lstSongs.add(song2);
        lstSongs.add(song3);
        lstSongs.add(song4);
        lstSongs.add(song5);
        lstSongs.add(song6);
    }
}
