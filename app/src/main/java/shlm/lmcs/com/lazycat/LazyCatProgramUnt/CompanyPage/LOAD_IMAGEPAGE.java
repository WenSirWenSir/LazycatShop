package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.widget.ImageView;

import java.io.File;

/**
 * ���̳߳�������ʱ�� Ҫ�����IMAGE��ֵ
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

    private File defaultFile;//Ĭ�ϵ�ͼƬ����·��

    private String img_url;

    public String getSaveName() {
        return saveName;
    }


    /**
     * ���浽���ص�ͼƬ���� ����Ӻ�׺����
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
     * ���浽�����
     * @return
     */
    public String getLruchTag() {
        return LruchTag;
    }

    public void setLruchTag(String tag) {
        this.LruchTag = tag;
    }


    /**
     * ��ȡһ��ͼƬ�ĵ�ַ  һ��ĸ�ʽΪ
     * dirs/name.jpg
     * @return
     */
    public String getImg_url() {
        return img_url;
    }


    /**
     * ����һ��ͼƬ�ĵ�ַ һ���ʽΪ
     * dirs/name.jpg
     * @param img_url
     */
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    /**
     * ������е�������Ϣ
     */
    public void clear() {
        this.img = null;
        this.img_url = "";
        this.LruchTag = "";
    }
}
