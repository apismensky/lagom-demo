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

case class UserCreated(userId: Long,
                       firstName: String,
                       lastName: String,
                       email: String,
                       timestamp: Instant = Instant.now()) extends UserEvent
