/*
 * Copyright (c) 2019. Hiberbee https://hiberbee.github.io/spring-boot-starter-influxdb
 *
 * This file is part of the Hiberbee OSS. Licensed under the MIT License
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.hiberbee.measurement;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.influxdb.annotation.Measurement;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Measurement(name = "application.event")
public final class DomainEvent<S, ID>
    implements MetricEvent<S, ID>, Serializable, Comparable<MetricEvent> {

  private static final long serialVersionUID = -1009588577633063567L;

  @EqualsAndHashCode.Include @ToString.Include private final ID id;

  private final S source;
  private final Enum type;
  private final LocalDateTime creationTimestamp = LocalDateTime.now();

  public DomainEvent(ID id, Enum type, S source) {
    this.id = id;
    this.source = source;
    this.type = type;
  }

  public S getSource() {
    return this.source;
  }

  @Override
  public int compareTo(MetricEvent o) {
    return this.getCreationTimestamp().compareTo(o.getCreationTimestamp());
  }
}
