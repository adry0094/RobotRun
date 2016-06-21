package edu.ub.pis2016.pis12.robotrun;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Clase que crea un hilo para el flujo del juego
 */
public class GameThread extends Thread {
    //Variables
    private SurfaceHolder sh;
    private GameView gameView;
    private boolean run;
    //private static int FPS = 30;
    private GameApplication ctrl;

    /**
     * Constructor
     * @param holder
     * @param gameView
     * @param ctrl
     */
    public GameThread(SurfaceHolder holder, GameView gameView,GameApplication ctrl) {
        super();
        this.ctrl = ctrl;
        sh = holder;
        this.gameView = gameView;
    }

    /**
     * Pone en marcha el thread
     */
    @Override
    public void run(){
        //variables
        long ticksPS = 1000/ctrl.getFPS();
        //long startTime = 0;
        long sleepTime;
        long startTime = System.nanoTime();
        while (run && !ctrl.getGameOver() && !ctrl.isWin() ) {
            while (!ctrl.getGameOver() && !ctrl.isPause() && !ctrl.isWin()) {
                startTime = System.nanoTime();
                Canvas c = null;
                try {

                    c = this.sh.lockCanvas();
                    synchronized (sh) {
                        this.gameView.update();
                        this.gameView.draw(c);
                        }

                } finally {
                    if (c != null) {
                        sh.unlockCanvasAndPost(c);
                    }
                }

                //sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                sleepTime = ticksPS - ((System.nanoTime() - startTime)/1000000);
                Log.d("TIEMPO", "currentTimeMi                    lilis"+startTime);
                Log.d("TIEMPO", "currentTimeMililis"+sleepTime);
                try {
                    if (sleepTime > 0) {
                        sleep(sleepTime);
                    } else {
                        //sleep(10);
                        //Log.d("GameThread: ", "" + System.currentTimeMillis());
                    }
                }
                catch (Exception e) {
                }

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Log.i("GameThread", "               GAMEOVER                " +ctrl.getGameOver());
            }
            //Log.i("GameThread", "               GAMEOVER                " +ctrl.getGameOver());
        }
        Log.i("GameView", "                  llamar metodo gamview            " );
        //Gestión del ganar o perder.
        if(!ctrl.isPause()){
            if(ctrl.getGameOver()) {
                gameView.fin();
            }
            else{
                gameView.win();
            }
        }

    }
    /**
     * Pone valor a run
     * @param run
     */
    public void setRunning(boolean run) {
        this.run = run;
    }
}


