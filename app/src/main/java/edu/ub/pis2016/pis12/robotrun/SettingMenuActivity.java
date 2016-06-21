package edu.ub.pis2016.pis12.robotrun;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

/**
 * Clase de los settings
 */
public class SettingMenuActivity extends AppCompatActivity {

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    private CheckBox check;
    private GameApplication ctrl;
    private Spinner spinner;
    public boolean sound=true;
    private boolean stopAct=true;
    /**
     * Metodo que se llama al crear la activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
        stopAct=true;
        ctrl = (GameApplication)getApplication();
        this.sound=ctrl.getSound();

        CheckBox check = (CheckBox)findViewById(R.id.checkMute);
        if(sound){
            check.setChecked(true);
        }else if(!sound){
            check.setChecked(false);
        }

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton_SettingsActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ctrl.savePreferences();
                stopAct=false;
                ctrl.setSound(sound);
                ctrl.saveSoundPreferences(sound);
                finish();
            }
        });

        ImageButton informacion = (ImageButton) findViewById(R.id.informacion_SettingMenu);
        informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.developed_SettingMenu) +
                                getString(R.string.names_SettingMenu),
                        Toast.LENGTH_LONG).show();
            }
        });



        //SPINNER IDIOMA
        spinner = (Spinner) findViewById(R.id.spinnerIdioma);
        spinner.setSelection(ctrl.getLanguage());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg1.setSelected(true);
                String idioma = spinner.getSelectedItem().toString();
                int posLang = spinner.getSelectedItemPosition();
                if (posLang!=ctrl.getLanguage() || ctrl.getisNew()) {
                    ctrl.setLanguage(posLang);
                    updateLanguage(getApplicationContext(), idioma);
                    ctrl.saveLangPreferences();
                    String items = spinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


    }


    private void updateLanguage(Context context, String idioma) {
        if (!"".equals(idioma)) {
            if (getString(R.string.es_SettingMenu).equals(idioma)) {
                idioma = "es";
            } else if (getString(R.string.en_SettingMenu).equals(idioma)) {
                idioma = "en";
            }
            else if (getString(R.string.cat_SettingMenu).equals(idioma)) {
                idioma = "ca";
            }
            setLocale(idioma,context);
        }
    }

    private void setLocale(String lang,Context context) {
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, null);
        Intent refresh = new Intent(this, SettingMenuActivity.class);
        startActivity(refresh);
        finish();
    }


    public void onCheckBoxClicked(View view){
        boolean checked= ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.checkMute:
                if (checked){
                    this.sound=true;
                    ctrl.saveSoundPreferences(sound);
                    ctrl.resumeMusic();
                }else if(!checked){
                    this.sound=false;
                    ctrl.saveSoundPreferences(sound);
                    ctrl.stopMusic();
                }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        ctrl.saveSoundPreferences(this.sound);
        if(stopAct){
            Log.i("SERVICE", "debug: Service stop started");
            ctrl.stopMusic();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        ctrl.saveSoundPreferences(this.sound);
        Log.i("SERVICE", "debug: Service started");
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        stopAct=false;
        this.finish();


    }
    @Override
    protected void onResume(){
        super.onResume();
        this.stopAct = true;
        ctrl = (GameApplication)getApplication();
        this.sound=ctrl.getSound();
        if(sound){
            ctrl.resumeMusic();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ctrl.saveSoundPreferences(this.sound);
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    /**
     * Metodo que gestiona el control de volumen ( seekbar )
     */

    /*


    private void initControls(){
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
}
