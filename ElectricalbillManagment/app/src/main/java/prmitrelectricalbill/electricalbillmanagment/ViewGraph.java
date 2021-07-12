package prmitrelectricalbill.electricalbillmanagment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by ibase on 03/04/2019.
 */

public class ViewGraph extends Activity {
WebView w1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewgraph);
        SharedPreferences shr= PreferenceManager.getDefaultSharedPreferences(ViewGraph.this);
        String uid=shr.getString("userid","na");
        Toast.makeText(ViewGraph.this, ""+uid, Toast.LENGTH_SHORT).show();
        w1=(WebView)findViewById(R.id.web11);
        String url="http://mahavidyalay.in/AcademicDevelopment/Electricitybillgenration/Graphs.php?uid="+uid.trim();
       // Toast.makeText(ViewGraph.this, ""+url, Toast.LENGTH_SHORT).show();
       // w1.loadUrl(url);

        Intent i1= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i1);
    }
}
