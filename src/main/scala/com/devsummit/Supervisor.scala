package com.devsummit

import akka.actor.{Props, Actor}
import com.devsummit.Main.{End, Start}

class Supervisor extends Actor {


  val pong = context.actorOf(Props[Pong], "pong")
  val ping = context.actorOf(Props(classOf[Ping], pong), "ping")

  ping ! Start(1)

  override def receive: Receive = {
    case End => {
      context.system.shutdown()
    }
  }

}
