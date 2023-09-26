package BookApi.Book.utill;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class OtaUtility {
    public static Locale getLocale(String lang)
    {
        if(lang == null || lang.equals(""))
            lang ="en";
        return  new Locale(lang);
    }

}
