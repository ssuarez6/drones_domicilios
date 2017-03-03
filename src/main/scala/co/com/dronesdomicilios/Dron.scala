package co.com.dronesdomicilios

class Dron (lim: Int = 10, val capEntregas: Int = 3, id: Int){

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
          case Adelante => ubicacion = ubicacion.adelante
          case Derecha => ubicacion = ubicacion.derecha
          case Izquierda => ubicacion = ubicacion.izquierda
      })  
      destinos = destinos.agregar(ubicacion)
    } 
  }

  def realizarEntrega(pack: PackRutas){
    destinoInalcanzable = false
    pack.rts.foreach(rt =>{
      entregarAlmuerzo(rt)
    })
    ubicacion = Coordenada.origen
  }

  def reporte = destinos.toString
}
