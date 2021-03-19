package com.aait.oms.util;

import java.util.Date;

public class BaseModel {

    private String ssCreator;
    private Date ssCreatedOn = new Date();
    private String ssModifier;
    private Date ssModifiedOn = new Date();

    public BaseModel() {
    }

    public BaseModel(String ssCreator, Date ssCreatedOn, String ssModifier, Date ssModifiedOn) {
        this.ssCreator = ssCreator;
        this.ssCreatedOn = ssCreatedOn;
        this.ssModifier = ssModifier;
        this.ssModifiedOn = ssModifiedOn;
    }

    public String getSsCreator() {
        return ssCreator;
    }

    public void setSsCreator(String ssCreator) {
        this.ssCreator = ssCreator;
    }

    public Date getSsCreatedOn() {
        return ssCreatedOn;
    }

    public void setSsCreatedOn(Date ssCreatedOn) {
        this.ssCreatedOn = ssCreatedOn;
    }

    public String getSsModifier() {
        return ssModifier;
    }

    public void setSsModifier(String ssModifier) {
        this.ssModifier = ssModifier;
    }

    public Date getSsModifiedOn() {
        return ssModifiedOn;
    }

    public void setSsModifiedOn(Date ssModifiedOn) {
        this.ssModifiedOn = ssModifiedOn;
    }
}