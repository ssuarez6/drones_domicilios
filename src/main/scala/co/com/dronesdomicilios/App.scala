package co.com.dronesdomicilios

//import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}
import scala.util.Try

object EntregaDeAlmuerzos extends App {
  val md = new ManejadorDron
  /*
   * Ejecucion de la primera parte del ejercicio
   */
  val lectorFichero = new RutasLectorFicheroService
  val escritorFichero = new RutasEscritorFicheroService
  val lecturaEntrada: Try[List[List[Comando]]] = lectorFichero.cargarRutas
  val escritor = escritorFichero.escribirRutas(md.despachar(lecturaEntrada))


  System.exit(0)
}
