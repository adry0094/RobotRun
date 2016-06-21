package edu.ub.pis2016.pis12.robotrun;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Clase robot, que tiene las caracteristicas de un robot
 */
public class Robot extends GameObject implements Serializable{
    //Atributos
    private float gravity = (float) 8.9; //Define the gravity acceleration
    private float vspeed = 2; //No debe ser 1
    private int jumpPower = -70; //Constant of a jump (Low value does higher jump) example: -10 |...| , -30 |.........|
    private float actualHeight;

    private boolean lock;//comprueba si esta bloqueado o no
    private int price;
    private int descripcion;

    //Constructors

    /**
     * Constructor
     * @param name
     * @param photoIDactor
     * @param descripcion
     * @param lock
     * @param price
     */
    public Robot(String name, int photoIDactor, int descripcion, boolean lock, int price, int jumpPower, float gravity) {
        this.name = name;
        this.photoIDactor = photoIDactor;
        this.descripcion = descripcion;
        this.lock = lock;
        this.price = price;
        this.jumpPower = jumpPower;
        this.gravity = gravity;
    }

    /**
     * Constructor
     * @param bmp
     * @param x
     * @param y
     */
    public Robot(Bitmap bmp, int x, int y, int jumpPower, float gravity){
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.height = bmp.getHeight();
        this.jumpPower = jumpPower;
        this.gravity = gravity;
    }


    /**
     * llama a checkground()
     */
    public void update(){
        checkground();
    }

    /**
     * Implementa la gravedad de salto y caida
     */
    public void checkground(){
        if (y < actualHeight -height){
            vspeed+=gravity;
            if (y > actualHeight -height-vspeed)
            {
                vspeed = actualHeight -y-height;
            }
        }
        else if (vspeed>0)
        {
            vspeed = 0;
            y = actualHeight -height;
        }
        y += vspeed;
    }
    /**
     * Implementa el salto del jugador
     */
    public void jump(){
        if (y>= actualHeight -height){
            vspeed = jumpPower;
        }
    }
    /**
     * Cuando se toca la pantalla se llama a jump
     */
    public void ontouch(){
        jump();
    }

    /**
     * Dibuja el robot en unas posiciones determinadas
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bmp, x, y, null);
    }


    //Getters Setters

    /**
     * Devuelve la descripcion
     * @return int
     */
    public int getDescripcion() {
        return descripcion;
    }
    /**
     * Devuelve el precio del robot
     * @return int
     */
    public int getPrice() {
        return price;
    }
    /**
     * Pone la altura actual al robot
     * @param actualHeight
     */
    public void setActualHeight(float actualHeight) {
        this.actualHeight = actualHeight;
    }
    /**
     * Mira si esta bloqueado o no
     * @return boolean
     */
    public boolean isLocked() {
        return lock;
    }
    /**
     * Pone un robot bloqueado
     * @param b
     */
    public void setLock(boolean b) {
        this.lock = b;
    }

    /**
     * Devuelve el jumPower
     * @return int
     */
    public int getJumpPower() {
        return jumpPower;
    }
    /**
     * Establece el jump
     * @param jumpPower
     */
    public void setJumpPower(int jumpPower) {
        this.jumpPower = jumpPower;
    }
    /**
     * Devuelve la gravedad
     * @return float
     */
    public float getGravity() {
        return gravity;
    }
    /**
     * Pone la gravedad del robot
     * @param gravity
     */
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }


}
