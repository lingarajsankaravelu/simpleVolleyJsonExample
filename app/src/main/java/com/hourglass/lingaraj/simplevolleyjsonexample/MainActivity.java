package com.hourglass.lingaraj.simplevolleyjsonexample;

import android.app.ProgressDialog;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue codexJsonExampleRequestQueue;
    Button data1,data2,data3,data4,getJsonData;
    TextView contactName,contactNumber,emailId,addres;
    JsonArrayRequest jsonArrayRequest;
    ProgressDialog myProgressDialog;
    String[] name,phoneNumber,email,address;
    int arraySizeOfJsonContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data1=(Button)findViewById(R.id.button_fetch_first_data_from_json);
        data2=(Button)findViewById(R.id.button_fetch_second_data_from_json);
        data3=(Button)findViewById(R.id.button_fetch_third_data_from_json);
        data4=(Button)findViewById(R.id.button_fetch_fourth_data_from_json);
        getJsonData=(Button)findViewById(R.id.getJsonData);
        contactName=(TextView) findViewById(R.id.contact_name);
        contactNumber=(TextView)findViewById(R.id.contact_number);
        emailId=(TextView)findViewById(R.id.email_id);
        addres=(TextView)findViewById(R.id.address);
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
     /*DiskBasedCache provides a
      *one-file-per-response cache with an in-memory index,
      * and BasicNetwork provides a network transport based on your preferred HTTP client.
      * */
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
     /*BasicNetwork is Volley's default network implementation. A BasicNetwork
     * must be initialized with the HTTP client your app is using to connect to the network.
     * Typically this is an HttpURLConnection.
     */
        codexJsonExampleRequestQueue=new RequestQueue(cache,network);
        //Started Representing the Request queue with cache and Network.
        codexJsonExampleRequestQueue.start();
     /*Start the queue, as discussed,network call can be queued and Executed in order,
     *here we are simply starting it don't have more than one call.
     */
        myProgressDialog=new ProgressDialog(this);
        getJsonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProgressDialog.setMessage("Getting Json Data From Mockable.IO");
                myProgressDialog.show();
                makeJsonArrayRequest();
            }
        });
        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName.setText(name[0]);
                contactNumber.setText(phoneNumber[0]);
                emailId.setText(email[0]);
                addres.setText(address[0]);
            }
        });
        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName.setText(name[1]);
                contactNumber.setText(phoneNumber[1]);
                emailId.setText(email[1]);
                addres.setText(address[1]);
            }
        });
        data3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName.setText(name[2]);
                contactNumber.setText(phoneNumber[2]);
                emailId.setText(email[2]);
                addres.setText(address[2]);
            }
        });
        data4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName.setText(name[3]);
                contactNumber.setText(phoneNumber[3]);
                emailId.setText(email[3]);
                addres.setText(address[3]);
            }
        });
    }
    public void makeJsonArrayRequest() {
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://demo5585860.mockable.io/contactdetails", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                arraySizeOfJsonContact=response.length();
                //response.length of will return the number of Jsonobject inside the JsonArrray;
                name=new String[arraySizeOfJsonContact];
                phoneNumber=new String[arraySizeOfJsonContact];
                email=new String[arraySizeOfJsonContact];
                address=new String[arraySizeOfJsonContact];
                //allocating Array size for name,phonenumber,email,address.
                for(int i=0;i<arraySizeOfJsonContact;i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
             /*Getting the Object 0,1,2,3 inside JsonArray
             *{
             * "name":"mark"
             * }
             * so when .getJsonObject(0),we will mark object ,
             * we can get all the values inside it.
             * .getJsonObject(1) will give you chrisitian object etc.
             *i.e.mark,chrisitian,Benedict,Bob contact detail
              */
                        name[i]=object.getString("name");
             /*
             * using the Object.getString("Name parameter mentioned in Json")
             * which is Name
             */
                        phoneNumber[i]=object.getString("phone number");
                        email[i]=object.getString("emailid");
                        address[i]=object.getString("address");
                        //so every data is stored into the array.
                    } catch (JSONException e) {
                    }
                }
         /*The above for loop will run until all the object is
         *
          */
                myProgressDialog.dismiss();
                codexJsonExampleRequestQueue.stop();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        myProgressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        codexJsonExampleRequestQueue.add(jsonArrayRequest);
    }
}

