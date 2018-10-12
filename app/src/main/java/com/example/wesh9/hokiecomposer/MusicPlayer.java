package com.example.wesh9.hokiecomposer;

import android.media.MediaPlayer;

/**
 * Created by wesh9 on 10/23/2017.
 */


public class MusicPlayer implements MediaPlayer.OnCompletionListener {
    //audio files
    static final String[] MUSICPATH = new String[]{
            "cheering",
            "clapping",
            "gotechgo",
            "lestgohokies",
            "mario",
            "pacman","tetris"
    };

    //titles to the files
    static final String[] MUSICNAME = new String[]{
            "Cheering",
            "Clapping",
            "Go Tech Go",
            "Letsgohokies",
            "Mario",
            "Pacman","Tetris"
    };

    String song;


    //reference of the service
    private MusicService musicService;

    //Android's media player
    MediaPlayer player;

    // seek possition
    int currentPosition = 0;

    //selected song
    int musicIndex = 0;


    //id of the file in the folder
    int resID=-1;

    //0: before starts 1: playing 2: paused
    private int musicStatus = 0;

    public int getMusicStatus() {
        return musicStatus;
    }

    public String getMusicName() {
        return MUSICNAME[musicIndex];
    }

    public int getDur(){
        return player.getDuration();
    }

    private void findIndex(String title){
        if(title.equals("Go Tech Go")){
            musicIndex = 2;
        }
        else if (title.equals("Mario")){
            musicIndex = 4;
        }
        else if(title.equals("Tetris")){
            musicIndex = 6;
        }
        else if(title.equals("Pacman")){
            musicIndex = 5;
        }
        else if(title.equals("Cheering")){
            musicIndex = 0;
        }
        else if(title.equals("Clapping")){
            musicIndex = 1;
        }
        else if(title.equals("Go Hokies")){
            musicIndex = 3;
        }

    }


    //starts playing
    public void playMusic(String title) {

        //build the media player
        //play from res/raw directly
        song = title;

        try{
            player = new MediaPlayer();

            findIndex(title);

            int resID=musicService.getResources().getIdentifier(MUSICPATH[musicIndex], "raw", musicService.getPackageName());

            player=MediaPlayer.create(musicService,resID);
            player.start();
            musicService.onUpdateMusicName(getMusicName());
            player.setOnCompletionListener(this);


        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        musicStatus = 1;
    }

    public void pauseMusic() {
        if (player != null && player.isPlaying()) {
            // pause the player
            player.pause();
            //save current position
            currentPosition = player.getCurrentPosition();
            //update status
            musicStatus = 2;
        }
    }

    public void resumeMusic(int start) {
        if (player != null) {
            //reusme to the saved position
            player.seekTo(currentPosition);
            //start player
            if(start == 2) {
                player.start();
                //udpate status
                musicStatus = 1;
            }
        }
    }

    public void restartMusic(int ov){
        if(ov == 0){//if its background
            player.seekTo(0);
            player.start();
        }
        else{
            if(player != null) {
                player.pause();
                player.seekTo(0);
            }
        }
        musicStatus = 1;


    }


    public void stopMusic(){
        player.stop();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //once the song is done, we switch to the next song
        //musicIndex = (musicIndex + 1) % MUSICNAME.length;
        //clean out the music player
        //player.release();
        //player = null;
        musicStatus = 3;
        player.seekTo(0);
        //start over -- infinite loop
        //playMusic(song);


    }

    public MusicPlayer(MusicService musicService) {
        this.musicService = musicService;
    }



    public int geCurrentT() {
        return player.getCurrentPosition();
    }
}
