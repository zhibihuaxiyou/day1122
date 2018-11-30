package com.bwie.day1122;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author：张腾
 * date：2018/11/22
 */
public class WaveView extends View{

    private Paint paint;
    private Paint paint1;
    private Path path;
    private Path path1;
    private PaintFlagsDrawFilter drawFilter;
    private double fai;

    public WaveView(Context context) {
        this( context,null );
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this( context, attrs ,-1);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        init();
    }

    // 波形浮动的监听
    public interface OnWaveChangeListener {
        void onChanged(float y);
    }

    private OnWaveChangeListener listener;

    public void setOnWaveChangeListener(OnWaveChangeListener listener) {
        this.listener = listener;
    }


    private void init() {
        paint = new Paint();
        paint.setColor( Color.BLUE );
        paint.setAntiAlias( true );
        paint.setStyle( Paint.Style.FILL );
        paint.setStrokeWidth( 5 );

        paint1 = new Paint();
        paint1.setColor( Color.BLUE );
        paint1.setAntiAlias( true );
        paint1.setStyle( Paint.Style.FILL );
        paint1.setStrokeWidth( 5 );
        paint1.setAlpha( 60 );

        path = new Path();
        path1 = new Path();

        drawFilter = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        double i = 2 * Math.PI / getMeasuredWidth();
        canvas.setDrawFilter( drawFilter );
        fai -= 0.1f;
        int A = getMeasuredHeight() / 2;
        path.reset();
        path1.reset();
        path.moveTo( getLeft(),getBottom() );
        path1.moveTo( getLeft(),getBottom() );
        for (int j = 0; j <= getMeasuredWidth(); j+=20) {
            //            float y = Asin(Ωx+φ)+k
            float y1 = A * (float) Math.sin(i * j + fai) + A;
            float y2 = -A * (float) Math.sin(i * j + fai) + A;

            if(j > getMeasuredWidth() / 2 - 10 && j < getMeasuredWidth() / 2 + 10){
                listener.onChanged(y2);
            }

            path.lineTo(j, y1);
            path1.lineTo(j, y2);
        }

        path.lineTo( getWidth(),getBottom() );
        path1.lineTo( getWidth(),getBottom() );

        canvas.drawPath( path,paint );
        canvas.drawPath( path1,paint1 );

        postInvalidateDelayed(50);
    }
}
