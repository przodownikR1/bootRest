package pl.java.scalatech.metrics.sample;

import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

public class SampledMetricSet implements MetricSet {

	private MetricSet metricSet;

	public SampledMetricSet(MetricSet metricSet) {
		this.metricSet = metricSet;
	}

	@Override
	public Map<String, Metric> getMetrics() {
		Map<String, Metric> sampledMap = new HashMap<>();
		for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
			Metric metric = entry.getValue();
			if (metric instanceof Gauge){
				metric = new SampledGauge((Gauge) metric);
			}
			sampledMap.put(entry.getKey(), metric);
		}
		return sampledMap;
	}
	
}