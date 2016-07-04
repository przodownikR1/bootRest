package pl.java.scalatech.metrics;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.health.HealthCheck;

public class DatabaseH2HealthCheck extends HealthCheck {
    
    @Autowired
    private DataSource dataSource;

    public DatabaseH2HealthCheck(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    @Override
    protected Result check() throws Exception  {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Result.healthy();
            }
            return Result.unhealthy("Connection is not valid.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}