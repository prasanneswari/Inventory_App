package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 20/03/18.
 */

public class ReqChildDict {

    public Boolean Checked;
    public String DetailsId;
    public String Pid;
    public String Pname;
    public String Qissued;

    public ReqChildDict(Boolean checked, String detailsId, String pid, String pname, String qissued, String qreq, String reqId, String status, String balance, String position, String positionId) {
        Checked = checked;
        DetailsId = detailsId;
        Pid = pid;
        Pname = pname;
        Qissued = qissued;
        Qreq = qreq;
        ReqId = reqId;
        Status = status;
        Balance = balance;
        Position = position;
        PositionId = positionId;
    }

    public String Qreq;
    public String ReqId;
    public String Status;
    public String Balance;
    public String Position;
    public String PositionId;


}
