package Test;

import Ej12.Grimorio;
import Ej12.Hechizo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ucu.edu.aed.tda.TDALista;

public class TestEjercicio12 extends TestCase {

	private Grimorio grimorio;

	public TestEjercicio12(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(TestEjercicio12.class);
	}

	protected void setUp() {
		grimorio = new Grimorio();
		grimorio.insertarHechizo(42, "Fireball");
        grimorio.insertarHechizo(17, "Ice Lance");
        grimorio.insertarHechizo(58, "Thunder");
        grimorio.insertarHechizo(9, "Invisibility");
        grimorio.insertarHechizo(31, "Levitate");
        grimorio.insertarHechizo(73, "Summon");
        grimorio.insertarHechizo(25, "Heal");
        grimorio.insertarHechizo(50, "Teleport");
        grimorio.insertarHechizo(65, "Shield");
        grimorio.insertarHechizo(88, "Curse");
	}

	public void testProhibidos() {
		TDALista<Hechizo> prohibidos = grimorio.prohibidos();

		assertEquals(6, prohibidos.tamaño());
		assertEquals("(9, Invisibility)", prohibidos.obtener(0).toString());
		assertEquals("(17, Ice Lance)", prohibidos.obtener(1).toString());
		assertEquals("(25, Heal)", prohibidos.obtener(2).toString());
		assertEquals("(31, Levitate)", prohibidos.obtener(3).toString());
		assertEquals("(65, Shield)", prohibidos.obtener(4).toString());
		assertEquals("(73, Summon)", prohibidos.obtener(5).toString());
	}

	public void testCanto() {
		assertEquals(
			"Invisibility - Ice Lance - Heal - Levitate - Fireball - Teleport - Thunder - Shield - Summon - Curse",
			grimorio.canto()
		);
	}

}
