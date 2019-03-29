package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 06/03/18.
 */

public class DetailsDict {
    public Boolean Checked;
    public String ProductId;
    public String ProductName;
    public String ProductDesc;
    public String QtyRequested;
    public String Status;


    public DetailsDict(Boolean checked, String productId, String productName, String productDesc, String qtyRequested, String status) {
        Checked = checked;
        ProductId = productId;
        ProductName = productName;
        ProductDesc = productDesc;
        QtyRequested = qtyRequested;
        Status = status;
    }
}

//    "productcomapany": "",
//            "productdescription": "Reynolds",
//            "productid": 1,
//            "productname": "Pen",
//            "purchasedetailsid": 1,
//            "purchaseorderid": 20,
//            "quantityreceived": 0,
//            "quantityrequested": 10,
//            "status": "Not Issued"
