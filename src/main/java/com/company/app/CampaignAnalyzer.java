package com.company.app;

import java.util.*;

/**
 * Business logic class for filtering and sorting campaign data.
 */
public class CampaignAnalyzer {

    /**
     * Returns the top 10 campaigns by CTR, sorted in descending order.
     */
    public static List<CampaignData> getTop10Ctr(Collection<CampaignData> campaigns) {
        // Min-Heap (retains 10 highest elements)
        PriorityQueue<CampaignData> queue = new PriorityQueue<>(
                Comparator.comparingDouble(CampaignData::getCtr)
        );

        for (CampaignData data : campaigns) {
            queue.offer(data);
            if (queue.size() > 10) queue.poll(); // Keep top 10
        }
        return extractAndReverse(queue);
    }

    /**
     * Returns the top 10 campaigns by CPA, sorted in ascending order.
     * Excludes campaigns with 0 conversions (CPA is null).
     */
    public static List<CampaignData> getTop10Cpa(Collection<CampaignData> campaigns) {
        // Max-Heap (retains 10 smallest elements)
        PriorityQueue<CampaignData> queue = new PriorityQueue<>(
                (a, b) -> Double.compare(b.getCpa(), a.getCpa()) // Reverse for Min-Heap of Minima
        );

        for (CampaignData data : campaigns) {
            if (data.getCpa() != null) {
                queue.offer(data);
                if (queue.size() > 10) queue.poll(); // Keep bottom 10
            }
        }
        return extractAndReverse(queue);
    }

    /**
     * Extracts elements from Min/Max Heap and reverses them for final sorted output.
     */
    private static List<CampaignData> extractAndReverse(PriorityQueue<CampaignData> queue) {
        List<CampaignData> result = new ArrayList<>(10);
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        Collections.reverse(result);
        return result;
    }
}
