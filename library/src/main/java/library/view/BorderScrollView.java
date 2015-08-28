package library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 
 * ClassName: BorderScrollView <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午7:53:00 <br/>
 * 边境滚动视图
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class BorderScrollView extends android.widget.ScrollView {

    private library.view.BorderScrollView.OnBorderListener onBorderListener;
    private android.view.View contentView;

    public BorderScrollView(android.content.Context context) {
        super(context);
    }

    public BorderScrollView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderScrollView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        doOnBorderListener();
    }

    public void setOnBorderListener(final library.view.BorderScrollView.OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
        if (onBorderListener == null) {
            return;
        }

        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * OnBorderListener, Called when scroll to top or bottom
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2013-5-22
     */
    public static interface OnBorderListener {

        /**
         * Called when scroll to bottom
         */
        public void onBottom();

        /**
         * Called when scroll to top
         */
        public void onTop();
    }

    private void doOnBorderListener() {
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            if (onBorderListener != null) {
                onBorderListener.onBottom();
            }
        } else if (getScrollY() == 0) {
            if (onBorderListener != null) {
                onBorderListener.onTop();
            }
        }
    }
}
