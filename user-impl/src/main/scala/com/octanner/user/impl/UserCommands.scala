package com.octanner.user.impl

import akka.Done
import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import com.lightbend.lagom.serialization.Jsonable
import com.octanner.user.api.User


sealed trait UserCommand extends Jsonable

case class CreateUser(user: User) extends PersistentEntity.ReplyType[Done] with UserCommand

// Each command must define what type of message to use as reply to the command by implementing the PersistentEntity.ReplyType interface
case class GetUser() extends PersistentEntity.ReplyType[GetUserReply] with UserCommand

case class GetUserReply(user: Option[User]) extends Jsonable
