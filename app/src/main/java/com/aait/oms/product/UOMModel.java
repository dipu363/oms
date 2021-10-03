package com.aait.oms.product;

import com.aait.oms.util.BaseModel;

public class UOMModel extends BaseModel {

/*    ssCreator	"admin"
    ssCreatedOn	"2021-09-22T23:00:00.000+00:00"
    ssModifier	null
    ssModifiedOn	"2021-09-22T23:00:00.000+00:00"
    uomId	34
    uomCode	"500gm"
    name	"KG"*/

    int uomId ;
    String uomCode;
    String name;

    public UOMModel() {
    }

    public UOMModel(int uomId, String uomCode, String name) {
        this.uomId = uomId;
        this.uomCode = uomCode;
        this.name = name;
    }



    public int getUomId() {
        return uomId;
    }

    public void setUomId(int uomId) {
        this.uomId = uomId;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UOMModel{" +
                "uomId=" + uomId +
                ", uomCode='" + uomCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
