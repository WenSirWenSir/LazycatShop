package shlm.lmcs.com.lazycat.LazyShopMonitor;


/**
 * 新品上架的数据管理者
 */
public class NewShopinMonitor extends Monitor {
    private boolean StopLoad = false;

    /**
     * 构造方法
     */
    public NewShopinMonitor() {

    }


    /**
     * 启动管理
     */
    public void Start(){
        if(StopLoad){
            /*不准加载*/
        }
    }


    /**
     * 停止管理
     */
    public void Stop(){

    }
}
