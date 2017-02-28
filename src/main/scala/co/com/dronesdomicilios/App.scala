package co.com.dronesdomicilios

import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}
import Dron._

object EntregaDeAlmuerzos extends App {
  val md = new ManejadorDron
  md.despachar
  md.escribirEnFichero
  System.exit(0)
}
