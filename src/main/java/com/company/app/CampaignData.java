package com.company.app;

import java.util.Locale;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/**
 * Modern, high-performance Data Model for campaign statistics.
 * Uses Lock-Free Adders (LongAdder/DoubleAdder) for thread-safe concurrency.
 */
public class CampaignData {
    private final String campaignId;
    private final LongAdder totalImpressions = new LongAdder();
    private final LongAdder totalClicks = new LongAdder();
    private final DoubleAdder totalSpend = new DoubleAdder();
    private final LongAdder totalConversions = new LongAdder();

    public CampaignData(String campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * Efficiently updates the counters in a thread-safe manner.
     */
    public void addRecord(long impressions, long clicks, double spend, long conversions) {
        this.totalImpressions.add(impressions);
        this.totalClicks.add(clicks);
        this.totalSpend.add(spend);
        this.totalConversions.add(conversions);
    }

    public String getCampaignId() { return campaignId; }

    /**
     * Calculates Click-Through Rate (CTR).
     *
     * @return CTR as double.
     */
    public double getCtr() {
        long impressions = totalImpressions.sum();
        if (impressions == 0) return 0.0;
        return (double) totalClicks.sum() / impressions;
    }

    /**
     * Calculates Cost Per Acquisition (CPA).
     * Returns null if total conversions is 0.
     *
     * @return CPA as Double or null.
     */
    public Double getCpa() {
        long conversions = totalConversions.sum();
        if (conversions == 0) return null;
        return totalSpend.sum() / conversions;
    }

    /**
     * Generates a CSV row representation of the aggregated data.
     */
    public String toCsvRow() {
        Double cpa = getCpa();
        String cpaStr = (cpa == null) ? "null" : String.format(Locale.US, "%.2f", cpa);
        return String.format(Locale.US, "%s,%d,%d,%.2f,%d,%.4f,%s",
                campaignId, totalImpressions.sum(), totalClicks.sum(), totalSpend.sum(), totalConversions.sum(), getCtr(), cpaStr);
    }
}