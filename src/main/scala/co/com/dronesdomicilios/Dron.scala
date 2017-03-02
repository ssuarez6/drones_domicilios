package co.com.dronesdomicilios

class Dron (lim: Int = 10){

  import Coordenada._
  var ubicacion = new Coordenada
  var destinos = new Destinos
  var destinoInalcanzable = false
  val ver = new VerificadorRuta

  def entregarAlmuerzo(rt: Ruta) = {
    val rutaEsInvalida = !ver.esValida(rt) || !ver.validaDesde(rt, ubicacion, lim)
    if(rutaEsInvalida || destinoInalcanzable) {
      destinos = destinos.agregar(s"Ruta ${rt.toString} es inalcanzable o inválida") 
      destinoInalcanzable = true
    }else{
      rt.text.foreach({
          case 'A' => ubicacion = ubicacion.adelante
          case 'D' => ubicacion = ubicacion.derecha
          case 'I' => ubicacion = ubicacion.izquierda
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
