package com.devsummit

import akka.actor.{Actor, Props}
import com.devsummit.Main.{End, Start}

class Supervisor extends Actor {

  val pong = context.actorOf(Props[Pong], "pong")
  val ping = context.actorOf(Props(classOf[Ping], pong), "ping")

  context.watch(pong)

  override def receive: Receive = {
    case End => context.system.shutdown()
    case Start => ping ! Start(1)
  }


}
