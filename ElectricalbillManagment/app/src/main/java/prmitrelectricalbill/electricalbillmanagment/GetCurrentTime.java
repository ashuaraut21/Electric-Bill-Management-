package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by ibase on 02/02/2019.
 */

public class GetCurrentTime extends Fragment implements CompoundButton.OnCheckedChangeListener {
    View v;
    Button b2,b3;
    SharedPreferences shr;
    String uid;
    ToggleButton onff1, onff2, onff3,onff4, onff5, onff6;
    TextView timeS1, timeS2, timeS3,timeS4, timeS5, timeS6,
            timeE1, timeE2, timeE3,timeE4, timeE5, timeE6,
            timeEs1, timeEs2, timeEs3,timeEs4, timeEs5, timeEs6,timeEs7;

    String diffTime1="00:00:00";
    String diffTime2="00:00:00";
    String diffTime3="00:00:00";
    String diffTime4="00:00:00";
    String diffTime5="00:00:00";
    String diffTime6="00:00:00";
    String totaltime="00:00:00";

    String time3;

    String lightt11,lightt12,lightt21,lightt22,fan11,fan12,fan21,fan22,
            lightt31,lightt32,lightt41,lightt42;


    String substr1,substr2,substr3,substr4,substr5,substr6;
    String substr7,substr8,substr9,substr10,substr11,substr12;
    String substr13,substr14,substr15,substr16,substr17,substr18;
    String substr41,substr42,substr43,substr44,substr45,substr46;
    String substr51,substr52,substr53,substr54,substr55,substr56;
    String substr61,substr62,substr63,substr64,substr65,substr66;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v = inflater.inflate(R.layout.getcurrenttime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shr= PreferenceManager.getDefaultSharedPreferences(getActivity());
        uid=shr.getString("userid","");


        b2 = (Button)v.findViewById(R.id.estbtn1);


        timeEs7 = (TextView)v.findViewById(R.id.timeEs7);

        onff1 = (ToggleButton)v.findViewById(R.id.onOff1);
        onff2 = (ToggleButton)v.findViewById(R.id.onOff2);
        onff3 = (ToggleButton)v.findViewById(R.id.onOff3);
        onff4 = (ToggleButton)v.findViewById(R.id.onOff4);
        onff5 = (ToggleButton)v.findViewById(R.id.onOff5);
        onff6 = (ToggleButton)v.findViewById(R.id.onOff6);


        timeS1 = (TextView)v.findViewById(R.id.timeS1);
        timeS2 = (TextView)v.findViewById(R.id.timeS2);
        timeS3 = (TextView)v.findViewById(R.id.timeS3);
        timeS4 = (TextView)v.findViewById(R.id.timeS4);
        timeS5 = (TextView)v.findViewById(R.id.timeS5);
        timeS6 = (TextView)v.findViewById(R.id.timeS6);


        timeE1 = (TextView)v.findViewById(R.id.timeE1);
        timeE2 = (TextView)v.findViewById(R.id.timeE2);
        timeE3 = (TextView)v.findViewById(R.id.timeE3);
        timeE4 = (TextView)v.findViewById(R.id.timeE4);
        timeE5 = (TextView)v.findViewById(R.id.timeE5);
        timeE6 = (TextView)v.findViewById(R.id.timeE6);


        timeEs1 = (TextView)v.findViewById(R.id.timeEs1);
        timeEs2 = (TextView)v.findViewById(R.id.timeEs2);
        timeEs3 = (TextView)v.findViewById(R.id.timeEs3);
        timeEs4 = (TextView)v.findViewById(R.id.timeEs4);
        timeEs5 = (TextView)v.findViewById(R.id.timeEs5);
        timeEs6 = (TextView)v.findViewById(R.id.timeEs6);




        onff1.setOnCheckedChangeListener(this);
        onff2.setOnCheckedChangeListener(this);
        onff3.setOnCheckedChangeListener(this);
        onff4.setOnCheckedChangeListener(this);
        onff5.setOnCheckedChangeListener(this);
        onff6.setOnCheckedChangeListener(this);




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "dkjs", Toast.LENGTH_SHORT).show();
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date1 = timeFormat.parse(diffTime1);
                    Date date2 = timeFormat.parse(diffTime2);
                     Date date3 = timeFormat.parse(diffTime3);
                    Toast.makeText(getActivity(),"Time 1"+ date1.getTime(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"Time 2"+ date2.getTime(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"Time 3"+ date3.getTime(),Toast.LENGTH_LONG).show();
                   // Toast.makeText(getActivity(),"Time 4"+ date4.getTime(),Toast.LENGTH_LONG).show();

                  Date date4 = timeFormat.parse(diffTime4);
                   Date date5 = timeFormat.parse(diffTime5);
                   Date date6 = timeFormat.parse(diffTime6);
                    long sum = date1.getTime() + date2.getTime()+date4.getTime()+date5.getTime()+date3.getTime()+date6.getTime();

                    String date7= timeFormat.format(new Date(sum));
                    System.out.println("The sum is "+date7);
                    Toast.makeText(getActivity(),"The sum is "+date7, Toast.LENGTH_SHORT).show();

                    Date date = timeFormat.parse(date7);
                    long readings=(date.getTime()) / (60000);
                    long now = ((date.getTime()) / (60000)*7);
                    Toast.makeText(getActivity(),"Now"+now, Toast.LENGTH_SHORT).show();
                    timeEs7.setText(date7);


                    UpdateStatus gettrans = new UpdateStatus();
                    DbParameter host = new DbParameter();
                    String url = host.getHostpath();
                    url = url + "Reading.php?uid="+ URLEncoder.encode(uid) + "&";
                    url = url + "reading="+ URLEncoder.encode(String.valueOf(readings)) + "&";
                    url = url + "payment="+ URLEncoder.encode(String.valueOf(now)) + "&";

                    gettrans.execute(url);


                } catch (Exception e) {
                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

            case R.id.onOff1:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                   lightt11 = format.format(calendar.getTime());
                    timeS1.setText(lightt11);

                  substr11 = lightt11.substring(0, 2);
                    substr12 = lightt11.substring(3, 5);
                    substr13 = lightt11.substring(6, 8);


                    try {
                       UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("1")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
              lightt12= format.format(calendar.getTime());
                    timeE1.setText(lightt12);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("1")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                    substr4=lightt12.substring(0,2);
                    substr5=lightt12.substring(3,5);
                    substr6=lightt12.substring(6,8);

                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(lightt11);
                        java.util.Date date2 = df.parse(lightt12);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                    diffTime1 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs1.setText(diffTime1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;


            case R.id.onOff2:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
         lightt21= format.format(calendar.getTime());
                    timeS2.setText(lightt21);

                    substr7 = lightt21.substring(0, 2);
                    substr8 = lightt21.substring(3, 5);
                    substr9 = lightt21.substring(6, 8);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("2")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                   lightt22= format.format(calendar.getTime());
                    timeE2.setText(lightt22);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("2")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }

                    substr10=lightt22.substring(0,2);
                    substr11=lightt22.substring(3,5);
                    substr12=lightt22.substring(6,8);
                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(lightt21);
                        java.util.Date date2 = df.parse(lightt22);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                        diffTime2 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs2.setText(diffTime2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;


            case R.id.onOff4:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    lightt41 = format.format(calendar.getTime());
                    timeS4.setText(lightt41);

                    substr41 = lightt41.substring(0, 2);
                    substr42 = lightt41.substring(3, 5);
                    substr43 = lightt41.substring(6, 8);


                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("3")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    lightt42= format.format(calendar.getTime());
                    timeE4.setText(lightt42);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("3")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                    substr44=lightt42.substring(0,2);
                    substr45=lightt42.substring(3,5);
                    substr46=lightt42.substring(6,8);

                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(lightt41);
                        java.util.Date date2 = df.parse(lightt42);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                        diffTime4 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs4.setText(diffTime4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;

            case R.id.onOff5:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    lightt31= format.format(calendar.getTime());
                    timeS5.setText(lightt31);

                    substr51 = lightt31.substring(0, 2);
                    substr52 = lightt31.substring(3, 5);
                    substr53= lightt31.substring(6, 8);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("4")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    lightt32= format.format(calendar.getTime());
                    timeE5.setText(lightt32);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("4")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }

                    substr54=lightt32.substring(0,2);
                    substr55=lightt32.substring(3,5);
                    substr56=lightt32.substring(6,8);
                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(lightt31);
                        java.util.Date date2 = df.parse(lightt32);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                        diffTime5 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs5.setText(diffTime5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;

            case R.id.onOff6:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    fan21= format.format(calendar.getTime());
                    timeS6.setText(fan21);

                    substr61 = fan21.substring(0, 2);
                    substr62 = fan21.substring(3, 5);
                    substr63 = fan21.substring(6, 8);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("5")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    fan22= format.format(calendar.getTime());
                    timeE6.setText(fan22);

                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("5")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }

                    substr64=fan22.substring(0,2);
                    substr65=fan22.substring(3,5);
                    substr66=fan22.substring(6,8);
                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(fan21);
                        java.util.Date date2 = df.parse(fan22);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                        diffTime6 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs6.setText(diffTime6);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;


            case R.id.onOff3:
                if (isChecked) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    fan11 = format.format(calendar.getTime());
                    timeS3.setText(fan11);

                    substr13=fan11.substring(0,2);
                    substr14=fan11.substring(3,5);
                    substr15=fan11.substring(6,8);
                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("1") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("6")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                 fan12 = format.format(calendar.getTime());
                    timeE3.setText(fan12);
                    try {
                        UpdateStatus gettrans = new UpdateStatus();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "OnOff.php?status="+ URLEncoder.encode("0") + "&";
                        url = url + "uid="+ URLEncoder.encode(String.valueOf("6")) + "&";

                        gettrans.execute(url);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
                    }

                    substr16=fan12.substring(0,2);
                    substr17=fan12.substring(3,5);
                    substr18=fan12.substring(6,8);

                    try {
                        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                        java.util.Date date1 = df.parse(fan11);
                        java.util.Date date2 = df.parse(fan12);
                        long diff = date2.getTime() - date1.getTime();


                        int timeInSeconds = (int) (diff / 1000);
                        int hours, minutes, seconds;
                        hours = timeInSeconds / 3600;
                        timeInSeconds = timeInSeconds - (hours * 3600);
                        minutes = timeInSeconds / 60;
                        timeInSeconds = timeInSeconds - (minutes * 60);
                        seconds = timeInSeconds;

                        diffTime3 = (hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        time3=""+(hours<10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                        timeEs3.setText(diffTime3);
                        Toast.makeText(getActivity(),"diff3"+diffTime3,Toast.LENGTH_LONG).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

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