package com.example.photogallery;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.photogallery.adapters.PhotoGalleryAdapter;
import com.example.photogallery.model.GalleryAllDataContainer;
import com.example.photogallery.model.GalleryCategoryDataContainer;
import com.example.photogallery.model.GalleryDataContainer;
import com.example.photogallery.network.NetworkHelper;
import com.example.photogallery.parser.GalleryDataParser;
import com.example.photogallery.utils.Constants;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainActivity extends BaseActivity {
	
	// Declaring all data members
	private ArrayList<JSONArray> jsonArrays = new ArrayList<JSONArray>();
	private GalleryCategoryDataContainer categoryContainer;
	private GalleryDataContainer dataContainer;
	private ImageView selectedImageView;
	private ArrayList<String> categoryList = new ArrayList<String>();
	private ArrayList<GalleryDataContainer> dataContainerList = new ArrayList<GalleryDataContainer>();
	private ArrayList<GalleryCategoryDataContainer> categoryContainerList = new ArrayList<GalleryCategoryDataContainer>();
	private GalleryAllDataContainer allCategoryContainer = new GalleryAllDataContainer();
	private DisplayImageOptions options;
	private PhotoGalleryAdapter adapter;
	private ListView galleryListView;
	private JSONObject jsonObject;
	GalleryDataParser cateDataParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initImageLoader(this);
		initDisplayOptions();

		

		// Initialize members
		galleryListView = (ListView) findViewById(R.id.lv_gallery_items);
		selectedImageView = (ImageView) findViewById(R.id.selected_image_view_id);

		
		// Call gallery response data 
		new GetImageData().execute();
	}

	public class GetImageData extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			  String URL = Constants.BASE_URL+Constants.GALLERY_URL;
			  NetworkHelper jsonParser = new NetworkHelper(URL);
			  cateDataParser = new GalleryDataParser(jsonParser.getResponseFromUrl());
			  allCategoryContainer = cateDataParser.categoryParser();
			 

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			
			if(allCategoryContainer.getDataAllContainersCotegoryList().size() != 0){
			adapter = new PhotoGalleryAdapter(MainActivity.this, allCategoryContainer.getDataAllContainersCotegoryList(),
					options, selectedImageView);
			galleryListView.setAdapter(adapter);
			}
			else {
				Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
			}

			super.onPostExecute(result);
		}

	}

	

	private void initDisplayOptions() {
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}



   
    
}
