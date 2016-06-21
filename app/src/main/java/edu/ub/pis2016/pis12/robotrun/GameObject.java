package edu.ub.pis2016.pis12.robotrun;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Classe abstracta que heredan algunos objetos
 */
public abstract class GameObject {
    protected String name;
    protected String info;
    protected int photoIDactor;
    protected float x;
    protected float y;
    protected int accelerationx;
    protected int accelerationy;
    protected int width;
    protected int height;
    protected Bitmap bmp;

    /**
     * Devuelve la anchura de la imagen
     * @return int
     */
    public int getWidth() {
        return width;
    }
    /**
     * Devuelve la altura de la imagen
     * @return int
     */
    public int getHeight() {
        return height;
    }
    /**
     * Devuelve la y
     * @return float
     */
    public float getY() {
        return y;
    }
    /**
     * Devuelve la x
     * @return float
     */
    public float getX() {
        return x;
    }
    /**
     * pone valor a x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Pone valor a y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Devuelve un Bitmap
     * @return Bitmap
     */
    public Bitmap getBmp() {
        return bmp;
    }
    /**
     * Devuelve el nombre del actor
     * @return String
     */
    public String getName() {
        return name;
    }
    /**
     * Pone nombre al actor
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Pone informacion al actor
     * @return String
     */
    public String getInfo() {
        return info;
    }
    /**
     * pone la info al actor
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }
    /**
     * Devuelve la foto del actor
     * @return int
     */
    public int getPhotoIDactor() {
        return photoIDactor;
    }
    /**
     * Pone foto al actor
     * @param photoIDactor
     */
    public void setPhotoIDactor(int photoIDactor) {
        this.photoIDactor = photoIDactor;
    }
    /**
     * Devuelve un rectangulo de la imagen
     * @return Rect
     */
    public Rect getIntersec(){
        return new Rect((int) x, (int) y,(int)x+width, (int) (y+height));
    }


}
