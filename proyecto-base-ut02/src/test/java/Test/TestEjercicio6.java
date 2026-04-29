package Test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ucu.edu.aed.tda.*;
import Impl.*;

public class TestEjercicio6 extends TestCase{
    private ABBImpl<Integer> arbol;

    public TestEjercicio6(String testName) { //constructor de la clase test
        super(testName);
    }

    public static Test suite() { //crea un conjunto de tests para después ejecutar juntos
        return (Test) new TestSuite(TestEjercicio6.class);
    }

    protected void setUp() {
        arbol = new ABBImpl<>(); //se ejecuta antes de cada test, entorno
    }    

    public void testAltura(){
        assertEquals(-1,arbol.altura()); //devuelve -1 cuando el arbol no tiene elementos
        arbol.insertar(1);
        assertEquals(0,arbol.altura());
        arbol.insertar(2);
        assertEquals(1,arbol.altura());
    }
    public void testTamaño(){
        assertEquals(0,arbol.tamaño()); //no agregamos ninguno
        arbol.insertar(1);
        assertEquals(1,arbol.tamaño());
        arbol.insertar(2);
        assertEquals(2,arbol.tamaño());
    }
    public void testHojas(){
        assertEquals(0,arbol.cantidadHojas()); //no tiene hohas si está vacío
        arbol.insertar(4); //la raíz es hoja
        assertEquals(1,arbol.cantidadHojas());
        arbol.insertar(2);
        arbol.insertar(3);// es hoja
        arbol.insertar(5); //es hoja
        assertEquals(2,arbol.cantidadHojas());
    }
    public void testInternos(){
        assertEquals(0,arbol.cantidadNodosInternos()); //no tiene si está vacío
        arbol.insertar(4); // la raiz no es nodo interno
        assertEquals(0,arbol.cantidadNodosInternos());
        arbol.insertar(2); //es nodo interno y la raíz ahora tmb
        arbol.insertar(3);// es hoja
        arbol.insertar(5); //es hoja
        assertEquals(2,arbol.cantidadNodosInternos());
    }
    public void testCompletos(){
        assertEquals(0, arbol.completos().tamaño());//árbol vacío no tiene completos
        arbol.insertar(4);//tiene 1 nodo pero no es compelto
        assertEquals(0, arbol.completos().tamaño());
        arbol.insertar(2);//tiene 1 hijo, sigue sin ser completo
        assertEquals(0, arbol.completos().tamaño());
        arbol.insertar(6);//ahora tiene 2 hijos, es completo
        assertEquals(1, arbol.completos().tamaño());
    }
    public void testEnNivel(){
        assertEquals(0, arbol.enNivel(0).tamaño());//está vacío
        arbol.insertar(4); //raiz(hijos 2 y 6)
        arbol.insertar(2); //hijos 1 y 3
        arbol.insertar(6); //hijos 5 y 7
        arbol.insertar(1);
        arbol.insertar(3);
        arbol.insertar(5);
        arbol.insertar(7);
        assertEquals(1, arbol.enNivel(0).tamaño());//solo la raiz
        assertEquals(2, arbol.enNivel(1).tamaño());// 2 y 6
        assertEquals(4, arbol.enNivel(2).tamaño());// 1,3,4,5
    }

    
    
}