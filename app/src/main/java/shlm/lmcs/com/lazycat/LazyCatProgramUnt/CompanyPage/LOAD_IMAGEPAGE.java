package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.widget.ImageView;

import java.io.File;

/**
 * 在线程池中下载时候 要传入的IMAGE的值
 */
public class LOAD_IMAGEPAGE {
    private ImageView img;
    private String LruchTag;

    public File getDefaultFile() {
        return defaultFile;
    }

    public void setDefaultFile(File defaultFile) {
        this.defaultFile = defaultFile;
    }

    private File defaultFile;//默认的图片缓存路径

    private String img_url;

    public String getSaveName() {
        return saveName;
    }


    /**
     * 保存到本地的图片名字 必须加后缀名字
     *
     * @param saveName
     */
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    private String saveName;

    public String getPostPixt() {
        return postPixt;
    }

    public void setPostPixt(String postPixt) {
        this.postPixt = postPixt;
    }

    private String postPixt;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }


    /**
     * 保存到缓存的
     * @return
     */
    public String getLruchTag() {
        return LruchTag;
    }

    public void setLruchTag(String tag) {
        this.LruchTag = tag;
    }


    /**
     * 获取一个图片的地址  一般的格式为
     * dirs/name.jpg
     * @return
     */
    public String getImg_url() {
        return img_url;
    }


    /**
     * 设置一个图片的地址 一般格式为
     * dirs/name.jpg
     * @param img_url
     */
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    /**
     * 清空所有的数据信息
     */
    public void clear() {
        this.img = null;
        this.img_url = "";
        this.LruchTag = "";
    }
}
