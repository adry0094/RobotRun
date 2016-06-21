package edu.ub.pis2016.pis12.robotrun;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que contiene toda la informacion a medida que se avanza en el juego
 */
public class Player implements Serializable{

    //Atributtes
    private GameApplication ctrl;
    private String nom;
    //eart 0, moon 1, nWorld n-1
    private ArrayList<World> worldList = new ArrayList<>();
    private int numberWorlds = 2;
    private int playerCoins;
    private int numberWorldsAvalible;

    //ShopMenuActivity
    private Robot robotSelect;

    /**
     * Constructor
     * @param name
     */
    public Player(String name){
        this.nom = name;
        this.playerCoins = 0;
        for (int i = 0; i < this.numberWorlds; i++) {
            worldList.add(new World());
        }
    }

//DATA
    /**
     * Devuelve el nombre
     * @return String
     */
    public String getNom() {
        return nom;
    }
    /**
     * Pone nombre
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
//ENDDATA

//COINS

    /**
     * Devuelve las coins del jugador
     * @return int
     */
    public int getCoins() {
        return playerCoins;
    }
    /**
     * Añade coins al jugador
     * @param playerCoins
     */
    public void addCoins(int playerCoins) {
        this.playerCoins += playerCoins;
    }
//ENDCOINS

//WORLDSTARS
    /**
     * Devuelve las estrellas totales del mundo
     * @param i
     * @return
     */
    public int getTotalWorldStars(int i) {
        return worldList.get(i).getStars();
    }

    /**
     * Pone el numero de estrellas totales del mundo
     * @param i
     * @param stars
     */
    public void setTotalWorldStars(int i, int stars){
        worldList.get(i).setStars(stars);
    }
    /**
     * Añadimos estrellas al planeta y al respectivo nivel
     * @param world
     * @param lvl
     * @param stars
     */
    public void setWorldStars(int world, int lvl, int stars) {
        int diffStars = stars - worldList.get(world).getLevelStars(lvl);
        if (diffStars > 0) worldList.get(world).addWorldLevelStars(lvl,stars);
        //ponemos el siguiente nivel a true, el ultimo nivel no tiene siguiente
        if (lvl < 2 && !worldList.get(world).getLevelState(lvl+1)) {
            worldList.get(world).setNextLevelState(lvl+1,true);
        }
    }
    /**
     * Devuelve el numero de estrellas del nivel pasado por param
     * @param i
     * @param lvl
     * @return int
     */
    public int getWorldStarsLevel(int i, int lvl) {
        return worldList.get(i).getLevelStars(lvl);
    }
//ENDWORLDSTARS

//WORLDS

    /**
     * Devuelve el numero de mundos
     * @return int
     */
    public int getNumberWorlds(){
        return this.numberWorlds;
    }
    /**
     * Pone valor al numero de mundos
     * @param i
     */
    public void setNumberWorlds(int i){
        this.numberWorlds=i;
    }
    /**
     * Devuelve un array de mundos disponibles
     * @return ArrayList<World>
     */
    public ArrayList<World> getWorldList(){
        return this.worldList;
    }

    /**
     * Pone las worlds disponibles
     * @param i
     */
    public void setAvailableWorlds(int i){


        this.numberWorldsAvalible = i;


    }
    /**
     * Mira si un mundo esta disponible
     * precondicion: i > 0 y wordList.size() > 1(ya que comprovaremos el anterior planeta al dado) i <= worldList.size()
     * @param i
     * @return boolean
     */
    public boolean checkWorld(int i) {
        return worldList.get(i-1).checkAvailabilityWorld();
    }
    /**
     * Retorna numero de niveles disponibles
     * @param worldInt
     * @return int
     */
    public int getAvailableLevels(int worldInt) { return worldList.get(worldInt).getAvailableLevels(); }
//ENDWORLDS

    /**
     * Carga datos para poner las estrellas en cada nivel de los mundos
     * @param avalibleW1
     * @param starsW1L1
     * @param starsW1L2
     * @param starsW1L3
     * @param avalibleW2
     * @param starsW2L1
     * @param starsW2L2
     * @param starsW2L3
     */
    public void loadData(int avalibleW1, int starsW1L1, int starsW1L2, int starsW1L3, int avalibleW2, int starsW2L1, int starsW2L2, int starsW2L3) {

        this.worldList.get(0).setLevelStars(0,starsW1L1);
        if(starsW1L1 !=0){worldList.get(0).setNextLevelState(1, true);}

        this.worldList.get(0).setLevelStars(1,starsW1L2);
        if(starsW1L2 != 0){worldList.get(0).setNextLevelState(2, true);}

        this.worldList.get(0).setLevelStars(2,starsW1L3);


        this.worldList.get(1).setLevelStars(0,starsW2L1);
        if(starsW2L1 !=0){worldList.get(1).setNextLevelState(1, true);}

        this.worldList.get(1).setLevelStars(1,starsW2L2);
        if(starsW2L2 !=0){worldList.get(1).setNextLevelState(2, true);}

        this.worldList.get(1).setLevelStars(2,starsW2L3);
    }

}
