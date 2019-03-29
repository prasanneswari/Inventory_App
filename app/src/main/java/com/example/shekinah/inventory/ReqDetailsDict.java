package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 20/03/18.
 */

public class ReqDetailsDict {

    public Boolean Checked;
    public String DetailsId;
    public String JtsPid;
    public String JtsName;
    public String Pid;
    public String Pname;
    public String Qissued;
    public String Qreq;
    public String ReqId;
    public String Status;
    public String Balance;

    public ReqDetailsDict(Boolean checked, String detailsId, String jtsPid, String jtsName, String pid, String pname, String qissued, String qreq, String reqId, String status, String balance) {
        this.Checked = checked;
        this.DetailsId = detailsId;
        this.JtsPid = jtsPid;
        this.JtsName = jtsName;
        this.Pid = pid;
        this.Pname = pname;
        this.Qissued = qissued;
        this.Qreq = qreq;
        this.ReqId = reqId;
        this.Status = status;
        this.Balance = balance;
    }
}
