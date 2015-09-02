/**
 * Project Name:DingDangKaUser
 * File Name:ScrollViewExtend.java
 * Package Name:com.ddkuclient.view
 * Date:2014年10月23日下午2:25:51
 * Copyright (c) 2014, zhuxiaohao All Rights Reserved.
 *
 */

package library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ClassName:ScrollViewExtend <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年10月23日 下午2:25:51 <br/>
 *  自定义 ScrollView
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see
 */
public class ScrollViewExtend extends ScrollView {
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            xDistance = yDistance = 0f;
            xLast = ev.getX();
            yLast = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float curX = ev.getX();
            final float curY = ev.getY();

            xDistance += Math.abs(curX - xLast);
            yDistance += Math.abs(curY - yLast);
            xLast = curX;
            yLast = curY;

            if (xDistance > yDistance) {
                return false;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
