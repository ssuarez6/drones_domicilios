package co.com.dronesdomicilios

class Ruta(val text: List[Comando]) {
  override def toString: String = {
    var text = ""
    this.text.foreach(c => text = text + comandoToChar(c))
    text
  }

  def comandoToChar(c: Comando): Char = {
    c match {
      case Adelante => 'A'
      case Derecha => 'D'
      case Izquierda => 'I'
      case Invalido => '$'
    }
  }
}

case class PackRutas(val rts: List[Ruta])

class VerificadorRuta(){
  def validaDesde(rt: Ruta, coor: Coordenada, lim: Int): Boolean = {
    var copy = new Coordenada(coor.x, coor.y, coor.sentido)
    var valida = true
    rt.text.foreach(acc => {
      acc match {
        case Adelante => copy = copy.adelante
        case Izquierda => copy = copy.izquierda
        case Derecha => copy = copy.derecha
      }
      valida = copy.entreLimites(lim)
    })
    valida
  }  

  def esValida(rt: Ruta): Boolean = 
    rt.text.filter(x => x==`Invalido`).size == 0
}
