package org.gradoop.foodbroker.manager;

import org.gradoop.foodbroker.model.Customer;
import org.gradoop.foodbroker.model.MasterDataObject;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class CustomerManager extends AbstractMasterDataManager {

    public Customer newInstance(Map<String, Object> baseValues) {
        Customer customer = new Customer(baseValues);
        this.instances.add(customer);
        return customer;
    }

    @Override
    public Customer nextInstance() {
        return (Customer) super.nextInstance();
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
