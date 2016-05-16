package com.octanner.user.impl

import java.util.Optional

import com.lightbend.lagom.javadsl.persistence.PersistentEntity

class UserEntity extends PersistentEntity[UserCommand, UserEvent, UserState] {
  override def initialBehavior(snapshotState: Optional[UserState]): Behavior = {
    val b = newBehaviorBuilder(snapshotState.orElseGet(() => UserState(Option.empty)))

    b.setReadOnlyCommandHandler(classOf[GetUser], (cmd: GetUser, ctx: ReadOnlyCommandContext[GetUserReply]) =>
      ctx.reply(GetUserReply(state.user))
    )

    b.build()
  }
}
