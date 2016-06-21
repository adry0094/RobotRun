package edu.ub.pis2016.pis12.robotrun;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase mundo que contiene las propiedades de un mundo
 */
public class World implements Serializable{
    //Variables
    private int stars;
    private int numberLevels = 3;
    private ArrayList<Integer> levelStars = new ArrayList<>(3);
    private ArrayList<Boolean> levelState = new ArrayList<>(3);

    /**
     * Constructor
     */
    public World() {
        stars = 0;
        for (int i=0;i<numberLevels;++i) {
            levelStars.add(0);
        }
        levelState.add(true);//el primer nivel siempre esta disponible
        for (int i=1;i<numberLevels;++i) {
            levelState.add(false);
        }
    }


    //LEVELS

    /**
     * Comprobación de estrellas
     * @return boolean
     */
    public boolean checkAvailabilityWorld() {
        Boolean found = (stars >= 5);
        if (found) {
            for(boolean b : levelState) if(!b) found = false;
        }
        return found;
    }
    /**
     * Retorna los niveles disponibles del mundo
     * @return int
     */
    public int getAvailableLevels() {
        int count = 0;
        for (boolean b : levelState) if(b) count+=1;
        return count;
    }
    /**
     * Devuelve el numero de niveles
     * @return int
     */
    public int getNumberLevels() {
        return numberLevels;
    }

    //ENDLEVELS
    //STARS
    /**
     * Devuelve un array con las estrellas de los niveles
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getLevelStars() {
        return levelStars;
    }
    /**
     * Devuelve el numero de estrellas del mundo
     * @return int
     */
    public int getStars() {
        return stars;
    }
    /**
     * Devuelve el numero de estrellas de un nivel
     * @param i
     * @return int
     */
    public int getLevelStars(int i) {
        return levelStars.get(i);
    }
    /**
     * Pone estrellas en el mundo
     * @param stars
     */
    public void setStars(int stars) {
        this.stars = stars;
    }
    /**
     * Pone en un nivel estrellas
     * @param level
     * @param stars
     */
    public void setLevelStars(int level, int stars){
        this.levelStars.add(level,stars);
    }

    /**
     * Añade a un nivel las estrellas
     * PRE: anteriormente se ha comprobado que las estrellas obtenidas en ese nivel son mayores que las inciales
     * @param lvl
     * @param stars
     */
    public void addWorldLevelStars(int lvl, int stars) {
        this.stars += stars;
        Integer auxStars = levelStars.get(lvl)+stars;
        levelStars.set(lvl, auxStars);
    }
    //ENDSTARS
    //STATE

    /**
     * Devuelve un array de estados de los niveles
     * @return ArrayList<Boolean>
     */
    public ArrayList<Boolean> getLevelState() {
        return levelState;
    }
    /**
     * Pone el estado al siguiente nivel
     * @param lvl
     * @param b
     */
    public void setNextLevelState(int lvl, boolean b) {
        levelState.set(lvl,b);
    }
    /**
     * Devuelve el estado de un nivel
     * @param lvl
     * @return boolean
     */
    public boolean getLevelState(int lvl) {
        return levelState.get(lvl);
    }
    //ENDSTATE
}
 
