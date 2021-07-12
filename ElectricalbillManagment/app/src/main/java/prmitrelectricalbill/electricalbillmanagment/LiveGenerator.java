package prmitrelectricalbill.electricalbillmanagment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by ibase on 01/09/2020.
 */

public class LiveGenerator extends Activity {
Button b1,b2;
TextView tv1,tv2,tv3;
long startTime=0;
long totalTime=0;
float currentreading= (float) 0.05;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livegenerator);
        b1=(Button)findViewById(R.id.on);
        b2=(Button)findViewById(R.id.off);

        tv1=(TextView)findViewById(R.id.time);
        tv2=(TextView)findViewById(R.id.livedata);
        tv3=(TextView)findViewById(R.id.consumeunit);




        ReceivedData gettrans = new ReceivedData();
        DbParameter host = new DbParameter();
        String url = "http://mahavidyalay.in/AcademicDevelopment/MeterBilling/ShowData.php";
        //url = url + "Registration.php?uname=" + URLEncoder.encode(getname) + "&";
        //url = url + "number=" + URLEncoder.encode(getnumber) + "&";
        gettrans.execute(url);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    startTime = System.currentTimeMillis();
                    SendData gettrans = new SendData();
                    DbParameter host = new DbParameter();
                    String url = "http://mahavidyalay.in/AcademicDevelopment/MeterBilling/BulbOnOff.php?data=b";
                   // url = url + "Registration.php?uname=" + URLEncoder.encode(getname) + "&";
                   // url = url + "number=" + URLEncoder.encode(getnumber) + "&";
                    gettrans.execute(url);


                }catch (Exception e){
                    Toast.makeText(LiveGenerator.this,""+e, Toast.LENGTH_SHORT).show();
                    Toast.makeText(LiveGenerator.this,""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendData gettrans = new SendData();
                DbParameter host = new DbParameter();
                String url = "http://mahavidyalay.in/AcademicDevelopment/MeterBilling/BulbOnOff.php?data=a";
                //url = url + "Registration.php?uname=" + URLEncoder.encode(getname) + "&";
                //url = url + "number=" + URLEncoder.encode(getnumber) + "&";
                gettrans.execute(url);
                long endTime   = System.currentTimeMillis();

               totalTime = endTime - startTime;

               tv1.setText("Estimated Time: "+totalTime+"ms");
               float total=(float)(currentreading*(totalTime/410));
               tv3.setText("Consume Unit:  "+total);

            }
        });


    }



    public class SendData extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;
                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            }catch (Exception e){

                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LiveGenerator.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //	Toast.makeText(UserRegister.this, " "+out, Toast.LENGTH_SHORT).show();

            progress.dismiss();
            super.onPostExecute(result);
        }


    }


    public class ReceivedData extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;
                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            }catch (Exception e){

                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LiveGenerator.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(LiveGenerator.this, " "+out, Toast.LENGTH_SHORT).show();
            tv2.setText("Wattage: "+out);
            //currentreading=Integer.parseInt(out);
            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
