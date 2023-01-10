package com.example.aitest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();



    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawPath(path, paint);
    }
    public void setStrokeWidth(float width){
        paint.setStrokeWidth(width);
    }

    public void setColor(int color){
        paint.setColor(color);
    }


    public Bitmap getBitmap(){
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap bitmap = getDrawingCache(true);
        return  bitmap;
    }

    public void clearCanvas(){
        path.reset();
        invalidate();
        setDrawingCacheEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX,eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX,eventY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
