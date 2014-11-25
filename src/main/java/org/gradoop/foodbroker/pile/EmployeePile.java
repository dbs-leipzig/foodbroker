package org.gradoop.foodbroker.pile;

import org.gradoop.foodbroker.model.Employee;

import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public class EmployeePile extends AbstractMasterDataPile{
    private final List<Employee> employees;

    public EmployeePile(List<Employee> employees){
        super(employees.size());
        this.employees = employees;
    }

    @Override
    public Employee nextInstance() {
        return employees.get(nextId());
    }
}
