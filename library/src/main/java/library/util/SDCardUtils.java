/**
 * Project Name:android-common-tools
 * File Name:SDCardUtils.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:32:54
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

/**
 * ClassName:SDCardUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年3月2日 上午10:32:54 <br/>
 * @author   chenhao
 * @version  
 * @since    JDK 1.6
 * @see 	SD卡相关辅助类 
 */
import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SDCardUtils {
	private SDCardUtils() {
		/* 不能被实例化 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 
	 * isSDCardEnable:(判断SDCard是否可用). <br/>
	 * 
	 * @author chenhao
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isSDCardEnable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

	}

	/**
	 * 
	 * getSDCardPath:(获取SD卡路径). <br/>
	 * 
	 * @author chenhao
	 * @return
	 * @since JDK 1.6
	 */
	public static String getSDCardPath() {
		return android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator;
	}

	/**
	 * 
	 * getSDCardAllSize:(获取SD卡的剩余容量 单位byte). <br/>
	 * 
	 * @author chenhao
	 * @return
	 * @since JDK 1.6
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			android.os.StatFs stat = new android.os.StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			@SuppressWarnings("deprecation")
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			@SuppressWarnings("deprecation")
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 
	 * getFreeBytes:(获取指定路径所在空间的剩余可用容量字节数，单位byte). <br/>
	 * 
	 * @author chenhao
	 * @param filePath
	 *            filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 * @since JDK 1.6
	 */
	@SuppressWarnings("deprecation")
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = android.os.Environment.getDataDirectory().getAbsolutePath();
		}
		android.os.StatFs stat = new android.os.StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 
	 * getRootDirectoryPath:(获取系统存储路径). <br/>
	 * 
	 * @author chenhao
	 * @return
	 * @since JDK 1.6
	 */
	public static String getRootDirectoryPath() {
		return android.os.Environment.getRootDirectory().getAbsolutePath();
	}

}
