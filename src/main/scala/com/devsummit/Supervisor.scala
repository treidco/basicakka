package com.devsummit

import akka.actor.SupervisorStrategy.{Directive, Restart}
import akka.actor.{Actor, OneForOneStrategy, Props, SupervisorStrategy}
import com.devsummit.Protocol._

class Supervisor extends Actor {

  var pongState: Option[PongState] = None

  val ping = context.actorOf(Props[Ping], "ping")
  val pong = context.actorOf(Props[Pong], "pong")
  //  val ping = context.actorOf(Props(classOf[Ping], pong), "ping")


  override def receive: Receive = {
    case End => context.system.shutdown()
    case Start => ping ! Start(1)
    case ps: PongState => pongState = Some(ps)
    case RequestState => sender ! pongState.get
  }

  val decider: PartialFunction[Throwable, Directive] = {
    case _: RandomException => Restart
  }

  override def supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy()(decider.orElse(SupervisorStrategy.defaultStrategy.decider))

}
