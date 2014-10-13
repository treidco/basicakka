package com.devsummit

import akka.actor.{ActorRef, Actor, PoisonPill}
import com.devsummit.Main.Interrupt

class Interrupter(pong: ActorRef) extends Actor {

  override def receive: Receive = {
    case Interrupt => {
      pong ! PoisonPill
    }
  }

}
