package com.example.irene.androidcourses;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        int id = menuItem.getItemId();

                        switch (id) {
                            case R.id.nav_manage: {
                                Context context = MainActivity.this;
                                Intent intent = new Intent(context, ProfileActivity.class);
                                context.startActivity(intent);
                                break;
                            }

                            case R.id.nav_exit: {
                                FirebaseAuth.getInstance().signOut();
                                mMenu.findItem(R.id.nav_exit).setVisible(false);
                                mMenu.findItem(R.id.login).setVisible(true);
                                break;
                            }

                            case R.id.login: {
                                Context context = MainActivity.this;
                                Intent intent = new Intent(context, AuthActivity.class);
                                context.startActivity(intent);
                                break;
                            }
                        }

                        return true;
                    }
                });
        mMenu = navigationView.getMenu();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mAuth.getCurrentUser() != null) {
            mMenu.findItem(R.id.login).setVisible(false);
            mMenu.findItem(R.id.nav_exit).setVisible(true);
        }
        else {
            mMenu.findItem(R.id.nav_exit).setVisible(false);
            mMenu.findItem(R.id.login).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
