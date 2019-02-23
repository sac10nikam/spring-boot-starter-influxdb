/*
 * Copyright (c) 2019. Hiberbee https://hiberbee.github.io/spring-boot-starter-influxdb
 *
 * This file is part of the Hiberbee OSS. Licensed under the MIT License
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.hiberbee.configuration;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InfluxDbConfiguration {

  @Value("${spring.influx.url}")
  private String url;

  @Value("${spring.influx.user}")
  private String user;

  @Value("${spring.influx.password}")
  private String password;

  @Value("${spring.influx.database}")
  private String database;

  @Bean
  public InfluxDB influxDB() {
    final var influxDB = InfluxDBFactory.connect(this.url, this.user, this.password);
    influxDB.setDatabase(this.database);
    influxDB.enableBatch(10, 100, TimeUnit.MILLISECONDS);
    return influxDB;
  }

  @Bean
  public BatchPoints batchPoints() {
    return BatchPoints.database(this.database).retentionPolicy("defaultPolicy").build();
  }
}
