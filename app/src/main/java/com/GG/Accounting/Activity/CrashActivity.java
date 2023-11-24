package com.GG.Accounting.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.GG.Accounting.R;
import com.gyf.immersionbar.ImmersionBar;

import net.center.blurview.ShapeBlurView;
import net.center.blurview.enu.BlurMode;

import java.util.List;

import cn.qqtheme.framework.tool.CrashHelper;

public class CrashActivity extends Activity {
    float a = 0;
    float b = 0;

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
        toolbar.setTitle(R.string.crashTitle);


        TextView textView = findViewById(R.id.crash_textview);
        textView.setText(String.format("%s %s %s", stackTrace, getString(R.string.devices), deviceInfo));

        //底栏模糊
        ShapeBlurView blurView = findViewById(R.id.crash_blurview);
        blurView.refreshView(
                ShapeBlurView.build(this).setBlurMode(BlurMode.MODE_RECTANGLE)
                        .setOverlayColor(Color.valueOf(255, 255, 255, 60).toArgb())
                        .setCornerRadius(25f)
//                        .setCornerRadius(BlurCorner.TOP_LEFT, 50f)
        );


        CardView showIME = findViewById(R.id.crash_share);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            showIME.setOnClickListener((view) -> view.getWindowInsetsController().show(WindowInsets.Type.ime()));
        }

        animation();

    }

    private void animation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ConstraintLayout bottomView = findViewById(R.id.crash_bottomview);
            bottomView.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(WindowInsetsAnimation.Callback.DISPATCH_MODE_STOP) {
                @NonNull
                @Override
                public WindowInsets onProgress(@NonNull WindowInsets insets, @NonNull List<WindowInsetsAnimation> runningAnimations) {
                    bottomView.setTranslationY(-insets.getInsets(WindowInsets.Type.ime()).bottom);
                    return insets;
                }
            });
        }
    }
}
