package co.jack;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Dlog {
    static final String TAG = "!@#Jack";
    static boolean DEBUG = false;

    public static void init(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            DEBUG = ( 0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) );
        }
        catch (PackageManager.NameNotFoundException e) {
        }
    }

    public static boolean isDebuggable(Context context) {
        boolean debuggable = false;

        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            debuggable = ( 0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) );
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        return debuggable;
    }

    /**
     * Log Level Error
     **/
    public static final void e(String message) {
        if (DEBUG) Log.e(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Warning
     **/
    public static final void w(String message) {
        if (DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static final void i(String message) {
        if (DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static final void d(String message) {
        if (DEBUG) Log.d(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(String message) {
        if (DEBUG) Log.v(TAG, buildLogMsg(message));
    }


    public static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        //sb.append(ste.getFileName().replace(".java", ""));
        sb.append(ste.getFileName());
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append("(");
        sb.append(ste.getLineNumber());
        sb.append(")");
        sb.append(message);
        return sb.toString();
    }
}
