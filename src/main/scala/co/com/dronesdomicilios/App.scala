package co.com.dronesdomicilios

//import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}
import scala.util.Try

object EntregaDeAlmuerzos extends App {
  val md = new ManejadorDron
  val lectorFichero = new RutasLectorFicheroService
  val lecturaEntrada: Try[List[List[Comando]]] = lectorFichero.cargarRutas
  val reporte = md.despachar(lecturaEntrada)
  val escritorFichero = new RutasEscritorFicheroService
  val escritor = escritorFichero.escribirRutas(reporte)
  System.exit(0)
}
