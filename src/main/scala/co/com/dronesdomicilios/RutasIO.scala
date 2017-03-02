package co.com.dronesdomicilios

import scala.util.Try
import scala.io.Source
import java.io.{PrintWriter, File}

sealed trait RutasReaderService {
  type ListaRutas = Try[List[List[Comando]]]
  def cargarRutas: ListaRutas
  
  def charToComando(c: Char): Comando = {
    c match {
      case 'A' => Adelante
      case 'D' => Derecha
      case 'I' => Izquierda
      case _ => Invalido
    }
  }
}

sealed trait RutasWriterService {
  type ErrorOReporte = Either[String, String]
  def escribirRutas(reporte: ErrorOReporte)
}

class RutasLectorFicheroService (ficheroEntrada: String = "in.txt") extends RutasReaderService {
  override def cargarRutas(): ListaRutas = Try{
    Source
      .fromFile(ficheroEntrada)
      .getLines
      .toList
      .map(_
          .toList
          .map(charToComando(_))
        )
  } 

  
}

class RutasEscritorFicheroService (ficheroSalida: String = "out.txt") extends RutasWriterService {
  override def escribirRutas(reporte: ErrorOReporte) = {
    val impresora = new PrintWriter(new File(ficheroSalida))
    if(reporte.isRight){
      impresora.write("== Reporte de entregas ==\n")
      impresora.write(reporte.right.get)
    }
    else impresora.write(reporte.left.get)
    impresora.close
  }
}
