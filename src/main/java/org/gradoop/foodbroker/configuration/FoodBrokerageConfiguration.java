package org.gradoop.foodbroker.configuration;

/**
 * Created by peet on 21.11.14.
 */
public class FoodBrokerageConfiguration {

    public ProcessConfiguration process = new ProcessConfiguration("FoodBrokerage");

    public IntRangeConfiguration numberOfQuotationLines = new IntRangeConfiguration("FoodBrokerageNumberOfQuotationLines");
    public IntRangeConfiguration numberOfQuotedProducts = new IntRangeConfiguration("FoodBrokerageNumberOfQuotedProducts");
    public IntRangeConfiguration numberOfVendors = new IntRangeConfiguration("FoodBrokerageNumberOfVendors");


    public DecimalVariationConfiguration salesMargin = new DecimalVariationConfiguration("FoodBrokerageSalesMargin");
    public DecimalVariationConfiguration purchPriceVariation = new DecimalVariationConfiguration("FoodBrokeragePurchPriceVariation");
    public TransitionConfiguration confirmationProbability = new TransitionConfiguration("FoodBrokerageConfirmationProbability");


    public DelayConfiguration confirmationDelay = new DelayConfiguration("FoodBrokerageConfirmationDelay");
    public DelayConfiguration deliveryAgreementDelay = new DelayConfiguration("FoodBrokerageDeliveryAgreementDelay");
    public DelayConfiguration purchDelay = new DelayConfiguration("FoodBrokeragePurchDelay");
    public DelayConfiguration purchDeliveryDelay = new DelayConfiguration("FoodBrokeragePurchDeliveryDelay");
    public DelayConfiguration purchInvoiceDelay = new DelayConfiguration("FoodBrokeragePurchInvoiceDelay");
    public DelayConfiguration salesInvoiceDelay = new DelayConfiguration("FoodBrokerageSalesInvoiceDelay");

}
