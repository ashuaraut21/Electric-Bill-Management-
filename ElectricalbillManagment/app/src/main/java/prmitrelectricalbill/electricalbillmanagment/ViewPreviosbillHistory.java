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
import android.widget.ListView;
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

public class ViewPreviosbillHistory extends Fragment {
    View v;
    ListView lst;
    String uid;
    String reading[];
    String payment[];
    String date[];
    SharedPreferences shr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return v = inflater.inflate(R.layout.viewpreviousllybillhistory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lst = (ListView) v.findViewById(R.id.lst);
        shr = PreferenceManager.getDefaultSharedPreferences(getActivity());
        uid = shr.getString("userid", "");

        try {


            UserViewdata logs = new UserViewdata();
            DbParameter host = new DbParameter();
            String url = host.getHostpath();
            url = url + "PreviousBill.php?uid=" + URLEncoder.encode(uid) + "&";

            logs.execute(url);


        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    private class UserViewdata extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out = "";
        int count = 0;

        @Override
        protected String doInBackground(String... geturl) {


            try {
                //	String url= ;
                HttpClient http = new DefaultHttpClient();
                HttpPost http_get = new HttpPost(geturl[0]);
                HttpResponse response = http.execute(http_get);
                HttpEntity http_entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            } catch (Exception e) {

                out = e.toString();
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

            try {
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("info");
                int arraylength = jsonMainNode.length();
                reading = new String[arraylength];
                payment = new String[arraylength];
                date = new String[arraylength];



                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    reading[i] = jsonChildNode.optString("Rading");
                    payment[i] = jsonChildNode.optString("Payment");

                    date[i] = jsonChildNode.optString("date").substring(0,10);

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            } catch (Exception e) {
                Toast.makeText(getActivity(), " Not Available Please Try Again", Toast.LENGTH_SHORT).show();
            }

            try {

                LevelAdapter adapter = new LevelAdapter(getActivity(),payment,date);
                lst.setAdapter(adapter);


            } catch (Exception e) {
                Toast.makeText(getActivity(), "No records Found" + e, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
        }
    }
}