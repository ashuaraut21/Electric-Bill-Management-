package prmitrelectricalbill.electricalbillmanagment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PaidBill extends AppCompatActivity {
TextView name,consumerno,buno,reading,payment;
String getname,getconsumerno,getbuno,getreading,getpayment,getuid,paidamount;
EditText cardname,cardnum;
Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_bill);

        name=(TextView)findViewById(R.id.name);
        consumerno=(TextView)findViewById(R.id.consumnerno);
        buno=(TextView)findViewById(R.id.buno);
        reading=(TextView)findViewById(R.id.rading);
        payment=(TextView)findViewById(R.id.amount);
        submit=(Button) findViewById(R.id.submit);

cardname=(EditText)findViewById(R.id.nameoncard);
cardnum=(EditText)findViewById(R.id.cardnumber);

       Bundle bundle=getIntent().getExtras();
        getname=bundle.getString("name");
        getconsumerno=bundle.getString("consumernumber");
        getbuno=bundle.getString("buno");
        getreading=bundle.getString("rading");
        getpayment=bundle.getString("amount");
        getuid=bundle.getString("uid");
        paidamount=bundle.getString("paidamount");
        Toast.makeText(PaidBill.this,getuid+"name"+getname+"cno"+getconsumerno+"buno"+getbuno+"reading"+getreading+"payment"+getpayment, Toast.LENGTH_SHORT).show();
int bill= Integer.parseInt(getpayment)- Integer.parseInt(paidamount);


        name.setText("Name : "+getname);
           consumerno.setText("Consumer No : "+getconsumerno);
        buno.setText("BU No : "+getbuno);
     //reading.setText("Reading : "+getreading);
        payment.setText("Amount : "+bill);

      if(bill<=0){

          Toast.makeText(PaidBill.this,"Amount is not valid to pay",Toast.LENGTH_LONG).show();

          submit.setEnabled(false);


      }else {

          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Boolean status = true;
                  String getname, getcard;
                  getname = cardname.getText().toString().trim();
                  getcard = cardnum.getText().toString().trim();
                  if (getname.contentEquals("")) {
                      cardname.setError("please Enter Card on name");
                      cardname.requestFocus();
                      status = false;
                  }
                  if (getcard.contentEquals("")) {
                      cardnum.setError("please Enter Card on number");
                      cardnum.requestFocus();
                      status = false;
                  }
                  if (status) {
                      try {
                          // Toast.makeText(PaidBill.this,""+getname+getcard,Toast.LENGTH_SHORT).show();

                          BillPaidHere gettrans = new BillPaidHere();
                          DbParameter host = new DbParameter();
                          String url = host.getHostpath();
                          url = url + "PaidBill.php?uid=" + URLEncoder.encode(getuid) + "&";
                          url = url + "reading=" + URLEncoder.encode(getreading) + "&";
                          url = url + "amount=" + URLEncoder.encode(getpayment) + "&";
                          gettrans.execute(url);


                      } catch (Exception e) {
                          Toast.makeText(PaidBill.this, "" + e, Toast.LENGTH_SHORT).show();
                      }
                  }
              }
          });
      }
    }
    public class BillPaidHere extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(PaidBill.this, null,
                    "Processing...");
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //	Toast.makeText(UserRegister.this, " "+out, Toast.LENGTH_SHORT).show();
            if(out.contains("1")){
                Toast.makeText(PaidBill.this, " Sucessfully done", Toast.LENGTH_SHORT).show();
                finish();
            }
            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
