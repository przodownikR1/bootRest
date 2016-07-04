/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.config;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQHealth implements HealthIndicator {
    private ConnectionFactory factory;

    @Autowired
    public ActiveMQHealth(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Health health() {
        try {
            factory.createConnection();
        } catch (JMSException e) {
            return new Health.Builder().down(e).build();
        }
        return new Health.Builder().status(Status.UP + ": Successfully connected to the broker").build();
    }
}