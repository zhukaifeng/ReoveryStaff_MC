package com.dclee.recovery.view.create_order;

import java.util.ArrayList;
import java.util.List;

public class ReceiveParam {
    private int order_id;
    private String user_id;
    private List<MkOrderProd> product = new ArrayList<>();
    private List<String> images = new ArrayList<>();

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public List<MkOrderProd> getProduct() {
        return product;
    }

    public void setProducts(List<MkOrderProd> product) {
        this.product = product;
    }

    public void setProduct(MkOrderProd productIten) {
        if (product == null) {
            product = new ArrayList<>();
        }
        this.product.add(productIten);
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static class MkOrderProd {
        private int product_id;
        private String product_name;
        private double price;
        private double count;

        public MkOrderProd() {
        }

        public MkOrderProd(int product_id, String product_name, double price, double count) {
            this.product_id = product_id;
            this.product_name = product_name;
            this.price = price;
            this.count = count;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        @Override
        public String toString() {
            return "MkOrderProd{" +
                    "product_id=" + product_id +
                    ", product_name='" + product_name + '\'' +
                    ", price=" + price +
                    ", count=" + count +
                    '}';
        }
    }
}
