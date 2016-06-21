package edu.ub.pis2016.pis12.robotrun;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Clase tile que tiene las propiedades de un tile
 */
public class Tile extends GameObject{

    /**
     * Constructor
     * @param context
     * @param num
     * @param width
     * @param height
     */
    public Tile(Context context,int num, int width,int height,int currentWorld) {
        //Distintos tipo de bloques 
        switch (num){
            case 0:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.blank);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.blank);
                    break;
                }
            //suelo de la tierra
            case 1:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.floor);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.floor4);
                    break;
                }

            // plataformas principales de la tierra
            case 2:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.platform);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.floor3);
                    break;
                }

            //moneda
            case 5:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.coin);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.coin);
                    break;
                }

            //pincho
            case 6:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.spike);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.spike2);
                    break;
                }

            //lava
            case 7:
                if(currentWorld == 0){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.lava);
                    break;
                }
                else if(currentWorld == 1){
                    bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.agualodo);
                    break;
                }

        }

        this.width = width;
        this.height= height;
    }

    /**
     * Pone el tamaño dado por parametros
     * @param x
     * @param y
     */
    public void setLoc(int x, int y) {
        this.x=x*width;
        this.y=y*height;
    }


}



