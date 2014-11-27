package org.biiig.foodbroker.pile;

import org.biiig.foodbroker.model.Customer;

import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public class CustomerPile extends AbstractMasterDataPile{
    private final List<Customer> customers;

    public CustomerPile(List<Customer> customers){
        super(customers.size());
        this.customers = customers;
    }

    @Override
    public Customer nextInstance() {
        return customers.get(nextId());
    }
}
