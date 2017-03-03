package co.com.dronesdomicilios

//import akka.actor.{ActorRef, ActorSystem, Props, Actor, Inbox}
import scala.util.Try
import scala.concurrent.ExecutionContext
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import java.util.concurrent.Executors

object EntregaDeAlmuerzos extends App {
  val md = new ManejadorDron
  implicit val ecEscritores = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))
  /*
   * Ejecucion de la primera parte del ejercicio
   */
  val lectorFichero = new RutasLectorFicheroService
  val escritorFichero = new RutasEscritorFicheroService
  val lecturaEntrada: Try[List[List[Comando]]] = lectorFichero.cargarRutas
  val escritor = escritorFichero.escribirRutas(md.despachar(lecturaEntrada))


  /*
   * Ejecución de la segunda parte del ejercicio
   */
  val listLectores = (1 to 20).toList
    .map(n => new RutasLectorFicheroService(s"in$n.txt"))
  val listEscritores = (1 to 20).toList
    .map(n => new RutasEscritorFicheroService(s"out$n.txt"))

  val listRutasEntrada = listLectores.map(lector => lector.cargarRutas)
  val reportes = md.despacharTodos(listRutasEntrada)
  (reportes zip listEscritores).foreach{
    case (rep, escritor) => rep.foreach(out => escritor.escribirRutas(out))
  }
  Await.ready(Future.sequence(reportes), 10 seconds)
  //System.exit(0)
}
