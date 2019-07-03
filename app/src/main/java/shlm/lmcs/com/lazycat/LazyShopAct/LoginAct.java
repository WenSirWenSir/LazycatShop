package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("NewApi")
public class LoginAct extends LazyCatAct {
    private RelativeLayout layout;
    private RelativeLayout progressbody;
    private EditText inputPhone;
    private String MSG = "LoginAct.java[+]";
    private TextView showPhone;
    private TextView btnCodeGo;
    private LinearLayout btnCodeDel;
    private RelativeLayout inputCodeBack;
    private TextView btnSendCode;
    private LinearLayout inputCodeBody;
    private String phone;
    private ProgressBar sendCodeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTransparentBar();
        layout = findViewById(R.id.activity_login_main);
        progressbody = findViewById(R.id.activity_login_progressbody);/**/
        inputPhone = findViewById(R.id.activity_login_inputphone);/*输入手机号码*/
        Typeface typeface = Typeface.createFromAsset(getApplication().getAssets(), "font/price" +
                ".ttf");
        inputPhone.setTypeface(typeface);/*设置字体*/
        inputPhone.setBackground(Tools.CreateDrawable(1, "#999999", "#ffffff", 10));
        layout.post(new Runnable() {
            @Override
            public void run() {
                Animator animation = Tools.createRoundAnimation(layout, 600);
                animation.start();
            }
        });
        showPhone = findViewById(R.id.activity_login_showphoneTitle);/*展示用户输入的手机号码*/
        showPhone.setText("152" + " " + "0603" + getResources().getString(R.string.space) + "6936");
        btnCodeGo = findViewById(R.id.activity_login_btnCodeGo);/*输入完验证码之后的点击按钮*/
        /*实现logo逐渐显示*/
        findViewById(R.id.activity_login_logo).startAnimation(Tools.createOnalpha(1000, false));
        /*设置边框*/
        btnCodeGo.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));
        btnCodeDel = findViewById(R.id.activity_login_btnCodeDel);/*删除一个验证码数字*/
        /*设置边框*/
        btnCodeDel.setBackground(Tools.CreateDrawable(1, "#c6c6c6", "#c6c6c6", 10));
        inputCodeBack = findViewById(R.id.activity_login_inputCodeBackBody);/*输入框的背景边框*/
        inputCodeBody = findViewById(R.id.activity_login_inputCodeBody);/*输入验证码的界面*/
        btnSendCode = findViewById(R.id.activity_login_btnSendCode);/*按钮点击发送验证码*/
        sendCodeProgressBar = findViewById(R.id.activity_login_sendCodeProgressBar);/*发送短信验证码的等待图标*/
        Tools.clearFocusable(inputPhone);/*失去焦点*/
        init();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        /*登录*/
        /*退出*/
        findViewById(R.id.activity_login_btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                sendCodeProgressBar.setVisibility(View.VISIBLE);
            }
        });
        /*判断是否清空*/
        inputPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (inputPhone.getText().toString().trim().equals("手机号/登录名称")) {
                    inputPhone.setText("");
                    Tools.getFocusable(inputPhone);
                }

                return false;
            }
        });

        inputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString().trim();/*手机号码*/
                if (s.length() == 11) {
                    String showPhoneStart = phone.substring(0, phone.length() - 8);
                    String showPhoneEnd = phone.substring(phone.length() - 4, phone.length());
                    showPhone.setText(showPhoneStart + " **** " + showPhoneEnd);
                    Tools.clearFocusable(inputPhone);
                    Tools.hideKeyboard(inputPhone);
                    inputCodeBack.setVisibility(View.VISIBLE);
                    /*开始显示输入验证码的界面*/
                    inputCodeBody.setVisibility(View.VISIBLE);
                    inputCodeBody.setAnimation(Tools.createOnalpha(1000, true));
                }
            }
        });


        /**
         * 开始监听输入验证码的监听
         */

        LinearLayout inputcode_first = findViewById(R.id.activity_login_inputCodeNumber_frist);
        /*第一排*/
        inputcode_first.getChildAt(0).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/
        inputcode_first.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        inputcode_first.getChildAt(1).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/

        inputcode_first.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        inputcode_first.getChildAt(2).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/
        inputcode_first.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        /**
         * 第二排的输入验证码的控件的初始化和监听事件
         */
        LinearLayout inputcode_second = findViewById(R.id.activity_login_inputCodeNumber_second);
        /*第二排*/
        inputcode_second.getChildAt(0).setBackground(Tools.CreateDrawable(1, "#ffffff",
                "#ffffff", 5));/*设置边框*/
        inputcode_second.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        inputcode_second.getChildAt(1).setBackground(Tools.CreateDrawable(1, "#ffffff",
                "#ffffff", 5));/*设置边框*/

        inputcode_second.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        inputcode_second.getChildAt(2).setBackground(Tools.CreateDrawable(1, "#ffffff",
                "#ffffff", 5));/*设置边框*/
        inputcode_second.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        /**
         * 第三排的输入验证码的键盘初始化和监听
         */
        LinearLayout inputcode_three = findViewById(R.id.activity_login_inputCodeNumber_three);
        /*第三排*/
        inputcode_three.getChildAt(0).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/
        inputcode_three.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());
            }
        });
        inputcode_three.getChildAt(1).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/

        inputcode_three.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());

            }
        });
        inputcode_three.getChildAt(2).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/
        inputcode_three.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                /*判断是不是第一次输入*/
                if (btnSendCode.getText().length() < 6) {
                    btnSendCode.setText(btnSendCode.getText().toString() + tv.getText().toString());
                }
                Log.i(MSG, "" + tv.getText().toString());
            }
        });

        /**
         * 第四排输入验证码的只有一个数字0的监听和初始化
         */
        /*第四排*/
        LinearLayout inputcode_four = findViewById(R.id.activity_login_inputCodeNumber_four);
        inputcode_four.getChildAt(0).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff",
                5));/*设置边框*/
        inputcode_four.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                final Animator aii = Tools.createRoundAnimation(tv, 200);
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        aii.start();
                    }
                });
                Log.i(MSG, "" + tv.getText().toString());

            }
        });


        /**
         * 设置删除一位验证码
         */
        btnCodeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animator btnDelAnimator = Tools.createRoundAnimation(btnCodeDel, 200);
                btnCodeDel.post(new Runnable() {
                    @Override
                    public void run() {
                        btnDelAnimator.start();
                    }
                });
                String code = btnSendCode.getText().toString();/*获取验证码*/
                if (code.length() == 0) {

                } else {
                    String new_code = code.substring(0, code.length() - 1);
                    btnSendCode.setText(new_code);
                }

            }
        });
        /**
         * 确定并开始验证
         */
        btnCodeGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animator btnCodeGoAnimator = Tools.createRoundAnimation(btnCodeGo, 200);
                btnCodeGo.post(new Runnable() {
                    @Override
                    public void run() {
                        btnCodeGoAnimator.start();
                    }
                });
                if (btnSendCode.getText().length() < 6) {
                    /*没有输入验证码*/
                    Toast.makeText(getApplicationContext(), "验证码长度不同,或者你并没有输入验证码", Toast
                            .LENGTH_SHORT).show();
                } else {

                }
            }
        });
    }


    /**
     * 输入框的动画
     */
    private void inputAnimation(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(60, w);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) valueAnimator.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                Log.i(MSG, "距离左边的MARGIN：" + value);
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
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
                /*顺便把猫的LOGO给取消了*/
                findViewById(R.id.activity_login_logo).startAnimation(Tools.clearOnalpha(1000,
                        true));


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
