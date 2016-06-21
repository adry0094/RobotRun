package edu.ub.pis2016.pis12.robotrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Clase que se encarga de leer y dibujar mapa y robot
 */
public class Engine {

    //Variables
    private Context context;

    //Gestion de la matriz
    private int col ;
    private int row;
    private int i,j;
    private int[][] matrix;
    private int num, limitLeft;
    private int extraPixels = 0;
    private int pps =  1600; //Pixeles por segundo

    private Boolean platform = false;
    private Boolean coin = false;
    private Robot robot;

    private int tile_w;
    private int tile_h;

    private int coinsCollected = 0;

    private GameApplication ctrl;

    /*Tiles*/
    //Blanco
    Tile t0;
    Bitmap b0;
    //Suelo
    Tile t1;
    Bitmap b1;
    //Plataforma
    Tile t2;
    Bitmap b2;
    //Coin
    Tile t5;
    Bitmap b5;
    //Pincho
    Tile t6;
    Bitmap b6;
    //Pincho
    Tile t7;
    Bitmap b7;


    /**
     * Constructor
     * @param context
     * @param robot
     * @param ctrl
     */
    public Engine(Context context, Robot robot, GameApplication ctrl){
        this.context=context;
        this.col=ctrl.getCol();
        this.row=ctrl.getRow();
        tile_w = ctrl.getTileWidth();
        tile_h = ctrl.getTileHeight();
        this.ctrl=ctrl;
        matrix = fillMatrix();
        this.robot = robot;

        /*Tiles*/
        //Blanco
        t0 = new Tile(context, 1,tile_w,tile_h,ctrl.getCurrentWorld());
        b0 = t0.getBmp();
        //Suelo
        t1 = new Tile(context, 1,tile_w,tile_h,ctrl.getCurrentWorld());
        b1 = t1.getBmp();
        //Plataforma
        t2 = new Tile(context, 2,tile_w,tile_h,ctrl.getCurrentWorld());
        b2 = t2.getBmp();
        //Coin
        t5 = new Tile(context,5,tile_w,tile_h,ctrl.getCurrentWorld());
        b5 = t5.getBmp();
        //Pincho
        t6 = new Tile(context,6,tile_w,tile_h,ctrl.getCurrentWorld());
        b6 = t6.getBmp();
        //Lava
        t7 = new Tile(context,7,tile_w,tile_h,ctrl.getCurrentWorld());
        b7 = t7.getBmp();

        //Reescalamos tiles
        b0 = b0.createScaledBitmap(b0,tile_w,tile_h,true); //Blanco
        b1 = b1.createScaledBitmap(b1,tile_w,tile_h,true); //Suelo
        b2 = b2.createScaledBitmap(b2,tile_w,tile_h,true); //Plataforma
        b5 = b5.createScaledBitmap(b5,tile_w,tile_h,true); //Moneda
        b6 = b6.createScaledBitmap(b6,tile_w,tile_h,true); //Pincho
        b7 = b7.createScaledBitmap(b7,tile_w,tile_h,true); //Lava
    }

    /**
     * Actualiza a nuevos valores unas variables
     */
    public void update(){
        //x+=vector;
        if((extraPixels + pps/ctrl.getFPS() )>tile_w){
            col=col+1;
            limitLeft = limitLeft + 1;
            extraPixels =extraPixels + pps/ctrl.getFPS() - tile_w;
            Log.i("Engine", "pixels " + extraPixels + " limitLeft " + limitLeft  +" in " + pps/ctrl.getFPS() + " tile widht  " +tile_w  );
        }else{
            extraPixels = extraPixels + pps/ctrl.getFPS();
            Log.i("Engine", "pixels " + extraPixels + " limitLeft " + limitLeft );
        }
    }

    /**
     * Se encarga de dibujar el mapa de una matriz a un Canvas
     * @param canvas
     */
    public void draw(Canvas canvas) {

        //platform = false;
        //coin = false;
        for (i= limitLeft; i < col+1; i++) {
            platform = false;
            coin = false;
            for (j = 0; j < row; j++) {
                num = getNumCoordenades(i,j);
                switch (num){
                    case 1:
                        t1.setLoc(i, j);
                        if (i == (limitLeft +1)){
                            checkBlockPos(1, j);
                        }
                        canvas.drawBitmap(b1, (t1.getX()-tile_w* limitLeft) -extraPixels, t1.getY(), null);

                        break;
                    //Platform
                    case 2:
                        t2.setLoc(i, j);

                        if (i == (limitLeft +1)){
                            if(!platform){
                                checkBlockPos(num, j);
                            }

                        }
                        canvas.drawBitmap(b2, (t2.getX()-tile_w* limitLeft) -extraPixels, t2.getY(), null);
                        break;
                    //coin
                    case 5:
                        t5.setLoc(i, j);
                        if (i == (limitLeft +1)){
                            canvas.drawBitmap(b5, (t5.getX() - tile_w * limitLeft) -extraPixels,  t5.getY(), null);
                            checkBlockPos(num, j);
                            if(coin){
                                matrix[i][j] = 0;
                            }
                        }
                        else {
                            canvas.drawBitmap(b5, (t5.getX() - tile_w * limitLeft) -extraPixels, t5.getY(), null);
                        }
                        break;
                    //pincho
                    case 6:
                        t6.setLoc(i, j);

                        if (i == (limitLeft +1)){
                            checkBlockPos(num, j);
                        }
                        canvas.drawBitmap(b6, (t6.getX()-tile_w* limitLeft) - extraPixels, t6.getY(), null);
                        break;
                    //lava
                    case 7:
                        t7.setLoc(i, j);

                        if (i == (limitLeft +1)){
                            checkBlockPos(num, j);
                        }
                        canvas.drawBitmap(b7, (t7.getX()-tile_w*limitLeft) - extraPixels, t7.getY(), null);
                        break;
                }
            }
        }
    }

    /**
     * Devuelve el numero que esta en la columna limitLeft y la fila correspondiente
     * @param i
     * @param j
     * @return int
     */
    public int getNumCoordenades(int i, int j){
        return matrix[i][j];
    }

    /**
     * Comprueba el elemento actual con la posicion del robot
     * @param blockId
     * @param j
     */
    public void checkBlockPos(int blockId, int j){
        int actual;
        Log.d("ENGINE","Platform = "+platform);
        switch (blockId){
            //NO BLOCK
            case 0:
                break;
            //SUELO
            case 1:
                if(!platform){
                    actual = j * tile_h;
                    robot.setActualHeight(actual);
                }
                break;
            //PLATAFORMA
            case 2:
                actual = j*tile_h;
                if(robot.getY()<=actual+tile_h) {
                    robot.setActualHeight(actual);
                    platform = true;
                }
                break;
            //MONEDA
            case 5:
                actual = j*tile_h;
                if(robot.getY()<= actual && robot.getY()>= actual-tile_h) {
                    coin = true;
                    coinsCollected = coinsCollected +1;
                }
                break;
            //PINCHO
            case 6:
                actual = j*tile_h;
                if(robot.getY()<= actual && robot.getY()>= actual-tile_h) {
                    ctrl.setGameOver(true);
                }
                break;
            //LAVA
            case 7:
                if(!platform) {
                    actual = j * tile_h;
                    if (robot.getY() <= actual && robot.getY() >= actual - tile_h) {
                        ctrl.setGameOver(true);
                    }
                }
                break;
        }
    }


    /**
     * Lectura del nivel
     * @return int[][]
     */
    public int[][] fillMatrix(){
        InputStreamReader isr = null;
        int[][] mon = new int[440][16];//x sera la longitud del mundo //320 //1024
        BufferedReader in = null;
        try {
            //isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth1));
            //isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth2));
            if(ctrl.getWorldSelect() == "earth") {
                switch (ctrl.getCurrentLevel()) {
                    case 0:
                        //isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth1));
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth1));
                        break;
                    case 1:
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth2));
                        break;
                    case 2:
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.earth3));
                        break;
                }
            }
            else if(ctrl.getWorldSelect() == "moon") {
                switch (ctrl.getCurrentLevel()) {
                    case 0:
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.moon1));
                        break;
                    case 1:
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.moon2));
                        break;
                    case 2:
                        isr = new InputStreamReader(context.getResources().openRawResource(R.raw.moon3));
                        break;
                }
            }
            in = new BufferedReader(isr);
            String linea = null;
            int fila = 0;
            while ((linea = in.readLine()) != null) {
                String[] arr = linea.split(",");
                for (int i = 0; i < arr.length; i++) {
                    int a = (int)arr[i].charAt(0);
                    mon[i][fila] = a-48;//codigo ascii
                }
                linea = null;
                fila++;
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("El fitxer %s no existeix"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        return mon;
    }

    /**
     * Get de columna
     * @return int
     */
    public int getCol() {
        return col;
    }

    /**
     * Devuelve las coins recogidas en el nivel
     * @return int
     */
    public int getCoinsCollected(){
        return coinsCollected;
    }

}