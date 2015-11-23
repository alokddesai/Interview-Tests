package com.example.photogallery.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.photogallery.R;
import com.example.photogallery.model.GalleryCategoryDataContainer;
import com.example.photogallery.model.GalleryDataContainer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by cuelogic on 20/11/15.
 */
public class PhotoGalleryAdapter extends BaseAdapter implements View.OnClickListener{


    /*********** Declare Used Variables *********/
    private Activity activity;
    private ImageView selectedImageView;
    private ArrayList<GalleryCategoryDataContainer> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    DisplayImageOptions options;
    GalleryCategoryDataContainer tempValues=null;
    ArrayList<String> allReadyAddItem = new ArrayList<String>();
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public PhotoGalleryAdapter(Activity activity, ArrayList<GalleryCategoryDataContainer> data, DisplayImageOptions options, ImageView imageView) {

        /********** Take passed values **********/
        activity = activity;
        data=data;
        options = options;
        selectedImageView = imageView;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
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
        public TextView tv_category_title;
        public LinearLayout lv_item_type;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
            if (convertView==null) {
                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.gallery_item_row, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/
                holder = new ViewHolder();
                holder.tv_category_title = (TextView)vi.findViewById(R.id.tv_category_type);
                holder.lv_item_type = (LinearLayout)vi.findViewById(R.id.lv_image_type);
                /************  Set holder with LayoutInflater ************/
                vi.setTag( holder );


            } else {
                holder = (ViewHolder) vi.getTag();
            }

        if(data.size()<=0)
        {
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
                   // imageView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                    //imageView.setBackgroundColor(Color.WHITE);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageLoader.getInstance().displayImage(dataItem.getImgURL(), selectedImageView, options);
                        }
                    });
                    
                    holder.lv_item_type.addView(imageView);
                    ImageLoader.getInstance().displayImage(dataItem.getImgURL(), imageView, options);
                    allReadyAddItem.add(dataItem.getName().toString());
            }
        }
        notifyDataSetChanged();
        return vi;
//        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
