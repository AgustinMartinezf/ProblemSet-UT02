package Ej13;

import ucu.edu.aed.tda.TDAElemento;
import java.util.ArrayList;
import java.util.List;
import Impl.*;

public class ElementoAVLNave extends ElementoAVL<Nave> {

    public ElementoAVLNave(Nave dato) {
        super(dato);
    }

    public void obtenerCodigosExploradoresRec(List<Integer> lista) {
        ElementoAVLNave izq = (ElementoAVLNave) getHijoIzquierdo();
        ElementoAVLNave der = (ElementoAVLNave) getHijoDerecho();

        if (izq != null)
            izq.obtenerCodigosExploradoresRec(lista);

        if (getDato().getClase().equals("Explorador"))
            lista.add(getDato().getCodigo());

        if (der != null)
            der.obtenerCodigosExploradoresRec(lista);
    }

    public void combustiblePromedioRec(int[] acum) {
        ElementoAVLNave izq = (ElementoAVLNave) getHijoIzquierdo();
        ElementoAVLNave der = (ElementoAVLNave) getHijoDerecho();

        if (izq != null)
            izq.combustiblePromedioRec(acum);

        if (getDato().getClase().equals("Explorador")) {
            acum[0] += getDato().getCombustible();
            acum[1] += 1; 
        }

        if (der != null)
            der.combustiblePromedioRec(acum);
    }
}