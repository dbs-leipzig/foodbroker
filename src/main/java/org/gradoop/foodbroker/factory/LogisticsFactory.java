package org.gradoop.foodbroker.factory;

import org.gradoop.foodbroker.model.Logistics;
import org.gradoop.foodbroker.pile.LogisticsPile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class LogisticsFactory extends AbstractMasterDataFactory {

    private final List<Logistics> logisticses = new ArrayList<>();

    @Override
    public Logistics newInstance(Map<String, Object> baseValues) {
        Logistics logistics = new Logistics(baseValues);
        this.logisticses.add(logistics);
        return logistics;
    }

    public LogisticsPile newLogisticsPile(){
        return new LogisticsPile(logisticses);
    }

    @Override
    public String getInstanceClassName() {
        return Logistics.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "SELECT count(*)" +
                "FROM logistics_adjectives a\n" +
                "JOIN logistics_nouns n\n" +
                "JOIN cities c";
    }

    @Override
    public String getBaseValueSql() {
        return "SELECT a.name || ' ' || n.name || ' ' || c.name AS name\n" +
                "FROM logistics_adjectives a\n" +
                "JOIN logistics_nouns n\n" +
                "JOIN cities c\n" +
                "ORDER BY c.name, n.name, a.name;";
    }
}
