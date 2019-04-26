package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.WebServiceAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;
import shlm.lmcs.com.lazycat.LazyShopMonitor.BrandSingMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.Monitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.NewShopinMonitor;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalMonitorPage;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment {
    private LinearLayout horizontaladv_body;
    private LayoutInflater inflater;
    private RefreshScrollView _RefreshScrollView;
    private static final int GONE_HEADE_IMG = 0;
    private Timer _GoneHeadimg;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GONE_HEADE_IMG:
                    _GoneHeadimg.cancel();
                    if (_RefreshScrollView != null) {
                        _RefreshScrollView.stopRefresh();//ֹͣˢ��
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View item = inflater.inflate(R.layout.fragment_main, null);
        //��ʼ��һ��������ʽ
        item.findViewById(R.id.assembly_head_input).setBackground(Tools.CreateDrawable(3,
                getResources().getString(R.color.ThemeColor), "#ffffff", 10));
        item.findViewById(R.id.assembly_head_input).setFocusable(false);
        init(item);
        Tools.getMemorySize(getContext(), new ProgramInterface.onMemorySize() {
            @SuppressLint("LongLogTag")
            @Override
            public void onGet(int max, float total, float free) {
                Log.i(Config.DEBUG, "���ʹ���ڴ�:" + max + "�ϼƷ����ڴ�:" + total + "ʣ���ڴ�:" + free);
            }
        });
        return item;
    }

    @SuppressLint({"NewApi", "ResourceType", "LongLogTag"})
    private void init(View item) {
        inflater = LayoutInflater.from(getContext());//��ʼ��inflater
        //���ص�һ������Ĺ�沼��
        horizontaladv_body = item.findViewById(R.id.fragment_main_horizontaladv_body);//������Ĳ���
        View horizontaladv_view = inflater.inflate(R.layout.assembly_fragment_main_horizontaladv,
                null);
        horizontaladv_body.addView(horizontaladv_view);

        /**
         * ���Լ���
         */
        _RefreshScrollView = item.findViewById(R.id.fragment_main_refreshScrollview);
        LinearLayout _layout = item.findViewById(R.id.fragment_main_layoutmore);
        _RefreshScrollView.SetHeadView(_layout, 200, R.id.layoutmore_progressbar, R.id
                .layoutmore_headimg);
        /**
         * �ڽ����� ����Ҫ���ص���������Ҫ�����������Ϣ
         */
        /*��ҳ���Ƽ��ĺ���Ĺ���*/
        /**
         * �����ָ���
         * ========================================================
         */
        /*<��ҳ����Ʒ�ϼܵĹ���>*/
        LinearLayout navA_body = item.findViewById(R.id.fragment_main_navA_body);
        View _navaBody = inflater.inflate(R.layout.assembly_fragment_main_nava, null);
        NewShopinMonitor newShopinMonitor = new NewShopinMonitor();
        newShopinMonitor.SaveTag(LocalMonitorPage.MONITOR_NEWSHOPIN);/*���ñ�ʶ ��ʶ��������ߵ�����*/
        navA_body.addView(_navaBody);
        navA_body.setTag(newShopinMonitor);
        /*</��ҳ����Ʒ�ϼܵĹ���>*/
        /**
         * �����ָ���
         * ========================================================
         */
        /*<��ҳ��Ʒ�ƴ����Ĺ���>*/
        LinearLayout layout = item.findViewById(R.id.fragment_main_singlebody);
        View mainSingle = inflater.inflate(R.layout.assembly_fragment_main_single, null);
        layout.addView(mainSingle);
        /*����Ʒ�Ƶ����������*/
        BrandSingMonitor brandSingMonitor = new BrandSingMonitor(mainSingle, getContext());
        brandSingMonitor.Start(new ProgramInterface() {
            @Override
            public void onSucess(String data, int code) {
                //�ж�code ���code��ȷ�Ļ� Ҫ����ScrollView��ֹͣ����tag
            }

            @Override
            public void onFaile(String data, int code) {

            }
        });
        brandSingMonitor.SaveTag(LocalMonitorPage.MONITOR_BRANDSING);/*��ʶ�����ߵ�����*/
        layout.setTag(brandSingMonitor);/*���������*/
        /*</��ҳ��Ʒ�ƴ����Ĺ���>*/
        _RefreshScrollView.SetLinstener(new RefreshScrollView.RefreshScrollViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefreshDone() {

            }

            @Override
            public void onStopRefresh() {

            }

            @Override
            public void onState(int _static) {
                if (_static == RefreshScrollView.RUNNOW_REFRESH) {
                    //���Կ�ʼˢ��

                }

            }

            @Override
            public void onLoadMore() {
                /**
                 * ����һ����ʱ���߳� �����������й�������
                 */
                _GoneHeadimg = new Timer();
                _GoneHeadimg.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = GONE_HEADE_IMG;
                        handler.sendMessage(msg);

                    }
                }, 3000, 1000);

            }

            @Override
            public void onLoadBottom() {

            }

            @Override
            public void onScrollStop() {
                LinearLayout i = (LinearLayout) _RefreshScrollView.getChildAt(0);
                Rect rect = new Rect();
                for (int y = 0; y < i.getChildCount(); y++) {
                    if (i.getChildAt(y).getLocalVisibleRect(rect)) {
                        /*������ʾ*/
                        Monitor monitor = (Monitor) i.getChildAt(y).getTag();
                        if (monitor != null) {
                            Toast.makeText(getContext(), "Tag:" + monitor.GetTag(), Toast
                                    .LENGTH_SHORT).show();

                        }
                    }
                }
                _RefreshScrollView.getHitRect(rect);
                _RefreshScrollView.onStopHandle = true;/*���ò����ٴλص�ֹͣ�¼� �ڰ��µ�ʱ�� ��ֵ���±�����*/

            }

            @Override
            public void onloadMessage() {
                Log.i(Config.DEBUG, "���ڿ��Կ�ʼ���ع�洰����");
                _RefreshScrollView.inLoadMessage = true;//�����ڲ� �Ѿ���ʼ���� �����ٴε��ýӿ�
                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT
                        ("http://120.79.63.36/lazyShop/webpage/1.html");
                Intent i = new Intent();
                i.setClass(getContext(), WebServiceAct.class);
                i.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, web_values_act);
                startActivity(i);
                //ֹͣˢ��
            }

            @Override
            public void onScrollDistance(int distance) {

            }
        });

        /**
         * ���Լ�����Ʒ�ϼܻ�����ʱ�����Ĳ�Ʒ
         */
        LinearLayout newShopin = item.findViewById(R.id.fragment_main_newShopin);
        newShopin.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));
        View _itemNewshopin = inflater.inflate(R.layout.assembly_fragment_main_newshopin, null);
        newShopin.addView(_itemNewshopin);

        /**
         * ʵ�ֵ�������������������
         */
        item.findViewById(R.id.assembly_head_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LazyCatFragmetStartAct(SearchAct.class);
            }
        });

    }
}
