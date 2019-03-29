package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 28/02/18.
 */

public class PendingDict {

    public Boolean checked;
    public String ProductId;
    public String ProductName;
    public String ProductPos;
    public String ProductThresh;
    public String ProductCurrQty;
    public String Quantity;

    public PendingDict(Boolean checked, String productId, String productName, String productPos, String productThresh, String productCurrQty, String quantity) {
        this.checked = checked;
        ProductId = productId;
        ProductName = productName;
        ProductPos = productPos;
        ProductThresh = productThresh;
        ProductCurrQty = productCurrQty;
        Quantity = quantity;
    }


}
