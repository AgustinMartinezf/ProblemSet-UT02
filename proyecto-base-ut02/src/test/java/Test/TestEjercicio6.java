package Test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import Impl.ABBImpl;

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

    public void altura(){
        assertEquals(0,arbol.altura());
    }
}
