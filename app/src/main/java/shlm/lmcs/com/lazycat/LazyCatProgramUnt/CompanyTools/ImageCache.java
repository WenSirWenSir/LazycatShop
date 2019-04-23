package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class ImageCache
{
	private Context mContext;
	private LruCache<String, Bitmap> mLruCache;
	private Bitmap mBitmap;
	public ImageCache(){
		int maxMemory  = (int) (Runtime.getRuntime().maxMemory());
		int MemorySize = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(MemorySize){
			@Override
			protected int sizeOf(String key, Bitmap value)
			{
				return value.getByteCount();
			}
		};
	}
	public Boolean isLocalLruchBitmap(String key){
		try
		{
			if(mLruCache.get(key) != null){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e)
		{
			return false;
		}
	}
	public void saveImage(String key,Bitmap bitmap){
		try {
			if(!isLocalLruchBitmap(key)){
				try
				{
					mLruCache.put(key, bitmap);
				} catch (Exception e)
				{
					Log.i("capitalist", "File ProgramLocalLruch.java Error Line in :59");
				}
			}
		} catch (Exception e) {
			
		}
		
	}
	public Bitmap getImage(String key){
		try {
			if(mLruCache.get(key) != null){
				return mLruCache.get(key);
			}
			else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		
	}
	public void ReleaseImageBitmap(ImageView image){
		if(image == null) return;
		Drawable drawable = image.getDrawable();
		if(drawable != null && drawable instanceof BitmapDrawable){
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			if(bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
		}
	}

}
