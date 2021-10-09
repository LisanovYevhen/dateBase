package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.database.R;
import com.google.android.material.tabs.TabLayout;

import fragment.FragmentAdapter;

public class MainWorkActivity extends AppCompatActivity {
    private TabLayout layout;
    private ViewPager2 pager2;
    private FragmentAdapter adapter;
    //У методов onCreate(), onStart(), onResume() вызов суперкласса должен происходить до вызова вашего кода.
    //В методах onPause(), onStop(), onDestroy() суперкласс следует вызывать после вашего кода

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(SignupActivity.LOG,"onCreate Activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_working_activity);
        //getLastNonConfigurationInstance();


        layout=findViewById(R.id.tab_layout);
        pager2=findViewById(R.id.view_pager2);
        FragmentManager manager=getSupportFragmentManager();

        adapter= new FragmentAdapter(manager,getLifecycle());
        pager2.setAdapter(adapter);
        layout.addTab(layout.newTab().setText("Ввод данных"));
        layout.addTab(layout.newTab().setText("Результаты работы"));
        layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
                //Log.d(SignupActivity.LOG,"Select"+ String.valueOf(layout.getSelectedTabPosition()));


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                layout.selectTab(layout.getTabAt(position));
                //Log.d(SignupActivity.LOG,"callBack"+ String.valueOf(layout.getSelectedTabPosition()));
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(SignupActivity.LOG,"onRestart Activity");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(SignupActivity.LOG,"onStart Activity");
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(SignupActivity.LOG,"onRestoreInstanceState Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(SignupActivity.LOG,"onResume Activity");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(SignupActivity.LOG,"onPause Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(SignupActivity.LOG,"onStop Activity");
    }

    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(SignupActivity.LOG,"onSaveInstanceState Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(SignupActivity.LOG,"onDestroy Activity");
    }


}