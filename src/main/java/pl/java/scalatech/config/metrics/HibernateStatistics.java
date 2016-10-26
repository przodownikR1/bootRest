package pl.java.scalatech.config.metrics;

import lombok.Getter;

@Getter

public class HibernateStatistics {



	private long executedQueries = 0;
	private long fetchedQueryResults = 0;
	private long queryTime = 0;

	private long updatedCollections = 0;
	private long fetchedCollections = 0;
	private long deletedEntities = 0;
	private long insertedEntities = 0;
	private long updatedEntities = 0;
	private long fetchedEntities = 0;
	private long loadedEntities = 0;

	public void queryExecuted(int rows, long time) {
		executedQueries++;
		fetchedQueryResults += rows;
		queryTime += time;
	}

	public void add(HibernateStatistics summary) {
		this.executedQueries += summary.executedQueries;
		this.fetchedQueryResults += summary.fetchedQueryResults;
		this.queryTime += summary.queryTime;
		this.updatedCollections += summary.updatedCollections;
		this.fetchedCollections += summary.fetchedCollections;
		this.deletedEntities += summary.deletedEntities;
		this.insertedEntities += summary.insertedEntities;
		this.updatedEntities += summary.updatedEntities;
		this.fetchedEntities += summary.fetchedEntities;
		this.loadedEntities += summary.loadedEntities;
	}

	public boolean isEmpty() {
		return loadedEntities == 0 && executedQueries == 0 && updatedCollections == 0 && fetchedCollections == 0 && deletedEntities == 0 && insertedEntities == 0 && updatedEntities == 0 && fetchedEntities == 0;
	}

	private void append(StringBuilder builder, String key, long count) {
		if (count > 0) {
			if (builder.length() > 0)
				builder.append(",");
			builder.append(key);
			builder.append("=");
			builder.append(count);
		}
	}

	public void toString(StringBuilder builder) {
		if (executedQueries > 0) {
			builder.append("qC=").append(executedQueries);
			builder.append(",qR=").append(fetchedQueryResults);
			builder.append(",qT=").append(queryTime);
		}
		append(builder, "cU", updatedCollections);
		append(builder, "cF", fetchedCollections);
		append(builder, "eD", deletedEntities);
		append(builder, "eI", insertedEntities);
		append(builder, "eU", updatedEntities);
		append(builder, "eF", fetchedEntities);
		append(builder, "eL", loadedEntities);
	}

    public HibernateStatistics getInstance() {        
        return this;
    }
}