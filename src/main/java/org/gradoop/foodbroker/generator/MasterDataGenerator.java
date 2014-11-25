package org.gradoop.foodbroker.generator;

import org.gradoop.foodbroker.configuration.MasterDataConfiguration;
import org.gradoop.foodbroker.manager.*;
import org.gradoop.foodbroker.model.*;
import org.gradoop.foodbroker.stores.Store;

import java.sql.*;
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

    private EmployeeManager employeeManager = new EmployeeManager();
    private ProductManager productManager = new ProductManager();
    private CustomerManager customerManager = new CustomerManager();
    private LogisticsManager logisticsManager = new LogisticsManager();
    private VendorManager vendorManager = new VendorManager();

    public MasterDataGenerator(int scaleFactor, Store store) {
        this.scaleFactor = scaleFactor;
        this.store = store;
    }

    public void generate() {

        List<MasterDataManager> managers = new ArrayList<>();

        managers.add(employeeManager);
        managers.add(productManager);
        managers.add(customerManager);
        managers.add(logisticsManager);
        managers.add(vendorManager);

        for (MasterDataManager manager : managers) {
            MasterDataConfiguration config = new MasterDataConfiguration(manager.getInstanceClassName(), scaleFactor);
            List<Map<String, Object>> baseValueList = getBaseValueList(config, manager);

            int currentOne = 0;
            String quality = "good";

            for (Map<String, Object> baseValues : baseValueList) {
                currentOne++;

                if (currentOne == config.getFirstBadOne()) {
                    quality = "bad";
                }

                baseValues.put("_QC", quality);
                MasterDataObject object = manager.newInstance(baseValues);

                if (currentOne == config.getLastGoodOne()) {
                    quality = "normal";
                }

                store.store(object);
            }
        }

    }

    private List<Map<String, Object>> getBaseValueList(MasterDataConfiguration config, MasterDataManager factory) {
        List<Map<String, Object>> baseValueList = new ArrayList();

        try {
            Class.forName("org.sqlite.JDBC");
            String DB_PATH = "/home/peet/IdeaProjects/FoodBroker/src/main/resources/baseValues.sqlite";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            Statement statement = connection.createStatement();

            ResultSet rowCountResultSet = statement.executeQuery(factory.getRowCountSql());
            int rowCount = 0;
            rowCountResultSet.next();
            rowCount = rowCountResultSet.getInt(1);
            rowCountResultSet.close();

            ResultSet baseValueResultSet = statement.executeQuery(factory.getBaseValueSql());
            int divisor = rowCount / config.getAmount();
            if (divisor == 0) divisor = 1;

            ResultSetMetaData baseValueResultSetMetaData = baseValueResultSet.getMetaData();
            while (baseValueResultSet.next()) {
                if (baseValueResultSet.getRow() % divisor == 0) {
                    Map<String, Object> baseValues = new HashMap();
                    for (int i = 1; i <= baseValueResultSetMetaData.getColumnCount(); i++) {
                        baseValues.put(baseValueResultSetMetaData.getColumnLabel(i), baseValueResultSet.getObject(i));
                    }
                    baseValueList.add(baseValues);
                }
            }
            baseValueResultSet.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return baseValueList;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public LogisticsManager getLogisticsManager() {
        return logisticsManager;
    }

    public VendorManager getVendorManager() {
        return vendorManager;
    }

    public void shuffle() {
        employeeManager.shuffle();
        productManager.shuffle();
        customerManager.shuffle();
        logisticsManager.shuffle();
        vendorManager.shuffle();
    }
}
