package com.GG.Accounting.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.GG.Accounting.R;
import com.gyf.immersionbar.ImmersionBar;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //状态栏相关
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .transparentNavigationBar()
                .init();

        Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
    }
}
