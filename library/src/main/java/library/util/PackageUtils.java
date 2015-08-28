package library.util;


import library.util.ShellUtils.CommandResult;

/**
 * ClassName: PackageUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午4:57:34 <br/>
 * Android包相关工具类， 可用于(root)安装应用、 (root)卸载应用、 判断是否系统应用等， 如： install(Context,
 * String) 安装应用，如果是系统应用或已经root，则静默安装，否则一般安装 uninstall(Context, String)
 * 卸载应用，如果是系统应用或已经root，则静默卸载，否则一般卸载 isSystemApplication(Context, String)
 * 判断应用是否为系统应用
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class PackageUtils {

	public static final String TAG = "PackageUtils";

	private PackageUtils() {
		throw new AssertionError();
	}

	/**
	 * 应用程序安装位置设置值,相同 {@link #PackageHelper}
	 */
	public static final int APP_INSTALL_AUTO = 0;
	public static final int APP_INSTALL_INTERNAL = 1;
	public static final int APP_INSTALL_EXTERNAL = 2;

	/**
	 * 显示安装条件
	 * <ul>
	 * <li>if system application or rooted, see
	 * {@link #installSilent(Context, String)}</li>
	 * <li>else see {@link #installNormal(Context, String)}</li>
	 * </ul>
	 * 
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static final int install(android.content.Context context, String filePath) {
		if (library.util.PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
			return installSilent(context, filePath);
		}
		return installNormal(context, filePath) ? INSTALL_SUCCEEDED : INSTALL_FAILED_INVALID_URI;
	}

	/**
	 * 安装包正常系统的意图
	 * 
	 * @param context
	 * @param filePath
	 *            file path of package
	 * @return whether apk exist
	 */
	public static boolean installNormal(android.content.Context context, String filePath) {
		android.content.Intent i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
		java.io.File file = new java.io.File(filePath);
		if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
			return false;
		}

		i.setDataAndType(android.net.Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 安装包沉默的根
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>Don't call this on the ui thread, it may costs some times.</li>
	 * <li>You should add <strong>android.permission.INSTALL_PACKAGES</strong>
	 * in manifest, so no need to request root permission, if you are system
	 * app.</li>
	 * <li>Default pm install params is "-r".</li>
	 * </ul>
	 * 
	 * @param context
	 * @param filePath
	 *            file path of package
	 * @return {@link PackageUtils#INSTALL_SUCCEEDED} means install success,
	 *         other means failed. details see {@link PackageUtils}
	 *         .INSTALL_FAILED_*. same to {@link PackageManager}.INSTALL_*
	 * @see #installSilent(Context, String, String)
	 */
	public static int installSilent(android.content.Context context, String filePath) {
		return installSilent(context, filePath, " -r " + getInstallLocationParams());
	}

	/**
	 * 安装包沉默的根
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>Don't call this on the ui thread, it may costs some times.</li>
	 * <li>You should add <strong>android.permission.INSTALL_PACKAGES</strong>
	 * in manifest, so no need to request root permission, if you are system
	 * app.</li>
	 * </ul>
	 * 
	 * @param context
	 * @param filePath
	 *            file path of package
	 * @param pmParams
	 *            pm install params
	 * @return {@link PackageUtils#INSTALL_SUCCEEDED} means install success,
	 *         other means failed. details see {@link PackageUtils}
	 *         .INSTALL_FAILED_*. same to {@link PackageManager}.INSTALL_*
	 */
	public static int installSilent(android.content.Context context, String filePath, String pmParams) {
		if (filePath == null || filePath.length() == 0) {
			return INSTALL_FAILED_INVALID_URI;
		}

		java.io.File file = new java.io.File(filePath);
		if (file == null || file.length() <= 0 || !file.exists() || !file.isFile()) {
			return INSTALL_FAILED_INVALID_URI;
		}

		/**
		 * if context is system app, don't need root permission, but should add
		 * <uses-permission android:name="android.permission.INSTALL_PACKAGES"
		 * /> in mainfest
		 **/
		StringBuilder command = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ").append(pmParams == null ? "" : pmParams).append(" ").append(filePath.replace(" ", "\\ "));
		CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
		if (commandResult.successMsg != null && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
			return INSTALL_SUCCEEDED;
		}

		android.util.Log.e(TAG, new StringBuilder().append("installSilent successMsg:").append(commandResult.successMsg).append(", ErrorMsg:").append(commandResult.errorMsg).toString());
		if (commandResult.errorMsg == null) {
			return INSTALL_FAILED_OTHER;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_ALREADY_EXISTS")) {
			return INSTALL_FAILED_ALREADY_EXISTS;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_APK")) {
			return INSTALL_FAILED_INVALID_APK;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_URI")) {
			return INSTALL_FAILED_INVALID_URI;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE")) {
			return INSTALL_FAILED_INSUFFICIENT_STORAGE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE")) {
			return INSTALL_FAILED_DUPLICATE_PACKAGE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER")) {
			return INSTALL_FAILED_NO_SHARED_USER;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE")) {
			return INSTALL_FAILED_UPDATE_INCOMPATIBLE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE")) {
			return INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY")) {
			return INSTALL_FAILED_MISSING_SHARED_LIBRARY;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE")) {
			return INSTALL_FAILED_REPLACE_COULDNT_DELETE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_DEXOPT")) {
			return INSTALL_FAILED_DEXOPT;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_OLDER_SDK")) {
			return INSTALL_FAILED_OLDER_SDK;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER")) {
			return INSTALL_FAILED_CONFLICTING_PROVIDER;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_NEWER_SDK")) {
			return INSTALL_FAILED_NEWER_SDK;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_TEST_ONLY")) {
			return INSTALL_FAILED_TEST_ONLY;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE")) {
			return INSTALL_FAILED_CPU_ABI_INCOMPATIBLE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE")) {
			return INSTALL_FAILED_MISSING_FEATURE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR")) {
			return INSTALL_FAILED_CONTAINER_ERROR;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION")) {
			return INSTALL_FAILED_INVALID_INSTALL_LOCATION;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE")) {
			return INSTALL_FAILED_MEDIA_UNAVAILABLE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT")) {
			return INSTALL_FAILED_VERIFICATION_TIMEOUT;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE")) {
			return INSTALL_FAILED_VERIFICATION_FAILURE;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED")) {
			return INSTALL_FAILED_PACKAGE_CHANGED;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_UID_CHANGED")) {
			return INSTALL_FAILED_UID_CHANGED;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK")) {
			return INSTALL_PARSE_FAILED_NOT_APK;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST")) {
			return INSTALL_PARSE_FAILED_BAD_MANIFEST;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION")) {
			return INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES")) {
			return INSTALL_PARSE_FAILED_NO_CERTIFICATES;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES")) {
			return INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING")) {
			return INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME")) {
			return INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID")) {
			return INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED")) {
			return INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
		}
		if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY")) {
			return INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
		}
		if (commandResult.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR")) {
			return INSTALL_FAILED_INTERNAL_ERROR;
		}
		return INSTALL_FAILED_OTHER;
	}

	/**
	 * 显示卸载条件下
	 * <ul>
	 * <li>if system application or rooted, see
	 * {@link #uninstallSilent(Context, String)}</li>
	 * <li>else see {@link #uninstallNormal(Context, String)}</li>
	 * </ul>
	 * 
	 * @param context
	 * @param packageName
	 *            package name of app
	 * @return whether package name is empty
	 * @return
	 */
	public static final int uninstall(android.content.Context context, String packageName) {
		if (library.util.PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
			return uninstallSilent(context, packageName);
		}
		return uninstallNormal(context, packageName) ? DELETE_SUCCEEDED : DELETE_FAILED_INVALID_PACKAGE;
	}

	/**
	 * 卸载包正常系统的意图
	 * 
	 * @param context
	 * @param packageName
	 *            package name of app
	 * @return whether package name is empty
	 */
	public static boolean uninstallNormal(android.content.Context context, String packageName) {
		if (packageName == null || packageName.length() == 0) {
			return false;
		}

		android.content.Intent i = new android.content.Intent(android.content.Intent.ACTION_DELETE, android.net.Uri.parse(new StringBuilder(32).append("package:").append(packageName).toString()));
		i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 卸载包和清除数据应用沉默的根
	 * 
	 * @param context
	 * @param packageName
	 *            package name of app
	 * @return
	 * @see #uninstallSilent(Context, String, boolean)
	 */
	public static int uninstallSilent(android.content.Context context, String packageName) {
		return uninstallSilent(context, packageName, true);
	}

	/**
	 * 由根用户卸载包沉默
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>Don't call this on the ui thread, it may costs some times.</li>
	 * <li>You should add <strong>android.permission.DELETE_PACKAGES</strong> in
	 * manifest, so no need to request root permission, if you are system app.</li>
	 * </ul>
	 * 
	 * @param context
	 *            file path of package
	 * @param packageName
	 *            package name of app
	 * @param isKeepData
	 *            whether keep the data and cache directories around after
	 *            package removal
	 * @return <ul>
	 *         <li>{@link #DELETE_SUCCEEDED} means uninstall success</li>
	 *         <li>{@link #DELETE_FAILED_INTERNAL_ERROR} means internal error</li>
	 *         <li>{@link #DELETE_FAILED_INVALID_PACKAGE} means package name
	 *         error</li>
	 *         <li>{@link #DELETE_FAILED_PERMISSION_DENIED} means permission
	 *         denied</li>
	 */
	public static int uninstallSilent(android.content.Context context, String packageName, boolean isKeepData) {
		if (packageName == null || packageName.length() == 0) {
			return DELETE_FAILED_INVALID_PACKAGE;
		}

		/**
		 * if context is system app, don't need root permission, but should add
		 * <uses-permission android:name="android.permission.DELETE_PACKAGES" />
		 * in mainfest
		 **/
		StringBuilder command = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall").append(isKeepData ? " -k " : " ").append(packageName.replace(" ", "\\ "));
		CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
		if (commandResult.successMsg != null && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
			return DELETE_SUCCEEDED;
		}
		android.util.Log.e(TAG, new StringBuilder().append("uninstallSilent successMsg:").append(commandResult.successMsg).append(", ErrorMsg:").append(commandResult.errorMsg).toString());
		if (commandResult.errorMsg == null) {
			return DELETE_FAILED_INTERNAL_ERROR;
		}
		if (commandResult.errorMsg.contains("Permission denied")) {
			return DELETE_FAILED_PERMISSION_DENIED;
		}
		return DELETE_FAILED_INTERNAL_ERROR;
	}

	/**
	 * 是否系统应用程序上下文
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSystemApplication(android.content.Context context) {
		if (context == null) {
			return false;
		}

		return isSystemApplication(context, context.getPackageName());
	}

	/**
	 * packageName是否系统应用程序
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApplication(android.content.Context context, String packageName) {
		if (context == null) {
			return false;
		}

		return isSystemApplication(context.getPackageManager(), packageName);
	}

	/**
	 * packageName是否系统应用程序
	 * 
	 * @param packageManager
	 * @param packageName
	 * @return <ul>
	 *         <li>if packageManager is null, return false</li>
	 *         <li>if package name is null or is empty, return false</li>
	 *         <li>if package name not exit, return false</li>
	 *         <li>if package name exit, but not system app, return false</li>
	 *         <li>else return true</li>
	 *         </ul>
	 */
	public static boolean isSystemApplication(android.content.pm.PackageManager packageManager, String packageName) {
		if (packageManager == null || packageName == null || packageName.length() == 0) {
			return false;
		}

		try {
			android.content.pm.ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
			return (app != null && (app.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) > 0);
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否应用whost包的名字是packageName是堆栈的顶部
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>You should add <strong>android.permission.GET_TASKS</strong> in
	 * manifest</li>
	 * </ul>
	 * 
	 * @param context
	 * @param packageName
	 * @return if params error or task stack is null, return null, otherwise
	 *         retun whether the app is on the top of stack
	 */
	public static Boolean isTopActivity(android.content.Context context, String packageName) {
		if (context == null || StringUtils.isEmpty(packageName)) {
			return null;
		}

		android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(android.content.Context.ACTIVITY_SERVICE);
		java.util.List<android.app.ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (ListUtils.isEmpty(tasksInfo)) {
			return null;
		}
		try {
			return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得应用程序版本代码
	 * 
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(android.content.Context context) {
		if (context != null) {
			android.content.pm.PackageManager pm = context.getPackageManager();
			if (pm != null) {
				android.content.pm.PackageInfo pi;
				try {
					pi = pm.getPackageInfo(context.getPackageName(), 0);
					if (pi != null) {
						return pi.versionCode;
					}
				} catch (android.content.pm.PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	/**
	 * 得到系统安装位置< br / >
	 * 可以由系统设置菜单设置- >存储- >优先安装位置
	 * 
	 * @return
	 * @see {@link IPackageManager#getInstallLocation()}
	 */
	public static int getInstallLocation() {
		CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
		if (commandResult.result == 0 && commandResult.successMsg != null && commandResult.successMsg.length() > 0) {
			try {
				int location = Integer.parseInt(commandResult.successMsg.substring(0, 1));
				switch (location) {
				case APP_INSTALL_INTERNAL:
					return APP_INSTALL_INTERNAL;
				case APP_INSTALL_EXTERNAL:
					return APP_INSTALL_EXTERNAL;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				android.util.Log.e(TAG, "pm get-install-location error");
			}
		}
		return APP_INSTALL_AUTO;
	}

	/**
	 * 得到参数点安装位置
	 * 
	 * @return
	 */
	private static String getInstallLocationParams() {
		int location = getInstallLocation();
		switch (location) {
		case APP_INSTALL_INTERNAL:
			return "-f";
		case APP_INSTALL_EXTERNAL:
			return "-s";
		}
		return "";
	}

	/**
	 * 开始InstalledAppDetails活动
	 * 
	 * @param context
	 * @param packageName
	 */
	public static void startInstalledAppDetails(android.content.Context context, String packageName) {
		android.content.Intent intent = new android.content.Intent();
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
			intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setData(android.net.Uri.fromParts("package", packageName, null));
		} else {
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			intent.putExtra((sdkVersion == android.os.Build.VERSION_CODES.FROYO ? "pkg" : "com.android.settings.ApplicationPkgName"), packageName);
		}
		intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 安装返回代码< br / >
	 * 安装成功。
	 */
	public static final int INSTALL_SUCCEEDED = 1;
	/**
	 * 安装返回代码< br / >
	 * 包已经安装。
	 */
	public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;

	/**
	 * 安装返回代码< br / >
	 * 包存档文件是无效的。
	 */
	public static final int INSTALL_FAILED_INVALID_APK = -2;

	/**
	 * 　　*安装返回代码< br / >
	 * 　　*通过URI是无效的。
	 */
	public static final int INSTALL_FAILED_INVALID_URI = -3;

	/**
	 * 　　*安装返回代码< br / >
	 * 　　*包管理器服务发现设备没有足够的 　　*存储空间安装应用程序。
	 */
	public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;

	/**
	 * 　　*安装返回代码< br / >
	 * 　　*一个包已经安装使用相同的名称。
	 */
	public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;

	/**
	 * 　　*安装返回代码< br / >
	 * 　　*请求的共享的用户不存在。
	 */
	public static final int INSTALL_FAILED_NO_SHARED_USER = -6;

	/**
	 * 　　*安装返回代码< br / >
	 * 　　同名*之前安装的包有不同的签名 　　*比新包(旧的包的数据并没有删除)。
	 */
	public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;

	/**
　　*安装返回代码< br / >
　　*新包请求一个共享的用户已经安装
　　*设备并没有匹配的签名。
	 */
	public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;

	/**
　　*安装返回代码< br / >
　　*新包使用共享库不可用。
	 */
	public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;

	/**
　　*安装返回代码< br / >
　　*新包使用共享库不可用。
	 */
	public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;

	/**
　　*安装返回代码< br / >
　　*新包失败,同时优化和验证其敏捷的文件,
　　*因为没有足够的存储或验证失败。
	 */
	public static final int INSTALL_FAILED_DEXOPT = -11;

	/**
　　*安装返回代码< br / >
　　*新包的失败是因为当前SDK版本是老
　　*所需要的包。
	 */
	public static final int INSTALL_FAILED_OLDER_SDK = -12;

	/**
	　　*安装返回代码< br / >
　　*新包的失败是因为它包含的内容提供者
　　*提供同样的权威已经安装在系统中。
	 */
	public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;

	/**
	 　　*安装返回代码< br / >
　　*新包的失败是因为当前SDK版本更新
　　*所需要的包。
	 */
	public static final int INSTALL_FAILED_NEWER_SDK = -14;

	/**
	　　*安装返回代码< br / >
　　*新包的失败是因为指定,这是一个测试
　　*包和调用者没有提供{ @link # INSTALL_ALLOW_TEST }
　　*旗帜。
	 */
	public static final int INSTALL_FAILED_TEST_ONLY = -15;

	/**
	　　*安装返回代码< br / >
　　*包安装包含本机代码,但没有
　　*与设备的CPU_ABI兼容。
	 */
	public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;

	/**
	　　*安装返回代码< br / >
　　*新包使用功能不可用。
	 */
	public static final int INSTALL_FAILED_MISSING_FEATURE = -17;

	/**
　　*安装返回代码< br / >
　　*安全容器挂载点无法访问外部媒体。
	 */
	public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;

	/**
	　　*安装返回代码< br / >
　　*新包不能被安装在指定的安装位置。
	 */
	public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;

	/**
	　　*安装返回代码< br / >
　　*新包不能被安装在指定的安装位置
　　*因为媒体是不可用的。
	 */
	public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;

	/**
　　*安装返回代码< br / >
　　*新包不能安装,因为验证超时。
	 */
	public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;

	/**
　　*安装返回代码< br / >
　　*新包不能安装,因为没有验证
　　*成功。
	 */
	public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;

	/**
　　*安装返回代码< br / >
　　*包从调用程序的预期。
	 */
	public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;

	/**
　　*安装返回代码< br / >
　　*新包分配一个与过去不同的UID。
	 */
	public static final int INSTALL_FAILED_UID_CHANGED = -24;

	/**
　　*安装返回代码< br / >
　　*如果给解析器不是一个文件的路径,或不结束
　　*预期”。apk的扩展。
	 */
	public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;

	/**
　　*安装返回代码< br / >
　　*如果解析器无法检索AndroidManifest。xml文件。
	 */
	public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;

	/**
　　*安装返回代码< br / >
　　如果解析器遇到了一个意想不到的异常.
	 */
	public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;

	/**
	　　*安装返回代码< br / >
　　*如果. apk解析器并没有发现任何证书。
	 */
	public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;

	/**
	 * Installation return code<br/>
	 * if the parser found inconsistent certificates on the files in the .apk.
	 */
	public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;

	/**
	 * Installation return code<br/>
	 * if the parser encountered a CertificateEncodingException in one of the
	 * files in the .apk.
	 */
	public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;

	/**
	 * Installation return code<br/>
	 * if the parser encountered a bad or missing package name in the manifest.
	 */
	public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;

	/**
	 * Installation return code<br/>
	 * if the parser encountered a bad shared user id name in the manifest.
	 */
	public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;

	/**
	 * Installation return code<br/>
	 * if the parser encountered some structural problem in the manifest.
	 */
	public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;

	/**
	 * Installation return code<br/>
	 * if the parser did not find any actionable tags (instrumentation or
	 * application) in the manifest.
	 */
	public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;

	/**
	 * Installation return code<br/>
	 * if the system failed to install the package because of system issues.
	 */
	public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
	/**
	 * Installation return code<br/>
	 * other reason
	 */
	public static final int INSTALL_FAILED_OTHER = -1000000;

	/**
	 * Uninstall return code<br/>
	 * uninstall success.
	 */
	public static final int DELETE_SUCCEEDED = 1;

	/**
	 * Uninstall return code<br/>
	 * uninstall fail if the system failed to delete the package for an
	 * unspecified reason.
	 */
	public static final int DELETE_FAILED_INTERNAL_ERROR = -1;

	/**
	 * Uninstall return code<br/>
	 * uninstall fail if the system failed to delete the package because it is
	 * the active DevicePolicy manager.
	 */
	public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;

	/**
	 * Uninstall return code<br/>
	 * uninstall fail if pcakge name is invalid
	 */
	public static final int DELETE_FAILED_INVALID_PACKAGE = -3;

	/**
	 * Uninstall return code<br/>
	 * uninstall fail if permission denied
	 */
	public static final int DELETE_FAILED_PERMISSION_DENIED = -4;
}
