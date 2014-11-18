package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class LogisticsFactory implements MasterDataFactory {
    @Override
    public Logistics newInstance(Map<String, Object> baseValues) {
        return new Logistics(baseValues);
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
