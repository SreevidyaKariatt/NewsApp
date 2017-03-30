package robosoft.com.jsonparser.news.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sree on 22/1/16.
 */
public class NewsListResponse {

    @SerializedName("NewsItem")
    public List<NewsItem> newsItems;
    public class NewsItem{
        @SerializedName("HeadLine")
        public String headLine;

        @SerializedName("WebURL")
        public String webURL;

        @SerializedName("Image")
        public ImageNewsResponse imageData;

        @SerializedName("DateLine")
        public String dateLine;
    }

    public class ImageNewsResponse{
        @SerializedName("Photo")
        public String photo;
    }


}
