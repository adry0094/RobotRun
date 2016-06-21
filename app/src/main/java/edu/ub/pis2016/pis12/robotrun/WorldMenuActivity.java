package edu.ub.pis2016.pis12.robotrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Clase worldActivity, que muestra el menu de mundos
 */
public class WorldMenuActivity extends AppCompatActivity {
    //Variables
    private ViewFlipper viewFlipper;
    protected GameApplication ctrl;
    private final int earth=0;
    private final int moon=1;
    private boolean stopAct=true;

    private boolean sound;
    /**
     * Metodo que se llama al crear una WorldMenuActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopAct=true;
        TextView numStars;
        setContentView(R.layout.activity_world_menu);
        RelativeLayout layoutFondo = (RelativeLayout)findViewById(R.id.layoutWorldFondo);
        layoutFondo.setBackgroundResource(R.drawable.fondomenu);
        //Transicion efecto
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        viewFlipper.setInAnimation(this, R.anim.fade_in);
        viewFlipper.setOutAnimation(this, R.anim.fade_out);

        ctrl =(GameApplication)getApplication();
        sound=ctrl.getSound();
        Player player = ctrl.getPlayer();

        ImageButton right = (ImageButton) findViewById(R.id.imageButton_der);
        ImageButton left = (ImageButton) findViewById(R.id.imageButton_izq);
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton_WorldMenu);

        /**
         * boton para regresar atrás
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct=false;
                //Intent intent = new Intent(WorldMenuActivity.this,MainActivity.class);
                finish();
                //startActivity(intent);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });

        viewFlipper.setOnTouchListener(new ListenerTouchViewFlipper());
        ImageButton earth = (ImageButton) findViewById(R.id.tierra_WorldMenu);
        earth.setBackgroundResource(R.drawable.earth_world);

        //estrellas en tierra
        numStars=(TextView) findViewById(R.id.textPorcentage_Tierra);
        numStars.setText(""+(player.getTotalWorldStars(this.earth)*100)/9+"% "+getString(R.string.completed_WorldMenu));

        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct=false;
                Intent tierraLevels = new Intent(WorldMenuActivity.this, LevelMenuActivity.class);
                ctrl.setWorldSelect("earth");
                ctrl.setCurrentWorld(WorldMenuActivity.this.earth);
                finish();
                startActivity(tierraLevels);
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            }
        });
        ImageButton moon = (ImageButton) findViewById(R.id.luna_WorldMenu);

        //Luna estrellas
        numStars=(TextView) findViewById(R.id.textPorcentage_Luna);
        numStars.setText(""+(player.getTotalWorldStars(this.moon)*100)/9+"% "+getString(R.string.completed_WorldMenu));

        //modificado la forma en la que se comprueba si el nivel esta disponible
        if (!player.checkWorld(this.moon)) {
            moon.setBackgroundResource(R.drawable.moon_worldlock);
            moon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {message("5");}
            });
        }
        else {

            moon.setBackgroundResource(R.drawable.moon_world);
            moon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopAct=false;
                    Intent tierraLevels = new Intent(WorldMenuActivity.this, LevelMenuActivity.class);
                    ctrl.setWorldSelect("moon");
                    ctrl.setCurrentWorld(WorldMenuActivity.this.moon);
                    finish();
                    startActivity(tierraLevels);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                }
            });
        }
    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        stopAct=false;
        //Intent intent = new Intent(WorldMenuActivity.this,MainActivity.class);
        finish();
        //startActivity(intent);

    }
    @Override
    protected void onStop(){
        super.onStop();
        if(stopAct){
            Log.i("SERVICE", "debug: Service stop started");
            ctrl.stopMusic();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("SERVICE", "debug: Service started");
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        this.stopAct = true;
        if(sound){
            ctrl.resumeMusic();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    /**
     * Mensaje cuando seleccionas un mundo bloqueado, "a" es el numero de estrellas necesarias
     */
    private void message(String a) {
        Toast.makeText(getApplicationContext(), getString(R.string.completedLevels)+a+
                getString(R.string.starsApp),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Gestión del swipe de la selección de mundos
     */
    private class ListenerTouchViewFlipper implements View.OnTouchListener{
        private float init_x;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
                    init_x=event.getX();
                    return true;

                case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
                    float distance =init_x-event.getX();

                    if(distance>0) {
                        viewFlipper.showNext();
                    }
                    if(distance<0)  {
                        viewFlipper.showPrevious();
                    }
                default:
                    break;
            }
            return false;
        }
    }
}


