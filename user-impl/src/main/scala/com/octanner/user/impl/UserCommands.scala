package com.octanner.user.impl

import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import com.lightbend.lagom.serialization.Jsonable
import com.octanner.user.api.User


sealed trait UserCommand extends Jsonable

case class GetUser() extends PersistentEntity.ReplyType[GetUserReply] with UserCommand

case class GetUserReply(user: Option[User]) extends Jsonable
