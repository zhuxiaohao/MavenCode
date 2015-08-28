/**
 * Project Name:DingDangKaUser
 * File Name:ListViewForScrollView.java
 * Package Name:com.ddkuclient.view
 * Date:2014年11月6日下午2:45:31
 * Copyright (c) 2014, zhuxiaohao All Rights Reserved.
 *
 */

package library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ClassName:ListViewForScrollView <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年11月6日 下午2:45:31 <br/>
 * 重写该方法，达到使ListView适应ScrollView的效果
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see
 */
public class ListViewForScrollView extends android.widget.ListView {

    public ListViewForScrollView(android.content.Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ListViewForScrollView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**

     * 重写该方法，达到使ListView适应ScrollView的效果

     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = android.view.View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, android.view.View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
