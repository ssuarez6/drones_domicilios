package co.com.dronesdomicilios

import scala.util.Try
import scala.io.Source
import java.io.{PrintWriter, File}

sealed trait RutasReaderService {
  type ListaRutas = Try[List[List[Char]]]
  def cargarRutas: ListaRutas
}

sealed trait RutasWriterService {
  type ErrorOReporte = Either[String, String]
  def escribirRutas(reporte: ErrorOReporte)
}

class RutasLectorFicheroService (ficheroEntrada: String = "in.txt") extends RutasReaderService {
  override def cargarRutas(): ListaRutas = 
    Try(Source.fromFile(ficheroEntrada).getLines.toList.map(_.toList))
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
