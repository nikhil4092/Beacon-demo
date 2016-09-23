package waverr.project.com.Proxinode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nikhil on 8/12/2015.
 */
public class BusinessCard extends Activity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog progressDialog;
    ArrayList<Contact> contact;
    public static TextView about;
    String about1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Activity activity = this;

        setContentView(R.layout.activity_main);
        TextView title = (TextView)findViewById(R.id.view);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        title.setTypeface(face);
        contact = new ArrayList<>();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        about=(TextView)findViewById(R.id.about);
        boolean check = isNetworkAvailable();
        if(check == true)
        {getCards();}
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(
                    BusinessCard.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Internet connection unavailable");

            // Setting Dialog Message
            alertDialog.setMessage("Please check your internet connection.");

            // Setting Icon to Dialog


            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        mAdapter = new CardAdapter(contact,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getCards(){
        String[] url = new String[]{
                "http://waverr.in/beacon/getinfo.php"

        };

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Information...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                BusinessCard.this.finish();
            }
        });
        progressDialog.show();

        JSONObtainer obtainer;
        obtainer = new JSONObtainer() {
            @Override
            protected void onPostExecute(JSONArray array) {

                final String things[] = {
                        "Name",
                        "Email",
                        "Phone",
                        "Data",
                        "Photo",
                        "About"
                };
                /*progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Getting Restaurant List...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(false);

                progressDialog.show();*/
                try {

                    if (array.length() == 0) {
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BusinessCard.this, "An error occurred. Please check your network connection and try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        for (int i = 0; i < array.length(); i++) {
                            final JSONObject object = array.getJSONObject(i);
                            Contact c = new Contact();
                            c.setName(object.getString(things[0]));
                            c.setEmail(object.getString(things[1]));
                            c.setPhone(object.getString(things[2]));
                            c.setDescription(object.getString(things[3]));
                            c.setUrl(object.getString(things[4]));
                            about1=(object.getString(things[5]));
                            if(about1.equals(""))
                            {

                            }
                            else
                            {
                                Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
                                about.setTypeface(face);
                                about.setText(about1);
                            }
                            //Toast.makeText(RestaurantListActivity.this, "Distance: "+results[0], Toast.LENGTH_SHORT).show();

                            contact.add(c);

                        }
                    }

                    //Toast.makeText(RestaurantListActivity.this, restaurants.toString(), Toast.LENGTH_SHORT).show();

                    mAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        obtainer.execute(url);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
