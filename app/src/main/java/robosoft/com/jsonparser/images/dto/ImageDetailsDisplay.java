package robosoft.com.jsonparser.images.dto;

import java.util.ArrayList;

/**
 * Created by sree on 23/1/16.
 */
public class ImageDetailsDisplay {

    private String imageUrl;
    private String imageTitle;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    private String imageDescription;
    private String pageUrl;

}
