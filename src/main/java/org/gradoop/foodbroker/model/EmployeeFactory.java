package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class EmployeeFactory implements MasterDataFactory {

    public Employee newInstance(Map<String, Object> baseValues) {
        return new Employee(baseValues);
    }

    @Override
    public String getInstanceClassName() {
        return Employee.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "WITH first_names AS (\n" +
                "  SELECT 'male' AS gender, name \n" +
                "  FROM male_first_names\n" +
                "  UNION ALL\n" +
                "  SELECT 'female' AS gender, name \n" +
                "  FROM female_first_names\n" +
                ")\n" +
                "SELECT  count(*) -- l.name, f.name, f.gender\n" +
                "FROM  family_names l\n" +
                "JOIN  first_names f;";
    }

    @Override
    public String getBaseValueSql() {
        return "WITH first_names AS (\n" +
                "  SELECT 'male' AS gender, name \n" +
                "  FROM male_first_names\n" +
                "  UNION ALL\n" +
                "  SELECT 'female' AS gender, name \n" +
                "  FROM female_first_names\n" +
                ")\n" +
                "SELECT l.name AS family_name, f.name AS first_name, f.gender\n" +
                "FROM  family_names l\n" +
                "JOIN  first_names f\n" +
                "ORDER BY 1,2;";
    }
}
