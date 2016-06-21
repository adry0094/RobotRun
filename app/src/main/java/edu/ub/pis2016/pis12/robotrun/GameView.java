package edu.ub.pis2016.pis12.robotrun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Clase que muestra muestra el juego e interactua con el
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    //Variables
    private Engine engine;
    private GameThread thread;
    private GameApplication ctrl;

    private Bitmap robotbmp;
    private Robot robot;
    private Bitmap fondo;

    private GameActivity context;

    /**
     * Constructor
     * @param context
     * @param ctrl
     */
    public GameView(GameActivity context, GameApplication ctrl) {
        super(context);
        this.context = context;
        this.ctrl = ctrl;
        //robotbmp = BitmapFactory.decodeResource(getResources(), R.drawable.robotpruebas);
        /**el robot tambien deberia crearse y gestionarse en engine unicamente*/
        robotbmp = BitmapFactory.decodeResource(getResources(), ctrl.getRobotSelected().getPhotoIDactor());
        robotbmp = robotbmp.createScaledBitmap(robotbmp, ctrl.getTileWidth(), ctrl.getTileHeight(), true);
        robot = new Robot(robotbmp,ctrl.getTileWidth(),ctrl.getTileHeight()*4,ctrl.getRobotSelected().getJumpPower(),ctrl.getRobotSelected().getGravity());

        engine = new Engine(context, robot,ctrl);

        //DIBUJA FONDO
        /**el findo deberia inicializarse cuando se inicializa engine y alli gestionarse*/
        if(ctrl.getCurrentWorld() == 0) {
            fondo = BitmapFactory.decodeResource(getResources(), R.drawable.ingame_background);
            fondo = fondo.createScaledBitmap(fondo, ctrl.getWidthScreen(), ctrl.getHeightScreen(), true);
        }
        else if(ctrl.getCurrentWorld() == 1){
            fondo = BitmapFactory.decodeResource(getResources(), R.drawable.ingamemoon_background);
            fondo = fondo.createScaledBitmap(fondo, ctrl.getWidthScreen(), ctrl.getHeightScreen(), true);

        }
        getHolder().addCallback(this);//para acceder a la superficie y poder manejar los eventos de Surfaceview
        thread = new GameThread(getHolder(), this,ctrl);
        setFocusable(true);//esto quitarlo para meter elementos
    }

    /**
     * Metodo que devuelve si se ha tocado la pantalla
     * @param event
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        robot.ontouch();/**aqui se deberia llamar a engine para que el robot salte*/
        return false;
        //return super.onTouchEvent(event);
    }

    /**
     * Metodo que pone en marcha el thread
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    /**
     * Metodo que cambia atributos cuando cambia el surface
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * Metodo que mata el thread cuando se destruye el surface
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    /**
     * Actualiza la view
     */
    public void update() {
        engine.update();
        robot.update();/**dentro de engine se haria este update*/

        if (engine.getCol() > 420) {//320
            ctrl.setWin(true);
        }

        Log.i("GameView", "col              = " + engine.getCol());
        //colision.comprobarPosicion(engine,robot);
    }

    /**
     * Metodo para parar el thread
     */
    public void interrupt(){
        thread.interrupt();
        thread.setRunning(false);

    }

    /**
     * Metodo para la finalización del nivel por perder
     */
    public void fin(){
        interrupt();
        //Log.i("GameView", "                  Thread interrupt            ");
        //Log.i("GameView", "                  Thread null            " );
        this.context.showGameOver();
        //Log.i("GameView", "                  mostrar gameover         ");
    }

    /**
     * Metodo para la finalización si ganamos
     */
    public void win(){
        interrupt();
        this.context.showWin(engine.getCoinsCollected());
    }

    /**
     * Metodo draw del GameView
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(fondo, 0, 0, null);//para dibujar el fondo imagen
        engine.draw(canvas);
        Log.d("Myview", "draw() -> engine pintat");
        robot.onDraw(canvas);/**tanto como coins y robot deberian hacerse en engine*/
        showCoins(canvas);
        Log.d("Myview", "draw() -> robot pintat");

    }

    /**
     * Metodo para mostrar las monedas
     * @param canvas
     */
    private void showCoins(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.YELLOW);
        paint.setTextSize(50);
        canvas.drawText("Coins: "+Integer.toString(engine.getCoinsCollected()),10, 60, paint);
    }



}