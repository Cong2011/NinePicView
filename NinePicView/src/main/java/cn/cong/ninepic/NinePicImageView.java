package cn.cong.ninepic;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NinePicImageView extends android.support.v7.widget.AppCompatImageView {
    public NinePicImageView(Context context) {
        super(context);
        setClickable(true);
    }

    public NinePicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    private NinePicAdapter.NinePicViewHolder vh;
    private boolean isCanDrag;

    public void bind(NinePicAdapter.NinePicViewHolder vh) {
        this.vh = vh;
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                int i = MeasureSpec.getMode(heightMeasureSpec);
                if (i == MeasureSpec.EXACTLY || i == MeasureSpec.AT_MOST) {
                    int spec = MeasureSpec.makeMeasureSpec(
                            Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
                            , MeasureSpec.AT_MOST);
                    super.onMeasure(spec, spec);
                } else super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                break;
            default:
                int mode = MeasureSpec.getMode(heightMeasureSpec);
                if (mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST)
                    super.onMeasure(heightMeasureSpec, heightMeasureSpec);
                else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
        }
    }

    private float downX, downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.7f);
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 可拖拽、没有缩放、有移动时，触发拖拽
                ItemTouchHelper helper = vh.getAdapter().getItemTouchHelper();
                if (helper != null && isCanDrag && vh.itemView.getScaleX() > 0.99 && (Math.abs(x - downX) > 10 || Math.abs(y - downY) > 10)) {
                    vh.getAdapter().setDragging(true);
                    helper.startDrag(vh);
                    vh.itemView.setPivotX(x);
                    vh.itemView.setPivotY(y);
                    vh.itemView.setScaleX(0.8F);
                    vh.itemView.setScaleY(0.8F);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
