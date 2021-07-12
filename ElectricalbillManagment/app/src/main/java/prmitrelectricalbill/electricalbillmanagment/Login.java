package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
Button login,reg;
EditText uname,pass;
SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
shr= PreferenceManager.getDefaultSharedPreferences(this);
uname=(EditText)findViewById(R.id.uname);
pass=(EditText)findViewById(R.id.pass);
login=(Button)findViewById(R.id.login);

        reg=(Button)findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Login.this,Registration.class);
                startActivity(i1);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getname,getpass;

                getname=uname.getText().toString().trim();
                getpass=pass.getText().toString().trim();
                boolean status=true;

                if (getname.contentEquals("")){
                    uname.setError("Please Enter username");
                    uname.requestFocus();
                    status=false;
                } if (getpass.contentEquals("")){
                    pass.setError("Please Enter password");
                    pass.requestFocus();
                    status=false;
                }
                if (status){
                    try {


                        UserLogin logs=new UserLogin();
                        DbParameter host=new DbParameter();
                        String url=host.getHostpath();
                        url=url+"Login.php?uname="+ URLEncoder.encode(getname)+"&";
                        url=url+"pass="+ URLEncoder.encode(getpass);
                        logs.execute(url);

                    }catch (Exception e){
                        Toast.makeText(Login.this,""+e, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    private class UserLogin extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(Login.this, null,
                    "Login...");

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
                String consumer_number=jsonChildNode.optString("Consumer_Number");
                String emailid=jsonChildNode.optString("Emailid");
                String pass=jsonChildNode.optString("Password");
                Toast.makeText(Login.this,"login sucsessfull", Toast.LENGTH_SHORT).show();

              //  I//  Toast.makeText(Login.this,"Owner Deshboard",Toast.LENGTH_SHORT).show();


                SharedPreferences.Editor edit=shr.edit();
                edit.putString("name", name);
                edit.putString("number", number);
                edit.putString("cono",consumer_number);
                edit.putString("userid",uid);
                edit.commit();

                if(out.contains("1")){
                    Intent i1=new Intent(Login.this,Dashboard.class);
             startActivity(i1);

                }



            }catch(Exception e){
                Toast.makeText(Login.this, " "+e, Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();

        }


    }
}
