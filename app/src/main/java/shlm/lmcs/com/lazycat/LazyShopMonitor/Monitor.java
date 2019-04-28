package shlm.lmcs.com.lazycat.LazyShopMonitor;


/**
 * 继承父类的方法
 */
public class Monitor {
    private int tag;

    public void SaveTag(int _tag) {
        this.tag = _tag;

    }

    /**
     * @return
     */
    public int GetTag() {
        return tag;
    }
}
