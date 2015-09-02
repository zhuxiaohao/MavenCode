package library.util;

import java.io.UnsupportedEncodingException;

/**
 * ClassName: StringUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:00:10 <br/>
 * String工具类，可用于常见字符串操作，如： 
 * isEmpty(String str) 判断字符串是否为空或长度为0
 * isBlank(String str) 判断字符串是否为空或长度为0 或由空格组成 utf8Encode(String str) 以utf-8格式编码
 * capitalizeFirstLetter(String str) 首字母大写
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class StringUtils extends DeviceUtils {

//	private StringUtils() {
//		throw new AssertionError();
//	}

    /**
     * is null or its length is 0 or it is made by space
     * 
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return
     *         true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * 
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0, return true, else return
     *         false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * compare two string
     * 
     * @param actual
     * @param expected
     * @return
     * @see ObjectUtils#isEquals(Object, Object)
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }

    /**
     * null Object to empty string
     * 
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * capitalize first letter
     * 
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length()).append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * 
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     *             if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return java.net.URLEncoder.encode(str, "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     * 
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return java.net.URLEncoder.encode(str, "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * 
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     * 
     * @param href
     * @return <ul>
     *         <li>if href is null, return ""</li>
     *         <li>if not match regx, return source</li>
     *         <li>return the last string that match regx</li>
     *         </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        java.util.regex.Pattern hrefPattern = java.util.regex.Pattern.compile(hrefReg, java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return library.util.StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * 
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * 
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * <pre>
     * 复制
     * </pre>
     * 
     * @param context
     * @param txt
     */
    @SuppressWarnings("deprecation")
	@android.annotation.SuppressLint("NewApi")
    public static void copy(android.content.Context context, String txt) {
        int v = getApiVersion();
        if (v < 11) {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(txt);
        } else {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            android.content.ClipData cd = android.content.ClipData.newPlainText("label", txt);
            clipboardManager.setPrimaryClip(cd);
        }
    }

    /**
     * <pre>
     * 获取复制的数据
     * </pre>
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
	@android.annotation.SuppressLint("NewApi")
    public static String getCopyString(android.content.Context context) {
        int v = getApiVersion();
        if (v < 11) {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            return clipboardManager.getText().toString();
        } else {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            return clipboardManager.getText().toString();
        }
    }

    /**
     * 过滤“null”或null
     * 
     * @param source
     *            input
     * @return source or ""
     */
    public static String stringFilter(String source) {
        source = (source == null ? "" : source);
        source = ("null".equals(source) ? "" : source);
        return source;
    }

    /**
     * 过滤器”或null
     * 
     * @param source
     * @return
     */
    public static boolean isNullOrEmpty(String source) {
        if (null == source) {
            return true;
        } else if (source.length() == 0) {
            return true;
        } else
            return false;
    }

    /**
     * 如果JSONObject是null或0-length返回true。
     * @param str    检查字符串
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(org.json.JSONObject object) {
        if (object == null || object == org.json.JSONObject.NULL || object.length() == 0)
            return true;
        else
            return false;
    }

    /**
     *如果JSONArray是null或0-length返回true。
     * @param str
     *           检查字符串
     * @return 真的如果str是null或零长度
     */
    public static boolean isEmpty(org.json.JSONArray object) {
        if (object == null || object == org.json.JSONObject.NULL || object.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 得到这幅画的名字从URL
     * @param url URI的图片
     * @return 名字删除扩展
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

    /**
     *获取文件名称
     * 
     * @param url    文件的URL
     * @return
     */
    public static String getFileName(String url) {
        if (null != url) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return url;
    }

    /**
     * 取代“< img / >”的标签的HTML数据
     * @param content   HTML数据
     * @param imgs_path 当地的道路图像
     * @return has been replace HTML file:///android_asset/pic.jpg
     */
    public static String replaceImgTag(int readingid, String content, String[] imgs_path) {
        if (imgs_path == null || content == null) {
            return null;
        }
        String regex = "<!--image#[0-9]+-->";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(content);
        if (matcher == null) {
            return null;
        }
        int n = 0;
        while (matcher.find()) {
            String targt = matcher.group();
            String re = "<img style=\"max-width:95%; box-shadow: 0px 0px 6px #000;\" src =\"" + "图片路径" + readingid + "/" + imgs_path[n] + "\"/>";
            content = content.replace(targt, re);
            n++;
        }
        return content;
    }

    /**
     * 取代“< img / >”的标签的HTML数据
     * 
     * @param content HTML data
     * @param imgs_paththe local path of images
     * @return has been replace HTML
     * 
     */
    public static String replaceImgToDefualt(int readingid, String content, String[] imgs_path) {
        if (imgs_path == null || content == null) {
            return null;
        }
        String regex = "<!--image#[0-9]+-->";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(content);
        if (matcher == null) {
            return null;
        }
        @SuppressWarnings("unused")
		int n = 0;
        while (matcher.find()) {
            String targt = matcher.group();
            String re = "<p style=\"text-align:center; margin:0; border:1px solid #f00;\"><img style=\"width:95%; box-shadow: 0px 0px 6px #000;\" src =\"file:///android_asset/pic_load_def.png\"/></p>";
            content = content.replace(targt, re);
            n++;
        }
        return content;
    }

    /**
     * 恢复照片的名字与扩展
     * 
     * @param aName
     *            picture name
     * @param bName
     *            extension
     * @return a full name
     */
    public static String replaceLastNameForPic(String aName, String bName) {
        if (aName != null && bName != null && !"".equals(aName) && !"".equals(bName)) {
            bName = aName.substring(0, aName.lastIndexOf(".") + 1) + bName;
        }
        return bName;
    }

    /**
     * URLencoding一个字符串数据集
     * 
     * @param str
     * @param charsetName
     *            <li>ISO-8859-1 <li>US-ASCII <li>UTF-16 <li>UTF-16BE <li>
     *            UTF-16LE <li>UTF-8
     * @return
     */
    public static String getURLEncode(String str, String charsetName) {
        if (!isNullOrEmpty(str)) {
            try {
                return java.net.URLEncoder.encode(str, charsetName);
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 功能：去掉所有的<*>标记,去除html标签
     * 
     * @param content
     * @return
     */
    public static String removeTagFromText(String content) {
        java.util.regex.Pattern p = null;
        java.util.regex.Matcher m = null;
        String value = null;
        // 去掉<>标签
        p = java.util.regex.Pattern.compile("(<[^>]*>)");
        m = p.matcher(content);
        String temp = content;
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value, "");
        }
        // 去掉换行或回车符号
        p = java.util.regex.Pattern.compile("(/r+|/n+)");
        m = p.matcher(temp);
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value, " ");
        }
        return temp;
    }

    /**
     * 返回当前程序版本名称
     */
    public static String getAppVersionName(android.content.Context context) {
        String versionName = "1.0.0";
        try {
            // Get the package info
            versionName = context.getPackageManager().getPackageInfo("com.vyuan.ddk.dingdangkauser", 0).versionName;
        } catch (Exception e) {
            return versionName;
        }
        return versionName;
    }

    /**
     * 返回当前程序版本名称
     */
    public static int getAppVersionNameInt(android.content.Context context) {
        int versionNameInt = 100;
        try {
            // Get the package info
            String versionName = context.getPackageManager().getPackageInfo("com.vyuan.ddk.dingdangkauser", 0).versionName;
            if (!isEmpty(versionName) && versionName.contains(".")) {
                String[] a = versionName.split("\\.");
                String b = "";
                for (int i = 0; i < a.length; i++) {
                    b = b + a[i];
                }
                versionNameInt = Integer.parseInt(b);
            }
        } catch (Exception e) {
            return versionNameInt;
        }
        return versionNameInt;
    }

    /**
     * 返回当前程序版本名称
     */
    public static int parseArrayToInt(String str) {
        int versionNameInt = 100;
        try {
            // Get the package info
            if (!isEmpty(str) && str.contains(".")) {
                String[] a = str.split("\\.");
                String b = "";
                for (int i = 0; i < a.length; i++) {
                    b = b + a[i];
                }
                versionNameInt = Integer.parseInt(b);
            }
        } catch (Exception e) {
            return versionNameInt;
        }
        return versionNameInt;
    }

    /**
     * 返回当前程序版本名称
     * 
     * @param context
     * @param packageName
     *            包名
     * @return
     */
    public static int getAppVersionNameInt(android.content.Context context, String packageName) {
        int versionNameInt = 3;
        context.getPackageName();
        try {
            // Get the package info
            String versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
            if (!isEmpty(versionName) && versionName.contains(".")) {
                String[] a = versionName.split("\\.");
                String b = "";
                for (int i = 0; i < a.length; i++) {
                    b = b + a[i];
                }
                versionNameInt = Integer.parseInt(b);
            }
        } catch (Exception e) {
            return versionNameInt;
        }
        return versionNameInt;
    }

    /**
     * 判断两个字符串是否相等
     * 
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return cs1 == null ? cs2 == null : cs1.equals(cs2);
    }
}
