package library.util;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 
 * ClassName: ImageUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:01:50 <br/>
 * 图片工具类， 可用于Bitmap, byte array, Drawable之间进行转换以及图片缩放，目前功能薄弱，后面会进行增强。
 * 如：bitmapToDrawable(Bitmap b) bimap转换为drawable <br/>
 * drawableToBitmap(Drawable d)drawable转换为bitmap <br/>
 * drawableToByte(Drawable d) drawable转换为byte<br/>
 * scaleImage(Bitmap org, float scaleWidth, float scaleHeight) 缩放图片<br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class ImageUtils {

	private ImageUtils() {
		throw new AssertionError();
	}

	/**
	 * 将位图转换成字节数组
	 * @param b
	 * @return
	 */
	public static byte[] bitmapToByte(android.graphics.Bitmap b) {
		if (b == null) {
			return null;
		}

		java.io.ByteArrayOutputStream o = new java.io.ByteArrayOutputStream();
		b.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * 将字节数组转换为位图
	 * @param b
	 * @return
	 */
	public static android.graphics.Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : android.graphics.BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * 可拉的转换为位图
	 * @param d
	 * @return
	 */
	public static android.graphics.Bitmap drawableToBitmap(android.graphics.drawable.Drawable d) {
		return d == null ? null : ((android.graphics.drawable.BitmapDrawable) d).getBitmap();
	}

	/**
	 * 将位图转换为可移动
	 * 
	 * @param b
	 * @return
	 */
	public static android.graphics.drawable.Drawable bitmapToDrawable(android.graphics.Bitmap b) {
		return b == null ? null : new android.graphics.drawable.BitmapDrawable(b);
	}

	/**
	 * 可拉的转换为字节数组
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] drawableToByte(android.graphics.drawable.Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * 将字节数组转换为可移动
	 * 
	 * @param b
	 * @return
	 */
	public static android.graphics.drawable.Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	/**
	 * 从网络获取输入流imageurl,你需要关闭inputStream
	 * @param imageUrl
	 * @param readTimeOutMillis
	 * @return
	 * @see ImageUtils#getInputStreamFromUrl(String, int, boolean)
	 */
	public static java.io.InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis) {
		return getInputStreamFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * 从网络获取输入流imageurl,你需要关闭inputStream
	 * 
	 * @param imageUrl
	 * @param readTimeOutMillis read time out, if less than 0, not set, in mills
	 * @param requestProperties http request properties
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static java.io.InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis, java.util.Map<String, String> requestProperties) {
		java.io.InputStream stream = null;
		try {
			java.net.URL url = new java.net.URL(imageUrl);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
			HttpUtils.setURLConnection(requestProperties, con);
			if (readTimeOutMillis > 0) {
				con.setReadTimeout(readTimeOutMillis);
			}
			stream = con.getInputStream();
		} catch (java.net.MalformedURLException e) {
			closeInputStream(stream);
			throw new RuntimeException("MalformedURLException occurred. ", e);
		} catch (java.io.IOException e) {
			closeInputStream(stream);
			throw new RuntimeException("IOException occurred. ", e);
		}
		return stream;
	}

	/**
	 * get drawable by imageUrl
	 * 
	 * @param imageUrl
	 * @param readTimeOutMillis
	 * @return
	 * @see ImageUtils#getDrawableFromUrl(String, int, boolean)
	 */
	public static android.graphics.drawable.Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis) {
		return getDrawableFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * get drawable by imageUrl
	 * 
	 * @param imageUrl
	 * @param readTimeOutMillis
	 *            read time out, if less than 0, not set, in mills
	 * @param requestProperties
	 *            http request properties
	 * @return
	 */
	public static android.graphics.drawable.Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis, java.util.Map<String, String> requestProperties) {
		java.io.InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis, requestProperties);
		android.graphics.drawable.Drawable d = android.graphics.drawable.Drawable.createFromStream(stream, "src");
		closeInputStream(stream);
		return d;
	}

	/**
	 * get Bitmap by imageUrl
	 * 
	 * @param imageUrl
	 * @param readTimeOut
	 * @return
	 * @see ImageUtils#getBitmapFromUrl(String, int, boolean)
	 */
	public static android.graphics.Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
		return getBitmapFromUrl(imageUrl, readTimeOut, null);
	}

	/**
	 * get Bitmap by imageUrl
	 * 
	 * @param imageUrl
	 * @param requestProperties
	 *            http request properties
	 * @return
	 */
	public static android.graphics.Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut, java.util.Map<String, String> requestProperties) {
		java.io.InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut, requestProperties);
		android.graphics.Bitmap b = android.graphics.BitmapFactory.decodeStream(stream);
		closeInputStream(stream);
		return b;
	}

	/**
	 * scale image
	 * 
	 * @param org
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static android.graphics.Bitmap scaleImageTo(android.graphics.Bitmap org, int newWidth, int newHeight) {
		return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
	}

	/**
	 * 规模的形象
	 * 
	 * @param org
	 * @param scaleWidt     sacle of width
	 * @param scaleHeight
	 *            scale of height
	 * @return
	 */
	public static android.graphics.Bitmap scaleImage(android.graphics.Bitmap org, float scaleWidth, float scaleHeight) {
		if (org == null) {
			return null;
		}

		android.graphics.Matrix matrix = new android.graphics.Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return android.graphics.Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
	}

	/**
	 * 关闭inputStream
	 * 
	 * @param s
	 */
	private static void closeInputStream(java.io.InputStream s) {
		if (s == null) {
			return;
		}
		try {
			s.close();
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}
}
