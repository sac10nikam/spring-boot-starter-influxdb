/*
 * Copyright (c) 2019. Hiberbee https://hiberbee.github.io/spring-boot-starter-influxdb
 *
 * This file is part of the Hiberbee OSS. Licensed under the MIT License
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.hiberbee.listener;

import com.hiberbee.measurement.MetricEvent;
import io.micrometer.core.instrument.MeterRegistry;
import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MetricEventListener<T extends MetricEvent> {

  private final BatchPoints batchPoints;
  private final MeterRegistry meterRegistry;

  @Autowired
  public MetricEventListener(BatchPoints batchPoints, MeterRegistry meterRegistry) {
    this.batchPoints = batchPoints;
    this.meterRegistry = meterRegistry;
  }

  @Async
  @EventListener(MetricEvent.class)
  public void onApplicationEvent(T event) {
    this.meterRegistry.counter(event.getClass().getSimpleName());
    this.batchPoints.point(event.buildPoint());
  }
}
