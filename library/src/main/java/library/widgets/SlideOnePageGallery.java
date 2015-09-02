package library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 
 * ClassName: SlideOnePageGallery <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午7:55:15 <br/>
 * 滑一页的画廊，播放每个幻灯片
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
@SuppressWarnings("deprecation")
public class SlideOnePageGallery extends Gallery {

    public SlideOnePageGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlideOnePageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideOnePageGallery(Context context) {
        super(context);
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int kEvent;
        if (isScrollingLeft(e1, e2)) {
            // Check if scrolling left
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            // Otherwise scrolling right
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }

        onKeyDown(kEvent, null);
        return true;
    }
}
