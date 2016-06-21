package edu.ub.pis2016.pis12.robotrun;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by UsuariPC on 28/05/2016.
 */
public class BackgroundSoundServiceGame extends Service {

    final static String TAG = "SoundServiceGame";
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "debug: Service started");
        mediaPlayer = MediaPlayer.create(this, R.raw.game_song);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            Log.i(TAG,"debug: Service started");
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
    public void pause(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public void resume(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

}
