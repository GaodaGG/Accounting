package com.GG.Accounting.Animation;

import android.os.Build;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class CrashAnimation {
    public static void animation(ConstraintLayout bottomView) {
        //软键盘顶起
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
