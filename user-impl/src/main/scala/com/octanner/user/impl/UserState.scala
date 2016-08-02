package com.octanner.user.impl

import com.lightbend.lagom.serialization.Jsonable
import com.octanner.user.api.User

case class UserState(user: Option[User]) extends Jsonable

object UserState {
  def apply(user: User): UserState = UserState(Option(user))
}