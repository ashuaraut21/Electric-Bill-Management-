package prmitrelectricalbill.electricalbillmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView nav;
SharedPreferences shr;
String cono,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

shr= PreferenceManager.getDefaultSharedPreferences(this);
cono=shr.getString("name","");
name=shr.getString("cono","");
//nav.setText("sk");
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav);
        TextView navUsername1 = (TextView) headerView.findViewById(R.id.nav1);
        navUsername.setText(name);
        navUsername1.setText(cono);
        navigationView.setNavigationItemSelectedListener(this);
        displayselecteditem(R.id.nav_billgenrate);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


private void displayselecteditem(int id) {
    Fragment fragment = null;
    switch (id) {
        case R.id.nav_billgenrate:
            fragment = new GetCurrentTime();
            break;
        case R.id.nav_paybill:
            fragment = new Billpay();
            break;
        case R.id.nav_previoushis:
            fragment = new ViewPreviosbillHistory();
            break;
        case R.id.nav_currentbill:
            fragment = new CurrentBill();
            break;

        case R.id.nav_readinghistory:
            fragment = new ReadingHistory();
            break;
        case R.id.livedetection:
            Intent i11=new Intent(Dashboard.this,LiveGenerator.class);
            startActivity(i11);
            break;
        case R.id.nav_logout:
            Intent i1=new Intent(Dashboard.this,Login.class);
            startActivity(i1);
            break;
    }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment != null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);

            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displayselecteditem(id);


        return true;
    }
}
