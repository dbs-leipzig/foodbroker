package org.biiig.foodbroker.model;

import org.biiig.foodbroker.pile.CustomerPile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class CustomerFactory extends AbstractMasterDataFactory {

    private final List<Customer> customers = new ArrayList<>();

    public Customer newInstance(Map<String, Object> baseValues) {
        Customer customer = new Customer(baseValues);
        this.customers.add(customer);
        return customer;
    }

    public CustomerPile newCustomerPile(){
        return new CustomerPile(customers);
    }

    @Override
    public String getInstanceClassName() {
        return Customer.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "SELECT count(*)" +
                "FROM customer_adjectives a\n" +
                "JOIN customer_nouns n\n" +
                "JOIN cities c;";
    }

    @Override
    public String getBaseValueSql() {
        return "SELECT a.name || ' ' || n.name || ' ' || c.name AS name\n" +
                "FROM customer_adjectives a\n" +
                "JOIN customer_nouns n\n" +
                "JOIN cities c\n" +
                "ORDER BY c.name, n.name, a.name;";
    }

}
