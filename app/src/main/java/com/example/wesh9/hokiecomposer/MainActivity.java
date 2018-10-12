package com.example.wesh9.hokiecomposer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Editor_Frag.OnFragmentInteractionListener,
        Playing_Frag.OnFragmentInteractionListener,
        ServiceConnection
{

    Editor_Frag editor_frag;
    Playing_Frag playing_frag;
    LinearLayout fragment_container;
    int frag = 1;
    boolean isInitialized = false;
    boolean isBound = false;
    MusicService musicService;
    //MusicService serviceOV1;
    MusicCompletionReceiver musicCompletionReceiver;
    //MusicCompletionReceiver ov1Receiver;
    Intent startMusicServiceIntent;
    Intent startOv1Intent;

    TimeAsyncTask timeAsyncTask;

    String currSong;

    int currTime;
    double compOV1 = 0;
    double compOV2 = 0;
    double compOV3 = 0;

    private final static String TAG = "hw4";

    int backSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_container = (LinearLayout)findViewById(R.id.fragment_container);
        timeAsyncTask = new TimeAsyncTask();
        editor_frag = new Editor_Frag();
        playing_frag = new Playing_Frag();

        editor_frag.setArguments(getIntent().getExtras());
        playing_frag.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, editor_frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, playing_frag).commit();


        //restoring info for the boolean and the 'song' label
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            //music.setText(savedInstanceState.getString(MUSIC_PLAYING));
        }


        // preparing the intent object that will launch the service
        startMusicServiceIntent = new Intent(this, MusicService.class);
        startOv1Intent = new Intent(this, MusicService.class);

        // if not started we go ahead and start it
        if (!isInitialized) {
            startService(startMusicServiceIntent);
            startService(startOv1Intent);
            isInitialized = true;
        }

        //also registering the broadcast receiver
        musicCompletionReceiver = new MusicCompletionReceiver(this);
        //ov1Receiver = new MusicCompletionReceiver(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        // unbinding from service
        // the service will have onUnbind() called after this
        // inside that method we will handle the logic of unbinding
        if (isBound) {
            unbindService(this);
            isBound = false;
        }
        //remove the broadcast receiver
        unregisterReceiver(musicCompletionReceiver);
        //unregisterReceiver(ov1Receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //is service is initialized and not boud we bind to it
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, this, Context.BIND_AUTO_CREATE);
            bindService(startOv1Intent, this, Context.BIND_AUTO_CREATE);
        }

        // registering the broadcast receiver
        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
        //registerReceiver(ov1Receiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    //keys for retrieving info on restore
    public static final String INITIALIZE_STATUS = "is_initialized";
    public static final String MUSIC_PLAYING = "is_music_playing";

    // saving state of the mplayer and service
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        //outState.putString(MUSIC_PLAYING, music.getText().toString());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    boolean canc = false;

    public void onClickPlay(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        playing_frag.setBackString(editor_frag.getBackground());


        if(frag == 1){// if its editor frag


            if(backSet == 0) {
                currSong = editor_frag.getBackground();
                musicService.startMusic(editor_frag.getBackground());
                timeAsyncTask.execute();
                editor_frag.setCurrSong(editor_frag.getBackground());
                editor_frag.setBackTime(musicService.getDuration());


                playing_frag.setBackgroundText(editor_frag.getBackground());
                backSet = 1;
                playing_frag.setImage(editor_frag.getBackground());
            }
            if(!editor_frag.getCurrSong().equals(editor_frag.getBackground())){
                musicService.restartMus();

                currSong = editor_frag.getBackground();
                editor_frag.setCurrSong(editor_frag.getBackground());
                Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();

                musicService.stopMus();
                timeAsyncTask.cancel(true);
                musicService.startMusic(editor_frag.getBackground());

                timeAsyncTask = new TimeAsyncTask();
                timeAsyncTask.execute();

                editor_frag.setBackTime(musicService.getDuration());
                playing_frag.setImage(editor_frag.getBackground());

            }
            else{
                timeAsyncTask.cancel(true);

                timeAsyncTask = new TimeAsyncTask();
                ov1 = false;
                ov2 = false;
                ov3 = false;
                status = musicService.getMusicStatus();
                musicService.restartMus();
                timeAsyncTask.execute();
                playing_frag.setImage(editor_frag.getBackground());

                playing_frag.setPP("pause");
            }
            playing_frag.setBackString(editor_frag.getBackground());
            //playing_frag.setBackgroundText();
            transaction.replace(R.id.fragment_container, playing_frag);
            transaction.addToBackStack(null);
            transaction.commit();
            frag = 0;
        }
        else{//if playing frag
            transaction.replace(R.id.fragment_container, editor_frag);//
            transaction.addToBackStack(null);
            transaction.commit();
            frag = 1;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d(TAG, "onServiceConnected()");

        // the biner object gets us an object that we use to extract a reference to service
        MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
        //MusicService.MyBinder binder2 = (MusicService.MyBinder) iBinder;

        // extracting the service object
        musicService = binder.getService();
        //1 = binder2.getService();

        // it is bound so we set the boolean
        isBound = true;


    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected()");
        musicService = null;
        //serviceOV1 = null;
        isBound = false;
    }

    public void pPasuse(View view) {
        if(musicService.getBackgroundStatus() == 1){
            musicService.pauseMusic();
            playing_frag.setPP("play");
        }
        else if(musicService.getBackgroundStatus() == 2){
            musicService.resumeMusic();
            playing_frag.setPP("pause");
        }

    }

    boolean ov1 = false;
    boolean ov2 = false;
    boolean ov3 = false;
    int status;


    public void onRestart(View view) {
        timeAsyncTask.cancel(true);

        timeAsyncTask = new TimeAsyncTask();
        ov1 = false;
        ov2 = false;
        ov3 = false;
        status = musicService.getMusicStatus();
        musicService.restartMus();
        timeAsyncTask.execute();
        playing_frag.setImage(editor_frag.getBackground());

        playing_frag.setPP("pause");

        //musicService.startMusic(currSong);


    }


    private class TimeAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean done1 = false;
        boolean done2 = false;
        boolean done3 = false;

        @Override
        protected Void doInBackground(Void... params) {



            while (!isCancelled()) {

                try {
                    if(musicService.getMusicStatus() != 3) {
                        this.publishProgress(musicService.getCurrentTime());
                        Thread.sleep(500);
                    }
                    if(musicService.getMusicStatus() == 3){
                        ov1 = false;
                        ov2 = false;
                        ov3 = false;
                        done1 = false;
                        done2 = false;
                        done3 = false;
                        cancel(true);


                    }



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if(isCancelled()){
                    break;
                }
            }
            return null;

        }

        boolean ov1done = true;
        boolean ov2done = true;
        boolean ov3done = true;



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            currTime = values[0];

            int [] seeks =editor_frag.getSeekPositions();
            String[] songs = editor_frag.getOVsongs();
            compOV1 = musicService.getDuration()*((double)seeks[0]/100.0);
            compOV2 = musicService.getDuration()*((double)seeks[1]/100.0);
            compOV3 = musicService.getDuration()*((double)seeks[2]/100.0);

            if(currTime >= compOV1 && !ov1){
                musicService.startov1(songs[0]);
                playing_frag.setImage(songs[0]);

                ov1 = true;
            }
            if(currTime >= compOV2 && !ov2){
                musicService.startov2(songs[1]);
                playing_frag.setImage(songs[1]);
                ov2 = true;
            }
            if(currTime >= compOV3 && !ov3){
                musicService.startov3(songs[2]);
                playing_frag.setImage(songs[2]);
                ov3 = true;
            }

            if(playing_frag.getCurrPic().equals(songs[0]) && musicService.getStatOV1() && !done1){
                playing_frag.setImage(editor_frag.getBackground());
                done1 = true;
            }
            if(playing_frag.getCurrPic().equals(songs[1]) && musicService.getStatOV2()&& !done2 ){
                playing_frag.setImage(editor_frag.getBackground());
                done2 = true;
            }
            if(playing_frag.getCurrPic().equals(songs[2]) && musicService.getStatOV3() && !done3){
                playing_frag.setImage(editor_frag.getBackground());
                done3 = true;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

}
