package com.dclee.recovery.pojo;

public class PositionDetail {

    /**
     * status : 0
     * message : query ok
     * request_id : 4943435a-1327-11ea-ab52-7c1e0621f2f5
     * result : {"location":{"lat":22.983357,"lng":113.900027},"address":"广东省东莞市兴山路29号","formatted_addresses":{"recommend":"凫山隆兴玩具礼品厂(兴山路北)","rough":"凫山隆兴玩具礼品厂(兴山路北)"},"address_component":{"nation":"中国","province":"广东省","city":"东莞市","district":"","street":"兴山路","street_number":"兴山路29号"},"ad_info":{"nation_code":"156","adcode":"441900","city_code":"156441900","name":"中国,广东省,东莞市,","location":{"lat":22.66667,"lng":114.184822},"nation":"中国","province":"广东省","city":"东莞市","district":""},"address_reference":{"business_area":{"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"},"famous_area":{"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"},"crossroad":{"id":"1439131","title":"兴山路/石新街(路口)","location":{"lat":22.98357,"lng":113.899391},"_distance":63.6,"_dir_desc":"东"},"town":{"id":"441900111","title":"寮步镇","location":{"lat":23.023001,"lng":113.801582},"_distance":0,"_dir_desc":"内"},"street_number":{"id":"9030566412838994863","title":"兴山路29号","location":{"lat":22.983419,"lng":113.899971},"_distance":13,"_dir_desc":""},"street":{"id":"11044807694381527649","title":"兴山路","location":{"lat":22.97891,"lng":113.90593},"_distance":15.4,"_dir_desc":"北"},"landmark_l1":{"id":"10485605785919601066","title":"凫山中心商业广场","location":{"lat":22.984245,"lng":113.901703},"_distance":159.1,"_dir_desc":"西南"},"landmark_l2":{"id":"9030566412838994863","title":"隆兴玩具礼品厂(兴山路)","location":{"lat":22.983347,"lng":113.899872},"_distance":0,"_dir_desc":"内"}}}
     */

    private int status;
    private String message;
    private String request_id;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"lat":22.983357,"lng":113.900027}
         * address : 广东省东莞市兴山路29号
         * formatted_addresses : {"recommend":"凫山隆兴玩具礼品厂(兴山路北)","rough":"凫山隆兴玩具礼品厂(兴山路北)"}
         * address_component : {"nation":"中国","province":"广东省","city":"东莞市","district":"","street":"兴山路","street_number":"兴山路29号"}
         * ad_info : {"nation_code":"156","adcode":"441900","city_code":"156441900","name":"中国,广东省,东莞市,","location":{"lat":22.66667,"lng":114.184822},"nation":"中国","province":"广东省","city":"东莞市","district":""}
         * address_reference : {"business_area":{"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"},"famous_area":{"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"},"crossroad":{"id":"1439131","title":"兴山路/石新街(路口)","location":{"lat":22.98357,"lng":113.899391},"_distance":63.6,"_dir_desc":"东"},"town":{"id":"441900111","title":"寮步镇","location":{"lat":23.023001,"lng":113.801582},"_distance":0,"_dir_desc":"内"},"street_number":{"id":"9030566412838994863","title":"兴山路29号","location":{"lat":22.983419,"lng":113.899971},"_distance":13,"_dir_desc":""},"street":{"id":"11044807694381527649","title":"兴山路","location":{"lat":22.97891,"lng":113.90593},"_distance":15.4,"_dir_desc":"北"},"landmark_l1":{"id":"10485605785919601066","title":"凫山中心商业广场","location":{"lat":22.984245,"lng":113.901703},"_distance":159.1,"_dir_desc":"西南"},"landmark_l2":{"id":"9030566412838994863","title":"隆兴玩具礼品厂(兴山路)","location":{"lat":22.983347,"lng":113.899872},"_distance":0,"_dir_desc":"内"}}
         */

        private LocationBean location;
        private String address;
        private FormattedAddressesBean formatted_addresses;
        private AddressComponentBean address_component;
        private AdInfoBean ad_info;
        private AddressReferenceBean address_reference;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public FormattedAddressesBean getFormatted_addresses() {
            return formatted_addresses;
        }

        public void setFormatted_addresses(FormattedAddressesBean formatted_addresses) {
            this.formatted_addresses = formatted_addresses;
        }

        public AddressComponentBean getAddress_component() {
            return address_component;
        }

        public void setAddress_component(AddressComponentBean address_component) {
            this.address_component = address_component;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }

        public AddressReferenceBean getAddress_reference() {
            return address_reference;
        }

        public void setAddress_reference(AddressReferenceBean address_reference) {
            this.address_reference = address_reference;
        }

        public static class LocationBean {
            /**
             * lat : 22.983357
             * lng : 113.900027
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class FormattedAddressesBean {
            /**
             * recommend : 凫山隆兴玩具礼品厂(兴山路北)
             * rough : 凫山隆兴玩具礼品厂(兴山路北)
             */

            private String recommend;
            private String rough;

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getRough() {
                return rough;
            }

            public void setRough(String rough) {
                this.rough = rough;
            }
        }

        public static class AddressComponentBean {
            /**
             * nation : 中国
             * province : 广东省
             * city : 东莞市
             * district :
             * street : 兴山路
             * street_number : 兴山路29号
             */

            private String nation;
            private String province;
            private String city;
            private String district;
            private String street;
            private String street_number;

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }

        public static class AdInfoBean {
            /**
             * nation_code : 156
             * adcode : 441900
             * city_code : 156441900
             * name : 中国,广东省,东莞市,
             * location : {"lat":22.66667,"lng":114.184822}
             * nation : 中国
             * province : 广东省
             * city : 东莞市
             * district :
             */

            private String nation_code;
            private String adcode;
            private String city_code;
            private String name;
            private LocationBeanX location;
            private String nation;
            private String province;
            private String city;
            private String district;

            public String getNation_code() {
                return nation_code;
            }

            public void setNation_code(String nation_code) {
                this.nation_code = nation_code;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public static class LocationBeanX {
                /**
                 * lat : 22.66667
                 * lng : 114.184822
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        public static class AddressReferenceBean {
            /**
             * business_area : {"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"}
             * famous_area : {"id":"13409825155993327698","title":"凫山","location":{"lat":22.9818,"lng":113.905998},"_distance":0,"_dir_desc":"内"}
             * crossroad : {"id":"1439131","title":"兴山路/石新街(路口)","location":{"lat":22.98357,"lng":113.899391},"_distance":63.6,"_dir_desc":"东"}
             * town : {"id":"441900111","title":"寮步镇","location":{"lat":23.023001,"lng":113.801582},"_distance":0,"_dir_desc":"内"}
             * street_number : {"id":"9030566412838994863","title":"兴山路29号","location":{"lat":22.983419,"lng":113.899971},"_distance":13,"_dir_desc":""}
             * street : {"id":"11044807694381527649","title":"兴山路","location":{"lat":22.97891,"lng":113.90593},"_distance":15.4,"_dir_desc":"北"}
             * landmark_l1 : {"id":"10485605785919601066","title":"凫山中心商业广场","location":{"lat":22.984245,"lng":113.901703},"_distance":159.1,"_dir_desc":"西南"}
             * landmark_l2 : {"id":"9030566412838994863","title":"隆兴玩具礼品厂(兴山路)","location":{"lat":22.983347,"lng":113.899872},"_distance":0,"_dir_desc":"内"}
             */

            private BusinessAreaBean business_area;
            private FamousAreaBean famous_area;
            private CrossroadBean crossroad;
            private TownBean town;
            private StreetNumberBean street_number;
            private StreetBean street;
            private LandmarkL1Bean landmark_l1;
            private LandmarkL2Bean landmark_l2;

            public BusinessAreaBean getBusiness_area() {
                return business_area;
            }

            public void setBusiness_area(BusinessAreaBean business_area) {
                this.business_area = business_area;
            }

            public FamousAreaBean getFamous_area() {
                return famous_area;
            }

            public void setFamous_area(FamousAreaBean famous_area) {
                this.famous_area = famous_area;
            }

            public CrossroadBean getCrossroad() {
                return crossroad;
            }

            public void setCrossroad(CrossroadBean crossroad) {
                this.crossroad = crossroad;
            }

            public TownBean getTown() {
                return town;
            }

            public void setTown(TownBean town) {
                this.town = town;
            }

            public StreetNumberBean getStreet_number() {
                return street_number;
            }

            public void setStreet_number(StreetNumberBean street_number) {
                this.street_number = street_number;
            }

            public StreetBean getStreet() {
                return street;
            }

            public void setStreet(StreetBean street) {
                this.street = street;
            }

            public LandmarkL1Bean getLandmark_l1() {
                return landmark_l1;
            }

            public void setLandmark_l1(LandmarkL1Bean landmark_l1) {
                this.landmark_l1 = landmark_l1;
            }

            public LandmarkL2Bean getLandmark_l2() {
                return landmark_l2;
            }

            public void setLandmark_l2(LandmarkL2Bean landmark_l2) {
                this.landmark_l2 = landmark_l2;
            }

            public static class BusinessAreaBean {
                /**
                 * id : 13409825155993327698
                 * title : 凫山
                 * location : {"lat":22.9818,"lng":113.905998}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBeanXX location;
                private int _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXX {
                    /**
                     * lat : 22.9818
                     * lng : 113.905998
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class FamousAreaBean {
                /**
                 * id : 13409825155993327698
                 * title : 凫山
                 * location : {"lat":22.9818,"lng":113.905998}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBeanXXX location;
                private int _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXX {
                    /**
                     * lat : 22.9818
                     * lng : 113.905998
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class CrossroadBean {
                /**
                 * id : 1439131
                 * title : 兴山路/石新街(路口)
                 * location : {"lat":22.98357,"lng":113.899391}
                 * _distance : 63.6
                 * _dir_desc : 东
                 */

                private String id;
                private String title;
                private LocationBeanXXXX location;
                private double _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXX location) {
                    this.location = location;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXX {
                    /**
                     * lat : 22.98357
                     * lng : 113.899391
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class TownBean {
                /**
                 * id : 441900111
                 * title : 寮步镇
                 * location : {"lat":23.023001,"lng":113.801582}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBeanXXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXX {
                    /**
                     * lat : 23.023001
                     * lng : 113.801582
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class StreetNumberBean {
                /**
                 * id : 9030566412838994863
                 * title : 兴山路29号
                 * location : {"lat":22.983419,"lng":113.899971}
                 * _distance : 13
                 * _dir_desc :
                 */

                private String id;
                private String title;
                private LocationBeanXXXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXX {
                    /**
                     * lat : 22.983419
                     * lng : 113.899971
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class StreetBean {
                /**
                 * id : 11044807694381527649
                 * title : 兴山路
                 * location : {"lat":22.97891,"lng":113.90593}
                 * _distance : 15.4
                 * _dir_desc : 北
                 */

                private String id;
                private String title;
                private LocationBeanXXXXXXX location;
                private double _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXX location) {
                    this.location = location;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXXX {
                    /**
                     * lat : 22.97891
                     * lng : 113.90593
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LandmarkL1Bean {
                /**
                 * id : 10485605785919601066
                 * title : 凫山中心商业广场
                 * location : {"lat":22.984245,"lng":113.901703}
                 * _distance : 159.1
                 * _dir_desc : 西南
                 */

                private String id;
                private String title;
                private LocationBeanXXXXXXXX location;
                private double _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXXX location) {
                    this.location = location;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXXXX {
                    /**
                     * lat : 22.984245
                     * lng : 113.901703
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LandmarkL2Bean {
                /**
                 * id : 9030566412838994863
                 * title : 隆兴玩具礼品厂(兴山路)
                 * location : {"lat":22.983347,"lng":113.899872}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBeanXXXXXXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXXXXX {
                    /**
                     * lat : 22.983347
                     * lng : 113.899872
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }
    }
}
