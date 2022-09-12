package cn.wbnull.hellotask.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

import cn.wbnull.hellotask.App;

/**
 * SharedPreferences 工具类
 *
 * @author dukunbiao(null)  2022-09-11
 */
public class SpUtils {

    private static volatile SharedPreferences sharedPreferences;
    private static volatile SharedPreferences.Editor editor;

    static {
        String preferencesName = String.format(Locale.CHINESE, "%s_preferences",
                App.getContext().getApplication().getPackageName());
        sharedPreferences = App.getInstance().getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static String getClockIn() {
        return sharedPreferences.getString("clockIn", "");
    }

    public static void setClockIn(String orgCode) {
        editor.putString("clockIn", orgCode);

        editor.commit();
    }

    public static String getClockOut() {
        return sharedPreferences.getString("clockOut", "");
    }

    public static void setClockOut(String shopCode) {
        editor.putString("clockOut", shopCode);

        editor.commit();
    }
}
