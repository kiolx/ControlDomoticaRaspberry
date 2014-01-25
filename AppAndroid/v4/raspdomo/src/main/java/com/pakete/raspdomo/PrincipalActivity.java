package com.pakete.raspdomo;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.view.View;

public class PrincipalActivity extends FragmentActivity implements ActionBar.TabListener {

    CollectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;
    public Context ctx;
    public int valorMenu;
    public View inflatedV;
    public String dataBaseName = "";
    public Fragment fragment;
    public String ActivityName = "PrincipalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);
        ctx = this;
        dataBaseName = ctx.getResources().getString(R.string.dataBaseName);

        //TO LO SIGUIENTE
        mCollectionPagerAdapter = new CollectionPagerAdapter(getFragmentManager());

        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                valorMenu = position;
                invalidateOptionsMenu();
            }

        });
        for (int i = 0; i < mCollectionPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mCollectionPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(valorMenu == 3){
            getMenuInflater().inflate(R.menu.opciones, menu);
        }else{
            getMenuInflater().inflate(R.menu.principal, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       int id = item.getItemId();
       if (id == R.id.action_about) {
         new Funciones(ctx,"",false,"").AcercaDe();
        //    return true;
        }else if(id == R.id.action_save){
           View fragmentV = fragment.getView();
           new Funciones(ctx,"",false, ActivityName).OpcionesBD(fragmentV, "guardarOpcionesBD", ctx, dataBaseName);
       }
        return super.onOptionsItemSelected(item);
    }


    ////////TOOOOO LO NUEVO/////////////////////////

    public void onTabUnselected(ActionBar.Tab tab,  FragmentTransaction fragmentTransaction) {  }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {  }

    public class CollectionPagerAdapter extends FragmentPagerAdapter {

        final int NUM_ITEMS = 4; // number of tabs

        public CollectionPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if((i != 3) && (i != 0)){
            fragment = new PaginasTabs.TabFragment();
            Bundle args = new Bundle();
            args.putInt(PaginasTabs.TabFragment.ARG_OBJECT, i);
            fragment.setArguments(args);
            }

            if(i == 0){
                fragment = new PaginasTabs.TabFragment0(ctx, dataBaseName, ActivityName);
                Bundle args = new Bundle();
                args.putInt(PaginasTabs.TabFragment0.ARG_OBJECT, i);
                fragment.setArguments(args);
            }
            if(i == 3){
                fragment = new PaginasTabs.TabFragment3(ctx, dataBaseName, ActivityName);
                Bundle args = new Bundle();
                args.putInt(PaginasTabs.TabFragment3.ARG_OBJECT, i);
                fragment.setArguments(args);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabLabel = null;
            switch (position) {
                case 0:
                    tabLabel = getString(R.string.title_section1);
                    break;
                case 1:
                    tabLabel = getString(R.string.title_section2);
                    break;
                case 2:
                    tabLabel = getString(R.string.title_section3);
                    break;
                case 3:
                    tabLabel = getString(R.string.title_section4);
                    break;

            }

            return tabLabel;

        }
    }




}
