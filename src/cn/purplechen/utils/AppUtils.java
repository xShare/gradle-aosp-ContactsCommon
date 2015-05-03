package cn.purplechen.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by chen on 15-5-3.
 */
public class AppUtils {

    private static Boolean sIsSystemApp = null;
    public static boolean isSystemApp() {
        if (sIsSystemApp == null) {
            return  false;
        }
        return sIsSystemApp;
    }

    public static boolean isSystemApp(Context context) {
        if (sIsSystemApp == null) {
            sIsSystemApp = (SYSTEM_REF_APP == checkAppType(context, context.getPackageName()));
        }
        return sIsSystemApp;
    }

    //软件类型判断软件
    //未知软件类型
    private static final int UNKNOW_APP = 0;
    //用户软件类型
    private static final int USER_APP = 1;
    //系统软件
    private static final int SYSTEM_APP = 2;
    //系统升级软件
    private static final int SYSTEM_UPDATE_APP = 4;
    //系统+升级软件
    private static final int SYSTEM_REF_APP = SYSTEM_APP | SYSTEM_UPDATE_APP;

    /**
     * 检查app是否是系统rom集成的
     *
     * @param pname
     * @return
     */
    private static int checkAppType(Context context, String pname) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(pname, 0);
            // 是系统软件或者是系统软件更新
            if (isSystemApp(pInfo) || isSystemUpdateApp(pInfo)) {
                return SYSTEM_REF_APP;
            } else {
                return USER_APP;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return UNKNOW_APP;
    }

    /**
     * 是否是系统软件或者是系统软件的更新软件
     *
     * @return
     */
    private static boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private static boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    private static boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }
}
