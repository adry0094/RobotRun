package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

/**
 * MenuPrincipal de la aplicacion, hereda de activity
 */
public class MainActivity extends Activity {
    //Variables
    public GameApplication ctrl;
    public static Context contextOfApplication;
    public Intent music;
    public boolean sound;
    public boolean stopAct = true;
    /**
     * Metodo que se llama al crear la activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();

        setDisplayGameApplication();
        this.stopAct=true;

        if(ctrl.getisNew()) {changeLang(); }//if its the first start

        ctrl.loadSoundPreferences();
        this.sound=ctrl.getSound();

        Intent music = new Intent();
        music.setClass(this,BackgroundSoundService.class);

        Intent musicgame = new Intent();
        musicgame.setClass(this,BackgroundSoundServiceGame.class);
        ctrl.setGameMusic(musicgame);
        stopService(musicgame);
        this.music = music;
        ctrl.setMusicBackgroundIntent(music);
        if(sound) {
            startService(music);
        }else if(!sound){
            stopService(music);
        }
        stopService(musicgame);
        setContentView(R.layout.activity_main);
        //Log.i(TAG, "debug: Menu Iniciado");


        //play, abre los mundos
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct = false;
                Intent mundos = new Intent(MainActivity.this,WorldMenuActivity.class);
                //finish();
                startActivity(mundos);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

            }
        });
        //tienda
        ImageButton shopButton = (ImageButton) findViewById(R.id.shopButton);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct = false;
                Intent shop = new Intent(MainActivity.this,ShopMenuActivity.class);
                //finish();
                startActivity(shop);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            }
        });


        //Ajustes
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct = false;
                Intent settings = new Intent(MainActivity.this,SettingMenuActivity.class);
                //finish();
                startActivity(settings);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

            }
        });
    }

    private void changeLang() {
        ctrl.setIsNew(false);
        int language = ctrl.getLanguage();
        String lang ="";
        if (language == 0){lang="en";}
        else if(language == 1) {lang="es";}
        else if (language ==2) {lang="ca";}
        System.out.println("D/P: "+lang+""+language);
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(stopAct){
            Log.i("SERVICE", "debug: Service stop started");
            stopService(music);
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //this.onDestroy();

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("SERVICE", "debug: Service started");
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            stopService(music);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("OnCreate/OnResume", "on resume Called!");
        ctrl = (GameApplication)getApplication();
        this.sound=ctrl.getSound();
        this.stopAct = true;
        if(sound){
            startService(music);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(music);
    }
    /**
     * Tamaño de la pantalla
     */
    private void setDisplayGameApplication() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        ctrl = (GameApplication)getApplication();
        ctrl.setScreenSize(width, height);
    }

    /**
     * Devuelve el context de la aplicacion
     * @return Context
     */
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }


}
