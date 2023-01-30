package com.shahin.techbrandeshop.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mFrameLayout = findViewById(R.id.fragment_container);
        setFragment(new LoginFragment(mFrameLayout));
    }

    private void setFragment(Fragment loginActivity) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(mFrameLayout.getId(), loginActivity).commit();
    }
}