/*
 * Copyright (c) 2019. Hiberbee https://hiberbee.github.io/spring-boot-starter-influxdb
 *
 * This file is part of the Hiberbee OSS. Licensed under the MIT License
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.hiberbee.measurement;

import org.influxdb.dto.Point;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public interface MetricEvent<S, ID> {

  ID getId();

  Enum getType();

  LocalDateTime getCreationTimestamp();

  S getSource();

  default Point buildPoint() {
    return Point.measurement(this.getType().name())
        .time(
            this.getCreationTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli(),
            TimeUnit.NANOSECONDS)
        .addField("id", this.getId().toString())
        .addField("source", this.getSource().toString())
        .build();
  }
}
