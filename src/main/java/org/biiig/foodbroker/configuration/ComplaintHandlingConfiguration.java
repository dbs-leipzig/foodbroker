package org.biiig.foodbroker.configuration;

/**
 * Created by peet on 02.12.14.
 */
public class ComplaintHandlingConfiguration {
    public TransitionConfiguration badQualityProbability = new TransitionConfiguration("ComplaintHandlingBadQualityProbability");
    public DecimalVariationConfiguration salesRefund = new DecimalVariationConfiguration("ComplaintHandlingSalesRefund");
    public DecimalVariationConfiguration purchRefund = new DecimalVariationConfiguration("ComplaintHandlingPurchRefund");
}
