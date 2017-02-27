package co.com.dronesdomicilios

import scala.io.Source
import java.io._

class Dron(val limite: Int = 3){
  def in = Source.fromFile("in.txt").getLines.toList.map(_.toCharArray)
  val out = new PrintWriter(new File("out.txt"))
  val c = new Coordenada
  var destinos = List[Coordenada]()

  class CaracterIlegalException(c: Char) extends Exception(s"El archivo de entrada es inválido debido a que el caracter '$c' es incorrecto")
  class PosicionIncorrectaException(coor: Coordenada) extends Exception(s"La posición (${coor.x}, ${coor.y}) se sale de los límites. No se pueden entregar más almuerzos")

  def irAOrigen = {
    c.x = 0
    c.y = 0
    c.sentido = "Norte"
  }

  def entregarAlmuerzos = {
    try{
      var cont = 0
      val lineas = in
      lineas.foreach(linea => {
        if(cont == limite) {
          cont = 0
          irAOrigen
        }
        linea.foreach(char => {
          char match {
            case 'A' => c.A
            case 'I' => c.I
            case 'D' => c.D
            case z => throw new CaracterIlegalException(z)
          }
        })
        cont += 1
        val copy = new Coordenada(c.x, c.y, c.sentido)
        if(! copy.esValida) throw new PosicionIncorrectaException(copy)
        destinos = copy::destinos
      })
      reportar
      out.close
    }catch{
      case pex: PosicionIncorrectaException => {
        reportar
        out.write(pex.getMessage)
        out.close
      }
      case cex: CaracterIlegalException =>{
        out.write(cex.getMessage)
        out.close
      }
      case ex: Exception => {
        println(s"No se pudo abrir el fichero.\nError: ${ex.printStackTrace}")
        System.exit(1)
      }
    }   
  }

  def reportar = {
    out.write("== Reporte de entregas ==\n")
    destinos.reverse.foreach(cx => out.write(cx.toString))
  }
}
