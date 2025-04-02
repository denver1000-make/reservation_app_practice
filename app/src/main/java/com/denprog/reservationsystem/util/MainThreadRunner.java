package com.denprog.reservationsystem.util;

import android.os.Handler;
import android.os.Looper;

public class MainThreadRunner {
    public static Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnMain(Runnable runnable) {
        handler.post(runnable);
    }

}
