package Impl;

import java.util.function.Consumer;
import ucu.edu.aed.tda.*;

public class ElementoAVL<T extends Comparable<T>> implements TDAElemento<T> {

    private T dato;
    private ElementoAVL<T> hijoIzquierdo;
    private ElementoAVL<T> hijoDerecho;

    public ElementoAVL(T dato) {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        this.hijoIzquierdo = (ElementoAVL<T>) hijoIzquierdo;
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        this.hijoDerecho = (ElementoAVL<T>) hijoDerecho;
    }

    @Override
    public TDAElemento<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    @Override
    public TDAElemento<T> getHijoDerecho() {
        return hijoDerecho;
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public int altura() {
        if (esHoja())
            return 0;

        int alturaIzq = 0;
        int alturaDer = 0;

        if (hijoIzquierdo != null)
            alturaIzq = hijoIzquierdo.altura();
        if (hijoDerecho != null)
            alturaDer = hijoDerecho.altura();

        return 1 + Math.max(alturaIzq, alturaDer);
    }

    private int obtenerAltura(ElementoAVL<T> nodo) {
        if (nodo == null)
            return -1;
        return nodo.altura();
    }

    private int factorBalance() {
        return obtenerAltura(hijoIzquierdo) - obtenerAltura(hijoDerecho);
    }

    private ElementoAVL<T> rotarDerecha() {
        ElementoAVL<T> nuevaRaiz = hijoIzquierdo;
        hijoIzquierdo = nuevaRaiz.hijoDerecho;
        nuevaRaiz.hijoDerecho = this;
        return nuevaRaiz;
    }

    private ElementoAVL<T> rotarIzquierda() {
        ElementoAVL<T> nuevaRaiz = hijoDerecho;
        hijoDerecho = nuevaRaiz.hijoIzquierdo;
        nuevaRaiz.hijoIzquierdo = this;
        return nuevaRaiz;
    }

    public ElementoAVL<T> balancear() {
        int balance = factorBalance();

        if (balance > 1 && hijoIzquierdo.factorBalance() >= 0)
            return rotarDerecha();

        if (balance > 1 && hijoIzquierdo.factorBalance() < 0) {
            hijoIzquierdo = hijoIzquierdo.rotarIzquierda();
            return rotarDerecha();
        }

        if (balance < -1 && hijoDerecho.factorBalance() <= 0)
            return rotarIzquierda();

        if (balance < -1 && hijoDerecho.factorBalance() > 0) {
            hijoDerecho = hijoDerecho.rotarDerecha();
            return rotarIzquierda();
        }

        return this;
    }

    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        int comparar = criterioBusqueda.compareTo(dato);

        if (comparar == 0)
            return this;

        if (comparar < 0 && hijoIzquierdo != null)
            return hijoIzquierdo.buscar(criterioBusqueda);
        else if (comparar > 0 && hijoDerecho != null)
            return hijoDerecho.buscar(criterioBusqueda);

        return null;
    }

    @Override
    public boolean insertar(Comparable<T> nuevoDato) {
        T datoAInsertar = (T) nuevoDato;
        int comparacion = nuevoDato.compareTo(dato);

        if (comparacion == 0)
            return false;

        boolean insertado;

        if (comparacion < 0) {
            if (hijoIzquierdo == null) {
                hijoIzquierdo = new ElementoAVL<>(datoAInsertar);
                insertado = true;
            } else {
                insertado = hijoIzquierdo.insertar(nuevoDato);
                hijoIzquierdo = hijoIzquierdo.balancear();
            }
        } else {
            if (hijoDerecho == null) {
                hijoDerecho = new ElementoAVL<>(datoAInsertar);
                insertado = true;
            } else {
                insertado = hijoDerecho.insertar(nuevoDato);
                hijoDerecho = hijoDerecho.balancear();
            }
        }

        return insertado;
    }


    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda) {
        TDAElemento<T> encontrado = buscar(criterioBusqueda);
        if (encontrado == null)
            return null;

        T datoEliminado = encontrado.getDato();
        quitarNodo(criterioBusqueda);

        return new ElementoAVL<>(datoEliminado);
    }

    private ElementoAVL<T> quitarNodo(Comparable<T> criterioBusqueda) {
        int comparacion = criterioBusqueda.compareTo(dato);

        if (comparacion < 0) {
            if (hijoIzquierdo != null) {
                hijoIzquierdo = hijoIzquierdo.quitarNodo(criterioBusqueda);
                hijoIzquierdo = (hijoIzquierdo != null) ? hijoIzquierdo.balancear() : null;
            }
        } else if (comparacion > 0) {
            if (hijoDerecho != null) {
                hijoDerecho = hijoDerecho.quitarNodo(criterioBusqueda);
                hijoDerecho = (hijoDerecho != null) ? hijoDerecho.balancear() : null;
            }
        } else {
            if (hijoIzquierdo == null) return hijoDerecho;
            if (hijoDerecho == null) return hijoIzquierdo;

            ElementoAVL<T> maximo = maximoNodo(hijoIzquierdo);
            T datoMaximo = maximo.getDato();

            hijoIzquierdo = hijoIzquierdo.quitarNodo(datoMaximo);
            hijoIzquierdo = (hijoIzquierdo != null) ? hijoIzquierdo.balancear() : null;
            dato = datoMaximo;
        }

        return balancear();
    }

    private ElementoAVL<T> maximoNodo(ElementoAVL<T> nodo) {
        if (nodo.hijoDerecho == null)
            return nodo;
        return maximoNodo(nodo.hijoDerecho);
    }


    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null)
            hijoIzquierdo.inOrder(consumidor);

        consumidor.accept(this);

        if (hijoDerecho != null)
            hijoDerecho.inOrder(consumidor);
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);

        if (hijoIzquierdo != null)
            hijoIzquierdo.preOrder(consumidor);

        if (hijoDerecho != null)
            hijoDerecho.preOrder(consumidor);
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null)
            hijoIzquierdo.postOrder(consumidor);

        if (hijoDerecho != null)
            hijoDerecho.postOrder(consumidor);

        consumidor.accept(this);
    }


    @Override
    public boolean esHoja() {
        return hijoIzquierdo == null && hijoDerecho == null;
    }

    @Override
    public int cantidadHojas() {
        if (esHoja())
            return 1;

        int hojas = 0;

        if (hijoIzquierdo != null)
            hojas += hijoIzquierdo.cantidadHojas();

        if (hijoDerecho != null)
            hojas += hijoDerecho.cantidadHojas();

        return hojas;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja())
            return 0;

        int cantidad = 1;

        if (hijoIzquierdo != null)
            cantidad += hijoIzquierdo.cantidadNodosInternos();

        if (hijoDerecho != null)
            cantidad += hijoDerecho.cantidadNodosInternos();

        return cantidad;
    }

    @Override
    public int cantidadNodos() {
        int cantidad = 1;

        if (hijoIzquierdo != null)
            cantidad += hijoIzquierdo.cantidadNodos();

        if (hijoDerecho != null)
            cantidad += hijoDerecho.cantidadNodos();

        return cantidad;
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        int comparar = criterioBusqueda.compareTo(dato);

        if (comparar == 0)
            return 0;

        int nivel = 0;

        if (comparar < 0 && hijoIzquierdo != null)
            nivel = hijoIzquierdo.obtenerNivel(criterioBusqueda);
        else if (comparar > 0 && hijoDerecho != null)
            nivel = hijoDerecho.obtenerNivel(criterioBusqueda);
        else
            return -1;

        if (nivel >= 0)
            return 1 + nivel;

        return nivel;
    }
}