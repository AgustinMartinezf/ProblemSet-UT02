# ProblemSet-UT02

Ejercicio 7 - Parte 1


Algoritmo Evaluar:
Dado un árbol binario que representa una expresión aritmética:
Si el nodo contiene una constante, devuelve su valor.
Si el nodo contiene una variable, se busca su valor en la estructura de valores y se devuelve.
Si el nodo contiene un operador, evalua recursivamente el subárbol izquierdo para luego evaluar recursivamente el subárbol derecho. Después se aplica la operación correspondiente y por último se devuelve el resultado.

Precondiciones:
Precondiciones:
- El árbol binario está correctamente formado
- Las variables presentes en el árbol tienen valores asignados
- No hay divisiones por cero
  
Postcondiciones:
- Se devuelve el valor numérico resultante de evaluar la expresión representada por el árbol

Pseudocódigo:
Funcion evaluar(nodo, valores):
  si nodo.dato es numero:
    retornar nodo.valor
    
  si nodo.dato es variable
    retornar valores[nodo.dato]

  izq = evaluar(nodo.izq, valores)
  der = evaluar(nodo.der, valores)

  si nodo.dato = '+':
    retornar izq + der
  si nodo.dato = '-':
    retornar izq - der
  si nodo.dato = '*':
    retornar izq * der
  si nodo.dato = '/':
    retornar izq / der
