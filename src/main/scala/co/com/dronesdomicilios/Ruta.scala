package co.com.dronesdomicilios

class Ruta(val text: List[Char]) {
  def validaDesde(coor: Coordenada, lim: Int): Boolean = {
    var copy = new Coordenada(coor.x, coor.y, coor.sentido)
    var valida = true
    text.foreach(acc => {
      acc match {
        case 'A' => copy = copy.adelante
        case 'I' => copy = copy.izquierda
        case 'D' => copy = copy.derecha
      }
      if(!copy.esValida(lim)) valida = false
    })
    valida
  }  

  def esValida: Boolean = {
    val test = text.filter(x => x!='A' && x!='I' && x!='D')
    test.size==0
  }

  override def toString: String = {
    var text = ""
    this.text.foreach(c => text = text + c)
    text
  }
}


case class PackRutas(val rts: List[Ruta])
