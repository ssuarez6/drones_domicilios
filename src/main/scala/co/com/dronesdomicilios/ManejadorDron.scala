package co.com.dronesdomicilios

import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{PrintWriter, File}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

class ManejadorDron {
  type DronesSimultaneos = List[Future[Dron]]
  type TryRutas = Try[List[List[Comando]]]
  type Resultado = Either[String, String]
  implicit val ecDrones = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))
  val dron = new Dron(id = -1)
  val drones: DronesSimultaneos = (1 to 20).toList.map(n => Future(new Dron(capEntregas = 10, id = n)))


  def crearPacks(rutas: List[Ruta], cap: Int): List[PackRutas] = {
    def crearPacks(rutas: List[Ruta], salida: List[PackRutas]): List[PackRutas] = {
      if(rutas.isEmpty) salida
      else crearPacks(rutas drop cap, salida :+ PackRutas(rutas take cap))
    }
    crearPacks(rutas, List[PackRutas]())
  }

  def despachar(posiblesRutas: TryRutas): Resultado= {
    posiblesRutas match {
      case Success(list) => {
        val listRutas: List[Ruta] = list.map(x => new Ruta(x))
        val listPacks: List[PackRutas] = crearPacks(listRutas, dron.capEntregas)
        listPacks.foreach(rt => dron.realizarEntrega(rt))
        Right(dron.reporte)
      }
      case Failure(_) => Left("No se pudo leer la entrada.")
    }
  }

  def despacharDron(posiblesRutas: TryRutas, futd: Future[Dron]): Future[Resultado] = {
    posiblesRutas match {
      case Success(list) => {
        val listRutas: List[Ruta] = list.map(x => new Ruta(x))
        val listPacks: List[PackRutas] = crearPacks(listRutas, 10)
        listPacks.foreach(rt => futd.foreach(d => d.realizarEntrega(rt)))
        Thread.sleep(500)
        futd.map(d => Right(d.reporte))
      }
      case Failure(ex) => Future(Left("No se pudo leer la entrada"))
    }
  }

  def despacharTodos(todasLasRutas: List[TryRutas]): List[Future[Resultado]] = {
    (todasLasRutas zip drones).map{
      case (tryruta, dron) => despacharDron(tryruta, dron)
    }
  }
}
