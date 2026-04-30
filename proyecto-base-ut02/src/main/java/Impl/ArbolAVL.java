package Impl;

import java.util.function.Consumer;

import ucu.edu.aed.tda.*;

import java.util.function.Consumer;

import ucu.edu.aed.tda.TDAArbolBinario;
import ucu.edu.aed.tda.TDAElemento;

public class ArbolAVL<T extends Comparable<T>> implements TDAArbolBinario<T> {

    private ElementoAVL<T> raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return raiz;
    }

    public void setRaiz(ElementoAVL<T> raiz) {
        this.raiz = raiz;
    }

    @Override
    public T buscar(Comparable<T> predicate) {
        if (esVacio())
            return null;

        TDAElemento<T> buscado = raiz.buscar(predicate);
        if (buscado == null)
            return null;

        return buscado.getDato();
    }

    @Override
    public boolean insertar(Comparable<T> dato) {
        if (esVacio()) {
            raiz = new ElementoAVL<>((T) dato);
            return true;
        }

        boolean insertado = raiz.insertar(dato);
        raiz = (ElementoAVL<T>) raiz.balancear();
        return insertado;
    }


    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        if (esVacio())
            return false;

        if (criterioBusqueda.compareTo(raiz.getDato()) == 0 && raiz.esHoja()) {
            raiz = null;
            return true;
        }

        TDAElemento<T> eliminado = raiz.eliminar(criterioBusqueda);
        raiz = (ElementoAVL<T>) raiz.balancear();
        return eliminado != null;
    }

    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (esVacio())
            return;

        raiz.inOrder(new Consumer<TDAElemento<T>>() {
            @Override
            public void accept(TDAElemento<T> elemento) {
                consumidor.accept(elemento.getDato());
            }
        });
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (esVacio())
            return;

        raiz.preOrder(new Consumer<TDAElemento<T>>() {
            @Override
            public void accept(TDAElemento<T> elemento) {
                consumidor.accept(elemento.getDato());
            }
        });
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (esVacio())
            return;

        raiz.postOrder(new Consumer<TDAElemento<T>>() {
            @Override
            public void accept(TDAElemento<T> elemento) {
                consumidor.accept(elemento.getDato());
            }
        });
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public int cantidadNodos() {
        if (esVacio())
            return 0;
        return raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        if (esVacio())
            return 0;
        return raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        if (esVacio())
            return 0;
        return raiz.cantidadNodosInternos();
    }
}