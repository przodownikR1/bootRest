package pl.java.scalatech.config.metrics;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_COLLECTION_FETCHED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_COLLECTION_UPDATED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_ENTITY_DELETED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_ENTITY_FETCHED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_ENTITY_INSERTED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_ENTITY_LOADED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_ENTITY_UPDATED;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_QUERY_COUNT;
import static pl.java.scalatech.config.constants.MetricsConstant.METRIC_QUERY_TIME;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;


@Configuration
@Profile("dev")
public class HibernateMetricSet implements MetricSet {

    private final HibernateStatistics stats ;
	
    private final Statistics statistics;
    
	public HibernateMetricSet(EntityManager em, Statistics statistics) {
	    this.stats = new HibernateStatistics();
	    this.statistics = statistics;	
	}

	public HibernateStatistics getStats() {
		return stats.getInstance();
	}

	public void finalize() {
	    //TODO
         //		collector.removeMetricSet(this);
	}

	public long getValue(String key) {
		Metric metric = getMetrics().get(key);
		return (Long) ((Gauge<?>) metric).getValue();
	}

	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> metrics = new HashMap<>();
		metrics.put(METRIC_QUERY_COUNT, (Gauge<Long>) () -> statistics.getQueryExecutionCount());
		//metrics.put(METRIC_QUERY_RESULTS, (Gauge<Long>) () -> getStats().getFetchedQueryResults());
		metrics.put(METRIC_QUERY_TIME, (Gauge<Long>) () -> getStats().getQueryTime());
		metrics.put(METRIC_COLLECTION_FETCHED, (Gauge<Long>) () -> getStats().getFetchedCollections());
		metrics.put(METRIC_COLLECTION_UPDATED, (Gauge<Long>) () -> getStats().getUpdatedCollections());
		metrics.put(METRIC_ENTITY_INSERTED, (Gauge<Long>) () -> getStats().getInsertedEntities());
		metrics.put(METRIC_ENTITY_UPDATED, (Gauge<Long>) () -> getStats().getUpdatedEntities());
		metrics.put(METRIC_ENTITY_LOADED, (Gauge<Long>) () -> getStats().getLoadedEntities());
		metrics.put(METRIC_ENTITY_FETCHED, (Gauge<Long>) () -> getStats().getFetchedEntities());
		metrics.put(METRIC_ENTITY_DELETED, (Gauge<Long>) () -> getStats().getDeletedEntities());
		return Collections.unmodifiableMap(metrics);
	}
}