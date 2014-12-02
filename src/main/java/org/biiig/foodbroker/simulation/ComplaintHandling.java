package org.biiig.foodbroker.simulation;

import org.biiig.foodbroker.configuration.ComplaintHandlingConfiguration;
import org.biiig.foodbroker.configuration.ProcessConfiguration;
import org.biiig.foodbroker.model.*;
import org.biiig.foodbroker.pile.EmployeePile;
import org.biiig.foodbroker.stores.Store;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by peet on 02.12.14.
 */
public class ComplaintHandling implements BusinessProcess {

    private final FoodBrokerage foodBrokerage;
    private final EmployeePile employeePile;
    private final ComplaintHandlingConfiguration config;
    private final Store store;

    public ComplaintHandling(FoodBrokerage foodBrokerage, EmployeePile employeePile) {
        this.foodBrokerage = foodBrokerage;
        this.employeePile = employeePile;
        this.store = foodBrokerage.getStore();
        this.config = new ComplaintHandlingConfiguration();
    }

    @Override
    public void start(Date startDate) {
        lateDelivery();
        badQuality();
    }

    private void badQuality() {
        List<DeliveryNote> deliveryNotes = foodBrokerage.getDeliveryNotes();



        for(DeliveryNote deliveryNote : deliveryNotes){
            List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();

            PurchOrder purchOrder = deliveryNote.getContains();
            List<PurchOrderLine> purchOrderLines = purchOrder.getLines();

            for(PurchOrderLine purchOrderLine : purchOrderLines){
                influencingMasterDataObjects.add(purchOrderLine.getContains());
            }

            int containedProducts = influencingMasterDataObjects.size();

            // increase relative influence of vendor and logistics
            for(int i = 1; i<=containedProducts/2;i++){
                influencingMasterDataObjects.add(deliveryNote.getOperatedBy());
                influencingMasterDataObjects.add(purchOrder.getPlacedAt());
            }

            if(config.badQualityProbability.happens(influencingMasterDataObjects)){
                List<SalesOrderLine> badSalesOrderLines = new ArrayList<>();
                for(PurchOrderLine purchOrderLine : purchOrder.getLines()){
                    badSalesOrderLines.add(purchOrderLine.getSalesOrderLine());
                }
                SalesOrder salesOrder = foodBrokerage.getSalesOrder();

                Ticket ticket = newTicket(salesOrder,"bad quality",deliveryNote.getDate());
                grantSalesRefund(badSalesOrderLines, ticket);
                claimPurchRefund(purchOrderLines, ticket);
            }
        }
    }

    private void lateDelivery() {
        List<DeliveryNote> deliveryNotes = foodBrokerage.getDeliveryNotes();
        SalesOrder salesOrder = foodBrokerage.getSalesOrder();
        List<SalesOrderLine> lateSalesOrderLines = new ArrayList<>();

        for(DeliveryNote deliveryNote : deliveryNotes){
            if(deliveryNote.getDate().after(salesOrder.getDeliveryDate())){
                lateSalesOrderLines.addAll(salesOrder.getLines());
            }
        }

        if(! lateSalesOrderLines.isEmpty()){
            List<PurchOrderLine> latePurchOrderLines = new ArrayList<>();
            for(SalesOrderLine salesOrderLine : lateSalesOrderLines){
                latePurchOrderLines.add(salesOrderLine.getPurchOrderLine());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(salesOrder.getDeliveryDate());
            calendar.add(Calendar.DATE,1);
            Date createdDate = calendar.getTime();


            Ticket ticket = newTicket(salesOrder, "late delivery", createdDate);
            grantSalesRefund(lateSalesOrderLines, ticket);
            claimPurchRefund(latePurchOrderLines,ticket);
        }
    }

    private void claimPurchRefund(List<PurchOrderLine> purchOrderLines, Ticket ticket) {
        PurchOrder purchOrder = purchOrderLines.get(0).getPartOf();

        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(ticket.getAllocatedTo());
        influencingMasterDataObjects.add(purchOrder.getPlacedAt());

        BigDecimal refundHeight = config.purchRefund.getValue(influencingMasterDataObjects);
        BigDecimal refundAmount = BigDecimal.ZERO;

        for(PurchOrderLine purchOrderLine : purchOrderLines){
            refundAmount = refundAmount.add(purchOrderLine.getPurchAmount());
        }
        refundAmount = refundAmount
                .multiply(BigDecimal.valueOf(-1))
                .multiply(refundHeight)
                .setScale(2,BigDecimal.ROUND_HALF_UP);

        if(refundAmount.floatValue() < 0){
            PurchInvoice purchInvoice = new PurchInvoice();
            purchInvoice.setCreatedFor(purchOrder);
            purchInvoice.setText("Refund Ticket " + String.valueOf(ticket.getLocalID()));
            purchInvoice.setDate(ticket.getCreatedAt());
            purchInvoice.setExpense(refundAmount);

            store.store(purchInvoice);
        }
    }

    private void grantSalesRefund(List<SalesOrderLine> salesOrderLines, Ticket ticket) {
        SalesOrder salesOrder = salesOrderLines.get(0).getPartOf();

        List<MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(ticket.getAllocatedTo());
        influencingMasterDataObjects.add(salesOrder.getReceivedFrom());

        BigDecimal refundHeight = config.salesRefund.getValue(influencingMasterDataObjects);
        BigDecimal refundAmount = BigDecimal.ZERO;

        for(SalesOrderLine salesOrderLine : salesOrderLines){
            refundAmount = refundAmount.add(salesOrderLine.getSalesAmount());
        }
        refundAmount = refundAmount
                .multiply(BigDecimal.valueOf(-1))
                .multiply(refundHeight)
                .setScale(2,BigDecimal.ROUND_HALF_UP);

        if(refundAmount.floatValue() < 0){
            SalesInvoice salesInvoice = new SalesInvoice();
            salesInvoice.setCreatedFor(salesOrder);
            salesInvoice.setText("Refund Ticket " + String.valueOf(ticket.getLocalID()));
            salesInvoice.setDate(ticket.getCreatedAt());
            salesInvoice.setRevenue(refundAmount);

            store.store(salesInvoice);
        }
    }

    private Ticket newTicket(SalesOrder salesOrder, String problem, Date createdDate) {
        Ticket ticket = new Ticket(salesOrder);
        ticket.setCreatedAt(salesOrder.getDeliveryDate());
        ticket.setCreatedBy(employeePile.nextInstance().getUser());
        ticket.setAllocatedTo(employeePile.nextInstance().getUser());
        ticket.setProblem(problem);
        ticket.setCreatedAt(createdDate);

        Customer customer = salesOrder.getReceivedFrom();

        if(! customer.hasClient()){
            Client client = new Client(customer);
            customer.setClient(client);
            store.store(client);
        }

        ticket.setOpenedBy(customer.getClient());

        store.store(ticket);

        return ticket;
    }

    @Override
    public ProcessConfiguration getConfig() {
        return null;
    }

    @Override
    public Store getStore() {
        return null;
    }
}
