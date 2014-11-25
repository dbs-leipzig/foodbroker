package org.gradoop.foodbroker.factory;

import org.gradoop.foodbroker.model.Employee;
import org.gradoop.foodbroker.pile.EmployeePile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class EmployeeFactory extends AbstractMasterDataFactory {


    private final List<Employee> employees = new ArrayList<>();

    public Employee newInstance(Map<String, Object> baseValues) {
        Employee employee = new Employee(baseValues);
        this.employees.add(employee);
        return employee;
    }

    public EmployeePile newEmployeePile(){
        return new EmployeePile(employees);
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
