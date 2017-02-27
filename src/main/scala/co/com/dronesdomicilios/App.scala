package co.com.dronesdomicilios

import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}
import Dron._

object EntregaDeAlmuerzos extends App {
  val system = ActorSystem("sistemaDrones")

  val d1 = system.actorOf(Props(new Dron), "Dron1")

  d1 ! EntregarAlmuerzos
  d1 ! Reportar

  system.terminate
}
