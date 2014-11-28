package org.biiig.foodbroker.formatter;

import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.PropertyContainer;
import org.biiig.foodbroker.model.Relationship;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peet on 28.11.14.
 */
public class SQLFormatter extends AbstractFormatter {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd");

    @Override
    public String format(DataObject dataObject) {
        String insertStmt = String.format("INSERT INTO `%s`.`%s`(",
                dataObject.getMetaData("system"),
                dataObject.getMetaData("class")
        );

        Map<String,Object> fields = new HashMap<>();

        for (String key : dataObject.getNestedRelationshipKeys()){
            fields.put(key,dataObject
                    .getNestedRelationship(key)
                    .getEndDataObject()
                    .getLocalID());
        }

        return formatInsertStmt(dataObject, insertStmt, fields);
    }

    @Override
    public String format(Relationship relationship) {
        String insertStmt = String.format("INSERT INTO `%s`.`%s`(",
                relationship.getMetaData("system"),
                relationship.getMetaData("type")
        );

        Map<String,Object> fields = new HashMap<>();

        fields.put( relationship.getStartAlias(),
                relationship.getStartDataObject().getLocalID());
        fields.put( relationship.getEndAlias(),
                relationship.getEndDataObject().getLocalID());

        return formatInsertStmt(relationship, insertStmt, fields);
    }

    private String formatInsertStmt(PropertyContainer dataObject, String insertStmt, Map<String, Object> fields) {
        for (String key : dataObject.getPropertyKeys()){
            fields.put(key,dataObject
                    .getProperty(key));
        }

        boolean isFirstColumn = true;

        for (String column : fields.keySet()){
            insertStmt += String.format("%s`%s`",
                    (isFirstColumn ? "" : ","),
                    column
            );
            isFirstColumn = false;
        }
        isFirstColumn = true;

        insertStmt += ") VALUES (";

        for (String column : fields.keySet()){
            Object value = fields.get(column);

            String stringValue;
            if (value instanceof Number){
                stringValue = String.valueOf(value);
            }
            else if (value instanceof Date) {
                stringValue = String.format("'%s'",dateFormatter.format((Date) value));
            }
            else {
                stringValue = String.format("'%s'",value.toString());
            }
            insertStmt += String.format("%s%s",
                    (isFirstColumn ? "" : ","),
                    stringValue
            );
            isFirstColumn = false;
        }
        insertStmt += ");";

        return insertStmt;
    }

    @Override
    public String getFileExtension() {
        return ".sql";
    }

    @Override
    public boolean hasSeparateRelationshipHandling() {
        return false;
    }
}
