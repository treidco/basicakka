package com.devsummit

import akka.actor.{ActorSystem, Props}

object Main extends App {

  sealed trait Message
  case class Start(count: Int) extends Message
  case class PingPong(count: Int) extends Message
  case object End extends Message

  val system = ActorSystem("DevSummit")
  val supervisor = system.actorOf(Props[Supervisor], "supervisor")

  supervisor ! Start

}