package co.com.dronesdomicilios

import scala.io.Source
import java.io._

class Dron (lim: Int = 10){

  import Coordenada._
  import RutaFactory._
  var ubicacion = new Coordenada
  var destinos = new Destinos
  var destinoInalcanzable = false

  def entregarAlmuerzo(rt: RutaValida) = {
    if(!rt.validaDesde(ubicacion, lim) || destinoInalcanzable) {
      destinos = destinos.agregar(s"Ruta ${rt.toString} es inalcanzable") 
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
  }

  def recibirPack(pack: PackRutas){
    destinoInalcanzable = false
    val rutas = pack.rts
    rutas.foreach(rt =>{
      entregarAlmuerzo(rt)
    })
    ubicacion = Coordenada.origen
  }

  def reporte = destinos.toString
}
