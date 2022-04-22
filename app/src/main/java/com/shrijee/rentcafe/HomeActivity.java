package com.shrijee.rentcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    //widget
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView mNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting the sharedPref
        sharedPreferences = getSharedPreferences(MainActivity.sharedPrefrence,MODE_PRIVATE);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_layout);

        //getting the userName and based on that getting if he is a renter or not
        String userName = sharedPreferences.getString("email","");

        Boolean enableRentee = sharedPreferences.getBoolean("EnableAddingRentProperty_"+userName,false);
//        and based on that showing the menu group for adding property other wise not
        mNavigationView.getMenu().setGroupVisible(R.id.menu_grp_rentee,enableRentee);

        setInitialFragment();
        //synchronizing the toggle menu
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //for showing the hamburger and back arrow button in navigation bar for opening and closing the drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //if defferent menu option gets seleted then based on that fragment is initialized to according value
                switch (item.getItemId())
                {
                    case R.id.find_rent:
                        fragment = new RentalFragment();
                        break;
                    case R.id.my_rent:
                        fragment = new MyRentFragment();
                        break;
                    case R.id.add_property:
                        fragment = new AddRentFragment();
                        break;
                    case R.id.view_property:
                        fragment = new MyPropertyFragment();
                        break;
                    case R.id.logout:
                        //if logout is pressed then removing the email from shared pref and taking user to login screen
                        sharedPreferences.edit().putString("email","").commit();
                        Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(mainActivity);
                        break;
                    case R.id.account:
                        fragment = new AccountFragment();
                        break;
                    default:
                        break;
                }
                if(fragment!=null)
                {
                    //initialize the transaction
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    //replacing the frame layout to seleted option fragment's layout
                    fragmentTransaction.replace(R.id.frm_frame_layout,fragment);
                    //committing the
                    fragmentTransaction.commit();
                    //closing the drawer
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    //initially starting the RentalFragment fragment
    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_frame_layout,new RentalFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if item is seleted then return true else call the super method
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}