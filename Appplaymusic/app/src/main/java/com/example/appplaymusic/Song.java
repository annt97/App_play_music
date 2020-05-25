package com.example.appplaymusic;

public class Song {
    private String songName;
    private int mp3File;

    public Song(String songName, int mp3File){
        this.songName = songName;
        this.mp3File = mp3File;
    }

    public String getSongName (){
        return this.songName;
    }

    public int getMp3File(){
        return this.mp3File;
    }

    public void setSongName(String songName){
        this.songName = songName;
    }

    public void setMp3File(int mp3File){
        this.mp3File = mp3File;
    }
}
