package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;


public class GameActivity extends Activity {

    //Variables
    private GameApplication ctrl;
    private LayoutInflater layoutInflater;
    private View popupView;
    private PopupWindow popupWindow;

    private boolean brei=false;

    private ImageButton continuar = null;//continuar al juego
    private ImageButton pauseButton = null;//boton pausa
    private ImageButton returnMenu = null;//volver al menu cuando estamos en pausa
    private ImageButton pasarMenu = null; //minigameactivitytest

    private boolean sound;
    /**
     * Metodo que se llama al crear la activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctrl = (GameApplication)getApplication();
        ctrl.stopMusic();
        brei=false;
        /**GameaActivity ha de tener las variables que usamos como globales para asi gestionarlas aqui, en view, thread, etc*/
        sound=ctrl.getSound();
        if(sound){
            ctrl.playMusicGame();
        }

       // Intent musicGame=new Intent();
        //musicGame.setClass(this,BackgroundSoundServiceGame.class);
        //startService(musicGame);

        setContentView(R.layout.widgets_ingame);
        FrameLayout game = (FrameLayout) findViewById(R.id.gameFrame);//new FrameLayout(this);

        final GameView gameView = new GameView (this,ctrl);
        game.addView(gameView);

        //reiniciamos la variable
        ctrl.setGameOver(false);
        ctrl.setPause(false);
        ctrl.setWin(false);

        // PAUSE
        pauseButton = (ImageButton) findViewById(R.id.pause_inGameButton);
        pauseButton.setEnabled(true);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ctrl.setPause(true);
                pauseButton.setEnabled(false);
                layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.pause_popup, null);
                popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);

                continuar = (ImageButton) popupView.findViewById(R.id.button_continuar);
                continuar.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        pauseButton.setEnabled(true);
                        ctrl.setPause(false);
                    }
                });
                // boton para volver al menu principal
                returnMenu = (ImageButton) popupView.findViewById(R.id.button_returnMenu);
                returnMenu.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent volver = new Intent(GameActivity.this,LevelMenuActivity.class);
                        ctrl.stopGameMusic();
                        finish();
                        startActivity(volver);
                    }
                });

                pasarMenu = (ImageButton) popupView.findViewById(R.id.button_restart);
                pasarMenu.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent reiniciar = getIntent();
                        brei=true;
                        finish();
                        gameView.interrupt();
                        //ctrl.setPause(false);
                        startActivity(reiniciar);

                    }
                });

                //popupWindow.showAsDropDown(pauseButton, -100, -50);
                popupWindow.showAtLocation(continuar, Gravity.CENTER, 0, 0);

            }});

    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();

    }
    @Override
    protected void onStop(){
        super.onStop();
        if(!brei){
            Log.i("SERVICE", "debug: Service stop started");
            ctrl.stopGameMusic();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("SERVICE", "debug: Service started");
        if(!brei){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopGameMusic();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        this.brei = false;
        if(sound){
            ctrl.playMusicGame();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(!brei){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopGameMusic();
        }

    }
    /**
     * Inicia una GameOverActivity
     */
    public void showGameOver(){
        Intent intent = new Intent(GameActivity.this,GameOverActivity.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

    }

    /**
     * Inicia una WinActivity pasandole los coins
     * @param coins
     */
    public void showWin(int coins){
        Intent intent = new Intent(GameActivity.this,WinActivity.class);
        intent.putExtra("coins", coins);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

    }
}