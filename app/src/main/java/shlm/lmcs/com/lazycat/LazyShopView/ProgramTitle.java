package shlm.lmcs.com.lazycat.LazyShopView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class ProgramTitle extends TextView {
    public ProgramTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/title" + "" +
                ".ttf");
        setTypeface(typeface);
    }

    public ProgramTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/title" + "" +
                ".ttf");
        setTypeface(typeface);
    }

    public ProgramTitle(Context context) {
        super(context);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/title" + "" +
                ".ttf");
        setTypeface(typeface);
    }
}
