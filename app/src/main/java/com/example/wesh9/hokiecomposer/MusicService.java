package com.example.wesh9.hokiecomposer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MusicService extends Service {


    private static final String TAG = "tutorial_4";


    // we delegate all of the playback related aspects in a separate class
    MusicPlayer musicPlayer;
    MusicPlayer ov1;
    MusicPlayer ov2;
    MusicPlayer ov3;

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";


    // We send song title using broadcast
    public void onUpdateMusicName(String musicName) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(MUSICNAME, musicName);

        // this broadcast will eventually deliver the updated label to MainActivity
        sendBroadcast(intent);
    }


    // once the service is built...
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate() inside service");
        //we initialize the music player
        musicPlayer = new MusicPlayer(this);
        ov1 = new MusicPlayer(this);
        ov2 = new MusicPlayer(this);
        ov3 = new MusicPlayer(this);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy() inside service");
        super.onDestroy();
    }


    //service learns that it is unbound.
    // We pause it with a 3 second delay
    @Override
    public boolean onUnbind(Intent intent) {


        // we use Handler as a way to run a delayed operation
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //we pause playback
                pauseMusic();
                //stop the service...
                stopSelf();

            }

            //after 3000ms (3 seconds)
        }, 10000);
        return super.onUnbind(intent);
    }

    public void startMusic(String title) {
        musicPlayer.playMusic(title);
        ov1.restartMusic(1);
        ov2.restartMusic(1);
        ov3.restartMusic(1);
        ov1.resumeMusic(ov1Stat());
        ov2.resumeMusic(ov2Stat());
        ov3.resumeMusic(ov3Stat());
    }
    public void startov1(String title){
        ov1.playMusic(title);
    }

    public void startov2(String title){
        ov2.playMusic(title);
    }
    public void startov3(String title){
        ov3.playMusic(title);
    }

    public void pauseMusic() {

        musicPlayer.pauseMusic();
        ov1.pauseMusic();
        ov2.pauseMusic();
        ov3.pauseMusic();
    }

    public void resumeMusic() {
        musicPlayer.resumeMusic(getBackgroundStatus());
        ov1.resumeMusic(ov1Stat());
        ov2.resumeMusic(ov2Stat());
        ov3.resumeMusic(ov3Stat());
    }

    public void stopMus(){
        ov1.restartMusic(1);
        ov2.restartMusic(1);
        ov3.restartMusic(1);
        musicPlayer.stopMusic();
    }

    public void restartMus(){
        musicPlayer.restartMusic(0);
        ov1.restartMusic(1);
        ov2.restartMusic(1);
        ov3.restartMusic(1);
    }

    public int getDuration(){
        return musicPlayer.getDur();
    }

    public int getCurrentTime(){
        return musicPlayer.geCurrentT();
    }

    public void playNext() {
        //musicPlayer.playNext();
    }
    public void playPrev() {
       // musicPlayer.playPrevious();
    }

    public int getMusicStatus() {
        if((ov1.getMusicStatus() == 3) && (ov2.getMusicStatus() ==3) && (ov3.getMusicStatus() == 3)){
            return 3;
        }
        else{
            return 1;
        }
    }
    public int getBackgroundStatus(){
        return musicPlayer.getMusicStatus();
    }
    public boolean getStatOV1(){
        return ov1.getMusicStatus() == 3;
    }
    public boolean getStatOV2(){
        return ov2.getMusicStatus() == 3;
    }
    public boolean getStatOV3(){
        return ov3.getMusicStatus() == 3;
    }

    public int ov1Stat(){
        return ov1.getMusicStatus();
    }
    public int ov2Stat(){
        return ov2.getMusicStatus();
    }
    public int ov3Stat(){
        return ov3.getMusicStatus();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG,"onBind() inside service");
        return iBinder;
    }

    // private reference to the private class that return reference to the service
    private final IBinder iBinder = new MyBinder();


    // need to create a small private class that can return reference to the service
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
