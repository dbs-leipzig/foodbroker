package org.gradoop.foodbroker.manager;

import org.gradoop.foodbroker.model.Logistics;
import org.gradoop.foodbroker.model.MasterDataObject;
import org.gradoop.foodbroker.model.Vendor;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class LogisticsManager extends AbstractMasterDataManager {
    @Override
    public Logistics newInstance(Map<String, Object> baseValues) {
        Logistics logistics = new Logistics(baseValues);
        this.instances.add(logistics);
        return logistics;
    }

    @Override
    public Logistics nextInstance() {
        return (Logistics) super.nextInstance();
    }

    @Override
    public String getInstanceClassName() {
        return Logistics.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "SELECT count(*)" +
                "FROM logistics_adjectives a\n" +
                "JOIN logistics_nouns n\n" +
                "JOIN cities c";
    }

    @Override
    public String getBaseValueSql() {
        return "SELECT a.name || ' ' || n.name || ' ' || c.name AS name\n" +
                "FROM logistics_adjectives a\n" +
                "JOIN logistics_nouns n\n" +
                "JOIN cities c\n" +
                "ORDER BY c.name, n.name, a.name;";
    }
}
