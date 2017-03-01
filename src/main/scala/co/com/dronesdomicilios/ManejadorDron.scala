package co.com.dronesdomicilios

import scala.io.Source
import java.io._

class ManejadorDron(val limiteEntregas: Int = 3){
  import RutaFactory._
  val impresora = new PrintWriter(new File("out.txt"))
  val dron = new Dron(limiteEntregas)

  def despachar: Either[String, String]= {
    var cont = 0
    val lineasEntrada: List[List[Char]] = Source.fromFile("in.txt").getLines.toList.map(_.toList)
    var rutaInvalida = false
    val listaRutas = lineasEntrada.map(x => RutaFactory(x))
    val todasValidas = listaRutas.forall({
      case _: RutaInvalida => false
      case _ => true
    })
    if(todasValidas){
      listaRutas.foreach(r => dron.recibirRuta(r.asInstanceOf[RutaValida]))
      Right(dron.reporte)
    }else{
      Left("Existe al menos una ruta con caracteres ilegales")
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
