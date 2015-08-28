/** 
 * Project Name:DingDangKaUser 
 * File Name:LineEditText.java 
 * Package Name:com.ddkuclient.view 
 * Date:2014年9月25日下午6:15:32 
 * Copyright (c) 2014, 13477030046@qq.com All Rights Reserved. 
 * 
 */

package library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * ClassName:LineEditText <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年9月25日 下午6:15:32 <br/>
 * 重写 Edittext 画底线
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see 类作用:  
 */
public class PaintBottomEditText extends android.widget.EditText {

	private android.graphics.Paint mPaint;

	/**
	 * @param context
	 * @param attrs
	 */
	public PaintBottomEditText(android.content.Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint = new android.graphics.Paint();

		mPaint.setStyle(android.graphics.Paint.Style.STROKE);
		mPaint.setColor(android.graphics.Color.WHITE);
		mPaint.setStrokeWidth(2);
	}

	@Override
	public void onDraw(android.graphics.Canvas canvas) {
		super.onDraw(canvas);

		// 画底线
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,this.getHeight() - 1, mPaint);
	}
}
