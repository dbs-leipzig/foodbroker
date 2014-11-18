package org.gradoop.foodbroker.generators;

import org.gradoop.foodbroker.formatters.Formatter;
import org.gradoop.foodbroker.formatters.JSONFormatter;
import org.gradoop.foodbroker.model.*;
import org.gradoop.foodbroker.stores.ConsoleStore;
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

    public MasterDataGenerator(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void generate() {
        List<MasterDataFactory> factories = new ArrayList<>();

        Store store = new ConsoleStore(new JSONFormatter());

        factories.add(new EmployeeFactory());
        factories.add(new ProductFactory());
        factories.add(new CustomerFactory());
        factories.add(new LogisticsFactory());
        factories.add(new VendorFactory());

        for (MasterDataFactory factory : factories) {
            MasterDataConfiguration config = new MasterDataConfiguration(factory.getInstanceClassName(), scaleFactor);
            List<Map<String, Object>> baseValueList = getBaseValueList(config, factory);

            int currentOne = 0;
            String quality = "good";

            for (Map<String, Object> baseValues : baseValueList) {
                currentOne++;

                if (currentOne == config.getFirstBadOne()) {
                    quality = "bad";
                }

                baseValues.put("_QC", quality);
                MasterDataObject object = factory.newInstance(baseValues);

                if (currentOne == config.getLastGoodOne()) {
                    quality = "normal";
                }

                store.store(object);
            }
        }

    }

    private List<Map<String, Object>> getBaseValueList(MasterDataConfiguration config, MasterDataFactory factory) {
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
}
