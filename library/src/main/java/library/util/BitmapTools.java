package library.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

/**
 * ClassName: BitmapTools <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:51:17 <br/>
 * 图片方法的封装
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class BitmapTools {
	private final static String TAG = "BitmapTools";

	private BitmapTools() {
	}

	/**
	 * 
	 * getBitmap:(从输入流中获取图片). <br/>
	 * 
	 * @author chenhao
	 * @param is
	 *            图片资源的输入流
	 * @return
	 * @since JDK 1.6
	 */
	public static android.graphics.Bitmap getBitmap(java.io.InputStream is) {
		android.util.Log.i(TAG, "getBitmap(InputStream is)");
		return android.graphics.BitmapFactory.decodeStream(is);
	}

	/**
	 * 
	 * getBitmap:(从输入流中获取图片). <br/>
	 * 
	 * @author chenhao
	 * @param is
	 *            图片资源的输入流
	 * @param scale
	 *            图片大小
	 * @return
	 * @since JDK 1.6
	 */
	public static android.graphics.Bitmap getBitmap(java.io.InputStream is, int scale) {
		android.graphics.Bitmap bitmap = null;
		android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
		opts.inSampleSize = scale;
		bitmap = android.graphics.BitmapFactory.decodeStream(is, null, opts);
		android.util.Log.i(TAG, "getBitmap(InputStream is, int scale)");
		return bitmap;
	}

	/**
	 * 
	 * getBitmapWithScale:(从path 路径地址获取图片). <br/>
	 * 
	 * @author chenhao
	 * @param path
	 *            图片
	 * @param width
	 *            设置图片的宽度
	 * @param height
	 *            设置图片的高度
	 * @return
	 * @since JDK 1.6
	 */
	public static android.graphics.Bitmap getBitmapWithScale(String path, int width, int height) {
		if (path == null) {
			return null;
		}
		android.graphics.Bitmap bitmap = null;
		android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		bitmap = android.graphics.BitmapFactory.decodeFile(path, opts);
		opts.inJustDecodeBounds = false;
		int scaleX = opts.outWidth / width;
		int scaleY = opts.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		opts.inSampleSize = scale;
		android.util.Log.i(TAG, "getBitmap() scale : " + scale);
		bitmap = android.graphics.BitmapFactory.decodeFile(path, opts);
		android.util.Log.i(TAG, "getBitmap(byte[] bytes, int width, int height)");
		return bitmap;
	}

	/**
	 * 从字节流中获取图片信息
	 * 
	 * @param bytes
	 *            字节流
	 * @param width
	 *            设置图片的宽度
	 * @param height
	 *            设置图片的高度
	 * @return
	 */
	public static android.graphics.Bitmap getBitmap(byte[] bytes, int width, int height) {
		if (bytes == null) {
			return null;
		}
		android.graphics.Bitmap bitmap = null;
		android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		opts.inJustDecodeBounds = false;
		int scaleX = opts.outWidth / width;
		int scaleY = opts.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		opts.inSampleSize = scale;
		android.util.Log.i(TAG, "getBitmap() scale : " + scale);
		bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		android.util.Log.i(TAG, "getBitmap(byte[] bytes, int width, int height)");
		return bitmap;
	}

	/**
	 * 获取本地图片
	 * 
	 * @param path
	 *            本地路径
	 * @return
	 */
	public static android.graphics.Bitmap getBitmap(String tag, String path) {
		android.graphics.Bitmap bitmap = null;
		bitmap = android.graphics.BitmapFactory.decodeFile(path);
		android.util.Log.i(TAG, tag + ">>getBitmap(String path)");
		return bitmap;
	}

	public static java.io.File getFilePath(String filePath, String fileName) {
		java.io.File file = null;
		makeRootDirectory(filePath);
		try {
			file = new java.io.File(filePath + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 
	 * makeRootDirectory:(创建根目录). <br/>
	 * 
	 * @author chenhao
	 * @param filePath
	 * @since JDK 1.6
	 */
	public static void makeRootDirectory(String filePath) {
		java.io.File file = null;
		try {
			file = new java.io.File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param path
	 *            图片保存的位置
	 * @param bitmap
	 *            图片
	 * @throws IOException
	 */
	public static void saveBitmap(String path, android.graphics.Bitmap bitmap) throws java.io.IOException {
		if (path != null && bitmap != null) {
			java.io.File file = new java.io.File(path);
			if (!file.exists()) {
				android.util.Log.i(TAG, "!file.exists()");
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			java.io.OutputStream stream = new java.io.FileOutputStream(file);
			String name = file.getName();
			String end = name.substring(name.lastIndexOf(".") + 1);
			if ("png".equals(end)) {
				bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
			} else {
				bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, stream);
			}
			android.util.Log.i(TAG, "saveBitmap(String path, Bitmap bitmap)");
		}
	}

	/**
	 * 该方法用于将一个矩形图片的边角钝化
	 * 
	 * @param bitmap
	 *            待修改的图片
	 * @param roundPx
	 *            边角的弧度
	 * @return 返回修改过边角的新图片
	 */
	public static android.graphics.Bitmap getRoundedCornerBitmap(android.graphics.Bitmap bitmap, float roundPx) {
		android.graphics.Bitmap output = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
		android.graphics.Canvas canvas = new android.graphics.Canvas(output);
		final int color = 0xff424242;
		final android.graphics.Paint paint = new android.graphics.Paint();
		final android.graphics.Rect rect = new android.graphics.Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final android.graphics.RectF rectF = new android.graphics.RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 该方法用于任意缩放指定大小的图片
	 * 
	 * @param bitmap
	 *            待修改的图片
	 * @param newWidth
	 *            新图片的宽度
	 * @param newHeight
	 *            新图片的高度
	 * @return 缩放后的新图片
	 */
	public static android.graphics.Bitmap zoomBitmap(android.graphics.Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		android.graphics.Matrix matrix = new android.graphics.Matrix();
		float scaleWidht = ((float) newWidth / width);
		float scaleHeight = ((float) newHeight / height);
		matrix.postScale(scaleWidht, scaleHeight);
		android.graphics.Bitmap newbmp = android.graphics.Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newbmp;
	}

	/**
	 * 该方法用于生成图片的下方倒影效果
	 * 
	 * @param bitmap
	 *            代修改的图片
	 * @return 有倒影效果的新图片
	 */
	public static android.graphics.Bitmap createReflectionImageWithOrigin(android.graphics.Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		android.graphics.Matrix matrix = new android.graphics.Matrix();
		matrix.preScale(1, -1);
		android.graphics.Bitmap reflectionImage = android.graphics.Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
		android.graphics.Bitmap bitmapWithReflection = android.graphics.Bitmap.createBitmap(width, (height + height / 2), android.graphics.Bitmap.Config.ARGB_8888);
		android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		android.graphics.Paint deafalutPaint = new android.graphics.Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		android.graphics.Paint paint = new android.graphics.Paint();
		android.graphics.LinearGradient shader = new android.graphics.LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, android.graphics.Shader.TileMode.CLAMP);
		paint.setShader(shader);
		//  Set the Transfer mode to be porter duff and destination in  
		paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
		//  Draw a rectangle using the paint with our linear gradient  
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * 该方法用于将bitmap转换为字节数组，png格式质量为100的。
	 * 
	 * @param bm
	 *            待转化的bitmap
	 * @return 返回btmap的字节数组
	 */
	public static byte[] Bitmap2Bytes(android.graphics.Bitmap bm) {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 该方法用于将字节数组转化为bitmap图
	 * 
	 * @param b
	 *            字节数组
	 * @return bitmap位图
	 */
	public static android.graphics.Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return android.graphics.BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Drawable转换成Bitmap
	 * 
	 * @param drawable
	 * @return bitmap 位图
	 * */
	public static android.graphics.Bitmap drawableToBitmap(android.graphics.drawable.Drawable drawable) {

		android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != android.graphics.PixelFormat.OPAQUE ? android.graphics.Bitmap.Config.ARGB_8888 : android.graphics.Bitmap.Config.RGB_565);
		android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static synchronized boolean loadImage(String folder, String url, String suffix) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName + suffix;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
			android.util.Log.i("downloadimg", "loadImage>>finish---" + url);
			return true;
		} catch (Exception e) {
			android.util.Log.e("downloadimg", "loadImage>>error---" + url + e.toString());
			return false;
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static synchronized void loadImage(String folder, String url, library.util.BitmapTools.LoadingListener listener) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
			listener.finish(outFilename);
		} catch (Exception e) {
			android.util.Log.e("downloadimg", "error---" + url + e.toString());
			listener.error(url + ">>" + e.toString());
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static android.graphics.Bitmap loadImage(String folder, String url) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
			return bitmap;
		} catch (Exception e) {
			android.util.Log.e("download img err", url + e.toString());
			return null;
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadImageAndStore(String folder, String url, boolean fullName) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = "";
			if (fullName) {
				fileName = url.substring(url.lastIndexOf("/") + 1);
			} else
				fileName = StringUtils.getPictureName(url);
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
		} catch (Exception e) {
			android.util.Log.e("download img err", url + e.toString());
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadImageAndStore(String folder, String url) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = StringUtils.getPictureName(url);

			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
		} catch (Exception e) {
			android.util.Log.e("download img err", url + e.toString());
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadIconAndStore(String folder, String url, String name, boolean fullName) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = "";
			if (name != null && name.length() > 0) {
				fileName = name;
			} else {
				if (fullName) {
					fileName = url.substring(url.lastIndexOf("/") + 1);
				} else
					fileName = getPictureName(url);
			}
			java.io.File icon = new java.io.File(folder + fileName);
			if (icon.exists()) {
				return;
			}
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(outFilename));
		} catch (Exception e) {
			android.util.Log.e("download img err", url + e.toString());
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static android.graphics.Bitmap loadImageAndStore(String folder, String url, String name) {
		java.io.FileOutputStream outputStream = null;
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = "";
			if (name != null && name.length() > 0) {
				fileName = name;
			} else {
				fileName = getPictureName(url);
			}
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			FileUtils.MakeDir(folder);
			String outFilename = folder + fileName;
			outputStream = new java.io.FileOutputStream(outFilename);
			bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream);
			outputStream.flush();
			outputStream.close();
			return bitmap;
		} catch (Exception e) {
			android.util.Log.e("download img err", url + e.toString());
			return null;
		}
	}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static android.graphics.drawable.Drawable loadImageFromUrlAndStore(String folder, String url, boolean fullName) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String fileName = "";
			if (fullName) {
				fileName = url.substring(url.lastIndexOf("/") + 1);
			} else
				fileName = StringUtils.getPictureName(url);
			@SuppressWarnings("deprecation")
			String encodeFileName = java.net.URLEncoder.encode(fileName);
			java.net.URL imageUrl = new java.net.URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((java.io.InputStream) imageUrl.openStream());
			android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(data, 0, data.length);
			String status = android.os.Environment.getExternalStorageState();
			if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
				FileUtils.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, new java.io.FileOutputStream(outFilename));
				android.graphics.Bitmap bitmapCompress = android.graphics.BitmapFactory.decodeFile(outFilename);
				@SuppressWarnings("deprecation")
				android.graphics.drawable.Drawable drawable = new android.graphics.drawable.BitmapDrawable(bitmapCompress);
				return drawable;
			}
		} catch (Exception e) {
			android.util.Log.e("download_img_err", e.toString());
		}
		return null;
	}

	/**
	 * 读取输入流
	 */
	private static byte[] readInputStream(java.io.InputStream inStream) throws Exception {
		java.io.ByteArrayOutputStream outSteam = new java.io.ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public interface LoadingListener {
		void finish(String path);

		void error(String errorinfo);
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static android.graphics.Bitmap getBitmapFromView(android.view.View view) {
		view.destroyDrawingCache();
		view.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED), android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		android.graphics.Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 从 url 得到这个URL
	 * 
	 * @param url
	 *            the URI of picture
	 * @return name removed the extension
	 */
	public static String getPictureName(String url) {
		if (null != url) {
			String x = url.substring(url.lastIndexOf("/") + 1);
			if (x.contains(".")) {
				return x.substring(0, x.lastIndexOf("."));
			}
			return x;
		}
		return url;
	}
}
