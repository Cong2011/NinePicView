package cn.cong.ninepic;

public class NinePicOption {
    int defSpaceSize = 3;
    int defColumnCount = 3;
    int defMaxNum = 9;
    boolean defIsEditMode;
    int defIcAddMoreResId = R.drawable.ic_ngv_add_pic;
    int defIcDeleteResId = R.drawable.ic_ngv_delete;
    float defRatioDelete = 0.25f;
    float imgScaleOnEdit = 0.8F;
    boolean defCanDrag;

    //child view click listener
    NinePicView.onItemClickListener defListener;
    //interface of imageloader
    INinePicImageLoader defImageLoader;

    public static class Builder {
        private NinePicOption option;

        public Builder() {
            this.option = new NinePicOption();
        }

        public NinePicOption build() {
            return option;
        }

        public Builder setSpaceSize(int spaceSize) {
            option.defSpaceSize = spaceSize;
            return this;
        }

        public Builder setColumnCount(int columnCount) {
            option.defColumnCount = columnCount;
            return this;
        }

        public Builder setMaxNum(int maxNum) {
            option.defMaxNum = maxNum;
            return this;
        }

        public Builder setEditMode(boolean editMode) {
            option.defIsEditMode = editMode;
            return this;
        }

        public Builder setIcAddMoreResId(int icAddMoreResId) {
            option.defIcAddMoreResId = icAddMoreResId;
            return this;
        }

        public Builder setIcDeleteResId(int icDeleteResId) {
            option.defIcDeleteResId = icDeleteResId;
            return this;
        }

        public Builder setRatioDelete(float ratioDelete) {
            option.defRatioDelete = ratioDelete;
            return this;
        }

        public Builder setCanDrag(boolean canDrag) {
            option.defCanDrag = canDrag;
            return this;
        }

        public Builder setImgScaleOnEdit(float imgScale) {
            option.imgScaleOnEdit = imgScale;
            return this;
        }


        public Builder setImageLoader(INinePicImageLoader imageLoader) {
            option.defImageLoader = imageLoader;
            return this;
        }

        public Builder setListener(NinePicView.onItemClickListener l) {
            option.defListener = l;
            return this;
        }
    }


    public int getDefSpaceSize() {
        return defSpaceSize;
    }

    public int getDefColumnCount() {
        return defColumnCount;
    }

    public int getDefMaxNum() {
        return defMaxNum;
    }

    public boolean isDefIsEditMode() {
        return defIsEditMode;
    }

    public int getDefIcAddMoreResId() {
        return defIcAddMoreResId;
    }

    public int getDefIcDeleteResId() {
        return defIcDeleteResId;
    }

    public float getDefRatioDelete() {
        return defRatioDelete;
    }

    public float getImgScaleOnEdit() {
        return imgScaleOnEdit;
    }

    public boolean isDefCanDrag() {
        return defCanDrag;
    }

    public NinePicView.onItemClickListener getDefListener() {
        return defListener;
    }

    public INinePicImageLoader getDefImageLoader() {
        return defImageLoader;
    }
}
