package com.devsummit

import akka.actor.{ActorSystem, Props}

/**
 * User: timreid
 * Date: 06/10/14
 */
object Main extends App {

  sealed trait Message
  case class Starter(count: Int) extends Message
  case class PingPong(count: Int) extends Message


  val system = ActorSystem("DevSummit")
  val pong = system.actorOf(Props[Pong], "pong")
  val ping = system.actorOf(Props(classOf[Ping], pong), "ping")

  ping ! Starter(1)

}