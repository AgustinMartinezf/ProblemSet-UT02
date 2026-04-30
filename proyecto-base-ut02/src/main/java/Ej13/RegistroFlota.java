package Ej13;

import ucu.edu.aed.tda.TDAElemento;
import java.util.ArrayList;
import java.util.List;

public class RegistroFlota {

    private ElementoAVLNave raiz;

    public RegistroFlota() {
        this.raiz = null;
    }

    public boolean insertar(Nave nave) {
        if (raiz == null) {
            raiz = new ElementoAVLNave(nave);
            return true;
        }
        boolean resultado = raiz.insertar(nave);
        raiz = (ElementoAVLNave) raiz.balancear();
        return resultado;
    }

    public TDAElemento<Nave> buscar(Nave criterioBusqueda) {
        if (raiz == null) return null;
        return raiz.buscar(criterioBusqueda);
    }

    public List<Integer> obtenerCodigosExploradores() {
        List<Integer> lista = new ArrayList<>();
        if (raiz != null)
            raiz.obtenerCodigosExploradoresRec(lista);
        return lista;
    }

    public double combustiblePromedioExploradores() {
        if (raiz == null) return -1;
        int[] acum = { 0, 0 };
        raiz.combustiblePromedioRec(acum);
        if (acum[1] == 0) return -1;
        return acum[0] / acum[1];
    }
}