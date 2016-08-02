package com.octanner.user.impl

import java.util.Optional
import akka.Done
import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import com.octanner.user.api.User
import scala.collection.JavaConverters._
import scala.collection.immutable.Seq

/**
  The three type parameters of the extended PersistentEntity class define:
  Command - the super class/interface of the commands
  Event - the super class/interface of the events
  State - the class of the state
  The behavior consists of current state and functions to process incoming commands and persisted events
  Each PersistentEntity instance is executed by a PersistentActor that is managed by Akka Cluster Sharding
  The Akka Persistence journal plugin is akka-persistence-cassandra.
  */
class UserEntity extends PersistentEntity[UserCommand, UserEvent, UserState] {
  override def initialBehavior(snapshotState: Optional[UserState]): Behavior = {
   // When the entity is started the state is recovered by replaying stored events.
   // To reduce this recovery time the entity may start the recovery from a snapshot of the state
   // and then only replaying the events that were stored after the snapshot.
    val b = newBehaviorBuilder(snapshotState.orElseGet(() => UserState(Option.empty)))

    // Here we define command and event handlers

    // Command handlers are invoked for incoming messages (commands).
    // A command handler must "return" the events to be persisted (if any).
    b.setCommandHandler(classOf[CreateUser], (cmd: CreateUser, ctx: CommandContext[Done]) => {
      state.user match {
        case Some(_) =>
          // The command can be validated before persisting state changes.
          // Use ctx.invalidCommand or ctx.commandFailed to reject an invalid command.
          ctx.invalidCommand(s"User $entityId is already created")
          ctx.done()
        case None =>
          val user = cmd.user
          val events = Seq(UserCreated(user.userId, user.firstName, user.lastName, user.email))
          ctx.thenPersistAll(events.asJava, () => ctx.reply(Done))
      }
    })

    // When an event has been persisted successfully the current state is updated by applying the event to the current state.
    // The functions for updating the state are registered with the setEventHandler method of the BehaviorBuilder.
    // Event handlers are used both when when persisting new events
    // and when replaying events.
    b.setEventHandler(classOf[UserCreated], (evt: UserCreated) => UserState(new User(evt.userId, evt.firstName, evt.lastName, evt.email, None)))

    // A PersistentEntity may also process commands that do not change application state, such as query commands
    b.setReadOnlyCommandHandler(classOf[GetUser], (cmd: GetUser, ctx: ReadOnlyCommandContext[GetUserReply]) =>
      ctx.reply(GetUserReply(state.user))
    )

    b.build()
  }
}
