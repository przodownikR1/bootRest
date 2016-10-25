package pl.java.scalatech.metrics.sample;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

public class SampleManager implements Runnable {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private MetricRegistry metrics;
	private int historySize;
	private long period;
	private TimeUnit timeUnit;

	public SampleManager(MetricRegistry metrics, long period, TimeUnit timeunit, int historySize) {
		this.metrics = metrics;
		this.historySize = historySize;
		this.period = period;
		this.timeUnit = timeunit;
		scheduler.scheduleAtFixedRate(this, 0, period, timeunit);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void run() {
		for (Gauge<?> gauge : metrics.getGauges().values()) {
			if (gauge instanceof SampledGauge) {
				SampledGauge historizedGauge = (SampledGauge<?>) gauge;
				historizedGauge.setSampler(this);
				Object value = historizedGauge.getValue();
				List history = historizedGauge.getHistory();
				while (history.size() >= historySize) {
					history.remove(0);
				}
				history.add(value);
			}
		}
	}

	public long getPeriod() {
		return period;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
}

    

