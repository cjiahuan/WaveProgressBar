package cjh.waveprogressbarlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by cjh on 16-12-8.
 */

public class WaveProgressbar extends View {

    protected Paint bgPaint, pathPaint;

    protected Path path;

    protected int dx, dy;

    protected int dwave = 60;

    protected Context context;

    public static final String CIRCLE = "circle";

    public static final String SQUARE = "square";

    protected static final int DEFAULT_BGCOLOR = 0xffefefef;

    protected static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");

    protected static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#3CFFFFFF");

    protected static final int SIDE_LENGTH = 800;

    protected int side_length;

    public WaveProgressbar(Context context) {
        super(context);
        init(context, null);
    }

    public WaveProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null)
            initAttrs(attrs);
        initPaints();
    }

    private void initAttrs(AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        side_length = initSideLength(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(side_length, side_length);
    }

    private int initSideLength(int widthMeasureSpec, int heightMeasureSpec) {
        int w, h;
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        w = getResult(wMode, wSize);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        h = getResult(hMode, hSize);
        return Math.min(w, h);
    }

    private int getResult(int mode, int size) {
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Math.min(SIDE_LENGTH, size);
                break;
        }
        return result;
    }

    protected void initPaints() {
        path = new Path();

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(DEFAULT_BGCOLOR);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);
    }

//    float s;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        path.reset();
        pathPaint.setColor(DEFAULT_BEHIND_WAVE_COLOR);
        path.moveTo(-side_length  + dx, side_length / 2);
        for (int i = 0; i < 3; i++) {
            path.rQuadTo(side_length / 4, -dwave, side_length / 2, 0);
            path.rQuadTo(side_length / 4, dwave, side_length / 2, 0);
        }
        path.lineTo(side_length, side_length);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        pathPaint.setColor(DEFAULT_FRONT_WAVE_COLOR);
        path.moveTo(-side_length -100+ dx, side_length / 2);
        for (int i = 0; i < 3; i++) {
            path.rQuadTo(side_length / 4, dwave, side_length / 2, 0);
            path.rQuadTo(side_length / 4, -dwave, side_length / 2, 0);
        }
        path.lineTo(side_length, side_length);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);

//===============================================================================

        RectF rectf = new RectF(0, 0, side_length, side_length);

        pathPaint.setColor(Color.WHITE);


        path.reset();
        path.moveTo(side_length / 2, 0);
        path.arcTo(rectf, 180, 90, true);
        path.lineTo(0, 0);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        path.moveTo(side_length / 2, 0);
        path.arcTo(rectf, 270, 90, true);
        path.lineTo(side_length, 0);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        path.moveTo(side_length, 0);
        path.arcTo(rectf, 0, 90, true);
        path.lineTo(side_length, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        path.moveTo(side_length / 2, side_length);
        path.arcTo(rectf, 90, 90, true);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);

    }

    public void startWaveAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, side_length);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();

//        startWaveAnimation2();
//        startWaveAnimation3();
    }

    public void startWaveAnimation2() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(side_length, side_length / 2);
        valueAnimator.setDuration(9000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dy = (int) valueAnimator.getAnimatedValue();
//                postInvalidate();
            }
        });
        valueAnimator.start();

        startWaveAnimation3();
    }

    public void startWaveAnimation3() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 70, 0);
        valueAnimator.setDuration(4500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dwave = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

}
