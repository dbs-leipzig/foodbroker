package org.biiig.foodbroker.pile;

import org.biiig.foodbroker.model.Vendor;

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
