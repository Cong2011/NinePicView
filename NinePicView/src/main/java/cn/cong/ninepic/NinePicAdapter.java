package cn.cong.ninepic;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NinePicAdapter extends RecyclerView.Adapter<NinePicAdapter.NinePicViewHolder> {

    //weather is in edit mode
    boolean mIsEditMode = NinePicView.defOption.defIsEditMode;
    //Maximum of image
    int mMaxNum = NinePicView.defOption.defMaxNum;
    //Resource Id of AddMore
    int mIcAddMoreResId = NinePicView.defOption.defIcAddMoreResId;
    //Resource Id of the delete icon
    int mIcDelete = NinePicView.defOption.defIcDeleteResId;
    //Ratio of delete icon with parent view
    float mRatioOfDelete = NinePicView.defOption.defRatioDelete;
    float mImgScaleOnEdit = NinePicView.defOption.imgScaleOnEdit;
    //Size of space
    int mSpaceSize = NinePicView.defOption.defSpaceSize;

    //child view click listener
    NinePicView.onItemClickListener mListener = NinePicView.defOption.defListener;
    //interface of imageloader
    INinePicImageLoader mImageLoader = NinePicView.defOption.defImageLoader;
    //image datas
    final List<String> mDataList = new ArrayList<>();

    private ItemTouchHelper helper;
    private boolean isDragging;
    private RecyclerView rv;

    @NonNull
    @Override
    public NinePicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NinePicViewHolder(this, new NinePicItemLayout(viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull NinePicViewHolder ninePicViewHolder, int i) {
        ninePicViewHolder.onBind();
    }

    @Override
    public int getItemCount() {
        return Math.min(mMaxNum, mDataList.size() + (mIsEditMode ? 1 : 0));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.rv = recyclerView;
    }

    public int getDiffValue() {
        return mMaxNum - mDataList.size();
    }

    public void setItemTouchHelper(ItemTouchHelper helper) {
        this.helper = helper;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return helper;
    }

    public boolean isAddItem(int pos) {
        return mDataList.size() <= pos;
    }

    public boolean isDragging() {
        return isDragging;
    }

    void setDragging(boolean dragging) {
        isDragging = dragging;
        if (rv == null) return;
        for (int i = 0; i < getItemCount(); i++) {
            RecyclerView.ViewHolder vh = rv.findViewHolderForLayoutPosition(i);
            if (vh instanceof NinePicViewHolder) ((NinePicViewHolder) vh).onBind();
        }
    }

    // --------------------------------------------------------
    // ********************* ViewHolder ************************

    public static class NinePicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @NonNull
        private final NinePicAdapter adapter;
        private final NinePicItemLayout itemLayout;
        private String url;

        public NinePicViewHolder(@NonNull NinePicAdapter adapter, @NonNull NinePicItemLayout itemLayout) {
            super(itemLayout);
            this.adapter = adapter;
            this.itemLayout = itemLayout;
            itemLayout.getImgView().setOnClickListener(this);
            itemLayout.getImgDelete().setOnClickListener(this);
            itemLayout.getImgView().bind(this);
        }

        public void onBind() {
            int pad = adapter.mSpaceSize;
            itemLayout.setPadding(pad, pad, pad, pad);
            ImageView imgDel = itemLayout.getImgDelete();
            final NinePicImageView imgView = itemLayout.getImgView();
            if (isAddItem()) {
                int v = adapter.isDragging() ? View.GONE : View.VISIBLE;
                if (itemLayout.getVisibility() != v) itemLayout.setVisibility(v);
                itemLayout.setIsDeleteMode(true);
                itemLayout.setImgScaleOnEdit(adapter.mImgScaleOnEdit);
                if (imgDel.getVisibility() != View.GONE) imgDel.setVisibility(View.GONE);
                imgView.setImageResource(adapter.mIcAddMoreResId);
                imgView.setCanDrag(false);
            } else {
                if (itemLayout.getVisibility() != View.VISIBLE)
                    itemLayout.setVisibility(View.VISIBLE);
                url = adapter.mDataList.get(getAdapterPosition());
                if (adapter.isDragging()) itemLayout.getImgDelete().setVisibility(View.GONE);
                else {
                    itemLayout.setRatioOfDeleteIcon(adapter.mRatioOfDelete);
                    itemLayout.setImgScaleOnEdit(adapter.mImgScaleOnEdit);
                    itemLayout.setIsDeleteMode(adapter.mIsEditMode);
                    itemLayout.setDeleteIcon(adapter.mIcDelete);
                }
                imgView.setCanDrag(adapter.mIsEditMode); // 编辑状态、图片，可拖拽
                itemLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (itemLayout.getImageWidth() != 0 && itemLayout.getImageWidth() != 0)
                            adapter.mImageLoader.displayNineGridImage(itemLayout.getContext(), url, imgView
                                    , itemLayout.getImageWidth(), itemLayout.getImageHeight());
                        else
                            adapter.mImageLoader.displayNineGridImage(itemLayout.getContext(), url, imgView);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            if (v == itemLayout.getImgView()) {
                if (adapter.mListener == null) return;
                if (isAddItem()) adapter.mListener.onNineGirdAddMoreClick(adapter.getDiffValue());
                else adapter.mListener.onNineGirdItemClick(getLayoutPosition(), url, itemLayout);
            } else if (v == itemLayout.getImgDelete()) {
                int position = getAdapterPosition();
                String url = adapter.mDataList.remove(position);
                adapter.notifyItemRemoved(position);
                if (adapter.mListener != null)
                    adapter.mListener.onNineGirdItemDeleted(position, url, itemLayout);
            }
        }

        private boolean isAddItem() {
            return adapter.isAddItem(getAdapterPosition());
        }

        @NonNull
        public NinePicAdapter getAdapter() {
            return adapter;
        }
    }
}


