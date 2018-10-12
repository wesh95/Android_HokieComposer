package com.example.wesh9.hokiecomposer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wesh9 on 10/23/2017.
 */

public class MusicCompletionReceiver extends BroadcastReceiver{
    MainActivity mainActivity;
    // constructor takes a reference to main activity so we can communicate with it
    public MusicCompletionReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // when we receive the udpate...
    @Override
    public void onReceive(Context context, Intent intent) {

        //...extract the song name
        String musicName = intent.getStringExtra(MusicService.MUSICNAME);
        //...pass it to main activity
        //mainActivity.updateName(musicName);
    }
}
