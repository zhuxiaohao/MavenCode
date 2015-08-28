package library.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * ClassName: ViewUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午7:52:14 <br/>
 * 视图工具类
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    /**
     * get ListView height according to every children
     * 
     * @param view
     * @return
     */
    public static int getListViewHeightBasedOnChildren(android.widget.ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        android.widget.ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }
    
    private static final String CLASS_NAME_GRID_VIEW        = "android.widget.GridView";
    private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";

    /**
     * get GridView vertical spacing
     * 
     * @param view
     * @return
     */
    public static int getGridViewVerticalSpacing(android.widget.GridView view) {
        // get mVerticalSpacing by android.widget.GridView
        Class<?> demo = null;
        int verticalSpacing = 0;
        try {
            demo = Class.forName(CLASS_NAME_GRID_VIEW);
            java.lang.reflect.Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
            field.setAccessible(true);
            verticalSpacing = (Integer)field.get(view);
            return verticalSpacing;
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException, NoSuchFieldException, InstantiationException,
             * IllegalArgumentException, IllegalAccessException, NullPointException
             */
            e.printStackTrace();
        }
        return verticalSpacing;
    }

    /**
     * get AbsListView height according to every children
     * 
     * @param view
     * @return
     */
    public static int getAbsListViewHeightBasedOnChildren(android.widget.AbsListView view) {
        android.widget.ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            android.view.View item = adapter.getView(i, null, view);
            if (item instanceof android.view.ViewGroup) {
                item.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * set view height
     * 
     * @param view
     * @param height
     */
    public static void setViewHeight(android.view.View view, int height) {
        if (view == null) {
            return;
        }

        android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    /**
     * set ListView height which is calculated by {@link # getListViewHeightBasedOnChildren(ListView)}
     * 
     * @param view
     * @return
     */
    public static void setListViewHeightBasedOnChildren(android.widget.ListView view) {
        setViewHeight(view, getListViewHeightBasedOnChildren(view));
    }

    /**
     * set AbsListView height which is calculated by {@link # getAbsListViewHeightBasedOnChildren(AbsListView)}
     * 
     * @param view
     * @return
     */
    public static void setAbsListViewHeightBasedOnChildren(android.widget.AbsListView view) {
        setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
    }

    /**
     * set SearchView OnClickListener
     * 
     * @param v
     * @param listener
     */
    public static void setSearchViewOnClickListener(android.view.View v, android.view.View.OnClickListener listener) {
        if (v instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup)v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                android.view.View child = group.getChildAt(i);
                if (child instanceof android.widget.LinearLayout || child instanceof android.widget.RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof android.widget.TextView) {
                    android.widget.TextView text = (android.widget.TextView)child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    /**
     * get descended views from parent.
     * 
     * @param parent
     * @param filter Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of filter or not.
     * @return
     */
    public static <T extends android.view.View> java.util.List<T> getDescendants(android.view.ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        java.util.List<T> descendedViewList = new java.util.ArrayList<T>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View child = parent.getChildAt(i);
            Class<? extends android.view.View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass))
                    || (!includeSubClass && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof android.view.ViewGroup) {
                descendedViewList.addAll(getDescendants((android.view.ViewGroup)child, filter, includeSubClass));
            }
        }
        return descendedViewList;
    }
    /**
     * 关闭输入法
     * @param context
     */
    public static void closeInputMethod(android.content.Context context) {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((android.app.Activity) context).getCurrentFocus().getWindowToken(), android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
