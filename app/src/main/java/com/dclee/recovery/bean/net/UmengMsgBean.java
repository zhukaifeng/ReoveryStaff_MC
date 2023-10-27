package com.dclee.recovery.bean.net;

public class UmengMsgBean {

    /**
     * display_type : notification
     * body : {"after_open":"go_app","ticker":"asd","title":"asd","play_sound":"true","text":"ad"}
     * msg_id : uu6nj96162920427319811
     */

    public String display_type;
    public BodyBean body;
    public String msg_id;

    public static class BodyBean {
        /**
         * after_open : go_app
         * ticker : asd
         * title : asd
         * play_sound : true
         * text : ad
         */

        public String after_open;
        public String ticker;
        public String title;
        public String play_sound;
        public String text;
    }
}
