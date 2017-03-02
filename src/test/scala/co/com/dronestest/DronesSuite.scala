package co.com.dronestest

import org.scalatest.FunSuite
import co.com.dronesdomicilios._
import scala.util.{Try, Success, Failure}

class DronesSuite extends FunSuite {
  test("Una ruta no debe ser valida si tiene comandos invalidos"){
    val comandos = List(Derecha, Izquierda, Adelante, Adelante, Izquierda, Invalido)
    val rt = new Ruta(comandos)
    val verificador = new VerificadorRuta
    assert(!verificador.esValida(rt))
  }

  test("Debe mostrarse el error en el reporte si la lectura de la entrada no se hizo correctamente"){
    val md = new ManejadorDron
    val reporte = md.despachar(Failure(new Exception("XD")))
    assert(reporte.isLeft)
  }

  test("Los movimientos del dron deben hacerse correctamente"){
    val comandos = List(Derecha, Adelante, Adelante, Izquierda)
    val rt = new Ruta(comandos)
    val d = new Dron
    d.entregarAlmuerzo(rt)
    val reporte = d.reporte
    val esperado = "(2, 0) dirección Norte"
    assert(reporte.contains(esperado))
  }

  test("Deben crearse packs de rutas de acuerdo a la capacidad del dron"){
    val cmds = List(Adelante, Derecha, Izquierda)
    val rt = new Ruta(cmds)
    val md = new ManejadorDron
    val listas = List(rt, rt, rt, rt, rt)
    val packs = md.crearPacks(listas)
    assert(packs(0).rts.size == md.dron.capEntregas)
  }

  test("No pueden ser validas rutas afuera de 10 puntos en el plano de cuadras"){
    val ubicacion = new Coordenada(9,9,"Norte")
    val rt = new Ruta(List(Adelante, Adelante, Adelante))
    val verifier = new VerificadorRuta
    assert(!verifier.validaDesde(rt, ubicacion, 10))
  }

  test("Despues de entregar el pack, el dron debe volver al restaurante"){
    val d = new Dron
    val rt = new Ruta(List(Adelante, Adelante, Derecha))
    val paquete = new PackRutas(List(rt,rt,rt))
    d.realizarEntrega(paquete)
    assert(d.ubicacion.x == 0 && d.ubicacion.y == 0 && d.ubicacion.sentido == "Norte")
  }
}
