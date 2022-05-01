package quietboy.android.sampleproject.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class StringUtils {

    public static String urlEncode(String toEncode){
        try{
            return URLEncoder.encode(toEncode, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException unEx){
            return "none";
        }
    }

    public static String getSearchURL(String term){
        String urldata = "https://itunes.apple.com/search?term=" + urlEncode(term)
                + "&amp;country=au&amp;media=movie&amp;all";

        return urldata;
    }
}
