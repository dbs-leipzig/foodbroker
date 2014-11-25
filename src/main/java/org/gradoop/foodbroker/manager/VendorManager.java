package org.gradoop.foodbroker.manager;

import org.gradoop.foodbroker.model.MasterDataObject;
import org.gradoop.foodbroker.model.Vendor;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class VendorManager extends AbstractMasterDataManager {
    @Override
    public Vendor newInstance(Map<String, Object> baseValues) {
        Vendor vendor = new Vendor(baseValues);
        this.instances.add(vendor);
        return vendor;
    }

    @Override
    public String getInstanceClassName() {
        return Vendor.class.getSimpleName();
    }

    @Override
    public Vendor nextInstance() {
        return (Vendor) super.nextInstance();
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
