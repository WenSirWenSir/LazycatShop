package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.widget.ImageView;

/**
 * 在线程池中下载时候 要传入的IMAGE的值
 */
public class LOAD_IMAGEPAGE {
    private ImageView img;
    private String tag;
    private String img_url;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    /**
     * 清空所有的数据信息
     */
    public void clear() {
        this.img = null;
        this.img_url = "";
        this.tag = "";
    }
}
