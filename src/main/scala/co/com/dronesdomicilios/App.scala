package co.com.dronesdomicilios

import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}

object EntregaDeAlmuerzos extends App {
  val md = new ManejadorDron
  md.escribirEnFichero
  System.exit(0)
}
