package shlm.lmcs.com.lazycat.LazyShopView;import android.annotation.SuppressLint;import android.content.Context;import android.graphics.Typeface;import android.util.AttributeSet;import android.widget.TextView;/** * 系统的文字字体 更好看点 * 使用方正兰亭中黑_GBK */@SuppressLint("AppCompatCustomView")public class SystemTextView extends TextView {    public SystemTextView(Context context) {        super(context);        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/system_ttf"                + ".ttf");        setTypeface(typeface);    }    public SystemTextView(Context context, AttributeSet attrs) {        super(context, attrs);        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/system_ttf"                + ".ttf");        setTypeface(typeface);    }    public SystemTextView(Context context, AttributeSet attrs, int defStyleAttr) {        super(context, attrs, defStyleAttr);        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/system_ttf"                + ".ttf");        setTypeface(typeface);    }}