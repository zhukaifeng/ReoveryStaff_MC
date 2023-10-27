package com.dclee.recovery.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LogUtils {

    //注册图所在的目录
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "rec_staff";
    public static final String PATH_LOG = ROOT_DIR + File.separator + "Log";

    /**
     * 写日志
     *
     * @param str 日志内容
     */
    @SuppressWarnings("static-access")
    public static void write(String str) {
        Calendar c = GregorianCalendar.getInstance();
        String path = PATH_LOG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();

        }
        String filename = path + File.separator + c.get(c.YEAR)
                + fillZero(1 + c.get(c.MONTH) + "", 2) + fillZero("" + c.get(c.DAY_OF_MONTH), 2) + ".txt";
        try {
            BufferedWriter bufOut;
            File f = new File(filename);
            if (f.exists()) {
                bufOut = new BufferedWriter(new FileWriter(f, true));
            } else {
                bufOut = new BufferedWriter(new FileWriter(f));
            }
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
            bufOut.write("\r\n" + "<<<" + datetime + ">>>");
            bufOut.write(str + "\r\n");
            bufOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 右对齐左补零
     *
     * @param str 字串
     * @param len 长度
     * @return
     */
    private static String fillZero(String str, int len) {
        int tmp = str.length();
        int t;
        String str1 = str;
        if (tmp >= len)
            return str1;
        t = len - tmp;
        for (int i = 0; i < t; i++)
            str1 = "0" + str1;
        return str1;
    }
}
