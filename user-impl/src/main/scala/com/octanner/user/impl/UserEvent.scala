/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.octanner.user.impl

import com.lightbend.lagom.javadsl.persistence.AggregateEvent
import com.lightbend.lagom.serialization.Jsonable
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag
import java.time.Instant

object UserEvent {
  val Tag = AggregateEventTag.of(classOf[UserEvent])
}
sealed trait UserEvent extends AggregateEvent[UserEvent] with Jsonable {
  override def aggregateTag(): AggregateEventTag[UserEvent] = UserEvent.Tag
}
