## Ejercicio 12 — El Grimorio del Archimago
### * Consultar hechizos prohibidos (ID impar)

**1. Descripción en lenguaje natural:**

Se recorren todos los nodos del árbol (recorrido inorden para obtenerlos ordenados por ID). Para cada nodo, se verifica si su ID es impar. Si lo es, se agrega a una lista de hechizos prohibidos.

**2. Precondiciones y Postcondiciones:**

PRECONDICIONES:
- El árbol (grimorio) puede estar vacío o tener hechizos insertados.
- Cada hechizo tiene un ID entero positivo.

POSTCONDICIONES:
- Se devuelve una lista con todos los hechizos cuyo ID es impar.
- La lista está ordenada por ID (consecuencia del recorrido inorden).
- El árbol no se modifica.

**3. Seudocódigo:**

```
// Método del árbol
obtenerProhibidos()
Comienzo
    listaProhibidos ← listaVacia
    SI raiz <> nulo ENTONCES
        raiz.obtenerProhibidos(listaProhibidos)
    FIN SI
    devolver listaProhibidos
Fin

// Método del nodo
obtenerProhibidos(lista)
Comienzo
    SI hijoIzquierdo <> nulo ENTONCES
        hijoIzquierdo.obtenerProhibidos(lista)
    FIN SI

    SI this.dato.id es impar ENTONCES
        lista.agregar(this.dato)
    FIN SI

    SI hijoDerecho <> nulo ENTONCES
        hijoDerecho.obtenerProhibidos(lista)
    FIN SI
Fin
```

**4. Tiempo de ejecución:** O(n), donde n es la cantidad de hechizos. Se visita cada nodo exactamente una vez.

### * Generar el cántico

**1. Descripción en lenguaje natural:**

Se recorre el árbol en inorden. Para cada hechizo cuyo ID es impar (prohibido), se concatena su nombre a una cadena, separando cada nombre con " - ". El resultado es el cántico secreto.

**2. Precondiciones y Postcondiciones:**

PRECONDICIONES:
- El árbol (grimorio) puede estar vacío o tener hechizos.

POSTCONDICIONES:
- Se devuelve una cadena con los nombres de los hechizos prohibidos (ID impar), en orden de ID, separados por " - ".
- Si no hay hechizos prohibidos, se devuelve una cadena vacía.

**3. Seudocódigo:**

```
generarCantico()
Comienzo
    cantico ← ""
    prohibidos ← obtenerProhibidos()

    i ← 0
    MIENTRAS i < prohibidos.tamaño() HACER
        SI cantico <> "" ENTONCES
            cantico ← cantico + " - "
        FIN SI
        cantico ← cantico + prohibidos.obtener(i).nombre
        i ← i + 1
    FIN MIENTRAS

    devolver cantico
Fin
```

**4. Tiempo de ejecución:** O(n) para el recorrido + O(k) para la concatenación, donde k es la cantidad de prohibidos. Total: O(n).