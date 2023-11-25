package com.GG.Accounting.Activity;

import static com.gyf.immersionbar.ImmersionBar.getNavigationBarHeight;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.GG.Accounting.Animation.CrashAnimation;
import com.GG.Accounting.R;
import com.gyf.immersionbar.ImmersionBar;

import net.center.blurview.ShapeBlurView;
import net.center.blurview.enu.BlurMode;

import java.util.Objects;

import cn.qqtheme.framework.tool.CrashHelper;

public class CrashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        //状态栏相关
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .transparentNavigationBar()
                .titleBar(R.id.crash_toolbar)
                .init();

        //获取错误信息
        String stackTrace = CrashHelper.getStackTraceFromIntent(getIntent());
        String deviceInfo = CrashHelper.getDeviceInfo();

        //应用栏
        Toolbar toolbar = findViewById(R.id.crash_toolbar);
        toolbar.setTitle(R.string.crash_titleText);

        //报错文本
        TextView textView = findViewById(R.id.crash_textview);
        textView.setText(String.format("%s %s %s", stackTrace, getString(R.string.crash_devices), deviceInfo));

        //增加底栏高度
        ShapeBlurView blurView = findViewById(R.id.crash_blurview);
        ConstraintLayout bottomView = findViewById(R.id.crash_bottomview);

        int navigationBarHeight = getNavigationBarHeight(this);
        ViewGroup.LayoutParams blurViewLayoutParams = blurView.getLayoutParams();
        blurViewLayoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics())) + navigationBarHeight;
        blurView.setLayoutParams(blurViewLayoutParams);

        //底栏模糊
        blurView.refreshView(
                ShapeBlurView.build(this).setBlurMode(BlurMode.MODE_RECTANGLE)
                        .setOverlayColor(Color.parseColor("#FFFFFF"))
        );


        //保存报错
        CardView saveErrorButton = findViewById(R.id.crash_save);
        saveErrorButton.setOnClickListener((view) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Objects.requireNonNull(view.getWindowInsetsController()).show(WindowInsets.Type.ime());
            }
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, textView.getText()));
        });

        //重启软件
        CardView restartButton = findViewById(R.id.crash_restartApp);
        restartButton.setOnClickListener((view) -> {
            final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            assert intent != null;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        //底栏动画
        CrashAnimation.animation(bottomView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重设底栏位置
        ConstraintLayout bottomView = findViewById(R.id.crash_bottomview);
        bottomView.setTranslationY(0);
    }
}
