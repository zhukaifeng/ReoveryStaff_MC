package com.dclee.recovery.base;

public class Config {
    public static final int BLUETEEN_SCAN = 0;
    public static final int BLUETEEN_SCAN_SUC = 1;
    public static final int BLUETEEN_SCAN_FAILED = 2;

    public static final int BLUETEEN_OPERA_START_SCAN = 0;
    public static final int BLUETEEN_OPERA_STOP_SCAN = 1;

    public static final String SERIALNO = "{6ADF7D6F-E641-B6B1-0D5E-4A61F279FCB1}";

    public static String userId = "";
    public static String userName = "";

    public static String UM_TOKEN = "UM_TOKEN";

    public static final int STATE_START = 0;
    public static final int STATE_UNRECEIVE = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_USER_CANCEL = 3;
    public static final int STATE_FINISH = 4;
    public static final int STATE_CHECK = 5;
    public static final int STATE_CANCEL = 6;
    public static final int STATE_UNGETGOODS = 7;
}
