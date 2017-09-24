package ourproject.nildlumbini;

import java.io.Serializable;

/**
 * Created by Ramesh on 9/23/2017.
 */

public class RetrieveData implements Serializable {
    //

    String name;
    String option;
    String title;
    String date;
    String article;
    String imgUrl;

    public RetrieveData()
    {}
    public RetrieveData(String name,String option, String title,String article,String imgUrl,String Date)
    {
        this.name= name;
        this.option= option;
        this.title= title;
        this.date= Date;
        this.article= article;
        this.imgUrl= imgUrl;
    }
}
