package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * չʾ��ͼƬ���ԷŴ��������С
 */
public class ZzomImage extends LazyCatAct {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * ��������
         */
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ImageView img = new ImageView(layout.getContext());
        setContentView(layout);
    }
}
