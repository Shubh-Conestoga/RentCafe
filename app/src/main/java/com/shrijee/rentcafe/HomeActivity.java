package com.shrijee.rentcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView mNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(MainActivity.sharedPrefrence,MODE_PRIVATE);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_layout);

        String userName = sharedPreferences.getString("email","");
        Boolean enableRentee = sharedPreferences.getBoolean("EnableAddingRentProperty_"+userName,false);
        mNavigationView.getMenu().setGroupVisible(R.id.menu_grp_rentee,enableRentee);

        setInitialFragment();
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //for showing the hamburger and back arrow button in navigation bar for opening and closing the drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                        break;
                    case R.id.logout:
                        sharedPreferences.edit().clear().commit();
                        onBackPressed();
                        break;
                    case R.id.account:
                        fragment = new AccountFragment();
                        break;
                    default:
                        break;
                }
                if(fragment!=null)
                {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frm_frame_layout,fragment);
                    fragmentTransaction.commit();
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

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