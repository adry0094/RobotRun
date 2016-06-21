package edu.ub.pis2016.pis12.robotrun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Clase shop que muestra la tienda
 */
public class ShopMenuActivity extends Activity {

    //Metodos
    private GameApplication ctrl;
    private View currentSelectedViewRobots;

    private Robot robotSelected = null;
    private List<Robot> robotList;

    private TextView textCoins;
    private TextView textSelected;
    private int currPosition;
    private Context context;
    private ImageButton buy;

    private boolean stopAct=true;

    ArrayAdapter<Robot>adapterRobot;
    ListView listRobots ;
    public ArrayAdapter<Robot> getAdapterRobot() {
        return adapterRobot;
    }

    public boolean sound;
    /**
     * Metodo que se llama al crear una activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopAct=true;
        setContentView(R.layout.activity_shop_menu);
        buy = (ImageButton) findViewById(R.id.buyButton_Shop);
        buy.setVisibility(View.GONE);

        //Afegim aelements al arraylist
        adapterRobot = new ListAdapterShopRobot();

        //Mostrem elements al listView
        ctrl = (GameApplication)getApplication();
        robotList = ctrl.getRobotList();
        robotsView();

        updateListView();// con esto cargamos los datos guardados.

        setCurrentRobot();
        textSelected = (TextView)findViewById(R.id.text2);
        textSelected.setText(getString(R.string.robotSelected_ShopMenu));
        textCoins = (TextView)findViewById(R.id.textViewCoins);
        //Info del controlador
        //Actualizamos lista de coins
        updateTextViewCoins();
        sound = ctrl.getSound();
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton_ShopActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //guardamos los cambios antes de volver atras
                ctrl.savePreferences();
                stopAct=false;
                finish();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctrl.getPlayerCoins() >= robotSelected.getPrice() && robotSelected.isLocked()) {
                    ctrl.setLock(currPosition, false);
                    ctrl.setRobotSelected(robotSelected);
                    ctrl.addPlayerCoins(-robotSelected.getPrice());
                    updateTextViewCoins();
                    Toast.makeText(getApplicationContext(), R.string.purchaseSucc_ShopMenu,
                            Toast.LENGTH_LONG).show();
                    robotsView();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.enoughCoins_ShopMenu)+
                                    (robotSelected.getPrice()-ctrl.getPlayerCoins())+getString(R.string.coinsApp),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    /**
     * Actualiza la ListView de robots
     */
    private void updateListView(){
        for(int i = 0; i < robotList.size(); i++){
            Robot robotActual = robotList.get(i);
            if(!robotActual.isLocked()){
                switch(i){
                    case 1:
                        robotActual.setPhotoIDactor(R.drawable.robot2);
                        break;
                    case 2:
                        robotActual.setPhotoIDactor(R.drawable.robot3);
                        break;
                    case 3:
                        robotActual.setPhotoIDactor(R.drawable.robot4);
                        break;

                    case 4:
                        robotActual.setPhotoIDactor(R.drawable.robot5);
                        break;
                    case 5:
                        robotActual.setPhotoIDactor(R.drawable.robot6);
                        break;
                    case 6:
                        robotActual.setPhotoIDactor(R.drawable.robot7);
                        break;

                }
            }
            ctrl.setRobotList(robotList);

        }


    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        ctrl.savePreferences();
        stopAct = false;
        finish();


    }
    @Override
    protected void onStop(){
        super.onStop();
        if(stopAct){
            Log.i("SERVICE", "debug: Service stop started");
            ctrl.stopMusic();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("SERVICE", "debug: Service started");
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        this.stopAct = true;
        if(sound){
            ctrl.resumeMusic();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(stopAct){
            Log.i("SERVICE", "debug: Service started");
            ctrl.stopMusic();
        }
    }
    /**
     * Metodo ens posa el robot actual a la imatge
     */
    private void setCurrentRobot(){
        if(ctrl.getRobotSelected() == null) {
            robotSelected = robotList.get(0);
            ImageView img = (ImageView) findViewById(R.id.img_robotSelected);
            img.setBackgroundResource(robotSelected.getPhotoIDactor());
            TextView text = (TextView) findViewById(R.id.text_robotSeleccionado);
            text.setText(robotSelected.getDescripcion());
        }
        else {
            ImageView img = (ImageView) findViewById(R.id.img_robotSelected);
            img.setBackgroundResource(ctrl.getRobotSelected().getPhotoIDactor());
            TextView text = (TextView) findViewById(R.id.text_robotSeleccionado);
            text.setText(ctrl.getRobotSelected().getDescripcion());
        }
        //ctrl.saveData();
        //ctrl.saveDataFile();
    }


    /**
     * Metode que afegeix els elements a la listView
     */
    private void robotsView(){
        ArrayAdapter<Robot>adapterRobot=new ListAdapterShopRobot();
        ListView listRobots=(ListView)findViewById(R.id.listViewRobots);
        listRobots.setAdapter(adapterRobot);
        //Default Selection
        //Load controller view if is not null
        /*if(ctrl.getViewRobotsSelected() == null){
            currentSelectedViewRobots=listRobots.getChildAt(0);
        }else{
            currentSelectedViewRobots= ctrl.getViewRobotsSelected();
        }*/
        currentSelectedViewRobots=listRobots.getChildAt(0);

        listRobots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                robotSelected = (Robot) parent.getAdapter().getItem(position);
                currPosition = position;
                if (!robotSelected.isLocked()) {
                    buy.setVisibility(View.GONE);
                    ctrl.setRobotSelected(robotSelected);
                    currentSelectedViewRobots = view;
                    Toast.makeText(getApplicationContext(), R.string.itemSelected_ShopMenu, Toast.LENGTH_SHORT).show();
                    ImageView img = (ImageView) findViewById(R.id.img_robotSelected);
                    TextView descripcion = (TextView) findViewById(R.id.text_robotSeleccionado);
                    img.setBackgroundResource(robotSelected.getPhotoIDactor());
                    descripcion.setText(robotSelected.getDescripcion());
                } else {
                    buy.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), getString(R.string.robotLocked_ShopMenu)+robotSelected.getPrice()+
                                    getString(R.string.coinsApp),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    //SHOPROBOT

    //ENDSHOPROBOT

    /**
     * Adapter de la lista de los robots de la shop
     */
    public class ListAdapterShopRobot extends ArrayAdapter<Robot> {

        public ListAdapterShopRobot(){
            super(ShopMenuActivity.this, R.layout.item_view, robotList);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView= convertView;
            if (itemView==null){
                itemView=getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }
            Robot currentRobot=robotList.get(position);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.imageItem_icon);
            imageView.setImageResource(currentRobot.getPhotoIDactor());

            TextView text = (TextView)itemView.findViewById(R.id.item_text);
            text.setText(currentRobot.getName());

            TextView info = (TextView)itemView.findViewById(R.id.item_info);
            info.setText(currentRobot.getInfo());

            return itemView;
        }
    }

    /**
     * Actualiza el textView de las coins de player
     */
    private void updateTextViewCoins(){
        textCoins.setText(Integer.toString(ctrl.getPlayerCoins()));
    }
}