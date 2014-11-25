package org.gradoop.foodbroker.pile;

import org.gradoop.foodbroker.model.MasterDataObject;
import org.gradoop.foodbroker.model.Vendor;

import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public class VendorPile extends AbstractMasterDataPile {

    private final List<Vendor> vendors;

    public VendorPile(List<Vendor> vendors){
        super(vendors.size());
        this.vendors = vendors;
    }

    @Override
    public Vendor nextInstance() {
        return vendors.get(nextId());
    }
}
