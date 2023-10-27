package com.dclee.recovery.pojo;

import java.util.List;

public class CheckOrderBean {

    /**
     * staff_name : 梅超风
     * staff_id : 111
     * product_info : [{"img":"https://gufeitongg.oss-cn-hangzhou.aliyuncs.com/images/2021/06/06/16229929470156016.jpg","predict_weight":0,"product_id":3,"verify_weight":10,"verify_price":990,"product_name":"废品C","predict_price":0}]
     * id : 10
     * verify_id : 1
     * verify_name : osiris
     * verify_date : 2021-06-06 23:22:31
     * create_at : 2021-06-06T23:22:31.000+0800
     * serial_no : {6ADF7D6F-E641-B6B1-0D5E-4A61F279FCB1}
     */

    private String staff_name;
    private Integer staff_id;
    private List<ProductInfoBean> product_info;
    private Integer id;
    private Integer verify_id;
    private String verify_name;
    private String verify_date;
    private String create_at;
    private String serial_no;

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public List<ProductInfoBean> getProduct_info() {
        return product_info;
    }

    public void setProduct_info(List<ProductInfoBean> product_info) {
        this.product_info = product_info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVerify_id() {
        return verify_id;
    }

    public void setVerify_id(Integer verify_id) {
        this.verify_id = verify_id;
    }

    public String getVerify_name() {
        return verify_name;
    }

    public void setVerify_name(String verify_name) {
        this.verify_name = verify_name;
    }

    public String getVerify_date() {
        return verify_date;
    }

    public void setVerify_date(String verify_date) {
        this.verify_date = verify_date;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public static class ProductInfoBean {
        /**
         * img : https://gufeitongg.oss-cn-hangzhou.aliyuncs.com/images/2021/06/06/16229929470156016.jpg
         * predict_weight : 0.0
         * product_id : 3
         * verify_weight : 10.0
         * verify_price : 990.0
         * product_name : 废品C
         * predict_price : 0.0
         */

        private String img;
        private Double predict_weight;
        private Integer product_id;
        private Double verify_weight;
        private Double verify_price;
        private String product_name;
        private Double predict_price;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Double getPredict_weight() {
            return predict_weight;
        }

        public void setPredict_weight(Double predict_weight) {
            this.predict_weight = predict_weight;
        }

        public Integer getProduct_id() {
            return product_id;
        }

        public void setProduct_id(Integer product_id) {
            this.product_id = product_id;
        }

        public Double getVerify_weight() {
            return verify_weight;
        }

        public void setVerify_weight(Double verify_weight) {
            this.verify_weight = verify_weight;
        }

        public Double getVerify_price() {
            return verify_price;
        }

        public void setVerify_price(Double verify_price) {
            this.verify_price = verify_price;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public Double getPredict_price() {
            return predict_price;
        }

        public void setPredict_price(Double predict_price) {
            this.predict_price = predict_price;
        }
    }
}
