package co.com.dronesdomicilios

import scala.io.Source
import java.io._

class Dron(val limiteEntregas: Int = 3, val id: String = "01") {

  import Coordenada._
  import RutaFactory._
  var ubicacion = new Coordenada
  var destinos = new Destinos
  var destinoInalcanzable = false
  var rutasHechas = 0

  def recibirRuta(rt: RutaValida) = {
    if(!rt.validaDesde(ubicacion) || destinoInalcanzable) {
      destinos = destinos.agregar(s"Ruta $rt es inalcanzable") 
      destinoInalcanzable = true
    }else{
      rt.text.foreach(cmd =>{
        cmd match {
          case 'A' => ubicacion = ubicacion.adelante
          case 'D' => ubicacion = ubicacion.derecha
          case 'I' => ubicacion = ubicacion.izquierda
        }
      })  
      destinos = destinos.agregar(ubicacion)
    }   
    rutasHechas += 1
    if(rutasHechas == limiteEntregas) {
      rutasHechas = 0
      ubicacion = Coordenada.origen
    }
  }

  def reporte = destinos.toString
}
