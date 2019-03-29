package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 27/03/18.
 */

public class OrderedReqDict {

    public String ProductId;
    public String ProductName;
    public String PurchaseDetailsId;
    public String PurchaseId;
    public String PurchaseOrderId;
    //public String QuantityOrdered;
    public String QuantityReceived;
    public String QuantityRequested;
    public String RequestedDate;
    public String ReceivedDate;
    public String DueDate;
    public String Status;

    public OrderedReqDict(String productId, String productName, String purchaseDetailsId, String purchaseId, String purchaseOrderId, String quantityReceived, String quantityRequested, String requestedDate, String receivedDate, String dueDate, String status) {
        ProductId = productId;
        ProductName = productName;
        PurchaseDetailsId = purchaseDetailsId;
        PurchaseId = purchaseId;
        PurchaseOrderId = purchaseOrderId;
        QuantityReceived = quantityReceived;
        QuantityRequested = quantityRequested;
        RequestedDate = requestedDate;
        ReceivedDate = receivedDate;
        DueDate = dueDate;
        Status = status;
    }
}
