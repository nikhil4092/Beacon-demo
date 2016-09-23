package waverr.project.com.Proxinode;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;

import java.util.Collection;


public class MainActivity extends Activity implements BeaconConsumer, RangeNotifier, View.OnClickListener {

    private BeaconManager mBeaconManager;
    Region region;
    Identifier instanceId;
    ImageView sneakpeak,getintouch;
    TextView faq,up,down;
    BluetoothAdapter mBluetoothAdapter;
    int flag=0;
    @Override
    public void onResume() {
        super.onResume();
        flag=0;
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15"));
        //mBeaconManager.setAndroidLScanningDisabled(true); for background
        mBeaconManager.setForegroundBetweenScanPeriod(10l);

        mBeaconManager.bind(this);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        mBeaconManager.unbind(this);
    }


    public void didExitRegion(Region region) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        sneakpeak=(ImageView)findViewById(R.id.home1);
        getintouch=(ImageView)findViewById(R.id.home2);
        faq=(TextView)findViewById(R.id.faq);
        up=(TextView)findViewById(R.id.up);
        down=(TextView)findViewById(R.id.down);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        faq.setTypeface(face);
        up.setTypeface(face);
        down.setTypeface(face);
        sneakpeak.setOnClickListener(this);
        getintouch.setOnClickListener(this);
        faq.setOnClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBeaconServiceConnect() {
        region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, org.altbeacon.beacon.Region region) {
    if(flag==0) {
        double small=100;
        for(Beacon beacon : beacons)
        {
            if(beacon.getDistance()<small)
            small=beacon.getDistance();

        }
        for (Beacon beacon : beacons) {
            if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00) {
                // This is a Eddystone-UID frame
                Identifier namespaceId = beacon.getId1();
                instanceId = beacon.getId2();
                if (beacon.getExtraDataFields().size() > 0) {
                    long battery = beacon.getExtraDataFields().get(1);
                    Toast.makeText(getBaseContext(),""+battery,Toast.LENGTH_SHORT).show();
                    sendData(instanceId,battery);

                }
                Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                final MediaPlayer mp = MediaPlayer.create(this, R.raw.inq);

                if (instanceId.toString().equals("0x00000000000b") && beacon.getDistance() <= small  && beacon.getDistance()<5.0) {
                    flag=1;

                    // Vibrate for 500 milliseconds
                    mp.start();
                    v.vibrate(500);
                    Intent i = new Intent(MainActivity.this, BusinessCard.class);
                    startActivity(i);
                    sendNotification(instanceId);

                } else if (instanceId.toString().equals("0x000000000001") && beacon.getDistance() <= small && beacon.getDistance()<5.0) {
                    flag=1;

                    mp.start();
                    v.vibrate(500);
                    Intent i = new Intent(MainActivity.this, Airport.class);
                    startActivity(i);
                    sendNotification(instanceId);
                } else if (instanceId.toString().equals("0x000000000002") && beacon.getDistance() <= small && beacon.getDistance()<5.0) {
                    flag=1;

                    mp.start();
                    v.vibrate(500);
                    Intent i = new Intent(MainActivity.this, Mall.class);
                    startActivity(i);
                    sendNotification(instanceId);
                } else if (instanceId.toString().equals("0x000000000003") && beacon.getDistance() <= small && beacon.getDistance()<5.0) {
                    flag=1;

                    mp.start();
                    v.vibrate(500);
                    Intent i = new Intent(MainActivity.this, SneakPeak.class);
                    startActivity(i);
                    sendNotification(instanceId);
                } else if (instanceId.toString().equals("0x000000000004") && beacon.getDistance() <= small && beacon.getDistance()<5.0) {
                    flag=1;

                    mp.start();
                    v.vibrate(500);
                    Intent i = new Intent(MainActivity.this, Faq.class);
                    startActivity(i);
                    sendNotification(instanceId);
                }


                //}
            }
        }
    }
    }
    private void sendNotification(Identifier instanceId) {

        String Title = "Project Proxinode";
        String Text = "";
        if(instanceId.toString().equals("0x00000000000b"))
        {
            Text="You just discovered a contacts beacon";
        }
        if(instanceId.toString().equals("0x000000000001"))
        {
            Text="You just discovered an Airport beacon";
        }
        if(instanceId.toString().equals("0x000000000002"))
        {
            Text="You just discovered a mall beacon";
        }
        if(instanceId.toString().equals("0x000000000003"))
        {
            Text="You just discovered a sneak peek beacon";
        }
        if(instanceId.toString().equals("0x000000000004"))
        {
            Text="You just discovered a FAQs beacon";
        }

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setContentTitle(Title)
                        .setContentText(Text)
                        .setSmallIcon(R.drawable.notif_icon);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MainActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.home1)
        {
            Intent i = new Intent(MainActivity.this,SneakPeak.class);
            startActivity(i);
        }
        if(v.getId()==R.id.home2)
        {
            Intent i = new Intent(MainActivity.this,BusinessCard.class);
            startActivity(i);
        }
        if(v.getId()==R.id.faq)
        {
            Intent i = new Intent(MainActivity.this,Faq.class);
            startActivity(i);
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mBluetoothAdapter.disable();
    }


    private void sendData(Identifier instanceId, long battery) {
        String[] url = new String[]{
                "http://waverr.in/beacon/telemetry.php",
                "instanceid",""+instanceId,
                "battery",""+battery

        };
        JSONObtainer obtainer;
        obtainer = new JSONObtainer() {

        };
        obtainer.execute(url);
    }


}
