package com.octanner.user.impl

import java.util.Optional
import akka.Done
import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import com.octanner.user.api.User
import scala.collection.JavaConverters._
import scala.collection.immutable.Seq

class UserEntity extends PersistentEntity[UserCommand, UserEvent, UserState] {
  override def initialBehavior(snapshotState: Optional[UserState]): Behavior = {

    val b = newBehaviorBuilder(snapshotState.orElseGet(() => UserState(Option.empty)))

    b.setCommandHandler(classOf[CreateUser], (cmd: CreateUser, ctx: CommandContext[Done]) => {
      state.user match {
        case Some(_) =>
          ctx.invalidCommand(s"User $entityId is already created")
          ctx.done()
        case None =>
          val user = cmd.user
          val events = Seq(UserCreated(user.userId, user.firstName, user.lastName, user.email))
          ctx.thenPersistAll(events.asJava, () => ctx.reply(Done))
      }
    })

    b.setEventHandler(classOf[UserCreated], (evt: UserCreated) => UserState(new User(evt.userId, evt.firstName, evt.lastName, evt.email, None)))

    b.setReadOnlyCommandHandler(classOf[GetUser], (cmd: GetUser, ctx: ReadOnlyCommandContext[GetUserReply]) =>
      ctx.reply(GetUserReply(state.user))
    )

    b.build()
  }
}
