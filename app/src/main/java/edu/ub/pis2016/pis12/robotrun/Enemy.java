package edu.ub.pis2016.pis12.robotrun;

/**
 * Clase que describe las propiedades de un enemigo
 */
public class Enemy extends GameObject {
    /**
     * Constructor
     * @param name
     * @param info
     * @param photoIDactor
     */
    public Enemy(String name, String info, int photoIDactor) {
        this.name = name;
        this.info = info;
        this.photoIDactor = photoIDactor;
    }
}
