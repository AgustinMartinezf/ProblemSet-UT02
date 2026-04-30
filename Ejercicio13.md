**1. Construcción del arbol AVL:**

                60
              /    \
           40        80
          /  \      /  \
        20   50   70   90
       /  \              \
      10  30             100

**2.Identificar naves exploradoras y retornar lista de códigos**

Descripción en lenguaje natural: Se recorre el arbol AVL completo (puede ser en cualquier orden, en este caso se recorre en inorden) y, por cada nodo visitado, se verifica si la clase de la nave es "Explorador". Si lo es, se agrega el código de dicha nave a una lista resultado.

PRECONDICIONES:
- El árbol AVL puede estar vacío o contener naves insertadas.
- Cada nave tiene una clase representada como string no nulo.

POSTCONDICIONES:
- Se devuelve una lista con los códigos de todas las naves de clase "Explorador", ordenada de forma ascendente (consecuencia del inorden).
- Si no existen naves exploradoras, la lista devuelta está vacía.
- El árbol no se modifica.

Pseudocodigo:

obtenerCodigosExploradores()
Comienzo
    lista = listaVacia
    SI raiz != nulo ENTONCES
        obtenerCodigosExploradoresRec(raiz, lista)
    FIN SI
    devolver lista
Fin

obtenerCodigosExploradoresRec(nodo, lista)
Comienzo
    SI nodo.izq != nulo ENTONCES
        obtenerCodigosExploradoresRec(nodo.izq, lista)
    FIN SI
    SI nodo.dato.clase == "Explorador" ENTONCES
        lista.agregar(nodo.dato.codigo)
    FIN SI
    SI nodo.der != nulo ENTONCES
        obtenerCodigosExploradoresRec(nodo.der, lista)
    FIN SI
Fin
    

Análisis de tiempo de ejecución: O(n) porque se visita exactamente una vez cada uno de los n nodos del árbol.


**3.Calcular el combustible promedio de las naves exploradoras**

Descripción en lenguaje natural: Se recorre el árbol AVL en inorden acumulando, en una sola pasada, la suma de combustible y la cantidad de naves exploradoras encontradas. Al finalizar, si se encontró al menos una nave exploradora, se devuelve la división suma/cantidad. Si no se encontró ninguna, se devuelve -1 como valor centinela.


PRECONDICIONES:
- El árbol AVL puede estar vacío o contener naves insertadas.

POSTCONDICIONES:
- Si existe al menos una nave exploradora, se devuelve un real con el promedio de combustible de dichas naves.
- Si no existen naves exploradoras, se devuelve -1 (valor centinela que indica ausencia de datos).
- El árbol no se modifica.


combustiblePromedioExploradores()
Comienzo
    acum = [suma=0, cantidad=0]
    SI raiz != nulo ENTONCES
        combustiblePromedioRec(raiz, acum)
    FIN SI
    SI acum.cantidad == 0 ENTONCES
        devolver -1
    FIN SI
    devolver acum.suma / acum.cantidad
Fin

combustiblePromedioRec(nodo, acum)
Comienzo
    SI nodo.izq != nulo ENTONCES
        combustiblePromedioRec(nodo.izq, acum)
    FIN SI
    SI nodo.dato.clase = "Explorador" ENTONCES
        acum.suma  = acum.suma + nodo.dato.combustible
        acum.cantidad = acum.cantidad + 1
    FIN SI
    SI nodo.der != nulo ENTONCES
        combustiblePromedioRec(nodo.der, acum)
    FIN SI
Fin


Análisis del tiempo de ejecución: Se visita cada nodo exactamente una vez. La división final es O(1). El total es O(n).