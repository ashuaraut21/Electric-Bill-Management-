package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



public class BillGenrateHere extends Fragment {
View v;
    Button btn;
    TextView txt1,txt2,txt3;
    SharedPreferences shr;
    String uid,d;
    long startTime,endTime,totalTime,radeaing,bill;

    double cal;
      @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v=inflater.inflate(R.layout.activity_bill_genrator,container,false);
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            btn=(Button)v.findViewById(R.id.button);
            txt1=(TextView)v.findViewById(R.id.txt1);
            txt2=(TextView)v.findViewById(R.id.txt2);
            txt3=(TextView)v.findViewById(R.id.txt3);
            shr= PreferenceManager.getDefaultSharedPreferences(getActivity());
            uid=shr.getString("userid","");
            try {


               ViewStatus logs=new ViewStatus();
                DbParameter host=new DbParameter();
                String url=host.getHostpath();
                url=url+"ViewReading.php?uid="+ URLEncoder.encode(uid)+"&";
                logs.execute(url);

            }catch (Exception e){
                Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
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
                        txt1.setText("Start Time :  "+startTime);
                        btn.setBackgroundColor(Color.RED);
                        btn.setText(d);
                    }   if (get_btn.contentEquals("ON")){
                        d="OFF";
                        btn.setText(d);
                        btn.setBackgroundColor(Color.GREEN);
                        endTime   = System.currentTimeMillis();

                        txt2.setText("Off Time :  "+endTime);
                        totalTime = endTime - startTime;

                        long sec = (totalTime / 1000) ;
                        bill=sec/60;
                        radeaing=bill*3;
                       txt3.setText("Current Reading : "+""+radeaing);
                        Toast.makeText(getActivity(),"sec"+sec+"bill"+bill, Toast.LENGTH_LONG).show();
                    }

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "Reading.php?uid="+ URLEncoder.encode(uid) + "&";
                        url = url + "reading="+ URLEncoder.encode(String.valueOf(bill)) + "&";
                        url = url + "payment="+ URLEncoder.encode(String.valueOf(radeaing)) + "&";
                        url = url + "status="+ URLEncoder.encode(d) + "&";
                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
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
            progress = ProgressDialog.show(getActivity(), null,
                    "Processing...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //JSONObject jsonResponse = new JSONObject(out);
                Toast.makeText(getActivity(),""+out, Toast.LENGTH_SHORT).show();

                btn.setText(out);
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
                Toast.makeText(getActivity(), " "+e, Toast.LENGTH_SHORT).show();
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
            progress = ProgressDialog.show(getActivity(), null,
                    "Processing...");
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //	Toast.makeText(UserRegister.this, " "+out, Toast.LENGTH_SHORT).show();
            if(out.contains("1")){
                Toast.makeText(getActivity(), " Sucessfully done", Toast.LENGTH_SHORT).show();
  }
            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
