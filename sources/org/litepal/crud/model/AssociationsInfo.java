package org.litepal.crud.model;

import java.lang.reflect.Field;

public class AssociationsInfo {
    private Field associateOtherModelFromSelf;
    private Field associateSelfFromOtherModel;
    private String associatedClassName;
    private int associationType;
    private String classHoldsForeignKey;
    private String selfClassName;

    public String getSelfClassName() {
        return this.selfClassName;
    }

    public void setSelfClassName(String selfClassName2) {
        this.selfClassName = selfClassName2;
    }

    public String getAssociatedClassName() {
        return this.associatedClassName;
    }

    public void setAssociatedClassName(String associatedClassName2) {
        this.associatedClassName = associatedClassName2;
    }

    public String getClassHoldsForeignKey() {
        return this.classHoldsForeignKey;
    }

    public void setClassHoldsForeignKey(String classHoldsForeignKey2) {
        this.classHoldsForeignKey = classHoldsForeignKey2;
    }

    public Field getAssociateOtherModelFromSelf() {
        return this.associateOtherModelFromSelf;
    }

    public void setAssociateOtherModelFromSelf(Field associateOtherModelFromSelf2) {
        this.associateOtherModelFromSelf = associateOtherModelFromSelf2;
    }

    public Field getAssociateSelfFromOtherModel() {
        return this.associateSelfFromOtherModel;
    }

    public void setAssociateSelfFromOtherModel(Field associateSelfFromOtherModel2) {
        this.associateSelfFromOtherModel = associateSelfFromOtherModel2;
    }

    public int getAssociationType() {
        return this.associationType;
    }

    public void setAssociationType(int associationType2) {
        this.associationType = associationType2;
    }

    public boolean equals(Object o) {
        if (o instanceof AssociationsInfo) {
            AssociationsInfo other = (AssociationsInfo) o;
            if (o != null && other != null && other.getAssociationType() == this.associationType && other.getClassHoldsForeignKey().equals(this.classHoldsForeignKey)) {
                if (other.getSelfClassName().equals(this.selfClassName) && other.getAssociatedClassName().equals(this.associatedClassName)) {
                    return true;
                }
                if (!other.getSelfClassName().equals(this.associatedClassName) || !other.getAssociatedClassName().equals(this.selfClassName)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
