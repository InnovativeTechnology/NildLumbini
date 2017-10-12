package ourproject.nildlumbini;

import java.io.Serializable;

/**
 * Created by Ramesh on 9/23/2017.
 */

public class RetrieveData implements Serializable {
    //
    public  String userIds;
   public String name;
    public String option;
    public String title;
    public String date;
    public String article;
    public String imgUrl;

    public RetrieveData()
    {}
    public RetrieveData(String userIds, String name, String option, String title, String article, String imgUrl, String Date)
    {
        this.userIds = userIds;
        this.name= name;
        this.option= option;
        this.title= title;
        this.date= Date;
        this.article= article;
        this.imgUrl= imgUrl;
    }
}
