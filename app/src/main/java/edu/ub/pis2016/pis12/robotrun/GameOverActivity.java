package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Clase que hereda de Activity y muestra la pantalla de game over
 */
public class GameOverActivity extends Activity {
    /**
     * Metodo que crea cuando se hace un objeto GameOverActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        ImageButton reiniciar = (ImageButton) findViewById(R.id.button_GOReiniciar);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reiniciar = new Intent(GameOverActivity.this,GameActivity.class);
                finish();
                startActivity(reiniciar);

            }
        });
        ImageButton atras = (ImageButton) findViewById(R.id.button_GOReturn);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(GameOverActivity.this,LevelMenuActivity.class);
                finish();
                startActivity(volver);

            }
        });


    }

    /**
     * Devuelve si se ha pulsado el boton de atras fisico
     * @param keyCode
     * @param event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Metodo que inicia una activity si se ha pulsado atras
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent volver = new Intent(GameOverActivity.this,LevelMenuActivity.class);
        finish();
        startActivity(volver);
    }
}
