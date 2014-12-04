package org.biiig.foodbroker.generator;

import org.biiig.foodbroker.configuration.MasterDataConfiguration;
import org.biiig.foodbroker.model.CustomerFactory;
import org.biiig.foodbroker.model.Employee;
import org.biiig.foodbroker.model.EmployeeFactory;
import org.biiig.foodbroker.model.LogisticsFactory;
import org.biiig.foodbroker.model.MasterDataFactory;
import org.biiig.foodbroker.model.MasterDataObject;
import org.biiig.foodbroker.model.ProductFactory;
import org.biiig.foodbroker.model.User;
import org.biiig.foodbroker.model.VendorFactory;
import org.biiig.foodbroker.stores.Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 12.11.14.
 */
public class MasterDataGenerator {

    private int scaleFactor;
    private Store store;

    private final EmployeeFactory employeeFactory = new EmployeeFactory();
    private final ProductFactory productFactory = new ProductFactory();
    private final CustomerFactory customerFactory = new CustomerFactory();
    private final LogisticsFactory logisticsFactory = new LogisticsFactory();
    private final VendorFactory vendorFactory = new VendorFactory();

    public MasterDataGenerator(int scaleFactor, Store store) {
        this.scaleFactor = scaleFactor;
        this.store = store;
    }

    public void generate() {

        List<MasterDataFactory> masterDataFactories = new ArrayList<>();
        masterDataFactories.add(employeeFactory);
        masterDataFactories.add(productFactory);
        masterDataFactories.add(customerFactory);
        masterDataFactories.add(logisticsFactory);
        masterDataFactories.add(vendorFactory);

        store.open();

        for (MasterDataFactory masterDataFactory : masterDataFactories) {
            MasterDataConfiguration config = new MasterDataConfiguration(masterDataFactory.getInstanceClassName(), scaleFactor);
            List<Map<String, Object>> baseValueList = getBaseValueList(config, masterDataFactory);

            int currentOne = 0;
            String quality = "good";

            for (Map<String, Object> baseValues : baseValueList) {
                currentOne++;

                if (currentOne == config.getFirstBadOne()) {
                    quality = "bad";
                }

                baseValues.put("_quality", quality);
                MasterDataObject object = masterDataFactory.newInstance(baseValues);

                if (currentOne == config.getLastGoodOne()) {
                    quality = "normal";
                }

                store.store(object);
            }
        }

        List<Employee> employees = employeeFactory.getEmployees();
        for(Employee employee : employees) {
            User user = new User(employee);
            employee.setUser(user);
            store.store(user);
        }

        store.close();

    }

    private List<Map<String, Object>> getBaseValueList(MasterDataConfiguration config, MasterDataFactory factory) {
        List<Map<String, Object>> baseValueList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            String DB_PATH = MasterDataGenerator.class.getClassLoader()
              .getResource("baseValues.sqlite").getPath();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            Statement statement = connection.createStatement();

            ResultSet rowCountResultSet = statement.executeQuery(factory.getRowCountSql());
            int rowCount;
            rowCountResultSet.next();
            rowCount = rowCountResultSet.getInt(1);
            rowCountResultSet.close();

            ResultSet baseValueResultSet = statement.executeQuery(factory.getBaseValueSql());
            int divisor = rowCount / config.getAmount();
            if (divisor == 0) divisor = 1;

            ResultSetMetaData baseValueResultSetMetaData = baseValueResultSet.getMetaData();
            while (baseValueResultSet.next()) {
                if (baseValueResultSet.getRow() % divisor == 0) {
                    Map<String, Object> baseValues = new HashMap<>();
                    for (int i = 1; i <= baseValueResultSetMetaData.getColumnCount(); i++) {
                        baseValues.put(baseValueResultSetMetaData.getColumnLabel(i), baseValueResultSet.getObject(i));
                    }
                    baseValueList.add(baseValues);
                }
            }
            baseValueResultSet.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baseValueList;
    }


    public EmployeeFactory getEmployeeFactory() {
        return employeeFactory;
    }

    public ProductFactory getProductFactory() {
        return productFactory;
    }

    public CustomerFactory getCustomerFactory() {
        return customerFactory;
    }

    public LogisticsFactory getLogisticsFactory() {
        return logisticsFactory;
    }

    public VendorFactory getVendorFactory() {
        return vendorFactory;
    }
}
