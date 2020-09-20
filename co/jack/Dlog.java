package co.jack;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Method;

public class Dlog {
    static final String TAG = "!@#Jack";

    final static int DEBUG_DISABLE_UNCHECKED = -1;
    final static int DEBUG_DISABLE_CHECKED = 0;
    final static int DEBUG_ENABLE = 1;
    static int DEBUG = DEBUG_DISABLE_UNCHECKED;

    static boolean TRACE = true;

    public static int  isDebuggable() {
        if(DEBUG == DEBUG_DISABLE_UNCHECKED) {
            DEBUG = DEBUG_DISABLE_CHECKED;

            if (SystemProperties.get("debug.enable.jack").equals("1") ||
                SystemProperties.get("debug.enable.jack").equals("true"))
            {
                DEBUG = DEBUG_ENABLE;
            }
        }
        return DEBUG;
    }

    public static void init(Context context) {
        if(DEBUG == DEBUG_ENABLE) return;

        DEBUG = DEBUG_DISABLE_CHECKED;

        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            DEBUG = ( 0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) )?DEBUG_ENABLE:DEBUG_DISABLE_CHECKED;
        }
        catch (PackageManager.NameNotFoundException e) {
        }
    }

    /**
     * Log Level Error
     **/
    public static final void e(String message) {
        if (isDebuggable() == DEBUG_ENABLE) Log.e(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Warning
     **/
    public static final void w(String message) {
        if (isDebuggable() == DEBUG_ENABLE) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static final void i(String message) {
        if (isDebuggable() == DEBUG_ENABLE) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static final void d(String message) {
        if (isDebuggable() == DEBUG_ENABLE) Log.d(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(String message) {
        if (isDebuggable() == DEBUG_ENABLE) Log.v(TAG, buildLogMsg(message));
    }

    /**
     * Trace Log Msg
     **/
    public static final void t() {
        Log.d("dafdadaf", "!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if(TRACE) Log.i(TAG, buildLogMsg(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
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

class SystemProperties {

    public static String get(String key) {
        String ret = "";
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");

            //Parameters Types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = {String.class};
            Method get = SystemProperties.getMethod("get", paramTypes);

            //Parameters
            Object[] params = {key};
            ret = (String) get.invoke(SystemProperties, params);
        } catch (IllegalArgumentException e) {
            ret = "";
            e.printStackTrace();
            Log.e("!@#Jack", "IllegalArgumentException e: " + e.toString());
        } catch (Exception e) {
            ret = "";
            e.printStackTrace();
            Log.e("!@#Jack", "Exception e: " + e.toString());
        }
        return ret;
    }

    public static String set(String key) {
        String ret = "";
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");

            //Parameters Types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = {String.class};
            Method set = SystemProperties.getMethod("set", paramTypes);

            //Parameters
            Object[] params = {key};
            set.invoke(SystemProperties, params);
        } catch (IllegalArgumentException e) {
            ret = "";
            e.printStackTrace();
            Log.e("!@#Jack", "IllegalArgumentException e: " + e.toString());
        } catch (Exception e) {
            ret = "";
            e.printStackTrace();
            Log.e("!@#Jack", "Exception e: " + e.toString());
        }
        return ret;
    }
}
