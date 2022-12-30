package com.rn.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class XDashedLineView extends View {

private Paint mPaint;
private Path mPath;
private int     vWidth;
private int     vHeight;

public XDashedLineView(Context context) {
    super(context);
    init();
}

public XDashedLineView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

public XDashedLineView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
}

private void init() {
    mPaint = new Paint();
    mPaint.setColor(Color.parseColor("#bdbdbd"));
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setPathEffect(new DashPathEffect(new float[] {15,15}, 4));
    mPath = new Path();
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    this.vWidth = getMeasuredWidth();
    this.vHeight = getMeasuredHeight();
    mPath.moveTo(0, this.vHeight / 2);
    mPath.quadTo(this.vWidth / 2, this.vHeight / 2, this.vWidth, this.vHeight / 2);
}

@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(mPath, mPaint);
}
}