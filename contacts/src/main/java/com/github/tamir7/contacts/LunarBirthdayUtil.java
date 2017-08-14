package com.github.tamir7.contacts;

import android.os.Build;

/**
 * Created by Senh Linsh on 17/7/24.
 */

public class LunarBirthdayUtil {

    public static final String MIME_TYPE;
    public static final String COLUMN;

    static {
        switch (Build.MANUFACTURER.toLowerCase()) {
            case Manufacturer.XIAOMI:
                MIME_TYPE = ROM.XiaoMi.MIME_TYPE;
                COLUMN = ROM.XiaoMi.COLUMN;
                break;
            case Manufacturer.MEIZU:
            default:
                MIME_TYPE = null;
                COLUMN = null;
                break;
        }
    }

    public static String getLunarBirthday(CursorHelper helper) {
        return helper.getString(COLUMN);
    }

    public static Event checkEvent(Event event) {
        if (event.getType() == Event.Type.UNKNOWN) {
            if (Build.MANUFACTURER.toLowerCase().equals(Manufacturer.MEIZU)) {
                return new Event(event.getStartDate(), Event.Type.LUNAR_BIRTHDAY);
            }
        }
        return event;
    }

    public static final class ROM {

        public static class XiaoMi {

            public static final String MIME_TYPE = "vnd.com.miui.cursor.item/lunarBirthday";
            public static final String COLUMN = "data1";
        }

    }

    private interface Manufacturer {
        String HUAWEI = "huawei";    // 华为
        String MEIZU = "meizu";      // 魅族
        String XIAOMI = "xiaomi";    // 小米
        String SONY = "sony";        // 索尼
        String SAMSUNG = "samsung";  // 三星
        String LETV = "letv";        // 乐视
        String ZTE = "zte";          // 中兴
        String YULONG = "yulong";    // 酷派
        String LENOVO = "lenovo";    // 联想
        String LG = "lg";            // LG
        String OPPO = "oppo";        // oppo
        String VIVO = "vivo";        // vivo
    }
}
