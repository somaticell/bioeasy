package org.litepal.crud;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.exceptions.DataSupportException;
import org.litepal.util.DBUtility;

abstract class AssociationsAnalyzer extends DataHandler {
    AssociationsAnalyzer() {
    }

    /* access modifiers changed from: protected */
    public Collection<DataSupport> getReverseAssociatedModels(DataSupport associatedModel, AssociationsInfo associationInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (Collection) takeGetMethodValueByField(associatedModel, associationInfo.getAssociateSelfFromOtherModel());
    }

    /* access modifiers changed from: protected */
    public void setReverseAssociatedModels(DataSupport associatedModel, AssociationsInfo associationInfo, Collection<DataSupport> associatedModelCollection) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        putSetMethodValueByField(associatedModel, associationInfo.getAssociateSelfFromOtherModel(), associatedModelCollection);
    }

    /* access modifiers changed from: protected */
    public Collection<DataSupport> checkAssociatedModelCollection(Collection<DataSupport> associatedModelCollection, Field associatedField) {
        Collection<DataSupport> collection;
        if (isList(associatedField.getType())) {
            collection = new ArrayList<>();
        } else if (isSet(associatedField.getType())) {
            collection = new HashSet<>();
        } else {
            throw new DataSupportException(DataSupportException.WRONG_FIELD_TYPE_FOR_ASSOCIATIONS);
        }
        if (associatedModelCollection != null) {
            collection.addAll(associatedModelCollection);
        }
        return collection;
    }

    /* access modifiers changed from: protected */
    public void buildBidirectionalAssociations(DataSupport baseObj, DataSupport associatedModel, AssociationsInfo associationInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        putSetMethodValueByField(associatedModel, associationInfo.getAssociateSelfFromOtherModel(), baseObj);
    }

    /* access modifiers changed from: protected */
    public void dealsAssociationsOnTheSideWithoutFK(DataSupport baseObj, DataSupport associatedModel) {
        if (associatedModel == null) {
            return;
        }
        if (associatedModel.isSaved()) {
            baseObj.addAssociatedModelWithFK(associatedModel.getTableName(), associatedModel.getBaseObjId());
        } else if (baseObj.isSaved()) {
            associatedModel.addAssociatedModelWithoutFK(baseObj.getTableName(), baseObj.getBaseObjId());
        }
    }

    /* access modifiers changed from: protected */
    public void mightClearFKValue(DataSupport baseObj, AssociationsInfo associationInfo) {
        baseObj.addFKNameToClearSelf(getForeignKeyName(associationInfo));
    }

    private String getForeignKeyName(AssociationsInfo associationInfo) {
        return getForeignKeyColumnName(DBUtility.getTableNameByClassName(associationInfo.getAssociatedClassName()));
    }
}
