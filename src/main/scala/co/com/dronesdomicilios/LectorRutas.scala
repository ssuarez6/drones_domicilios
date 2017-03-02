package co.com.dronesdomicilios

import scala.util.Try
import scala.io.Source

sealed trait LectorRutas {
  type ListaRutas = Try[List[List[Char]]]
  def cargarRutas: ListaRutas
}

class LectorFichero(nombreFichero: String = "in.txt") extends LectorRutas {
  override def cargarRutas: ListaRutas = 
    Try(Source.fromFile(nombreFichero).getLines.toList.map(_.toList))
}
