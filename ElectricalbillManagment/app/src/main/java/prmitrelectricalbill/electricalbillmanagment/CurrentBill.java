package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by om sai on 03-11-2018.
 */

public class CurrentBill extends Fragment {
    View v;
    TextView txt,txt2,txt3,txt4;
    String uid;
    SharedPreferences shr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    return  v=inflater.inflate(R.layout.currentbill,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt=(TextView)v.findViewById(R.id.name);
        txt2=(TextView)v.findViewById(R.id.rate);
        txt3=(TextView)v.findViewById(R.id.date);
        txt4=(TextView)v.findViewById(R.id.status);
        shr= PreferenceManager.getDefaultSharedPreferences(getActivity());

        uid=shr.getString("userid","");


        try {

            ViewCurrentBill logs=new ViewCurrentBill();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"CurrentBill.php?uid="+ URLEncoder.encode(uid)+"&";

            logs.execute(url);

        }catch (Exception e){
            Toast.makeText(getActivity(),""+e, Toast.LENGTH_SHORT).show();
        }
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
                String uid=jsonChildNode.optString("Uid");
                String name=jsonChildNode.optString("name");
                String number=jsonChildNode.optString("Contact_Number");
                String BU_Number=jsonChildNode.optString("BU_Number");
                String rate=jsonChildNode.optString("Payment");
                String reading=jsonChildNode.optString("Reading");
                String date=jsonChildNode.optString("data");
                String consumer_number=jsonChildNode.optString("Consumer_Number");
                String emailid=jsonChildNode.optString("Emailid");
                String pass=jsonChildNode.optString("Password");
                String paidreading=jsonChildNode.optString("PaidReading");
                        String paidbill=jsonChildNode.optString("Paidamount");
                Toast.makeText(getActivity()," sucsessfull", Toast.LENGTH_SHORT).show();

                //  I//  Toast.makeText(Login.this,"Owner Deshboard",Toast.LENGTH_SHORT).show();

txt.setText(name+" : "+consumer_number);
txt3.setText("Bill date : "+date.substring(0,10));
int bill= Integer.parseInt(rate)- Integer.parseInt(paidbill);
txt2.setText("RS." +""+bill);

if (rate.contentEquals(paidbill)){
    txt4.setText("Paid");
}else{
    txt4.setText("Pending");
}
//rate.setText("RS." +""+reading);
                SharedPreferences.Editor edit=shr.edit();
                //edit.putString("uid", uid);
                edit.putString("number", number);
                //edit.putString("password",pass);
                edit.putString("userid",uid);
                edit.commit();




            }catch(Exception e){
                Toast.makeText(getActivity(), " "+e, Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();

        }


    }

}
