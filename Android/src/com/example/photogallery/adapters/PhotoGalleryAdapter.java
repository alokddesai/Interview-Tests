package com.example.photogallery.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photogallery.R;
import com.example.photogallery.model.GalleryCategoryDataContainer;
import com.example.photogallery.model.GalleryDataContainer;
import com.example.photogallery.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotoGalleryAdapter extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ImageView selectedImageView;
    private ArrayList<GalleryCategoryDataContainer> data;
    private static LayoutInflater inflater=null;
    private Resources res;
    private DisplayImageOptions options;
    private GalleryCategoryDataContainer tempValues=null;
    private ArrayList<String> allReadyAddItem = new ArrayList<String>();
    private int i=0;

    /*************  CustomAdapter Constructor *****************/
    public PhotoGalleryAdapter(Activity activity, ArrayList<GalleryCategoryDataContainer> data, DisplayImageOptions options, ImageView imageView) {

        /********** Take passed values **********/
        this.activity = activity;
        this.data=data;
        this.options = options;
        selectedImageView = imageView;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() > 0)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        TextView tv_category_title;
        LinearLayout lv_item_type;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
            if (convertView==null) {
                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                view = inflater.inflate(R.layout.gallery_item_row, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/
                holder = new ViewHolder();
                holder.tv_category_title = (TextView)view.findViewById(R.id.tv_category_type);
                holder.lv_item_type = (LinearLayout)view.findViewById(R.id.lv_image_type);
                /************  Set holder with LayoutInflater ************/
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

        if(data.size()<=0) {
            holder.tv_category_title.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( GalleryCategoryDataContainer ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.tv_category_title.setText( tempValues.getCategoryName() );

            ArrayList<GalleryDataContainer> dataList = tempValues.getGalleryDataContainerList();
            holder.lv_item_type.removeAllViews();
            for(final GalleryDataContainer dataItem : dataList){
                    ImageView imageView = new ImageView(activity);
                    final float scale = activity.getResources().getDisplayMetrics().density;
                    int pixels = (int) (100 * scale + 0.5f);
                    int paddingPixel = (int) (2 * scale + 0.5f);
                    LayoutParams params = new LayoutParams(pixels,pixels);
                    params.setMargins(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                    imageView.setLayoutParams(params);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageLoader.getInstance().displayImage(Constants.BASE_URL+dataItem.getImgURL(), selectedImageView, options);
                            Toast.makeText(activity, dataItem.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                    holder.lv_item_type.addView(imageView);
                    System.out.println("Image URL "+dataItem.getImgURL());
                    ImageLoader.getInstance().displayImage(Constants.BASE_URL+dataItem.getImgURL(), imageView, options);
                    allReadyAddItem.add(dataItem.getName().toString());
            }
        }
        notifyDataSetChanged();
        return view;

    }

 
}