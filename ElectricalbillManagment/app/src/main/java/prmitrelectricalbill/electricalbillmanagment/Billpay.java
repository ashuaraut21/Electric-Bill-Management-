package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by om sai on 13-11-2018.
 */

public class Billpay extends Fragment {
    View v;
    int bill=0;
    TextView txt,txt2,txt3;
    String uid;
    String uid1,name,number,BU_Number,rate,reading,date,consumer_number,emailid,pass,paidamount;
    Button paybill,graph;
    SharedPreferences shr;
       @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v=inflater.inflate(R.layout.billpay,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt=(TextView)v.findViewById(R.id.name);
        txt2=(TextView)v.findViewById(R.id.rate);
        txt3=(TextView)v.findViewById(R.id.date);
        graph=(Button)v.findViewById(R.id.graph);
        shr= PreferenceManager.getDefaultSharedPreferences(getActivity());
        paybill=(Button)v.findViewById(R.id.btn);
        uid=shr.getString("userid","");
        Toast.makeText(getActivity(),"uid"+ uid, Toast.LENGTH_SHORT).show();

        try {

       ViewCurrentBill logs=new ViewCurrentBill();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"CurrentBill.php?uid="+ URLEncoder.encode(uid)+"&";

            logs.execute(url);

        }catch (Exception e){
            Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
        }


        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(getActivity(),ViewGraph.class);
                startActivity(i1);

            }
        });

        paybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill<=0){
                    Toast.makeText(getActivity(),"Amount is less to pay",Toast.LENGTH_LONG).show();
                    paybill.setEnabled(false);

                }else {
                    Intent i1 = new Intent(getActivity(), PaidBill.class);
                    i1.putExtra("uid", uid);
                    i1.putExtra("name", name);
                    i1.putExtra("consumernumber", consumer_number);
                    i1.putExtra("buno", BU_Number);
                    i1.putExtra("amount", rate);
                    i1.putExtra("rading", reading);
                    i1.putExtra("paidamount", paidamount);
                    startActivity(i1);
                }
            }
        });
    }


    private class ViewCurrentBill extends AsyncTask<String, Integer, String> {
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
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                //itemname[i]=jsonChildNode.optString("TransactionDate");
                //itemname[i]="12FEb";
                uid1=jsonChildNode.optString("Uid");
                name=jsonChildNode.optString("name");
              number=jsonChildNode.optString("Contact_Number");
               BU_Number=jsonChildNode.optString("BU_Number");
                 rate=jsonChildNode.optString("Payment");
                 reading=jsonChildNode.optString("Reading");
                date=jsonChildNode.optString("data");
                paidamount=jsonChildNode.optString("Paidamount");
           consumer_number=jsonChildNode.optString("Consumer_Number");
              emailid=jsonChildNode.optString("Emailid");
             pass=jsonChildNode.optString("Password");
                Toast.makeText(getActivity()," sucsessfull", Toast.LENGTH_SHORT).show();

                //  I//  Toast.makeText(Login.this,"Owner Deshboard",Toast.LENGTH_SHORT).show();
                bill= Integer.parseInt(rate)- Integer.parseInt(paidamount);
                txt.setText(name+" : "+consumer_number);
                txt3.setText("Bill date : "+date.substring(0,10));
                txt2.setText("RS." +""+bill);
//rate.setText("RS." +""+reading);
                SharedPreferences.Editor edit=shr.edit();
                //edit.putString("uid", uid);
                edit.putString("number", number);
                //edit.putString("password",pass);
                edit.putString("userid",uid);
                edit.commit();

                if(bill<=0){
                    paybill.setEnabled(false);

                }




            }catch(Exception e){
                Toast.makeText(getActivity(), " "+e, Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();

        }


    }
}
