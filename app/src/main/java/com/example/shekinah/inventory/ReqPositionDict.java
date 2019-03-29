package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 22/03/18.
 */

public class ReqPositionDict {

    public String Position;
    public String quantity;
    public String positionId;
    public String quantityId;
    public String positionStat;

    public ReqPositionDict(String position, String quantity, String positionId, String quantityId, String positionStat) {
        Position = position;
        this.quantity = quantity;
        this.positionId = positionId;
        this.quantityId = quantityId;
        this.positionStat = positionStat;
    }
}
