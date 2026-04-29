package Impl;

import java.util.function.Consumer;
import ucu.edu.aed.tda.TDAArbolBinario;
import ucu.edu.aed.tda.TDAElemento;


public class ABBImpl<T> implements TDAArbolBinario<T> {

    private TDAElemento<T> raiz;

    public ABBImpl() {
        this.raiz = null;
    }

    @Override
    public T buscar(Comparable<T> predicate) {
        if (raiz == null) {
            return null;
        }
        TDAElemento<T> resultado = raiz.buscar(predicate);
        if (resultado != null) {
            return resultado.getDato();
        }
        return null;
    }

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return raiz;
    }

    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        if (raiz == null) {
            return false;
        }

        // Caso especial: la raíz es el nodo a eliminar
        int comparacion = criterioBusqueda.compareTo(raiz.getDato());
        if (comparacion == 0) {
            raiz = resolverEliminacionRaiz(raiz);
            return true;
        }

        TDAElemento<T> eliminado = raiz.eliminar(criterioBusqueda);
        return eliminado != null;
    }

    /**
     * Resuelve la eliminación de la raíz del árbol.
     * Retorna el nuevo nodo raíz.
     */
    private TDAElemento<T> resolverEliminacionRaiz(TDAElemento<T> nodo) {
        // Caso 1: es hoja
        if (nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() == null) {
            return null;
        }

        // Caso 2: solo hijo derecho
        if (nodo.getHijoIzquierdo() == null) {
            return nodo.getHijoDerecho();
        }

        // Caso 3: solo hijo izquierdo
        if (nodo.getHijoDerecho() == null) {
            return nodo.getHijoIzquierdo();
        }

        // Caso 4: tiene dos hijos - buscar sucesor inorden (menor del subárbol derecho)
        TDAElemento<T> sucesor = nodo.getHijoDerecho();
        while (sucesor.getHijoIzquierdo() != null) {
            sucesor = sucesor.getHijoIzquierdo();
        }
        T datoSucesor = sucesor.getDato();

        // Eliminar el sucesor de su posición original
        if (nodo.getHijoDerecho().getDato().equals(datoSucesor)) {
            // El sucesor es el hijo derecho directo
            nodo.setHijoDerecho(nodo.getHijoDerecho().getHijoDerecho());
        } else {
            eliminarMenorDeSubarbol(nodo.getHijoDerecho());
        }

        nodo.setDato(datoSucesor);
        return nodo;
    }

    /**
     * Elimina el nodo más a la izquierda del subárbol dado.
     */
    private void eliminarMenorDeSubarbol(TDAElemento<T> nodo) {
        if (nodo.getHijoIzquierdo().getHijoIzquierdo() == null) {
            nodo.setHijoIzquierdo(nodo.getHijoIzquierdo().getHijoDerecho());
        } else {
            eliminarMenorDeSubarbol(nodo.getHijoIzquierdo());
        }
    }

    @Override
    public boolean insertar(Comparable<T> dato) {
        if (raiz == null) {
            raiz = new ElementoABBImpl<>((T) dato);
            return true;
        }
        return raiz.insertar(dato);
    }

    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.inOrder(elem -> consumidor.accept(elem.getDato()));
        }
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.preOrder(elem -> consumidor.accept(elem.getDato()));
        }
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.postOrder(elem -> consumidor.accept(elem.getDato()));
        }
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public int cantidadNodos() {
        if (raiz == null) {
            return 0;
        }
        return raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        if (raiz == null) {
            return 0;
        }
        return raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        if (raiz == null) {
            return 0;
        }
        return raiz.cantidadNodosInternos();
    }

    /**
     * Devuelve la altura del árbol. -1 si está vacío.
     */
    public int altura() {
        if (raiz == null) {
            return -1;
        }
        return raiz.altura();
    }
}