package Ej12;

import Impl.ABBImpl;
import Impl.ListaEnlazada;
import java.util.function.Consumer;
import ucu.edu.aed.tda.TDAArbolBinario;
import ucu.edu.aed.tda.TDALista;

public class Grimorio {
    private TDAArbolBinario<Hechizo> grimorio;

    public Grimorio(){
        this.grimorio = new ABBImpl<>();
    }

    public TDALista<Hechizo> prohibidos(){
        TDALista<Hechizo> prohibidos = new ListaEnlazada<>();
        grimorio.inOrder(new Consumer<Hechizo>() {
            @Override
            public void accept(Hechizo hechizo) {
                if (hechizo.getId() % 2 != 0) {
                    prohibidos.agregar(hechizo);
                }
            }
        });
        return prohibidos;
    }

    public String canto(){
        StringBuilder canto = new StringBuilder();
        grimorio.inOrder(new Consumer<Hechizo>() {
            @Override
            public void accept(Hechizo hechizo) {
                if (canto.length() > 0) {
                    canto.append(" - ");
                }
                canto.append(hechizo.getNombre());
            }
        });
        return canto.toString();
    }

    public void insertarHechizo(int id, String nombre) {
        grimorio.insertar(new Hechizo(id, nombre));
    }

    
}
