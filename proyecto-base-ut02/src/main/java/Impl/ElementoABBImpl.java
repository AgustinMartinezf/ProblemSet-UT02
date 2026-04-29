package Impl;

import java.util.function.Consumer;

import ucu.edu.aed.tda.*;

public class ElementoABBImpl<T> implements TDAElemento<T> {

    private T dato;
    private TDAElemento<T> hijoIzquierdo;
    private TDAElemento<T> hijoDerecho;

    // Contador estático que se incrementa con cada invocación recursiva
    public static int contador = 0;

    public static void resetContador() {
        contador = 0;
    }

    public ElementoABBImpl(T dato) {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
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
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        contador++;
        // Comparar con el dato actual
        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion == 0) {
            return this;
        }

        if (comparacion < 0) {
            // Buscar en el subárbol izquierdo
            if (hijoIzquierdo != null) {
                return hijoIzquierdo.buscar(criterioBusqueda);
            }
        } else {
            // Buscar en el subárbol derecho
            if (hijoDerecho != null) {
                return hijoDerecho.buscar(criterioBusqueda);
            }
        }

        return null;
    }

    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda) {
        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion < 0) {
            if (hijoIzquierdo != null) {
                // Caso: el hijo izquierdo es el nodo a eliminar
                int compHijo = criterioBusqueda.compareTo(hijoIzquierdo.getDato());
                if (compHijo == 0) {
                    TDAElemento<T> eliminado = hijoIzquierdo;
                    hijoIzquierdo = resolverEliminacion(hijoIzquierdo);
                    return eliminado;
                }
                return hijoIzquierdo.eliminar(criterioBusqueda);
            }
        } else if (comparacion > 0) {
            if (hijoDerecho != null) {
                int compHijo = criterioBusqueda.compareTo(hijoDerecho.getDato());
                if (compHijo == 0) {
                    TDAElemento<T> eliminado = hijoDerecho;
                    hijoDerecho = resolverEliminacion(hijoDerecho);
                    return eliminado;
                }
                return hijoDerecho.eliminar(criterioBusqueda);
            }
        }

        return null;
    }

    /**
     * Resuelve la eliminación de un nodo, retornando el nodo que lo reemplaza.
     * Maneja los tres casos: hoja, un hijo, dos hijos.
     */
    private TDAElemento<T> resolverEliminacion(TDAElemento<T> nodo) {
        // Caso 1: es hoja
        if (nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() == null) {
            return null;
        }

        // Caso 2: tiene solo hijo derecho
        if (nodo.getHijoIzquierdo() == null) {
            return nodo.getHijoDerecho();
        }

        // Caso 3: tiene solo hijo izquierdo
        if (nodo.getHijoDerecho() == null) {
            return nodo.getHijoIzquierdo();
        }

        // Caso 4: tiene dos hijos - buscar sucesor inorden (menor del subárbol derecho)
        TDAElemento<T> sucesor = obtenerMenor(nodo.getHijoDerecho());
        // Eliminar el sucesor de su posición original
        // Crear un Comparable para buscar el sucesor
        T datoSucesor = sucesor.getDato();
        // Eliminamos el sucesor del subárbol derecho
        if (nodo.getHijoDerecho().getDato().equals(datoSucesor)) {
            // El sucesor es el hijo derecho directo
            nodo.setDato(datoSucesor);
            nodo.setHijoDerecho(nodo.getHijoDerecho().getHijoDerecho());
        } else {
            // El sucesor está más abajo: eliminar recursivamente
            eliminarMenor(nodo.getHijoDerecho());
            nodo.setDato(datoSucesor);
        }

        return nodo;
    }

    /**
     * Obtiene el nodo con la clave menor del subárbol (más a la izquierda).
     */
    private TDAElemento<T> obtenerMenor(TDAElemento<T> nodo) {
        TDAElemento<T> actual = nodo;
        while (actual.getHijoIzquierdo() != null) {
            actual = actual.getHijoIzquierdo();
        }
        return actual;
    }

    /**
     * Elimina el nodo más a la izquierda del subárbol.
     */
    private void eliminarMenor(TDAElemento<T> nodo) {
        if (nodo.getHijoIzquierdo().getHijoIzquierdo() == null) {
            // El hijo izquierdo es el menor
            nodo.setHijoIzquierdo(nodo.getHijoIzquierdo().getHijoDerecho());
        } else {
            eliminarMenor(nodo.getHijoIzquierdo());
        }
    }

    @Override
    public boolean insertar(Comparable<T> nuevoDato) {
        contador++;
        int comparacion = nuevoDato.compareTo(this.dato);

        if (comparacion == 0) {
            // Ya existe, no insertar
            return false;
        }

        if (comparacion < 0) {
            if (hijoIzquierdo == null) {
                hijoIzquierdo = new ElementoABBImpl<>((T) nuevoDato);
                return true;
            } else {
                return hijoIzquierdo.insertar(nuevoDato);
            }
        } else {
            if (hijoDerecho == null) {
                hijoDerecho = new ElementoABBImpl<>((T) nuevoDato);
                return true;
            } else {
                return hijoDerecho.insertar(nuevoDato);
            }
        }
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null) {
            hijoIzquierdo.inOrder(consumidor);
        }
        consumidor.accept(this);
        if (hijoDerecho != null) {
            hijoDerecho.inOrder(consumidor);
        }
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);
        if (hijoIzquierdo != null) {
            hijoIzquierdo.preOrder(consumidor);
        }
        if (hijoDerecho != null) {
            hijoDerecho.preOrder(consumidor);
        }
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null) {
            hijoIzquierdo.postOrder(consumidor);
        }
        if (hijoDerecho != null) {
            hijoDerecho.postOrder(consumidor);
        }
        consumidor.accept(this);
    }

    @Override
    public boolean esHoja() {
        return hijoIzquierdo == null && hijoDerecho == null;
    }

    @Override
    public int cantidadHojas() {
        if (esHoja()) {
            return 1;
        }

        int hojas = 0;
        if (hijoIzquierdo != null) {
            hojas = hojas + hijoIzquierdo.cantidadHojas();
        }
        if (hijoDerecho != null) {
            hojas = hojas + hijoDerecho.cantidadHojas();
        }
        return hojas;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja()) {
            return 0;
        }

        int internos = 1;
        if (hijoIzquierdo != null) {
            internos = internos + hijoIzquierdo.cantidadNodosInternos();
        }
        if (hijoDerecho != null) {
            internos = internos + hijoDerecho.cantidadNodosInternos();
        }
        return internos;
    }

    @Override
    public int cantidadNodos() {
        int cantidad = 1;
        if (hijoIzquierdo != null) {
            cantidad = cantidad + hijoIzquierdo.cantidadNodos();
        }
        if (hijoDerecho != null) {
            cantidad = cantidad + hijoDerecho.cantidadNodos();
        }
        return cantidad;
    }

    @Override
    public int altura() {
        int alturaIzq = -1;
        int alturaDer = -1;

        if (hijoIzquierdo != null) {
            alturaIzq = hijoIzquierdo.altura();
        }
        if (hijoDerecho != null) {
            alturaDer = hijoDerecho.altura();
        }

        if (alturaIzq > alturaDer) {
            return alturaIzq + 1;
        } else {
            return alturaDer + 1;
        }
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion == 0) {
            return 0;
        }

        if (comparacion < 0) {
            if (hijoIzquierdo != null) {
                int nivel = hijoIzquierdo.obtenerNivel(criterioBusqueda);
                if (nivel >= 0) {
                    return nivel + 1;
                }
            }
        } else {
            if (hijoDerecho != null) {
                int nivel = hijoDerecho.obtenerNivel(criterioBusqueda);
                if (nivel >= 0) {
                    return nivel + 1;
                }
            }
        }

        return -1;
    }

    public void cantidadNodosCompletos(TDALista<TDAElemento<T>> lista){ //Ejercicio 6
        if (this.esHoja()) {//caso base
            return;
        }
        if (this.hijoIzquierdo != null && this.hijoDerecho != null) {
            lista.agregar(this);
        }

        //si solo tiene uno vuelve a recursar y no pasa nada si sigue
        if (this.hijoIzquierdo != null) {
            ((ElementoABBImpl<T>)this.hijoIzquierdo).cantidadNodosCompletos(lista);
        }

        if (this.hijoDerecho != null) {
            ((ElementoABBImpl<T>)this.hijoDerecho).cantidadNodosCompletos(lista);
        }
    }

    public void enNivel(int nivel, TDALista<TDAElemento<T>> lista) { //método para Ejercicio 6

        if (nivel == 0) {
            lista.agregar(this);
            return;
        }

        if (hijoIzquierdo != null) {
            ((ElementoABBImpl<T>) hijoIzquierdo).enNivel(nivel - 1, lista);
        }

        if (hijoDerecho != null) {
            ((ElementoABBImpl<T>) hijoDerecho).enNivel(nivel - 1, lista);
        }
    }

}