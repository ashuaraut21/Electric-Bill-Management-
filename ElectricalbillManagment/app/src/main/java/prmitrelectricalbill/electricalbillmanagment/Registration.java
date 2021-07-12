package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Registration extends AppCompatActivity {
EditText name,number,buno,consumerno,emailid,password;
String getname,getnumber,getbuno,getconsumnerno,getemailid,getpassword;
Button submit,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(EditText)findViewById(R.id.name);
        number=(EditText)findViewById(R.id.number);
        buno=(EditText)findViewById(R.id.buno);
        consumerno=(EditText)findViewById(R.id.consumerno);
        emailid=(EditText)findViewById(R.id.emailid);
        password=(EditText)findViewById(R.id.password);

submit=(Button)findViewById(R.id.submit);
signup=(Button)findViewById(R.id.signin);

signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(Registration.this,Login.class);
        startActivity(i1);
    }
});


submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email_pattern="[a-zA-z0-9._]+@[a-z]+\\.+[a-z]+";
       getname=name.getText().toString().trim();
       getnumber=number.getText().toString().trim();
       getbuno=buno.getText().toString().trim();
       getconsumnerno=consumerno.getText().toString().trim();
       getemailid=emailid.getText().toString().trim();
       getpassword=password.getText().toString().trim();
Boolean status=true;
       if(getname.contentEquals("")){
           name.requestFocus();
           name.setError("Please Enter Name");
           status=false;
       }if(getnumber.contentEquals("")){
            number.requestFocus();
            number.setError("Please Enter Contact Number");
            status=false;
        }if(getbuno.contentEquals("")){
            buno.requestFocus();
            buno.setError("Please Enter BU no");
            status=false;
        }if(getconsumnerno.contentEquals("")){
            consumerno.requestFocus();
            consumerno.setError("Please Enter Consumer Number");
            status=false;
        }if(getemailid.matches(email_pattern)==false){
            emailid.requestFocus();
            emailid.setError("Please Enter Emailid");
            status=false;
        }if(getpassword.contentEquals("")){
            password.requestFocus();
            password.setError("Please Enter Password");
            status=false;
        }
if (status){
        try {

            RegisterUser gettrans = new RegisterUser();
            DbParameter host = new DbParameter();
            String url = host.getHostpath();
            url = url + "Registration.php?uname=" + URLEncoder.encode(getname) + "&";
            url = url + "number=" + URLEncoder.encode(getnumber) + "&";
            url = url + "buno=" + URLEncoder.encode(getbuno) + "&";
            url = url + "sonsumer=" + URLEncoder.encode(getconsumnerno) + "&";
            url = url + "emailid=" + URLEncoder.encode(getemailid) + "&";
            url = url + "password=" + URLEncoder.encode(getpassword) + "&";

            gettrans.execute(url);


        }catch (Exception e){
            Toast.makeText(Registration.this,""+e, Toast.LENGTH_SHORT).show();
        }
}
    }
});


    }


    public class RegisterUser extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(Registration.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //	Toast.makeText(UserRegister.this, " "+out, Toast.LENGTH_SHORT).show();
            String mess="Your Register email Id Is:"+getemailid+"And Password Id Is:"+getpassword+"Thank You For Using Application";
            SmsManager sms= SmsManager.getDefault();
            sms.sendTextMessage(getnumber, null, ""+mess, null, null);
            Toast.makeText(Registration.this,"Your email id and password sent on your varify number", Toast.LENGTH_SHORT).show();
            if(out.contains("1")){
                Toast.makeText(Registration.this, "You are registerd Sucessfully", Toast.LENGTH_SHORT).show();

                name.setText("");
                number.setText("");
                emailid.setText("");
                password.setText("");
                buno.setText("");
                consumerno.setText("");

                finish();
                //  Toast.makeText(UserRegister.this, "Output"+out, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
