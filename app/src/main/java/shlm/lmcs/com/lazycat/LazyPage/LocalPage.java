package shlm.lmcs.com.lazycat.LazyPage;public class LocalPage {    public LocalShopPage getLocalShopPageInstance() {        return new LocalShopPage();    }    public class LocalShopPage {        /*标题*/ String title;        /*价格*/ String price;        /*图片地址*/ String imgUrl;        /*商品的状态*/ String status;        /*商品画虚线的价格*/ String linePrice;        String company;        String business;        String businessLong;        String businessLat;        Boolean isLogin;/*是否登录成功*/        Boolean isVip;/*是否VIP*/        public Boolean getLogin() {            return isLogin;        }        public void setLogin(Boolean login) {            isLogin = login;        }        public Boolean getVip() {            return isVip;        }        public void setVip(Boolean vip) {            isVip = vip;        }        public String getBusinessLong() {            return businessLong;        }        public void setBusinessLong(String businessLong) {            this.businessLong = businessLong;        }        public String getBusinessLat() {            return businessLat;        }        public void setBusinessLat(String businessLat) {            this.businessLat = businessLat;        }        public String getBusiness() {            return business;        }        public void setBusiness(String business) {            this.business = business;        }        public String getCompany() {            return company;        }        public void setCompany(String company) {            this.company = company;        }        public String getDottenlinePrice() {            return this.linePrice;        }        public void setDottenlinePrice(String Stprice) {            this.linePrice = Stprice;        }        public String getStatus() {            return status;        }        public void setStatus(String status) {            this.status = status;        }        public String getImgUrl() {            return imgUrl;        }        public void setImgUrl(String imgUrl) {            this.imgUrl = imgUrl;        }        public String getPrice() {            return price;        }        public void setPrice(String price) {            this.price = price;        }        public String getTitle() {            return title;        }        public void setTitle(String title) {            this.title = title;        }    }}