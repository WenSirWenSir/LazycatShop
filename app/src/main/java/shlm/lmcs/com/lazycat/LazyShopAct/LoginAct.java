package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("NewApi")
public class LoginAct extends LazyCatAct {
    private RelativeLayout layout;
    private RelativeLayout progressbody;
    private LinearLayout inputBody;
    private EditText inputPhone, inputCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        layout = findViewById(R.id.activity_login_main);
        inputBody = findViewById(R.id.activity_login_inputbody);
        progressbody = findViewById(R.id.activity_login_progressbody);/**/
        inputPhone = findViewById(R.id.activity_login_inputphone);/*输入手机号码*/
        inputCode = findViewById(R.id.activity_login_inputcode);/*验证码*/
        Typeface typeface = Typeface.createFromAsset(getApplication().getAssets(), "font/price" +
                ".ttf");
        inputPhone.setTypeface(typeface);/*设置字体*/
        inputCode.setTypeface(typeface);/*设置字体*/
        layout.post(new Runnable() {
            @Override
            public void run() {
                Animator animation = Tools.createRoundAnimation(layout, 600);
                animation.start();
            }
        });
        /*实现logo逐渐显示*/
        findViewById(R.id.activity_login_logo).startAnimation(Tools.createOnalpha(3000, false));
        /*设置边框*/
        inputBody.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));
        init();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        /*登录*/
        findViewById(R.id.activity_login_btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPhone.setVisibility(View.GONE);
                inputCode.setVisibility(View.GONE);
                inputAnimation(inputBody, inputBody.getMeasuredWidth(), inputBody
                        .getMeasuredHeight());
            }
        });
        /*退出*/
        findViewById(R.id.activity_login_btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*判断是否清空*/
        inputCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (inputCode.getText().toString().trim().equals("输入验证码")) inputCode.setText("");
                return false;
            }
        });
        /*判断是否清空*/
        inputPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (inputPhone.getText().toString().trim().equals("手机号")) inputPhone.setText("");
                return false;
            }
        });
    }


    /**
     * 输入框的动画
     */
    private void inputAnimation(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, w - 50);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) valueAnimator.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(inputBody, "scaleX", 0.2f, 0.5f);
        set.setDuration(3000);
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(valueAnimator, objectAnimator);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*显示加载进度*/
                progressbody.setVisibility(View.VISIBLE);
                progressAnimation(progressbody);
                inputBody.setVisibility(View.GONE);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    /**
     * 出现加载的动画
     *
     * @param view
     */
    private void progressAnimation(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new myInterpolator());
        animator3.start();
    }


    /**
     * 自定义的插值器
     */
    class myInterpolator extends LinearInterpolator {
        private float factor;

        public myInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math
                    .PI) / factor) + 1);
        }
    }

}
