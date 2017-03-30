package robosoft.com.jsonparser.common.net;

import android.content.Context;

import com.android.volley.Response;

import robosoft.com.jsonparser.images.dto.ImageResponse;
import robosoft.com.jsonparser.news.dto.NewsListResponse;


/**
 * Created by sree on 13/12/15.
 */
public class DownloadManager {


    //TODO change method names. implement request cancelling, create a base manager with custom listeners
    public static void downloadData(Context context,String url,Response.Listener<NewsListResponse> listener,Response.ErrorListener errorListener)
    {
        GsonRequest<NewsListResponse> request = new GsonRequest<NewsListResponse>(url,NewsListResponse.class,listener, errorListener);
        request.setTag(url);
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    public static void downloadDataForImage(Context context,String url,Response.Listener<ImageResponse> listener,Response.ErrorListener errorListener)
    {
        GsonRequest<ImageResponse> request = new GsonRequest<ImageResponse>(url,ImageResponse.class,listener, errorListener);
        request.setTag(url);
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }




}
