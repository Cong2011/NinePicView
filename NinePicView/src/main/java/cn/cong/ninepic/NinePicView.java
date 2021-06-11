package cn.cong.ninepic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NinePicView extends RecyclerView {
    static NinePicOption defOption = new NinePicOption();

    public static void init(@NonNull NinePicOption option) {
        NinePicView.defOption = option;
    }

    public NinePicView(@NonNull Context context) {
        this(context, null, 0);
    }

    public NinePicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    //column count
    private int mColumnCount = defOption.defColumnCount;
    //
    private boolean canDrag = defOption.defCanDrag;
    private NineGridLayoutManager layoutManager;
    private ItemTouchHelper helper;

    private final NinePicAdapter mAdapter = new NinePicAdapter();

    private void initView(Context ctx, AttributeSet attrs) {
        initParams(ctx, attrs);
        layoutManager = new NineGridLayoutManager(ctx, mColumnCount);
        setLayoutManager(layoutManager);
        setAdapter(mAdapter);
        setCanDrag(canDrag);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NinePicView);
        if (ta != null) {
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int index = ta.getIndex(i);
                if (index == R.styleable.NinePicView_space_size) {
                    mAdapter.mSpaceSize = ta.getDimensionPixelSize(index, mAdapter.mSpaceSize);
                } else if (index == R.styleable.NinePicView_column_count) {
                    mColumnCount = ta.getInteger(index, mColumnCount);
                } else if (index == R.styleable.NinePicView_is_edit_mode) {
                    mAdapter.mIsEditMode = ta.getBoolean(index, mAdapter.mIsEditMode);
                } else if (index == R.styleable.NinePicView_max_num) {
                    mAdapter.mMaxNum = ta.getInteger(index, mAdapter.mMaxNum);
                } else if (index == R.styleable.NinePicView_icon_add_more) {
                    mAdapter.mIcAddMoreResId = ta.getResourceId(index, mAdapter.mIcAddMoreResId);
                } else if (index == R.styleable.NinePicView_icon_delete) {
                    mAdapter.mIcDelete = ta.getResourceId(index, mAdapter.mIcDelete);
                } else if (index == R.styleable.NinePicView_delete_ratio) {
                    mAdapter.mRatioOfDelete = ta.getFloat(index, mAdapter.mRatioOfDelete);
                } else if (index == R.styleable.NinePicView_scale_on_edit) {
                    mAdapter.mImgScaleOnEdit = ta.getFloat(index, mAdapter.mImgScaleOnEdit);
                } else if (index == R.styleable.NinePicView_can_drag_on_edit) {
                    canDrag = ta.getBoolean(index, canDrag);
                }
            }
            ta.recycle();
        }
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
        if (canDrag) {
            if (helper == null) {
                helper = new ItemTouchHelper(new NineItemTouchCallback(mAdapter));
                helper.attachToRecyclerView(this);
            }
            mAdapter.setItemTouchHelper(helper);
        } else {
            mAdapter.setItemTouchHelper(null);
        }
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    /**
     * Set data source
     */
    public void setDataList(List<String> dataList) {
        mAdapter.mDataList.clear();
        //Not allowed to exceed the maximum number
        if (dataList != null && dataList.size() > 0) {
            if (dataList.size() <= mAdapter.mMaxNum) {
                mAdapter.mDataList.addAll(dataList);
            } else {
                mAdapter.mDataList.addAll(dataList.subList(0, mAdapter.mMaxNum - 1));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Add data source
     */
    public void addDataList(List<String> dataList) {
        if (mAdapter.mDataList.size() >= mAdapter.mMaxNum) {
            return;
        }
        int start = mAdapter.mDataList.size();
        //Not allowed to exceed the maximum number
        int cha = mAdapter.mMaxNum - mAdapter.mDataList.size();
        if (dataList.size() <= cha) {
            mAdapter.mDataList.addAll(dataList);
        } else {
            mAdapter.mDataList.addAll(dataList.subList(0, cha - 1));
        }
        mAdapter.notifyItemRangeInserted(start, mAdapter.mDataList.size() - start);
    }

    /**
     * Add data source
     */
    public void addDataList(String url) {
        if (mAdapter.mDataList.size() >= mAdapter.mMaxNum) {
            return;
        }
        int start = mAdapter.mDataList.size();
        //Not allowed to exceed the maximum number
        mAdapter.mDataList.add(url);
        mAdapter.notifyItemInserted(start);
    }

    /**
     * Set weather is in edit mode
     */
    public void setIsEditMode(boolean b) {
        mAdapter.mIsEditMode = b;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isInEditMode() {
        return mAdapter.mIsEditMode;
    }

    /**
     * Set up imageloader
     */
    public void setImageLoader(INinePicImageLoader loader) {
        this.mAdapter.mImageLoader = loader;
    }

    /**
     * Set column count
     */
    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        layoutManager.setSpanCount(columnCount);
    }

    /**
     * Set the ratio for the size of delete icon with the Parent View
     */
    public void setRatioOfDeleteIcon(float ratio) {
        mAdapter.mRatioOfDelete = ratio;
    }

    public void setImgScaleOnEdit(float scale) {
        mAdapter.mImgScaleOnEdit = scale;
    }

    /**
     * Set the maximum number
     */
    public void setMaxNum(int maxNum) {
        this.mAdapter.mMaxNum = maxNum;
    }

    /**
     * Set the space size, dip unit
     */
    public void setSpaceSize(int dpValue) {
        this.mAdapter.mSpaceSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue
                , getContext().getResources().getDisplayMetrics());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get data source
     */
    public List<String> getDataList() {
        return mAdapter.mDataList;
    }

    /**
     * Set resource id of icon for AddMore
     */
    public void setIcAddMoreResId(int resId) {
        this.mAdapter.mIcAddMoreResId = resId;
        int size = mAdapter.mDataList.size();
        if (size > 0) mAdapter.notifyItemChanged(size - 1);
    }

    /**
     * Set resource id of the icon of delete
     */
    public void setIcDeleteResId(int resId) {
        this.mAdapter.mIcDelete = resId;
        //        for (int i = 0, count = getChildCount(); i < count; i++)
        //        {
        //            View child = getChildAt(i);
        //            if (child instanceof NinePicItemLayout)
        //            {
        //                ((NinePicItemLayout) child).setDeleteIcon(resId);
        //            }
        //        }
    }

    /**
     * Return the diff value between current data number displayed and maximum number
     */
    public int getDiffValue() {
        return mAdapter.getDiffValue();
    }

    /**
     * Set up child view click listener
     */
    public void setOnItemClickListener(onItemClickListener l) {
        mAdapter.mListener = l;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedViewState ss = new SavedViewState(super.onSaveInstanceState());
        ss.spaceSize = mAdapter.mSpaceSize;
        ss.columnCount = mColumnCount;
        ss.maxNum = mAdapter.mMaxNum;
        ss.isEditMode = mAdapter.mIsEditMode;
        ss.icAddMoreResId = mAdapter.mIcAddMoreResId;
        ss.icDeleteResId = mAdapter.mIcDelete;
        ss.ratioDelete = mAdapter.mRatioOfDelete;
        ss.mImgScaleOnEdit = mAdapter.mImgScaleOnEdit;
        ss.dataList = mAdapter.mDataList;
        ss.canDrag = canDrag;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedViewState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedViewState ss = (SavedViewState) state;
        super.onRestoreInstanceState(ss);
        mAdapter.mSpaceSize = ss.spaceSize;
        mColumnCount = ss.columnCount;
        mAdapter.mMaxNum = ss.maxNum;
        mAdapter.mIsEditMode = ss.isEditMode;
        mAdapter.mIcAddMoreResId = ss.icAddMoreResId;
        mAdapter.mIcDelete = ss.icDeleteResId;
        mAdapter.mRatioOfDelete = ss.ratioDelete;
        mAdapter.mImgScaleOnEdit = ss.mImgScaleOnEdit;
        setDataList(ss.dataList);
        canDrag = ss.canDrag;
    }

    static class SavedViewState extends BaseSavedState {
        int spaceSize;
        int columnCount;
        int maxNum;
        boolean isEditMode;
        int icAddMoreResId;
        List<String> dataList = new ArrayList<>();
        int icDeleteResId;
        float ratioDelete;
        float mImgScaleOnEdit;
        boolean canDrag;

        SavedViewState(Parcelable superState) {
            super(superState);
        }

        private SavedViewState(Parcel source) {
            super(source);
            spaceSize = source.readInt();
            columnCount = source.readInt();
            maxNum = source.readInt();
            isEditMode = source.readByte() == (byte) 1;
            icAddMoreResId = source.readInt();
            source.readStringList(dataList);
            icDeleteResId = source.readInt();
            canDrag = source.readByte() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(spaceSize);
            out.writeInt(columnCount);
            out.writeInt(maxNum);
            out.writeByte(isEditMode ? (byte) 1 : (byte) 0);
            out.writeInt(icAddMoreResId);
            out.writeStringList(dataList);
            out.writeInt(icDeleteResId);
            out.writeByte(canDrag ? (byte) 1 : (byte) 0);
        }

        public static final Creator<SavedViewState> CREATOR = new Creator<SavedViewState>() {
            @Override
            public SavedViewState createFromParcel(Parcel source) {
                return new SavedViewState(source);
            }

            @Override
            public SavedViewState[] newArray(int size) {
                return new SavedViewState[size];
            }
        };
    }


    public interface onItemClickListener {
        void onNineGirdAddMoreClick(int dValue);

        void onNineGirdItemClick(int pos, String img, @NonNull NinePicItemLayout item);

        void onNineGirdItemDeleted(int pos, String img, @NonNull NinePicItemLayout item);
    }
}
