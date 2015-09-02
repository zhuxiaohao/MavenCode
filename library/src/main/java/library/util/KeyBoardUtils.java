/**
 * Project Name:android-common-tools
 * File Name:KeyBoardUtils.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:37:10
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

/**
 * ClassName:KeyBoardUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年3月2日 上午10:37:10 <br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see 打开或关闭软键盘
 */
public class KeyBoardUtils {
    /**
     * 打开软键盘
     * 
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void openKeybord(android.widget.EditText mEditText, android.content.Context mContext) {
        android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) mContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, android.view.inputmethod.InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED, android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * 
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void closeKeybord(android.widget.EditText mEditText, android.content.Context mContext) {
        android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) mContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
