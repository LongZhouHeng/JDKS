package com.jdruanjian.jdks.utils;

import android.text.TextUtils;

import java.io.Serializable;

public class ImageItem implements Serializable {
    private static final long serialVersionUID = -9105358010760564089L;
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public String url;
    public boolean isSelected = false;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getPath() {
        return TextUtils.isEmpty(thumbnailPath) ? imagePath : thumbnailPath;
    }
}
