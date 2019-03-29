package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 28/02/18.
 */

public class OrderedDict {
    public String OderId;
    public String ReqDate;
    public String DueDate;
    public String Status;

    public OrderedDict(String oderId, String reqDate, String dueDate, String status) {
        OderId = oderId;
        ReqDate = reqDate;
        DueDate = dueDate;
        Status = status;
    }
}
