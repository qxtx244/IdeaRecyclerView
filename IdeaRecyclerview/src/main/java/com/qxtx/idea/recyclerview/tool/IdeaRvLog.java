package com.qxtx.idea.recyclerview.tool;

import android.text.TextUtils;
import android.util.Log;

import com.qxtx.idea.recyclerview.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CreateDate 2020/4/19 13:41
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 快速日志打印类
 */
public class IdeaRvLog {
    
    public static String TAG = "IdeaRvLog";

    private static boolean logEnable = BuildConfig.DEBUG;

    private static String tag = TAG;

    @Retention(RetentionPolicy.SOURCE)
    private @interface LogType {
        String TYPE_D = "D";
        String TYPE_I = "I";
        String TYPE_W = "W";
        String TYPE_E = "E";
        String TYPE_WTF = "WTF";
    }

    private final static StringBuilder sb = new StringBuilder();
    
    public static void D( String... msgArray) {
        log("D", msgArray);
    }

    public static void I( String... msgArray) {
        log("I", msgArray);
    }

    public static void W( String... msgArray) {
        log("W", msgArray);
    }

    public static void E( String... msgArray) {
        log("E", msgArray);
    }

    public static void WTF( String... msgArray) {
        log("WTF", msgArray);
    }

    private static void log(@LogType String type, String... msgArray) {
        if (!logEnable && !type.equals(LogType.TYPE_E) && !type.equals(LogType.TYPE_WTF)) {
            return ;
        }

        sb.delete(0, sb.length());
        for (String s : msgArray) {
            sb.append(s);
        }

        String msg = sb.toString();
        if (TextUtils.isEmpty(msg)) {
            return ;
        }

        String flag = tag;
        switch (type.toUpperCase()) {
            case "I":
                Log.i(flag, msg);
                break;
            case "W":
                Log.w(flag, msg);
                break;
            case "E":
                Log.e(flag, msg);
                break;
            case "WTF":
                Log.wtf(flag, msg);
                break;
            default:
                Log.d(flag, msg);
                break;
        }

    }
}
