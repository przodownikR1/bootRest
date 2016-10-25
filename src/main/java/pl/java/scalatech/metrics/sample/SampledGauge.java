package pl.java.scalatech.metrics.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Sampling;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.UniformSnapshot;

public class SampledGauge<T> implements Sampling, Gauge<T> {

	private Gauge<T> gauge;

	private ArrayList<T> history = new ArrayList<T>();

	private SampleManager sampler;

	public SampledGauge(Gauge<T> gauge) {
		this.gauge = gauge;
	}

	@Override
	public T getValue() {
		return gauge.getValue();
	}

	@Override
	public Snapshot getSnapshot() {
		if (sampler != null && !history.isEmpty() && history.get(0) instanceof Number) {

			// TODO remo: support non-integer numbers
			long[] values = new long[history.size()];
			for(int i = 0 ; i < values.length;i++){
				Number value = (Number) history.get(i);
				values[i] = value.longValue();
			}

			return new UniformSnapshot(values);
		}
        return null;
	}

	public List<T> getHistory() {
		return history;
	}

	protected void setSampler(SampleManager sampler) {
		this.sampler = sampler;
	}

	public long getPeriod() {
		return sampler.getPeriod();
	}

	public TimeUnit getTimeUnit() {
		return sampler.getTimeUnit();
	}
}