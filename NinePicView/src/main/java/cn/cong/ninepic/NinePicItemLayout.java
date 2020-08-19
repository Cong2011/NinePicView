package cn.cong.ninepic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class NinePicItemLayout extends FrameLayout {
    private NinePicImageView mImageView;
    private ImageView mImgDelete;
    private boolean mIsDeleteMode;
    private int mImageWidth, mImageHeight;
    private int mIcDelete = R.drawable.ic_ngv_delete;
    private float mRatio = 0.25f;

    public NinePicItemLayout(Context context) {
        super(context);
        init(context, null);
    }

    public NinePicItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        inflate(context, R.layout.nine_pic_item, this);
        mImageView = findViewById(R.id.nine_iv);
        mImgDelete = findViewById(R.id.nine_delete);
        mImgDelete.setImageResource(mIcDelete);
        setIsDeleteMode(mIsDeleteMode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth, mHeight;
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d("ItemLayout", "onMeasure: w->" + mWidth + ",h->" + mHeight);
        if (mWidth == 0 && mHeight == 0) return;
        mWidth = mWidth - getPaddingLeft() - getPaddingRight();
        mHeight = mHeight - getPaddingTop() - getPaddingBottom();
        //Measure the size of the delete button
        int delSize = (int) (mWidth * mRatio);
        int delMode = MeasureSpec.EXACTLY;
        int delSpec = MeasureSpec.makeMeasureSpec(delSize, delMode);
        mImgDelete.measure(delSpec, delSpec);
        //Measure the size of imageView
        mImageWidth = 0;
        mImageHeight = 0;
        int imgMode = MeasureSpec.EXACTLY, imgWidthSpec = 0, imgHeightSpec = 0;
        if (mIsDeleteMode) {
            mImageWidth = mWidth * 4 / 5;
            mImageHeight = mHeight * 4 / 5;
        } else {
            mImageWidth = mWidth;
            mImageHeight = mHeight;
        }
        imgWidthSpec = MeasureSpec.makeMeasureSpec(mImageWidth, imgMode);
        imgHeightSpec = MeasureSpec.makeMeasureSpec(mImageHeight, imgMode);
        mImageView.measure(imgWidthSpec, imgHeightSpec);
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    /**
     * Set scantype of imageView
     */
    private void setScanType(ImageView.ScaleType scanType) {
        if (mImageView != null)
            mImageView.setScaleType(scanType);
    }

    /**
     * Set if is in the delete mode
     */
    public void setIsDeleteMode(boolean b) {
        this.mIsDeleteMode = b;
        if (mIsDeleteMode)
            mImgDelete.setVisibility(VISIBLE);
        else
            mImgDelete.setVisibility(GONE);
        requestLayout();
    }

    /**
     * Set the resource id of the delete
     */
    public void setDeleteIcon(int resId) {
        this.mIcDelete = resId;
        if (mImgDelete != null)
            mImgDelete.setImageResource(mIcDelete);
    }

    /**
     * If is in the delete mode
     */
    public boolean isDeleteMode() {
        return mIsDeleteMode;
    }

    /**
     * Set the ratio for the size of delete icon with the Parent View
     *
     * @param ratio
     */
    public void setRatioOfDeleteIcon(float ratio) {
        this.mRatio = ratio;
    }

    /**
     * Get imageView object
     */
    public NinePicImageView getImgView() {
        return mImageView;
    }

    public ImageView getImgDelete() {
        return mImgDelete;
    }
}
