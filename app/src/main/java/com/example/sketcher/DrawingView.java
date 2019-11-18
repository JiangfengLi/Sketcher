package com.example.sketcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/*
 * @author: Jiangfeng Li
 * @description: This is the DrawingView class of Sketcher, it extends the view and set up as a
 * canvas to let the users to drawing some curves.
 *
 */
public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF0077ff;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    /**
     * This is the constructor of DrawingView function, it sets up the environment as well as the
     * background color for users to draw. It accepts a Context of context and AttributeSet of attrs
     */
    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.setBackgroundColor(0xFFFFFFFF);
    }

    /**
     * This setupDrawing function, it sets up the configurations to draw some shapes.
     * It accepts and return nothing.
     */
    private void setupDrawing(){
        //prepare for drawing and setup paint stroke properties
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15.0f);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    /**
     * This onSizeChanged function, it sets up the size assigned to view.
     * It accepts integer width of w, height of h old width of w, height of h and return nothing.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    /**
     * This onDraw function, it draws some shapes on the location that the mouse clicked.
     * It accepts Canvas of canvas and return nothing.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    /**
     * This onTouchEvent function, it registers user touches as drawing action.
     * It accepts event of MotionEvent and return nothing.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;

    }

    /**
     * This startNew function, which starts new drawing. It accepts and return nothing.
     */
    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    /**
     * This getBitmap function, which returns a Bitmap of the image that users draw.
     * It accepts nothing and return a Bitmap of image.
     */
    public Bitmap getBitmap() {
        return canvasBitmap.copy(canvasBitmap.getConfig(), true);
    }

    /**
     * This changeStrokeColor function, which help to change the stroke color of pointer
     * It accepts an integer of pc which stands for the numeric value of paintColor and
     * return nothing.
     */
    public void changeStrokeColor(int pc) {
        this.paintColor = pc;
        drawPaint.setColor(paintColor);
    }

    /**
     * This changeStrokeSize function, which help to change the stroke size of pointer
     * It accepts a float of size which stands for the numeric value of stroke size and
     * return nothing.
     */
    public void changeStrokeSize(float size) {
        drawPaint.setStrokeWidth(size);
    }
}

