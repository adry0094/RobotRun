package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase que hereda de Activity y muestra la pantalla de levels
 */
public class LevelMenuActivity extends Activity {
    protected GameApplication ctrl;
    private TextView numStars;
    private final int earth=0;
    private final int moon=1;
    private boolean stopAct = true;
    private boolean sound;
    /**
     * Metodo que crea cuando se hace un objeto LevelMenuActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        stopAct=true;
        ctrl = (GameApplication)getApplication();
        Player player = ctrl.getPlayer();
        sound=ctrl.getSound();
        if(sound) {
            ctrl.resumeMusic();
        }
        //Busquem elements a pantalla:
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton_LevelActivity);

        RelativeLayout levelBackground = (RelativeLayout)findViewById(R.id.fonsLevel);

        ImageButton level1 = (ImageButton)findViewById(R.id.level1Button_LevelActivity);
        ImageButton level2 = (ImageButton)findViewById(R.id.level2Button_LevelActivity);
        ImageButton level3 = (ImageButton)findViewById(R.id.level3Button_LevelActivity);


        //si retorna un 2 hay dos niveles disponibles, si 3 hay 3 niveles disponibles
        int levelsAvailable = player.getAvailableLevels(ctrl.getCurrentWorld());
        setLevel(ctrl.getWorldSelect(), levelsAvailable, levelBackground, level1, level2, level3, player);

        //ClickListeners el primer nivel siempre se puede acceder
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.setCurrentLevel(0);
                stopAct=false;
                Intent gameplay = new Intent(LevelMenuActivity.this, GameActivity.class);
                finish();
                //gameplay.putExtra("worldType", worldSelect);
                startActivity(gameplay);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });
        if (levelsAvailable==1) {
            level2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {message(getString(R.string.firstApp));}
            });
            level3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {message(getString(R.string.secondApp));}
            });
        }

        else if (levelsAvailable==2) {
            level2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctrl.setCurrentLevel(1);
                    stopAct = false;
                    Intent gameplay = new Intent(LevelMenuActivity.this, GameActivity.class);
                    //gameplay.putExtra("worldType", worldSelect);
                    finish();
                    startActivity(gameplay);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
            });
            level3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {message(getString(R.string.secondApp));}
            });
        }
        else if (levelsAvailable==3) {
            level2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctrl.setCurrentLevel(1);
                    stopAct = false;
                    Intent gameplay = new Intent(LevelMenuActivity.this, GameActivity.class);
                    //gameplay.putExtra("worldType", worldSelect);
                    finish();
                    startActivity(gameplay);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
            });
            level3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctrl.setCurrentLevel(2);
                    stopAct = false;
                    Intent gameplay = new Intent(LevelMenuActivity.this, GameActivity.class);
                    //gameplay.putExtra("worldType", worldSelect);
                    finish();
                    startActivity(gameplay);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
            });
        }

        //ClickListeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAct = false;
                Intent intent = new Intent(LevelMenuActivity.this,WorldMenuActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        stopAct = false;
        Intent intent = new Intent(LevelMenuActivity.this,WorldMenuActivity.class);
        finish();
        startActivity(intent);

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
     * Mensaje cuando seleccionas un nivel bloqueado, "a" es el nivel previo necesario para acceder
     * @param a
     */
    private void message(String a) {
        Toast.makeText(getApplicationContext(),getString(R.string.toast_LevelMenu)+a
                        +getString(R.string.levelApp),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Muestra una imagen u otra dependiendo de el mundo seleccionado y los niveles disponibles
     * @param worldSelect
     * @param levelsAvailable
     * @param levelBackground
     * @param level1
     * @param level2
     * @param level3
     * @param player
     */
    private void setLevel(String  worldSelect, int levelsAvailable, RelativeLayout levelBackground,
                          ImageButton level1,ImageButton level2,ImageButton level3,Player player) {
        //Cogemos las estrellas
        ImageView star1l1 = (ImageView)findViewById(R.id.imageStar1l1);
        ImageView star2l1 = (ImageView)findViewById(R.id.imageStar2l1);
        ImageView star3l1 = (ImageView)findViewById(R.id.imageStar3l1);

        ImageView star1l2 = (ImageView)findViewById(R.id.imageStar1l2);
        ImageView star2l2 = (ImageView)findViewById(R.id.imageStar2l2);
        ImageView star3l2 = (ImageView)findViewById(R.id.imageStar3l2);

        ImageView star1l3 = (ImageView)findViewById(R.id.imageStar1l3);
        ImageView star2l3 = (ImageView)findViewById(R.id.imageStar2l3);
        ImageView star3l3 = (ImageView)findViewById(R.id.imageStar3l3);

        switch (worldSelect) {
            case "earth":
                levelBackground.setBackgroundResource(R.drawable.earthlevelbackground);

                fillStars(player.getWorldStarsLevel(earth, 0), star1l1, star2l1, star3l1);
                //numStars=(TextView) findViewById(R.id.level1StarText_LevelActivity);
                //numStars.setText("" + player.getWorldStarsLevel(0, 0) + "/3");
                level1.setBackgroundResource(R.drawable.levelearth1mb);

                fillStars(player.getWorldStarsLevel(earth, 1), star1l2, star2l2, star3l2);
                level2.setImageResource(R.drawable.levelearth2mblock);


                fillStars(player.getWorldStarsLevel(earth, 2), star1l3, star2l3, star3l3);
                level3.setImageResource(R.drawable.levelearth3mblock);

                if (levelsAvailable == 2) {
                    level2.setImageResource(R.drawable.levelearth2mb);

                } else if (levelsAvailable == 3) {
                    level2.setImageResource(R.drawable.levelearth2mb);
                    level3.setImageResource(R.drawable.levelearth3mb);
                }
                break;

            case "moon":
                levelBackground.setBackgroundResource(R.drawable.moonlevelbackground);

                fillStars(player.getWorldStarsLevel(moon, 0), star1l1, star2l1, star3l1);

                //numStars=(TextView) findViewById(R.id.level1StarText_LevelActivity);
                //numStars.setText("" + player.getWorldStarsLevel(1,0)+"/3");
                level1.setBackgroundResource(R.drawable.levelmoon1mb);

                fillStars(player.getWorldStarsLevel(moon, 1), star1l2, star2l2, star3l2);
                level2.setImageResource(R.drawable.levelmoon2mblock);

                fillStars(player.getWorldStarsLevel(moon, 2), star1l3, star2l3, star3l3);
                level3.setImageResource(R.drawable.levelmoon3mblock);

                if (levelsAvailable == 2) {
                    level2.setImageResource(R.drawable.levelmoon2mb);
                } else if (levelsAvailable == 3) {
                    level2.setImageResource(R.drawable.levelmoon2mb);
                    level3.setImageResource(R.drawable.levelmoon3mb);
                }
        }
    }
    /**
     * Metodo que rellena las estrellas de la win activity
     */
    private void fillStars(int numStar, ImageView star1, ImageView star2, ImageView star3){

        //Rellenamos estrellas
        if (numStar == 0) {
            star1.setImageResource(R.drawable.staremptys);
            star2.setImageResource(R.drawable.staremptys);
            star3.setImageResource(R.drawable.staremptys);
        }
        //Caso 1 estrella
        else if (numStar == 1) {
            star1.setImageResource(R.drawable.starfulls);
            star2.setImageResource(R.drawable.staremptys);
            star3.setImageResource(R.drawable.staremptys);
        }
        //Caso 2 estrella
        else if (numStar == 2) {
            star1.setImageResource(R.drawable.starfulls);
            star2.setImageResource(R.drawable.starfulls);
            star3.setImageResource(R.drawable.staremptys);
        }
        //Caso 3 estrella
        else if (numStar == 3) {
            star1.setImageResource(R.drawable.starfulls);
            star2.setImageResource(R.drawable.starfulls);
            star3.setImageResource(R.drawable.starfulls);
        }
    }
}

