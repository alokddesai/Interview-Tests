package com.example.photogallery.parser;


import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.photogallery.model.GalleryAllDataContainer;
import com.example.photogallery.model.GalleryCategoryDataContainer;
import com.example.photogallery.model.GalleryDataContainer;

public class GalleryDataParser extends BaseParser{
	private GalleryCategoryDataContainer categoryContainer;
	ArrayList<GalleryDataContainer> dataContainerList;
	ArrayList<String> categoryList = new ArrayList<String>();
    private GalleryDataContainer dataContainer;
    private GalleryAllDataContainer allCategoryContainer;
	private ArrayList<GalleryCategoryDataContainer> categoryContainerList;
	private String reponseString;

    private ArrayList<JSONArray> jsonArrays = new ArrayList<JSONArray>();

    public GalleryDataParser(byte[] responseData) {
    	categoryContainerList =  new ArrayList<GalleryCategoryDataContainer>();
        this.reponseString = new String(responseData);
        allCategoryContainer = new GalleryAllDataContainer();
        categoryList.add("animals");
        categoryList.add("birds");
        categoryList.add("flags");
        categoryList.add("flowers");
        categoryList.add("fruits");
        categoryList.add("technology");
        categoryList.add("vegetables");
    }
    
    public GalleryDataParser(String responseData) {
    	categoryContainerList =  new ArrayList<GalleryCategoryDataContainer>();
        this.reponseString = new String(responseData);
        allCategoryContainer = new GalleryAllDataContainer();
        categoryList.add("animals");
        categoryList.add("birds");
        categoryList.add("flags");
        categoryList.add("flowers");
        categoryList.add("fruits");
        categoryList.add("technology");
        categoryList.add("vegetables");
    }

    public GalleryAllDataContainer categoryParser(){
        categoryContainerList =  new ArrayList<GalleryCategoryDataContainer>();

        try {
            JSONObject jsonObject = new JSONObject(reponseString);
            Iterator<String> iteratorKeys = jsonObject.keys();
            int i = 0;
            while (iteratorKeys.hasNext()) {
                categoryContainer = new GalleryCategoryDataContainer();
                dataContainerList = new ArrayList<GalleryDataContainer>();
                JSONArray jsonArray = jsonObject.optJSONArray(iteratorKeys.next());
                jsonArrays.add(jsonArray);
                //categoryList.add(jsonArray.)
                for (int j=0;j<jsonArray.length();j++) {
                    dataContainer = new GalleryDataContainer();
                    JSONObject jsonObjectData = jsonArray.getJSONObject(j);
                    // TODO: Remove the hardcoded strings.

                    String name = jsonObjectData.optString("name").toString();
                    String imageUrl = jsonObjectData.optString("imgURL").toString();

                    dataContainer.setName(name);
                    dataContainer.setImgURL(imageUrl);
                    dataContainerList.add(dataContainer);
                }
                categoryContainer.setCategoryName(categoryList.get(i).toString());
                categoryContainer.setGalleryDataContainerList(dataContainerList);
                categoryContainerList.add(categoryContainer);
                i++;
            }
            allCategoryContainer.setDataAllContainersCotegoryList(categoryContainerList);
//            categoryContainer.setJsonArrays(jsonArrays);
        } catch (Exception e) {
            e.printStackTrace();
            allCategoryContainer.setDataAllContainersCotegoryList(categoryContainerList);
        }
        return allCategoryContainer;
    }
}
