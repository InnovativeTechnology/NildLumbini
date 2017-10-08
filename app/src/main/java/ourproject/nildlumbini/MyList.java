package ourproject.nildlumbini;

import java.util.ArrayList;

/**
 * Created by Ramesh on 10/8/2017.
 */

public class MyList {
    static ArrayList<RetrieveData> doclist;
    public MyList(ArrayList<RetrieveData> doclist )
    {
        this.doclist= doclist;
    }
    public static ArrayList<RetrieveData> arrayList()
    {
        return doclist;
    }

}
