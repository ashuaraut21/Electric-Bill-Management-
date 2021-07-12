package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class BillGenrator extends AppCompatActivity {
    Button btn;
    TextView txt1,txt2;
    SharedPreferences shr;
    String uid,d;
    long startTime,endTime,totalTime,radeaing,bill;

    double cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_genrator);

        btn=(Button)findViewById(R.id.button);
        txt1=(TextView)findViewById(R.id.txt1);
        txt2=(TextView)findViewById(R.id.txt2);
        shr= PreferenceManager.getDefaultSharedPreferences(this);
        uid=shr.getString("userid","");
        try {


            ViewStatus logs=new ViewStatus();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"ViewReading.php?uid="+ URLEncoder.encode(uid)+"&";
            logs.execute(url);

        }catch (Exception e){
            Toast.makeText(BillGenrator.this,""+e, Toast.LENGTH_SHORT).show();
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_btn=btn.getText().toString().trim();
                if (get_btn.contentEquals("")){
                    d="OFF";
                    btn.setText(d);
                    btn.setBackgroundColor(Color.GREEN);
                }
                if (get_btn.contentEquals("OFF")){
                    d="ON";
                    startTime = System.currentTimeMillis();
                    txt1.setText("Start Time"+startTime);
                    btn.setBackgroundColor(Color.RED);
                    btn.setText(d);
                }   if (get_btn.contentEquals("ON")){
                    d="OFF";
                    btn.setText(d);
                    btn.setBackgroundColor(Color.GREEN);
                    endTime   = System.currentTimeMillis();

                    txt2.setText("Off Time"+endTime);
                    totalTime = endTime - startTime;

                    long sec = (totalTime / 1000) ;
                    bill=sec/60;
                    radeaing=bill*3;
                    Toast.makeText(BillGenrator.this,"sec"+sec+"bill"+bill, Toast.LENGTH_LONG).show();
                }


                try {
                    UpdateStatus gettrans = new UpdateStatus();
                    DbParameter host = new DbParameter();
                    String url = host.getHostpath();
                    url = url + "Reading.php?uid=" + URLEncoder.encode(uid) + "&";
                    url = url + "reading=" + URLEncoder.encode(String.valueOf(bill)) + "&";
                    url = url + "payment=" + URLEncoder.encode(String.valueOf(radeaing)) + "&";
                    url = url + "status=" + URLEncoder.encode(d) + "&";
                    gettrans.execute(url);

//-----------------------------------------start time---------------------------//





                    //------------------------------end time------------------------------//




                }catch (Exception e){
                    Toast.makeText(BillGenrator.this,""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class ViewStatus extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
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
            progress = ProgressDialog.show(BillGenrator.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //JSONObject jsonResponse = new JSONObject(out);
                Toast.makeText(BillGenrator.this,""+out, Toast.LENGTH_SHORT).show();

                btn.setText(out);



               /* JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                //itemname[i]=jsonChildNode.optString("TransactionDate");
                //itemname[i]="12FEb";
                String uid=jsonChildNode.optString("Uid");
                String name=jsonChildNode.optString("name");
                String number=jsonChildNode.optString("Contact_Number");
                String BU_Number=jsonChildNode.optString("BU_Number");
                String consumer_number=jsonChildNode.optString("Consumer_Number");
                String emailid=jsonChildNode.optString("Emailid");
                String pass=jsonChildNode.optString("Password");
                Toast.makeText(BillGenrator.this,"login sucsessfull", Toast.LENGTH_SHORT).show();

                //  I//  Toast.makeText(Login.this,"Owner Deshboard",Toast.LENGTH_SHORT).show();


*/
                String data;
                if(out.contains("")){
                    data="OFF";
                    btn.setText(data);
                    btn.setBackgroundColor(Color.GREEN);
                }if(out.contains("0")){
                    data="OFF";
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setText(data);
                }if(out.contains("1")){
                    data="ON";
                    btn.setBackgroundColor(Color.RED);
                    btn.setText(data);
                }



            }catch(Exception e){
                Toast.makeText(BillGenrator.this, " "+e, Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();

        }

    }

    public class UpdateStatus extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(BillGenrator.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //	Toast.makeText(UserRegister.this, " "+out, Toast.LENGTH_SHORT).show();
            if(out.contains("1")){
                Toast.makeText(BillGenrator.this, " Sucessfully done", Toast.LENGTH_SHORT).show();


                //  Toast.makeText(UserRegister.this, "Output"+out, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
