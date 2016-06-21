package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Clase WinActivity que muestra la pantalla de win
 */
public class WinActivity extends Activity {

    //Variables
    private int coinsCollected;
    private GameApplication ctrl;
    private int coinsPerLevel =0;
    private int starsNum =0;


    /**
     * Metodo que se llama al crearse la activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        ctrl = (GameApplication)getApplication();

        if(ctrl.getCurrentWorld() == 0){
            RelativeLayout layoutFondo = (RelativeLayout)findViewById(R.id.layoutWin);
            layoutFondo.setBackgroundResource(R.drawable.ingame_background);
        }
        else if(ctrl.getCurrentWorld() == 1){
            RelativeLayout layoutFondo = (RelativeLayout)findViewById(R.id.layoutWin);
            layoutFondo.setBackgroundResource(R.drawable.ingamemoon_background);
        }

        //Leemos los datos del intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                coinsCollected= 0;
            } else {
                coinsCollected= extras.getInt("coins");
                ctrl.setCoinsCollected(coinsCollected);
            }
        } else {
            coinsCollected= (int) savedInstanceState.getSerializable("coins");
        }

        //coinsCollected = 99999999;
        fillStars();
        fillCoinsText();

        ctrl.addPlayerCoins(coinsCollected);

        /**
         * Guardamos los datos actuales del nivel
         */
        //ctrl.saveData();

        /**
         * Boton para reiniciar el nivel
         */
        ImageButton restart = (ImageButton) findViewById(R.id.button_WINReiniciar);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reiniciar = new Intent(WinActivity.this,GameActivity.class);
                finish();
                //guardamos antes de volver a reiniciar elnivel, para las monedas
                ctrl.savePreferences();
                startActivity(reiniciar);

            }
        });

        /**
         * Boton para regresar atrás
         */
        ImageButton back = (ImageButton) findViewById(R.id.button_WINContinuar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(WinActivity.this,LevelMenuActivity.class);
                finish();
                //guardamos al continuar
                ctrl.savePreferences();
                startActivity(volver);

            }
        });
    }

    /**
     * Devuelve si se ha pulsado la tecla de hacia atras fisico
     * @param keyCode
     * @param event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Si se ha pulsado atras muestra el levelmenu
     */
    @Override
    public void onBackPressed() {
        //Intent volver = new Intent(WinActivity.this,LevelMenuActivity.class);
        finish();
        //startActivity(volver);
    }


    /**
     * Metodo para llenar el text de coins
     */
    private void fillCoinsText(){
        TextView textCoins = (TextView) findViewById(R.id.textViewCoinsWin);
        textCoins.setText("+" + Integer.toString(coinsCollected));
    }
    /**
     * Metodo que rellena las estrellas de la win activity
     */
    private void fillStars(){
        //Cogemos las estrellas
        ImageView star1 = (ImageView)findViewById(R.id.imageStar1);
        ImageView star2 = (ImageView)findViewById(R.id.imageStar2);
        ImageView star3 = (ImageView)findViewById(R.id.imageStar3);
        //Rellenamos estrellas
        coinsPerLevel = ctrl.getCoinsForLevel()/3;
        //para evitar la division por 0 y por numero negativo
        if(coinsCollected <= 0) coinsCollected = 1;

        starsNum = compruebaEstrellas();
        //dependiendo del numero de estrellas obtenidas, pinta las estrellas correspondientes
        setStartsImage(starsNum,star1,star2,star3);
        ctrl.addPlayerStars(starsNum);
    }

    /**
     * Metodo para colocar las estrellas.
     * @param starsNum
     * @param star1
     * @param star2
     * @param star3
     */
    private void setStartsImage(float starsNum, ImageView star1, ImageView star2, ImageView star3) {
        if (starsNum <= 1) {
            star1.setImageResource(R.drawable.starfullb);
            star2.setImageResource(R.drawable.staremptyb);
            star3.setImageResource(R.drawable.staremptyb);
        }
        else if (starsNum == 2) {
            star1.setImageResource(R.drawable.starfullb);
            star2.setImageResource(R.drawable.starfullb);
            star3.setImageResource(R.drawable.staremptyb);
        }
        else if (starsNum >= 3) {
            star1.setImageResource(R.drawable.starfullb);
            star2.setImageResource(R.drawable.starfullb);
            star3.setImageResource(R.drawable.starfullb);
        }
    }
    /**
     * Comprobamos el numero de estrellas
     */
    private int compruebaEstrellas(){
        int estrella = 1;
        if(coinsCollected>=0 && coinsCollected<=20){
            estrella = 1;
        }
        else if(coinsCollected>20 && coinsCollected<=40){
            estrella = 2;
        }
        else if(coinsCollected>40){
            estrella = 3;
        }
        return estrella;
    }
}
