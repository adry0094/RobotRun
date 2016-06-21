package edu.ub.pis2016.pis12.robotrun;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Clase controladora de la aplicacion general
 */
public class GameApplication extends Application{
    static final String TAG = "GameAplication";
    private Player player = null;
    private int NumLevelsWorld = 3;

    private int WIDTH_SCREEN;
    private int HEIGHT_SCREEN;

    private int TILE_WIDTH;
    private int TILE_HEIGHT;

    private final int col = 30;
    private final int row = 16;

    private boolean robotsHasBeenSet = false;
    //variable del pausa
    private boolean pause = false;

    // variable game over
    private boolean gameOver = false;

    //variable para la victoria
    private boolean win = false;

    //Variable Shop
    private Robot robotSelected;
    private List<Robot> robotList = new ArrayList<Robot>();
    private final int price = 10;


    //Variables inGame
    private int coinsCollected = 0;
    private int currentWorld;
    private String worldSelect;
    private int currentLevel;

    //Variables WinActivity
    private int coinsForLevel = 60;
    private int numEstrellasLevelActual = 0;

    //Variables Intent control de musica
    private Intent music;
    private Intent musicGame;

    //VARIABLE DE LOS FRAMES
    private int FPS = 30;

    Context appContext = MainActivity.getContextOfApplication();
    private int language; //posicion del lenguaje seleccionado (array.xml)

    private boolean sound;
    private boolean isNew;

    /**
     * Metodo que se llama al crear el GameApplication
     */
    @Override
    public void onCreate() {
        super.onCreate();
        player = new Player("pl");
        loadPreferences();
    }

//SIZE

    /**
     * Devuelve la columna del mapa
     * @return int
     */
    public int getCol() {
        return col;
    }
    /**
     * Devuelve la fila del mapa
     * @return int
     */
    public int getRow() {
        return row;
    }
    /**
     * Devuelve el ancho de un tile
     * @return int
     */
    public int getTileWidth() {
        return TILE_WIDTH;
    }
    /**
     * Devuelve la altura de un tile
     * @return int
     */
    public int getTileHeight() {
        return TILE_HEIGHT;
    }
    /**
     * Devuelve la anchura de la pantalla
     * @return int
     */
    public int getWidthScreen() {
        return WIDTH_SCREEN;
    }
    /**
     * Devuelve la altura de la pantalla
     * @return int
     */
    public int getHeightScreen() {
        return HEIGHT_SCREEN;
    }
    /**
     * Pone valores de anchura y altura al tile
     * @param width
     * @param height
     */
    public void setTileSize(int width, int height) {
        TILE_WIDTH = width/col;//el 16 son los bloques de altura de pantalla (y)
        TILE_HEIGHT = height/row;//el 30 son los bloques de anchura pantalla(x)
    }
    /**
     * Pone valores de anchura y altura al tamaño de pantalla
     * @param width
     * @param height
     */
    public void setScreenSize(int width, int height) {
        WIDTH_SCREEN = width;
        HEIGHT_SCREEN = height;
        setTileSize(width,height);
        setRobots();
    }
//ENDSIZE


//ROBOT

    /**
     * Devuelve el robotSeleccionado
     * @return Robot
     */
    public Robot getRobotSelected() {
        return robotSelected;
    }
    /**
     * Pone el robot de parametro en robotSelected
     * @param robotSelected
     */
    public void setRobotSelected(Robot robotSelected) {
        this.robotSelected = robotSelected;
    }

    /**
     * Pone los robots que estaran el la shop
     */
    private void setRobots() {
        if(!robotsHasBeenSet){
            robotList.add(new Robot("GOLM", R.drawable.robot1,R.string.desc_robot1,false,price*0,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            robotList.add(new Robot("BUM9", R.drawable.robotlock2,R.string.desc_robot2,true,price*2,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            robotList.add(new Robot("BA23", R.drawable.robotlock3,R.string.desc_robot3,true,price*4,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            robotList.add(new Robot("IronR", R.drawable.robotlock4, R.string.desc_robot4,true,price*6,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            //Nuevos robots
            robotList.add(new Robot("Master", R.drawable.robot5lock, R.string.desc_robot5,true,price*8,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            robotList.add(new Robot("Rock", R.drawable.robot6lock, R.string.desc_robot6,true,price*10,-60*(HEIGHT_SCREEN/1080),(float)9.8));
            robotList.add(new Robot("Sp-Der", R.drawable.robot7lock, R.string.desc_robot7,true,price*12,-60*(HEIGHT_SCREEN/1080),(float)9.8));

            robotSelected = robotList.get(0);
            robotsHasBeenSet = true;
        }
    }

    /**
     * Devuelve la lista de robots de la shop
     * @return List<Robot>
     */
    public List<Robot> getRobotList(){return robotList;}

    /**
     * Pone la lista pasada por parametro  a la lista de robots actual.
     * @param robotList
     */
    public void setRobotList(List<Robot> robotList) {
        this.robotList = robotList;
    }
    //ENDROBOT


//WORLD

    /**
     * Pone el mundo actual con el mundo pasado por parametro
     * @param currentWorld
     */
    public void setCurrentWorld(int currentWorld) {
        this.currentWorld = currentWorld;
    }
    /**
     * Devuelve el mundo actual
     * @return int
     */
    public int getCurrentWorld() {
        return currentWorld;
    }
    /**
     * Devuelve el mundo seleccionado
     * @return String
     */
    public String getWorldSelect() {
        return worldSelect;
    }
    /**
     * Pone el mundo seleccionado con el mundo pasado por parametro
     * @param worldSelect
     */
    public void setWorldSelect(String worldSelect) {
        this.worldSelect = worldSelect;
    }
//ENDWORLD

//LEVELS

    /**
     * Devuelve el nivel actual
     * @return int
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Ponel el nivelActual con el nivel pasado por parametro
     * @param currentLevel
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

//ENDLEVELS

//COINS

    /**
     * Return el numero de monedas por nivel
     * @return int
     */
    public int getCoinsForLevel() {
        return coinsForLevel;
    }
    /**
     * Devuelve el numero de monedas recogidas
     * @return int
     */
    public int getCoinsCollected() {
        return coinsCollected;
    }
    /**
     * Pone valor a las monedas recogidas con el parametro dado
     * @param coins
     */
    public void setCoinsCollected(int coins) {
        this.coinsCollected = coins;
    }
//ENDCOINS

//PLAYER

    /**
     * Devuelve objeto player
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Añade estrellas al player
     * @param starsNum
     */
    public void addPlayerStars(int starsNum) {
        player.setWorldStars(currentWorld,currentLevel,starsNum);
    }
    /**
     * Devuelve el numero de monedas de player
     * @return int
     */
    public int getPlayerCoins() {
        return player.getCoins();
    }
    /**
     * Añade monedas al player
     * @param playerCoins
     */
    public void addPlayerCoins(int playerCoins) {
        player.addCoins(playerCoins);
    }
//ENDPLAYER

//WIN/GAMEOVER/PAUSE

    /**
     * Pone game over dado el parametro
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    /**
     * Devuelve game over
     * @return boolean
     */
    public boolean getGameOver() {
        return gameOver;
    }
    /**
     * Devuelve si ha ganado
     * @return boolean
     */
    public boolean isWin() {
        return win;
    }
    /**
     * Pone win dado el parametro
     * @param win
     */
    public void setWin(boolean win) {
        this.win = win;
    }
    /**
     * pone pause dado el parametro
     * @param pause
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    /**
     * Devuelve si es pausa
     * @return boolean
     */
    public boolean isPause() {
        return pause;
    }
//ENDWIN/GAMEOVER/PAUSE

    /**
     * Guarda data de la aplicacion
     */
    public void savePreferences(){
        SharedPreferences sp = getSharedPreferences("Data",appContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name", player.getNom());
        editor.putInt("NumWorlds", player.getNumberWorlds());
        editor.putInt("NumCoins", player.getCoins());
        editor.putInt("AvalibleWorlds", player.getNumberWorlds());

        //DATOS DEL MUNDO TIERRA
        editor.putInt("AvalibleLevelsWorld1",player.getWorldList().get(0).getAvailableLevels());
        editor.putInt("W1L1stars",player.getWorldList().get(0).getLevelStars(0));
        editor.putInt("W1L2stars",player.getWorldList().get(0).getLevelStars(1));
        editor.putInt("W1L3stars",player.getWorldList().get(0).getLevelStars(2));

        //DATOS DEL MUNDO LUNA
        editor.putInt("AvalibleLevelsWorld2",player.getWorldList().get(1).getAvailableLevels());
        editor.putInt("W2L1stars",player.getWorldList().get(1).getLevelStars(0));
        editor.putInt("W2L2stars",player.getWorldList().get(1).getLevelStars(1));
        editor.putInt("W2L3stars",player.getWorldList().get(1).getLevelStars(2));

        //DATOS DE LAS ESTRELLAS GLOBALES DE LA TIERRA
        editor.putInt("W1TotalStars",player.getTotalWorldStars(0));
        //DATOS DE LAS ESTRELLAS GLOBALES DE LA TIERRA
        editor.putInt("W2TotalStars",player.getTotalWorldStars(1));

        //DATOS DE LOS ROBOTS
        editor.putBoolean("Robot1State",robotList.get(0).isLocked());
        editor.putBoolean("Robot2State",robotList.get(1).isLocked());
        editor.putBoolean("Robot3State",robotList.get(2).isLocked());
        editor.putBoolean("Robot4State",robotList.get(3).isLocked());
        editor.putBoolean("Robot5State",robotList.get(4).isLocked());
        editor.putBoolean("Robot6State",robotList.get(5).isLocked());
        editor.putBoolean("Robot7State",robotList.get(6).isLocked());

        for(int i = 0; i < player.getNumberWorlds(); i++){
          for (int x = 0; x<player.getWorldList().get(i).getAvailableLevels(); x++){
            editor.putInt("W"+i+"L"+x,player.getWorldList().get(i).getLevelStars(x));
        }
        }
        editor.commit();

    }

    /**
     * Carga data de la aplicacion
     */
    public int loadPreferences() {
        try{
            SharedPreferences sp = getSharedPreferences("Data", appContext.MODE_PRIVATE);
            String Name = sp.getString("Name", null);
            int worlds = sp.getInt("NumLevels", 0);
            int coins = sp.getInt("NumCoins", 0);
            int AvalibleW= sp.getInt("AvalibleWorlds", 0);


            //CARGAMOS LOS DATOS DEL MUNDO TIERRA
            int AvalibleW1= sp.getInt("AvalibleLevelsWorld1",0);
            int starsW1L1 = sp.getInt("W1L1stars",0);
            int starsW1L2 = sp.getInt("W1L2stars",0);
            int starsW1L3 = sp.getInt("W1L3stars",0);

            //CARGAMOS LOS DATOS DEL MUNDO LUNA
            int AvalibleW2= sp.getInt("AvalibleLevelsWorld2",0);
            int starsW2L1 = sp.getInt("W2L1stars",0);
            int starsW2L2 = sp.getInt("W2L2stars",0);
            int starsW2L3 = sp.getInt("W2L3stars",0);

            //CARGAMOS LAS ESTRELLAS GLOBALES DE LA TIERRA
            int w1TotalStars = sp.getInt("W1TotalStars",0);
            //CARGAMOS LAS ESTRELLAS GLOBALES DE LA TIERRA
            int w2TotalStars = sp.getInt("W2TotalStars",0);

            //CARGAMOS LOS DATOS DE LA TIENDA (ROBOTS DESBLOQUEADOS)
            boolean Robot1State = sp.getBoolean("Robot1State",false);
            boolean Robot2State = sp.getBoolean("Robot2State",true);
            boolean Robot3State = sp.getBoolean("Robot3State",true);
            boolean Robot4State = sp.getBoolean("Robot4State",true);
            boolean Robot5State = sp.getBoolean("Robot5State",true);
            boolean Robot6State = sp.getBoolean("Robot6State",true);
            boolean Robot7State = sp.getBoolean("Robot7State",true);

            loadLangPreferences();

            player.setNom(Name);
            player.setNumberWorlds(worlds);
            player.addCoins(coins);
            player.setAvailableWorlds(AvalibleW);

            //ACTUALIZAMOS LOS DATOS DE LOS MUNDOS

            player.loadData(AvalibleW1, starsW1L1, starsW1L2, starsW1L3, AvalibleW2, starsW2L1, starsW2L2, starsW2L3);
            player.setTotalWorldStars(0,w1TotalStars);
            player.setTotalWorldStars(1,w2TotalStars);
            //ACTUALIZAMOS LOS ROBOTS
            robotList.get(0).setLock(Robot1State);
            robotList.get(1).setLock(Robot2State);
            robotList.get(2).setLock(Robot3State);
            robotList.get(3).setLock(Robot4State);
            robotList.get(4).setLock(Robot5State);
            robotList.get(5).setLock(Robot6State);
            robotList.get(6).setLock(Robot7State);
            //IDIOMA


            return 1;
        }
        catch(Exception e){
            Log.d("GameApplication","Error:"+e.getMessage());
            return 0;
        }

    }

    /**
     * Pone los robots bloqueados segun los parametros
     * @param position
     * @param b
     */
    public void setLock(Integer position, boolean b) {
        robotList.get(position).setLock(b);
        if (position+1 == 2) {robotList.get(position).setPhotoIDactor(R.drawable.robot2);}
        else if (position+1 == 3) {robotList.get(position).setPhotoIDactor(R.drawable.robot3); }
        else if (position+1 == 4) {robotList.get(position).setPhotoIDactor(R.drawable.robot4); }
        else if (position+1 == 5) {robotList.get(position).setPhotoIDactor(R.drawable.robot5); }
        else if (position+1 == 6) {robotList.get(position).setPhotoIDactor(R.drawable.robot6); }
        else if (position+1 == 7) {robotList.get(position).setPhotoIDactor(R.drawable.robot7); }
    }

    public Object getPlayerName() {
        return player.getNom();
    }

    public Object getStringPlayerCoins() {
        return player.getCoins();
    }


    public int getFPS() {
        return FPS;
    }

    public void setMusicBackgroundIntent(Intent intent){
        this.music= intent;
    }
    public void stopMusic(){
        stopService(music);
    }
    public void resumeMusic(){
        startService(music);
    }
    public void setGameMusic(Intent intent){
        this.musicGame=intent;
    }
    public void stopGameMusic(){
        stopService(musicGame);
    }
    public void playMusicGame(){
        startService(musicGame);
    }

//SETTINGS
    public int getLanguage(){return language;}
    public void setLanguage(int language) {
        this.language = language;
    }

    public void saveLangPreferences(){
        isNew=false;
        SharedPreferences sp = getSharedPreferences("Language",appContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Language", language);
        editor.commit();
    }
    public void loadLangPreferences(){
        SharedPreferences sp = getSharedPreferences("Language", appContext.MODE_PRIVATE);
        int s= sp.getInt("Language", 0);
        language=s;
        isNew=true;
    }

    public boolean getSound(){
        return this.sound;
    }
    public void setSound(boolean s){
        this.sound=s;
    }
    public void saveSoundPreferences(){
        SharedPreferences sp = getSharedPreferences("Sound",appContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Sound",this.sound);
        editor.commit();
    }
    public void saveSoundPreferences(boolean s){
        SharedPreferences sp = getSharedPreferences("Sound",appContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Sound",s);
        editor.commit();
    }
    public void loadSoundPreferences(){
        SharedPreferences sp = getSharedPreferences("Sound", appContext.MODE_PRIVATE);
        boolean s= sp.getBoolean("Sound", true);
        this.sound=s;
    }
    public boolean getisNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
//SETTINGS
}

 