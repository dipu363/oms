package com.aait.oms.util;

import android.content.Context;
import android.database.Cursor;

import java.util.Date;

public class BaseModel {
    private String ssCreator;
    private String ssCreatedOn;
    private String ssModifier;
    private String ssModifiedOn;

    public BaseModel() {
    }

    public BaseModel(String ssCreator, String ssCreatedOn, String ssModifier, String ssModifiedOn) {
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

    public String getSsCreatedOn() {
        return ssCreatedOn;
    }

    public void setSsCreatedOn(String ssCreatedOn) {
        this.ssCreatedOn = ssCreatedOn;
    }

    public String getSsModifier() {
        return ssModifier;
    }

    public void setSsModifier(String ssModifier) {
        this.ssModifier = ssModifier;
    }

    public String getSsModifiedOn() {
        return ssModifiedOn;
    }

    public void setSsModifiedOn(String ssModifiedOn) {
        this.ssModifiedOn = ssModifiedOn;
    }
}