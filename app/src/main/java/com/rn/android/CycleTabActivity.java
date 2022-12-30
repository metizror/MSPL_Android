package com.rn.android;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metiz.pelconnect.R;
import com.rn.android.fragment.CensusFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CycleTabActivity extends BaseActivity {

    String cycleStartDate = "";
    @BindView(R.id.tv_tab_census)
    TextView tvTabCensus;
    @BindView(R.id.tv_tab_need_refill)
    TextView tvTabNeedRefill;
    @BindView(R.id.tv_tab_will_be_refill)
    TextView tvTabWillBeRefill;
    @BindView(R.id.tab_fragment)
    FrameLayout tabFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int HeaderId =0;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    int pendingDays=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_tab);
        ButterKnife.bind(this);
        setTitle("Patients");
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent().hasExtra("cycleStartDate")){
            cycleStartDate = getIntent().getStringExtra("cycleStartDate");

        }
        if (getIntent().hasExtra("HeaderId")){
            HeaderId = getIntent().getIntExtra("HeaderId" ,0);

        } if (getIntent().hasExtra("pendingDays")){
            pendingDays = getIntent().getIntExtra("pendingDays" ,0);

        }

        Log.e("cycleStartDate=====>", cycleStartDate);
        replaceFragment(new CensusFragment(cycleStartDate,HeaderId,pendingDays));

        tvTabCensus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CensusFragment(cycleStartDate,HeaderId,pendingDays));

                tvTabCensus.setBackground(getResources().getDrawable(R.drawable.squre_tab_left));
                tvTabCensus.setTextColor(Color.WHITE);

                tvTabNeedRefill.setBackground(getResources().getDrawable(R.drawable.middle_white_radius));
                tvTabNeedRefill.setTextColor(Color.BLACK);
                tvTabWillBeRefill.setTextColor(Color.BLACK);
                tvTabWillBeRefill.setBackground(getResources().getDrawable(R.drawable.right_border_white));


            }
        });
        tvTabNeedRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new NeedrefillFragment(cycleStartDate));
                tvTabNeedRefill.setBackground(getResources().getDrawable(R.drawable.squre_tab_no_radius));
                tvTabNeedRefill.setTextColor(Color.WHITE);

                tvTabCensus.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
                tvTabCensus.setTextColor(Color.BLACK);
                tvTabWillBeRefill.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                tvTabWillBeRefill.setTextColor(Color.BLACK);
            }
        });
        tvTabWillBeRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new WillBeRefillFragment(cycleStartDate));
                tvTabWillBeRefill.setBackground(getResources().getDrawable(R.drawable.squre_tab_right));
                tvTabWillBeRefill.setTextColor(Color.WHITE);

                tvTabCensus.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
                tvTabCensus.setTextColor(Color.BLACK);
                tvTabNeedRefill.setBackground(getResources().getDrawable(R.drawable.middle_white_radius));
                tvTabNeedRefill.setTextColor(Color.BLACK);

            }
        });


    }

    public void replaceFragment(Fragment fr) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tab_fragment, fr);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
