package shlm.lmcs.com.lazycat.LazyShopMonitor;


/**
 * 继承父类的方法
 */
public class Monitor {
    private int tag;

    public final static int IN_LOAD = 0;/*正在加载*/
    public final static int IN_PAUSE = 1;/*暂时停止*/
    public final static int IN_STOP = 2;/*已经停止*/
    public final static int IN_RESTART = 3;/*重启加载*/
    public final static int IN_START = 4;/*开始加载*/
    public final static int IN_DONE = 5;/*加载完成*/
    public final static int IN_CANTLOAD = 6;/*不准加载*/

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
