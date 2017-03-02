package co.com.dronesdomicilios

import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{PrintWriter, File}

class ManejadorDron(val limiteEntregas: Int = 3){
  val dron = new Dron

  def crearPacks(rutas: List[Ruta]): List[PackRutas] = {
    def crearPacks(rutas: List[Ruta], salida: List[PackRutas]): List[PackRutas] = {
      if(rutas.isEmpty) salida
      else crearPacks(rutas drop limiteEntregas, salida :+ PackRutas(rutas take limiteEntregas))
    }
    crearPacks(rutas, List[PackRutas]())
  }

  def despachar(posiblesRutas: Try[List[List[Comando]]]): Either[String, String]= {
    posiblesRutas match {
      case Success(list) => {
        val listRutas: List[Ruta] = list.map(x => new Ruta(x))
        val listPacks: List[PackRutas] = crearPacks(listRutas)
        listPacks.foreach(rt => dron.recibirPack(rt))
        Right(dron.reporte)
      }
      case Failure(_) => Left("No se pudo leer la entrada.")
    }
  }
}
