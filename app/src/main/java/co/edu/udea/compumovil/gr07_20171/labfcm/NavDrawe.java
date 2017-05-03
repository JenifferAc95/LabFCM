package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavDrawe extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    preferencesActivity preferences = new preferencesActivity();
    Fragment about = new About();
    AddEvent add = new AddEvent();
    FloatingActionButton fab;
    Fragment events = new EventList();
    FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
    private boolean controlSelect = false;

    private FirebaseAuth mFirebaseAuth;
    String mUsername;
    private String mPhotoUrl;
    private String mEmail;
    public static final String ANONYMOUS = "anonymous";
    private FirebaseUser mFirebaseUser;
    private static final String TAG = "NavDraw";
    private ImageView imageView;
    private TextView textViewName;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawe);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(AppInvite.API)
                .build();*/

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mEmail = mFirebaseUser.getEmail().toString();
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                Log.d(TAG, "Username, email " + mUsername + mEmail);

            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Log.d(TAG, "Username, email" + mUsername + mEmail);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        manager.commit();
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        manager = getSupportFragmentManager().beginTransaction();
        events = new EventList();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!controlSelect){moveTaskToBack(true);}
            else{
                manager.replace(R.id.fragment_container, events);
                manager.commit();
                controlSelect=false;
                fab.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            mFirebaseAuth.signOut();
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, Login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        manager = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.events) {
            events = new EventList();
            manager.replace(R.id.fragment_container, events);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.about) {
            about = new About();
            manager.replace(R.id.fragment_container, about);
            fab.setVisibility(View.INVISIBLE);
        } else if (id == R.id.settingsNav) {
            Intent preferences = new Intent(this, preferencesActivity .class);
            startActivity(preferences);
            return true;
        } else if (id == R.id.logout) {
            mFirebaseAuth.signOut();
            //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, Login.class));
            return true;
        } else if (id == R.id.nav_send) {

        }
        manager.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void DClic(View v){
        add.DateClic();
    }
    public void GClic(View v){
        add.ClickGalleryR();
    }

    public void AddEvent(View v){
        manager = getSupportFragmentManager().beginTransaction();
        add = new AddEvent();
        manager.replace(R.id.fragment_container, add);
        manager.commit();
        fab.setVisibility(View.INVISIBLE);
        controlSelect=true;
    }

    public void onClickEvent(View v){
        add.onClickEvent();
        manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.fragment_container, events);
        manager.commit();
        fab.setVisibility(View.VISIBLE);
    }
}
