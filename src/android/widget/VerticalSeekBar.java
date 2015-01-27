package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * 
 * @author Alexander Brennecke
 * 
 *  a widget to display a vertical seek bar in an XML-file
 *
 */

public class VerticalSeekBar extends SeekBar {

	/**
	 * constructor
	 * @param context contextn on which it should displayed
	 */
    public VerticalSeekBar(Context context) {
        super(context);
    }
    
    /**
     * 
     * contructor
     * @param context
     * @param attrs
     * @param defStyle
     */

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    /**
     * constructor
     * @param context
     * @param attrs
     */

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 
     * changes the size of the seek Bar
     * @param w new width of the seek Bar
     * @param h new height of the seek Bar
     * @param oldw old width of the seek Bar
     * @param oldh of the seek Bar
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }
    
    /**
     * generates the rotated Canvas field displaying the seek bar
     * @param c Canvas field, which should be rotated, translated and drawn
     */

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(),0);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
             int i=0;
             i=getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                Log.i("Progress",getProgress()+"");
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    
}