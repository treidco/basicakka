package com.devsummit

import akka.actor.SupervisorStrategy.{Directive, Restart}
import akka.actor.{Actor, OneForOneStrategy, Props, SupervisorStrategy}
import com.devsummit.Main.{RequestState, End, PongState, Start}

class Supervisor extends Actor {

  val pong = context.actorOf(Props[Pong], "pong")
  val ping = context.actorOf(Props(classOf[Ping], pong), "ping")

  var pongState: Option[PongState] = None

//  context.watch(pong)

  override def receive: Receive = {
    case End => context.system.shutdown()
    case Start => ping ! Start(1)
    case ps :PongState => pongState = Some(ps)
    case RequestState => sender ! pongState.get
  }

  val decider: PartialFunction[Throwable, Directive] = {
    case _: RandomException => Restart
  }

  override def supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy()(decider.orElse(SupervisorStrategy.defaultStrategy.decider))

}
