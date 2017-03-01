package co.com.dronesdomicilios

import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io._

class ManejadorDron(val limiteEntregas: Int = 3){
  val impresora = new PrintWriter(new File("out.txt"))
  val dron = new Dron

  def crearPacks(rutas: List[Ruta]): List[PackRutas] = {
    def crearPacks(rutas: List[Ruta], salida: List[PackRutas]): List[PackRutas] = {
      if(rutas.isEmpty) salida
      else crearPacks(rutas drop limiteEntregas, salida :+ PackRutas(rutas take limiteEntregas))
    }
    crearPacks(rutas, List[PackRutas]() )
  }

  def despachar: Either[String, String]= {
    val attemp = Try(Source.fromFile("in.txt").getLines.toList.map(_.toList))
    attemp match {
      case Success(list) => {
        val listRutas: List[Ruta] = list.map(x => new Ruta(x))
        val listPacks: List[PackRutas] = crearPacks(listRutas)
        listPacks.foreach(rt => dron.recibirPack(rt))
        Right(dron.reporte)
      }
      case Failure(ex) => Left("No se pudo abrir el fichero, compruebe que exista")
    }
  }

  def escribirEnFichero = {
    val resultado = despachar
    if(resultado.isRight){
      impresora.write("== Reporte de entregas ==\n")
      impresora.write(resultado.right.get)
    }
    else impresora.write(resultado.left.get)
    impresora.close
  }
}
