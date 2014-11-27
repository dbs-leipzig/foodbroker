package org.gradoop.foodbroker.simulation;

import org.gradoop.foodbroker.configuration.FoodBrokerageConfiguration;
import org.gradoop.foodbroker.configuration.ProcessConfiguration;
import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.model.*;
import org.gradoop.foodbroker.pile.*;
import org.gradoop.foodbroker.stores.Store;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by peet on 18.11.14.
 */
public class FoodBrokerage implements BusinessProcess {

    private final FoodBrokerageConfiguration config = new FoodBrokerageConfiguration();
    private final Store store;
    private final EmployeePile employeePile;
    private final CustomerPile customerPile;
    private final LogisticsPile logisticsPile;
    private final ProductPile productPile;
    private final VendorPile vendorPile;

    public FoodBrokerage(MasterDataGenerator generator,Store store){
        this.store = store;
        this.employeePile = generator.getEmployeeFactory().newEmployeePile();
        this.customerPile = generator.getCustomerFactory().newCustomerPile();
        this.logisticsPile = generator.getLogisticsFactory().newLogisticsPile();
        this.productPile = generator.getProductFactory().newProductPile();
        this.vendorPile = generator.getVendorFactory().newVendorPile();
    }

    @Override
    public ProcessConfiguration getConfig() {
        return config.process;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public void start(Date startDate){

        SalesQuotation salesQuotation = newSalesQuotation(startDate);
        List<SalesQuotationLine> salesQuotationLines = newSalesQuotationLines(salesQuotation);

        if (confirmed(salesQuotation)){
            SalesOrder salesOrder = newSalesOrder(salesQuotation);
            // keys are distinct product categories
            List<SalesOrderLine> salesOrderLines = newSalesOrderLines(salesOrder,salesQuotationLines);

            List<PurchOrder> purchOrders = newPurchOrders (salesOrder,salesOrderLines);

            List<PurchOrderLine> purchOrderLines = newPurchOrderLines(purchOrders,salesOrderLines);

            //List<DeliveryNote> deliveryNotes =
            newDeliveryNotes(purchOrders);

            //List<PurchInvoice> purchInvoices =
            newPurchInvoices(purchOrderLines);

            //SalesInvoice salesInvoice =
            newSalesInvoice(salesOrderLines);
        }

    }

    private boolean confirmed (SalesQuotation salesQuotation){
        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(salesQuotation.getSentBy());
        influencingMasterDataObjects.add(salesQuotation.getSentTo());

        return config.confirmationProbability.happens(influencingMasterDataObjects);
    }

    private SalesQuotation newSalesQuotation(Date startDate){
        SalesQuotation salesQuotation = new SalesQuotation();

        salesQuotation.setDate(startDate);
        salesQuotation.setSentBy(employeePile.nextInstance());
        salesQuotation.setSentTo(customerPile.nextInstance());

        store.store(salesQuotation);

        return salesQuotation;
    }

    private List<SalesQuotationLine> newSalesQuotationLines(SalesQuotation salesQuotation){
        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(salesQuotation.getSentBy());
        influencingMasterDataObjects.add(salesQuotation.getSentTo());

        int numberOfQuotationLines = config.numberOfQuotationLines.getValue(influencingMasterDataObjects);
        List<SalesQuotationLine> salesQuotationLines = new ArrayList<>();

        for (int i = 1 ; i <= numberOfQuotationLines ; i++){
            salesQuotationLines.add(newSalesQuotationLine(salesQuotation));
        }

        return salesQuotationLines;
    }

    private SalesQuotationLine newSalesQuotationLine(SalesQuotation salesQuotation){
        SalesQuotationLine salesQuotationLine = new SalesQuotationLine();
        salesQuotationLine.setPartOf(salesQuotation);
        salesQuotationLine.setContains(productPile.nextInstance());
        salesQuotationLine.setPurchPrice(salesQuotationLine.getContains().getPrice());

        List <MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(salesQuotation.getSentTo());
        influencingMasterDataObjects.add(salesQuotation.getSentBy());
        influencingMasterDataObjects.add(salesQuotationLine.getContains());


        BigDecimal salesMargin = config.salesMargin.getValue(influencingMasterDataObjects);
        salesQuotationLine.setSalesPrice(
                salesMargin
                        .add(BigDecimal.ONE)
                        .multiply(salesQuotationLine.getPurchPrice())
                        .setScale(2,BigDecimal.ROUND_HALF_UP)
        );

        int quantity = config.numberOfQuotedProducts.getValue();
        if (quantity > 15 ) {
            quantity = quantity/5*5;
        }
        salesQuotationLine.setQuantity(quantity);

        store.store(salesQuotationLine);

        return  salesQuotationLine;
    }

    private SalesOrder newSalesOrder(SalesQuotation salesQuotation){
        SalesOrder salesOrder = new SalesOrder();

        salesOrder.setProcessedBy(employeePile.nextInstance());
        salesOrder.setReceivedFrom(salesQuotation.getSentTo());
        salesOrder.setBasedOn(salesQuotation);

        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(salesQuotation.getSentTo());
        influencingMasterDataObjects.add(salesQuotation.getSentBy());

        salesOrder.setDate(
                config.confirmationDelay.delay(salesQuotation.getDate(), influencingMasterDataObjects)
        );

        influencingMasterDataObjects.clear();
        influencingMasterDataObjects.add(salesOrder.getReceivedFrom());
        influencingMasterDataObjects.add(salesOrder.getProcessedBy());

        salesOrder.setDeliveryDate(
                config.deliveryAgreementDelay.delay(salesOrder.getDate(),influencingMasterDataObjects)
        );

        store.store(salesOrder);

        return salesOrder;
    }

    private List<SalesOrderLine> newSalesOrderLines(SalesOrder salesOrder, List<SalesQuotationLine> salesQuotationLines){

        List<SalesOrderLine> salesOrderLines = new ArrayList<>();

        for(SalesQuotationLine salesQuotationLine : salesQuotationLines){
            // TODO line confirmationProbability

            salesOrderLines.add(
                    newSalesOrderLine(salesOrder, salesQuotationLine)
            );
        }

        return salesOrderLines;
    }

    private SalesOrderLine newSalesOrderLine(SalesOrder salesOrder, SalesQuotationLine salesQuotationLine){
        SalesOrderLine salesOrderLine = new SalesOrderLine();
        salesOrderLine.setSalesPrice(salesQuotationLine.getSalesPrice());
        salesOrderLine.setContains(salesQuotationLine.getContains());
        salesOrderLine.setPartOf(salesOrder);
        salesOrderLine.setQuantity(salesQuotationLine.getQuantity());

        store.store(salesOrderLine);

        return salesOrderLine;
    }

    private List<PurchOrder> newPurchOrders(SalesOrder salesOrder, List<SalesOrderLine> salesOrderLines) {

        List<PurchOrder> purchOrders = new ArrayList<>();
        int numberOfVendors = config.numberOfVendors.getValue();
        int salesOrderLineCount = salesOrderLines.size();

        if (numberOfVendors > salesOrderLineCount){
            numberOfVendors = salesOrderLineCount;
        }

        for (int i = 1; i<= numberOfVendors; i++){
            Employee processedBy = employeePile.nextInstance();
            purchOrders.add(newPurchOrder(salesOrder,processedBy));
        }

        return purchOrders;
    }

    private PurchOrder newPurchOrder(SalesOrder salesOrder, Employee processedBy) {
        PurchOrder purchOrder = new PurchOrder();

        purchOrder.setServes(salesOrder);
        purchOrder.setPlacesAt(vendorPile.nextInstance());
        purchOrder.setProcessedBy(processedBy);

        purchOrder.setDate(
                config.purchDelay.delay(
                        salesOrder.getDate(),
                        salesOrder.getProcessedBy()
                )
        );

        store.store(purchOrder);

        return purchOrder;
    }

    private List<PurchOrderLine> newPurchOrderLines(List<PurchOrder> purchOrders, List<SalesOrderLine> salesOrderLines) {
        List<PurchOrderLine> purchOrderLines = new ArrayList<>();

        int linesPerPurchOrder = salesOrderLines.size() / purchOrders.size();

        for (SalesOrderLine salesOrderLine : salesOrderLines){
            int purchOrderIndex = salesOrderLines.indexOf(salesOrderLine) / linesPerPurchOrder;

            if (purchOrderIndex > (purchOrders.size()-1)){
                purchOrderIndex = purchOrders.size()-1;
            }

            PurchOrder purchOrder = purchOrders.get(purchOrderIndex);
            purchOrderLines.add(newPurchOrderLine(purchOrder,salesOrderLine));
        }

        return purchOrderLines;
    }

    private PurchOrderLine newPurchOrderLine(PurchOrder purchOrder, SalesOrderLine salesOrderLine) {
        PurchOrderLine purchOrderLine = new PurchOrderLine();

        purchOrderLine.setContains(salesOrderLine.getContains());
        purchOrderLine.setQuantity(salesOrderLine.getQuantity());
        purchOrderLine.setPartOf(purchOrder);

        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(purchOrder.getProcessedBy());
        influencingMasterDataObjects.add(purchOrder.getPlacesAt());

        BigDecimal purchPrice = purchOrderLine.getContains().getPrice();
        purchPrice = config.purchPriceVariation.getValue(influencingMasterDataObjects)
                .add(BigDecimal.ONE)
                .multiply(purchPrice)
                .setScale(2,BigDecimal.ROUND_HALF_UP);

        purchOrderLine.setPurchPrice(purchPrice);

        store.store(purchOrderLine);

        return purchOrderLine;
    }

    private List<DeliveryNote> newDeliveryNotes(List<PurchOrder> purchOrders) {
        List<DeliveryNote> deliveryNotes = new ArrayList<>();
        for(PurchOrder purchOrder : purchOrders){
            deliveryNotes.add(newDeliveryNote(purchOrder));
        }
        return deliveryNotes;
    }

    private DeliveryNote newDeliveryNote(PurchOrder purchOrder) {
        DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setContains(purchOrder);
        deliveryNote.setOperatedBy(logisticsPile.nextInstance());

        List<MasterDataObject> influencingDataObjects = new ArrayList<>();

        influencingDataObjects.add(deliveryNote.getOperatedBy());
        influencingDataObjects.add(purchOrder.getPlacesAt());

        deliveryNote.setDate(
                config.purchDeliveryDelay.delay(
                        purchOrder.getDate(),
                        influencingDataObjects
                )
        );

        deliveryNote.setTrackingCode("***TODO***");

        store.store(deliveryNote);

        return deliveryNote;
    }

    private List<PurchInvoice> newPurchInvoices(List<PurchOrderLine> purchOrderLines) {

        Map<PurchOrder,BigDecimal> purchOrderTotals = new HashMap<>();

        for(PurchOrderLine purchOrderLine : purchOrderLines){
            PurchOrder purchOrder = purchOrderLine.getPartOf();
            BigDecimal total = BigDecimal.ZERO;

            if (purchOrderTotals.containsKey(purchOrder)){
                total = purchOrderTotals.get(purchOrder);
            }
            total = total.add(purchOrderLine.getPurchAmount());
            purchOrderTotals.put(purchOrder,total);
        }

        List<PurchInvoice> purchInvoices = new ArrayList<>();

        for(Map.Entry<PurchOrder,BigDecimal> purchOrderTotal : purchOrderTotals.entrySet()){
            purchInvoices.add(newPurchInvoice(
                    purchOrderTotal.getKey(),
                    purchOrderTotal.getValue()
            ));
        }

        return purchInvoices;
    }

    private PurchInvoice newPurchInvoice(PurchOrder purchOrder, BigDecimal total) {
        PurchInvoice purchInvoice = new PurchInvoice();
        purchInvoice.setExpense(total);
        purchInvoice.setCreatedFor(purchOrder);
        purchInvoice.setText("*** TODO @ FoodBrokerage ***");
        purchInvoice.setDate(config.purchInvoiceDelay.delay(purchOrder.getDate(),purchOrder.getPlacesAt()));

        store.store(purchInvoice);

        return purchInvoice;
    }

    private SalesInvoice newSalesInvoice(List<SalesOrderLine> salesOrderLines) {
        SalesOrder salesOrder = salesOrderLines.get(0).getPartOf();

        SalesInvoice salesInvoice = new SalesInvoice();
        salesInvoice.setCreatedFor(salesOrder);
        salesInvoice.setText("*** TODO @ FoodBrokerage ***");
        salesInvoice.setRevenue(BigDecimal.ZERO);
        salesInvoice.setDate(config.salesInvoiceDelay.delay(salesOrder.getDate(),salesOrder.getProcessedBy()));

        for (SalesOrderLine salesOrderLine : salesOrderLines){
            salesInvoice.setRevenue(salesInvoice.getRevenue().add(salesOrderLine.getSalesAmount()));
        }

        store.store(salesInvoice);

        return salesInvoice;
    }


    @Override
    public List<TransactionalDataObject> getTransactionalDataObjects() {
        return null;
    }
}
