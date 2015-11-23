package com.example.photogallery.model;

import java.util.ArrayList;

public class GalleryCategoryDataContainer {

	private String categoryName;

	private ArrayList<GalleryDataContainer> galleryDataContainerList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<GalleryDataContainer> getGalleryDataContainerList() {
		return galleryDataContainerList;
	}

	public void setGalleryDataContainerList(
			ArrayList<GalleryDataContainer> galleryDataContainerList) {
		this.galleryDataContainerList = galleryDataContainerList;
	}

}
