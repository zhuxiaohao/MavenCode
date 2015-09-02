/**
 * Project Name:androidTools
 * File Name:GetActivityImage.java
 * Package Name:com.zhuxiaohao.common.util
 * Date:2015-4-30下午3:31:23
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

/**
 * ClassName:GetActivityImage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-30 下午3:31:23 <br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see
 */
public class ScreenShot {

	private static android.graphics.Bitmap takeScreenShot(android.app.Activity activity) {
		// View是你需要截图的View
		android.view.View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		android.graphics.Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		android.graphics.Rect frame = new android.graphics.Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		android.graphics.Bitmap b = android.graphics.Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	private static void savePic(android.graphics.Bitmap b, java.io.File filePath) {
		java.io.FileOutputStream fos = null;
		try {
			fos = new java.io.FileOutputStream(filePath);
			if (null != fos) {
				b.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (java.io.FileNotFoundException e) {
			// e.printStackTrace();
		} catch (java.io.IOException e) {
			// e.printStackTrace();
		}
	}

	public static void shoot(android.app.Activity a, java.io.File filePath) {
		if (filePath == null) {
			return;
		}
		if (!filePath.getParentFile().exists()) {
			filePath.getParentFile().mkdirs();
		}
		library.util.ScreenShot.savePic(library.util.ScreenShot.takeScreenShot(a), filePath);
	}
}
