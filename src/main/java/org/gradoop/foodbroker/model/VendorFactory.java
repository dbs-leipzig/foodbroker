package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class VendorFactory implements MasterDataFactory {
    @Override
    public Vendor newInstance(Map<String, Object> baseValues) {
        return new Vendor(baseValues);
    }

    @Override
    public String getInstanceClassName() {
        return Vendor.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "SELECT count(*)" +
                "FROM vendor_adjectives a\n" +
                "JOIN vendor_nouns n\n" +
                "JOIN cities c;";
    }

    @Override
    public String getBaseValueSql() {
        return "SELECT a.name || ' ' || n.name || ' ' || c.name AS name\n" +
                "FROM vendor_adjectives a\n" +
                "JOIN vendor_nouns n\n" +
                "JOIN cities c\n" +
                "ORDER BY c.name, n.name, a.name;";
    }
}
