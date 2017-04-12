package com.greip.core.util;

/**
 * Created by ronaldchang on 14/11/16.
 */
public class UtlGreip {
    public static Boolean CONSOLE_STATUS = Boolean.TRUE;
    public static String CONSOLE_LABEL = "[GREIP CORE] ";


    public static void console(Object obj) {
        if (CONSOLE_STATUS)
            System.out.println(CONSOLE_LABEL + obj);
    }
}
