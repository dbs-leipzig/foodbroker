package org.gradoop.foodbroker.factory;

import org.gradoop.foodbroker.model.Vendor;
import org.gradoop.foodbroker.pile.VendorPile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class VendorFactory extends AbstractMasterDataFactory {

    private final List<Vendor> vendors = new ArrayList<>();

    @Override
    public Vendor newInstance(Map<String, Object> baseValues) {
        Vendor vendor = new Vendor(baseValues);
        this.vendors.add(vendor);
        return vendor;
    }

    public VendorPile newVendorPile(){
        return new VendorPile(vendors);
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
