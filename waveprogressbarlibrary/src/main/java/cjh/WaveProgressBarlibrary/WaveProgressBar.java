package cjh.WaveProgressBarlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cjh.waveprogressbarlibrary.R;

/**
 * Created by cjh on 16-12-8.
 */

public class WaveProgressBar extends View {

    protected static final String TAG = "WaveProgressBar";

    protected Paint textPaint, pathPaint;

    protected Path path;

    protected int dx;

    protected int dwave = -1;

    protected Context context;

    public static final String CIRCLE = "circle";

    public static final String SQUARE = "square";

    protected static final String PERCENT_CHAR = "%";

    protected static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    protected static final int DEFAULT_TEXT_SIZE = 20;

    protected static final int DEFAULT_CAVANS_BG = Color.GRAY;

    protected static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;

    protected static final int DEFAULT_BORDER_WIDTH = 0;

    protected static final int DEFAULT_ARC_COLOR = Color.WHITE;

    protected static final int DEFAULT_WAVE_DURATION = 1500;

    protected static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");

    protected static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#3CFFFFFF");

    protected static final int SIDE_LENGTH = 800;

    protected static final int MAX = 100;

    protected int max = MAX;

    protected int progress;

    protected int wave_duration = DEFAULT_WAVE_DURATION;

    protected int side_length = SIDE_LENGTH;

    protected int arcColor = DEFAULT_ARC_COLOR;

    protected int border_width = DEFAULT_BORDER_WIDTH;

    protected int border_color = DEFAULT_BORDER_COLOR;

    protected String shape = CIRCLE;

    protected int cavans_bg = DEFAULT_CAVANS_BG;

    protected int behind_wave_color = DEFAULT_BEHIND_WAVE_COLOR;

    protected int front_wave_color = DEFAULT_FRONT_WAVE_COLOR;

    protected int half_side_length, quarter_side_length, eighth_side_length;

    protected RectF rectf;

    protected int percent_height;

    protected boolean animation;

    protected ValueAnimator valueAnimator;

    protected int width, height;

    protected Paint.FontMetrics fontMetrics;

    protected int textSize = DEFAULT_TEXT_SIZE;
    protected int textColor = DEFAULT_TEXT_COLOR;
    protected int text_margin_top;

    protected boolean autoTestSize;

    public WaveProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public WaveProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null)
            initAttrs(attrs);
        initPaints();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgressBar);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.WaveProgressBar_arc_color)
                setArccolor(typedArray.getColor(attr, DEFAULT_ARC_COLOR));
            else if (attr == R.styleable.WaveProgressBar_behind_wave_color)
                setBehidWaveColor(typedArray.getColor(attr, DEFAULT_BEHIND_WAVE_COLOR));
            else if (attr == R.styleable.WaveProgressBar_front_wave_color)
                setFrontWaveColor(typedArray.getColor(attr, DEFAULT_FRONT_WAVE_COLOR));
            else if (attr == R.styleable.WaveProgressBar_cavans_bg)
                setCavansBG(typedArray.getColor(attr, DEFAULT_CAVANS_BG));
            else if (attr == R.styleable.WaveProgressBar_dwave)
                setDwave(typedArray.getDimensionPixelSize(attr, -1));
            else if (attr == R.styleable.WaveProgressBar_wave_duration)
                setWaveDuration(typedArray.getInteger(attr, DEFAULT_WAVE_DURATION));
            else if (attr == R.styleable.WaveProgressBar_border_color)
                setBorderColor(typedArray.getColor(attr, DEFAULT_BORDER_COLOR));
            else if (attr == R.styleable.WaveProgressBar_border_width)
                setBorderWidth(typedArray.getDimensionPixelSize(attr, DEFAULT_BORDER_WIDTH));
            else if (attr == R.styleable.WaveProgressBar_shape)
                setShape(typedArray.getString(attr));
            else if (attr == R.styleable.WaveProgressBar_progress)
                progress = typedArray.getInteger(attr, 0);
            else if (attr == R.styleable.WaveProgressBar_width)
                setWidth(typedArray.getDimensionPixelSize(attr, SIDE_LENGTH));
            else if (attr == R.styleable.WaveProgressBar_height)
                setHeight(typedArray.getDimensionPixelSize(attr, SIDE_LENGTH));
            else if (attr == R.styleable.WaveProgressBar_text_size)
                setTextSize(typedArray.getDimensionPixelSize(attr, DEFAULT_TEXT_SIZE));
            else if (attr == R.styleable.WaveProgressBar_text_color)
                setTextColor(typedArray.getColor(attr, DEFAULT_TEXT_COLOR));
            else if (attr == R.styleable.WaveProgressBar_auto_text_size)
                setAudoTextSize(typedArray.getBoolean(attr, false));
            else if (attr == R.styleable.WaveProgressBar_text_margin_top)
                setTextMarginTop(typedArray.getDimensionPixelSize(attr, 0));
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        side_length = initSideLength(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(side_length, side_length);
    }

    protected int initSideLength(int widthMeasureSpec, int heightMeasureSpec) {
        int w, h;
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        w = getResult(wMode, wSize);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        h = getResult(hMode, hSize);
        return Math.min(w, h);
    }

    protected int getResult(int mode, int size) {
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                if (width == height && width == 0) width = height = SIDE_LENGTH;
                result = Math.min(Math.min(Math.min(width, height), SIDE_LENGTH), size);
                break;
        }
        return result;
    }

    protected void initPaints() {
        path = new Path();

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);


        initTxtPaint();
    }

    private void initTxtPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (autoTestSize)
            textSize = side_length / 12;
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        fontMetrics = textPaint.getFontMetrics();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.d(TAG, "w = " + w + " : h = " + h);
        rectf = new RectF(0, 0, side_length, side_length);

        half_side_length = side_length / 2;
        quarter_side_length = half_side_length / 2;
        eighth_side_length = quarter_side_length / 2;

        percent_height = side_length / max;

        if (dwave == -1)
            dwave = side_length / 40 * 3;

        if (text_margin_top == 0)
            text_margin_top = (int) (side_length / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2);

        initPaints();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(cavans_bg);
//        Log.d(TAG, "progresss = " + progress);
        if (progress > 0 && progress < max)
            drawWave(canvas);
        else if (progress == max)
            canvas.drawColor(front_wave_color);

        drawText(canvas);

        if (shape.equals(CIRCLE))
            drawArcs(canvas);

    }

    protected void drawText(Canvas canvas) {
        String text = progress + PERCENT_CHAR;
        int textLength = (int) textPaint.measureText(text);
        int i = (side_length - textLength) / 2;
        canvas.drawText(text, i, text_margin_top, textPaint);

    }

    protected void drawWave(Canvas canvas) {
        int wave_height = side_length - progress * percent_height;
        int baseX = -side_length + dx;
//        Log.d(TAG, baseX + "");
        if (baseX > 0)
            baseX = 0;
        path.reset();
        pathPaint.setColor(behind_wave_color);
        path.moveTo(baseX, wave_height);
        for (int i = 0; i < 2; i++) {
            path.rQuadTo(quarter_side_length, -dwave, half_side_length, 0);
            path.rQuadTo(quarter_side_length, dwave, half_side_length, 0);
        }
        path.lineTo(side_length, side_length);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        pathPaint.setColor(front_wave_color);
        path.moveTo(baseX - eighth_side_length, wave_height);
        for (int i = 0; i < 3; i++) {
            path.rQuadTo(quarter_side_length, dwave, half_side_length, 0);
            path.rQuadTo(quarter_side_length, -dwave, half_side_length, 0);
        }
        path.lineTo(side_length, side_length);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);
    }

    protected void drawArcs(Canvas canvas) {
        pathPaint.setColor(arcColor);

        path.reset();
        path.moveTo(half_side_length, 0);
        path.arcTo(rectf, 180, 90, true);
        path.lineTo(0, 0);
        path.close();
        canvas.drawPath(path, pathPaint);

        path.reset();
        path.moveTo(half_side_length, 0);
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
        path.moveTo(half_side_length, side_length);
        path.arcTo(rectf, 90, 90, true);
        path.lineTo(0, side_length);
        path.close();
        canvas.drawPath(path, pathPaint);
    }

    public void startWaveAnimation() {
        animation = true;
        valueAnimator = ValueAnimator.ofInt(0, Math.min(side_length, Math.min(width, height)));
        valueAnimator.setDuration(wave_duration);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx = (int) valueAnimator.getAnimatedValue();
                if (animation)
                    postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public void setDwave(int dwave) {
        this.dwave = dwave;
        postInvalidate();
    }

    public void setTextMarginTop(int text_margin_top) {
        this.text_margin_top = text_margin_top;
        postInvalidate();
    }

    public void setAudoTextSize(boolean audoTextSize) {
        this.autoTestSize = audoTextSize;
        initTxtPaint();
        postInvalidate();
    }

    public void setTextSize(int textSize) {
        setTextParams(textSize, -1);
    }

    public void setTextColor(int textColor) {
        setTextParams(-1, textColor);
    }

    public void setTextParams(int textSize, int textColor) {
        if (textSize != -1)
            this.textSize = textSize;
        if (textSize != -1)
            this.textColor = textColor;
        initTxtPaint();
        postInvalidate();
    }

    public void setWidth(int width) {
        setWH(width, 0);
    }

    public void setHeight(int height) {
        setWH(0, height);
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
        postInvalidate();
    }

    public void stopWaveAnimation() {
        if (valueAnimator != null && animation) {
            valueAnimator.cancel();
            animation = false;
        }
    }

    public void setShape(String shape) {
        if (TextUtils.isEmpty(shape))
            shape = CIRCLE;
        this.shape = shape;
        postInvalidate();
    }

    public void setBorderColor(int border_color) {
        this.border_color = border_color;
    }

    public void setBorderWidth(int border_width) {
        this.border_width = border_width;
    }

    public void setCavansBG(int cavans_bg) {
        this.cavans_bg = cavans_bg;
    }

    public void setBehidWaveColor(int behidWaveColor) {
        this.behind_wave_color = behidWaveColor;
    }

    public void setFrontWaveColor(int frontWaveColor) {
        this.front_wave_color = frontWaveColor;
    }

    public void setArccolor(int arcColor) {
        this.arcColor = arcColor;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progress > 0 && progress < max)
            if (!animation)
                startWaveAnimation();
            else
                postInvalidate();
        else {
            stopWaveAnimation();
            postInvalidate();
        }

    }

    public void setWaveDuration(int wave_duration) {
        this.wave_duration = wave_duration;
    }
}
