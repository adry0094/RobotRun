package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Mich on 08/04/2016.
 */
public class MiniGameActivity extends Activity implements SensorEventListener{

    SensorManager sManager;
    Sensor gyroSensor;
    TextView tv;
    ImageButton backButton = null;
    CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x;
    public static int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);

        tv=(TextView) findViewById(R.id.textViewTe);
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //gyroSensor=sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        backButton = (ImageButton) findViewById(R.id.imageButtonTe);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
        // Register this class as a listener for the accelerometer sensor
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        // ...and the orientation sensor
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);}

    //When this Activity isn't visible anymore
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener((SensorEventListener) this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        x = (int) Math.pow(event.values[1], 2);
        y = (int) Math.pow(event.values[2], 2);
        tv.setText("Orientation X (Roll) :" + Float.toString(event.values[2]) + "\n" +
                "Orientation Y (Pitch) :" + Float.toString(event.values[1]) + "\n" +
                "Orientation Z (Yaw) :" + Float.toString(event.values[0]));
    }
    public class CustomDrawableView extends View
    {
        static final int width = 50;
        static final int height = 50;

        public CustomDrawableView(Context context)
        {
            super(context);

            mDrawable.getPaint().setColor(0xff74AC23);
            mDrawable.setBounds(x, y, x + width, y + height);
        }

        protected void onDraw(Canvas canvas){
            RectF oval = new RectF(MiniGameActivity.x, MiniGameActivity.y, MiniGameActivity.x + width, MiniGameActivity.y
                    + height); // set bounds of rectangle
            Paint p = new Paint(); // set some paint options
            p.setColor(Color.BLUE);
            canvas.drawOval(oval, p);
            invalidate();
        }
    }
}
