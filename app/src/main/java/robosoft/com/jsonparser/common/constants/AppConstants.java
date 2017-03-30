package robosoft.com.jsonparser.common.constants;

/**
 * Created by sree on 4/2/16.
 */
public class AppConstants {

    public interface stringConstants
    {

        String DATE_FORMAT = "MMM dd, yyyy";
        String STR_IMAGE = "image";
        String STR_URL = "URL";
        String STR_POSITION = "POSITION";
        String STR_IMAGE_BITMAP = "IMAGEBITMAP";
        String STR_HEADLINE = "HEADLINE";
        String STR_NEWS_DETAIL =  "News in Detail";
        String STR_IMAGE_DESCRIPTION = "imageDescription";
        String STR_NEWS =  "news";
        String STR_DASHBOARD_FRAGMENT = "dashboardfragment";
        String STR_IMAGE_URL = "imageurl";
        String STR_INFO = "info";
        String STR_VIDEO = "video";
        String STR_VIDEO_URL = "videoUrl";
        String STR_YOUTUBE_API_KEY = "AIzaSyBDyk7vqLcsQaq8bdJ_n1uDiAnzaMncpvc";



    }

    public interface integerConstants{
        int NO_OF_TABS = 4;
        int SPACING = 15;
        int SPAN_COUNT = 2;

    }

    public interface booleanConstants {
        boolean IMCLUDE_EDGE = true;

    }

    public interface urlConstants
    {
        String NEWS_URL = "http://timesofindia.indiatimes.com/feeds/newsdefaultfeeds.cms?feedtype=sjson";
        String DEFAULT_IMAGE_URL = "http://timesofindia.indiatimes.com/photo.cms?photoid=50900385";
        String IMAGE_URL = "http://decor.vsapi01.com/api-search/by-url?url=http://usdvps.com/wp-content/uploads/2015/01/red-sofa.jpg&apikey=84a2817c-21ce-42b0-ae53-356fbfb634d3";
    }
}
