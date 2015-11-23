package com.example.photogallery.parser;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.photogallery.model.GalleryAllDataContainer;
import com.example.photogallery.model.GalleryCategoryDataContainer;
import com.example.photogallery.model.GalleryDataContainer;
import com.example.photogallery.utils.Constants;

public class GalleryDataParser extends BaseParser{
	private GalleryCategoryDataContainer categoryContainer;
	private ArrayList<GalleryDataContainer> dataContainerList;
	private ArrayList<String> categoryList = new ArrayList<String>();
    private GalleryDataContainer dataContainer;
    private GalleryAllDataContainer allCategoryContainer;
	private ArrayList<GalleryCategoryDataContainer> categoryContainerList;
	private String reponseString;
    private ArrayList<JSONArray> jsonArrays = new ArrayList<JSONArray>();
    
    public GalleryDataParser(String responseData) {
    	categoryContainerList =  new ArrayList<GalleryCategoryDataContainer>();
        this.reponseString = new String(responseData);
        allCategoryContainer = new GalleryAllDataContainer();
        categoryList.add(Constants.CATEGORY_ANIMAL);
        categoryList.add(Constants.CATEGORY_BIRDS);
        categoryList.add(Constants.CATEGORY_FLAGS);
        categoryList.add(Constants.CATEGORY_FLOWERS);
        categoryList.add(Constants.CATEGORY_FRUITS);
        categoryList.add(Constants.CATEGORY_TECHNOLOGY);
        categoryList.add(Constants.CATEGORY_VEGETABLES);
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
                for (int j=0;j<jsonArray.length();j++) {
                    dataContainer = new GalleryDataContainer();
                    JSONObject jsonObjectData = jsonArray.getJSONObject(j);
                    String name = jsonObjectData.optString(imageNameConstant).toString();
                    String imageUrl = jsonObjectData.optString(imageUrlConstant).toString();
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
        } catch (Exception e) {
            e.printStackTrace();
            allCategoryContainer.setDataAllContainersCotegoryList(categoryContainerList);
        }
        return allCategoryContainer;
    }
}
