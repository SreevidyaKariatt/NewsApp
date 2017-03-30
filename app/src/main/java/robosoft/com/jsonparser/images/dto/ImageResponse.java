package robosoft.com.jsonparser.images.dto;

/**
 * Created by sree on 19/1/16.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {

    @SerializedName("images")
    public List<ImageDescription> images;

    @SerializedName("keywords")
    public List<String> keywords;

    @SerializedName("searchId")
    public String searchId;

    public class ImageDescription{
        @SerializedName("id")
        public String id;

        @SerializedName("imageUrl")
        public String imageUrl;

        @SerializedName("title")
        public String title;

        @SerializedName("description")
        public String description;

        @SerializedName("pageUrl")
        public String pageUrl;

        @SerializedName("merchant")
        public String merchant;
    }
}
