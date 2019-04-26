package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory;


import android.util.Log;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;

/**
 * ���̴߳�����
 */
public class ThreadFactory {
    private boolean can_load;
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadFactory() {
        can_load = true;
    }

    /**
     * ʹ�ö��̴߳�������
     */
    public void doThread(List<Runnable> r_list) {
        Log.e(Config.DEBUG, "doThread");
        //�����̳߳�
        threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new
                LinkedBlockingQueue<Runnable>(100));
        Log.e(Config.DEBUG, "doThread2" + r_list.size());
        for (int i = 0; i < r_list.size(); i++) {
            if (this.can_load) {
                Log.e(Config.DEBUG, "can_load");
                threadPoolExecutor.execute(r_list.get(i));
            } else {
                threadPoolExecutor.shutdownNow();
                Log.e(Config.DEBUG, "canno_load");

            }
        }
    }

    public void Stop() {
        this.can_load = false;//�����Լ���
        threadPoolExecutor.shutdownNow();//�ر�
        threadPoolExecutor = null;
    }

    public void Restart(List<Runnable> r_list) {
        this.can_load = true;
        if (threadPoolExecutor != null) {
            doThread(r_list);

        }
    }


}
