# Implementación y Análisis de Complejidad: List, Stack y Queue

Proyecto de **Estructuras de Datos (2026-2)**, Facultad de Ingeniería, Universidad Nacional de Colombia.

- **Autora:** Clelia Edithzoe Alzate León
- **Docente:** David Alberto Herrera Álvarez
- **Fecha:** 30 de septiembre de 2026

## Descripción

Este repositorio contiene las implementaciones en **Java** de varias estructuras de datos lineales y el análisis experimental de su complejidad temporal:

- **Listas enlazadas** (`LinkedList`) en cuatro variantes: simple y doble, con y sin referencia al último nodo (`tail`).
- **`MyStack<T>`**, una pila sobre arreglo dinámico genérico.
- **`MyQueue<T>`**, una cola sobre arreglo dinámico circular genérico.

El objetivo es comparar su comportamiento, contrastar la complejidad teórica con los tiempos medidos (en nanosegundos) e identificar en qué escenarios conviene cada estructura. El análisis completo está en el informe del proyecto.

## Estructuras implementadas

| Estructura | Clase | Descripción |
|---|---|---|
| Lista simple sin tail | `SinglyLinkedListNoTail` | Atributos `head` y `size`. |
| Lista simple con tail | `SinglyLinkedListWithTail` | Atributos `head`, `tail` y `size`. |
| Lista doble sin tail | `DoublyLinkedListNoTail` | Nodos con `next` y `prev`; atributos `head` y `size`. |
| Lista doble con tail | `DoublyLinkedListWithTail` | Nodos con `next` y `prev`; atributos `head`, `tail` y `size`. |
| Pila | `MyStack<T>` | Arreglo dinámico; duplica la capacidad al llenarse. |
| Cola | `MyQueue<T>` | Arreglo circular con índices `front` y `rear`; duplica la capacidad al llenarse. |

### Operaciones

- **Listas:** `pushFront`, `pushBack`, `popFront`, `popBack`, `front`, `back`, `find`, `erase`, `addBefore`, `addAfter`, `isEmpty`, `size`.
- **`MyStack<T>`:** `push`, `pop`, `peek`, `isEmpty`, `size`, `delete`.
- **`MyQueue<T>`:** `enqueue`, `dequeue`, `peek`, `isEmpty`, `size`, `delete`.

> **Nota:** las listas almacenan datos de tipo `int`; `MyStack<T>` y `MyQueue<T>` son genéricas. Los métodos que no encuentran elemento (por ejemplo `popFront` o `front` en una lista vacía) retornan `-1`, y los de pila y cola retornan `null`.

## Complejidad temporal teórica

Las celdas en **negrita** marcan dónde la referencia `tail` cambia el orden de complejidad respecto a la lista sin cola.

| Operación | Simple sin tail | Simple con tail | Doble sin tail | Doble con tail |
|---|:---:|:---:|:---:|:---:|
| `pushFront` | O(1) | O(1) | O(1) | O(1) |
| `pushBack` | O(n) | **O(1)** | O(n) | **O(1)** |
| `popFront` | O(1) | O(1) | O(1) | O(1) |
| `popBack` | O(n) | O(n) | O(n) | **O(1)** |
| `front` | O(1) | O(1) | O(1) | O(1) |
| `back` | O(n) | **O(1)** | O(n) | **O(1)** |
| `find` | O(n) | O(n) | O(n) | O(n) |
| `erase` | O(n) | O(n) | O(n) | O(n) |
| `addBefore` | O(n) | O(n) | O(n) | O(n) |
| `addAfter` | O(n) | O(n) | O(n) | O(n) |
| `isEmpty` / `size` | O(1) | O(1) | O(1) | O(1) |

| Estructura | Operaciones principales | `peek`, `size`, `isEmpty` | `delete` |
|---|---|:---:|:---:|
| `MyStack<T>` | `push`, `pop`: O(1) amortizado | O(1) | O(n) |
| `MyQueue<T>` | `enqueue`, `dequeue`: O(1) amortizado | O(1) | O(n) |

**Observaciones:**

- `pushBack` y `back` pasan de O(n) a O(1) con `tail`, tanto en la lista simple como en la doble.
- `popBack` solo llega a O(1) en la **lista doble con tail**. En la simple con tail sigue siendo O(n), porque hay que recorrer hasta el penúltimo nodo.
- `find`, `erase`, `addBefore` y `addAfter` son O(n) en las cuatro listas porque buscan por valor. Si ya se dispone del nodo, la inserción o el borrado en sí cuesta O(1), pero localizarlo cuesta O(n).
- En pila y cola, el redimensionamiento cuesta O(n) pero ocurre solo cuando la capacidad se duplica, de ahí el costo **amortizado** O(1). `delete` es O(n) por la búsqueda y el desplazamiento de elementos.

## Resultados experimentales

Los tiempos se midieron en **nanosegundos** para tamaños de n = 10¹ hasta 10⁷. Se usa esta unidad porque las operaciones O(1) duran apenas unos nanosegundos y en milisegundos se redondearían a 0, mientras que las O(n) llegan a milisegundos con n grande. Las mediciones de `pushBack` y `popBack` con n ≥ 10⁶ en las listas que deben recorrer hasta el final se **omitieron** por el tiempo que tomarían.

Valores representativos (tiempo promedio por operación, n = 10⁵):

| Operación | Simple sin tail | Simple con tail | Doble sin tail | Doble con tail |
|---|---:|---:|---:|---:|
| `pushBack` | 84 887 ns | 3,87 ns | 93 616 ns | 4,35 ns |
| `popBack` | 98 535 ns | 85 039 ns | 84 814 ns | 4,86 ns |
| `find` | 143 079 ns | 126 488 ns | 142 996 ns | 136 182 ns |

Conclusiones principales de las mediciones:

- Las operaciones O(1) se mantienen planas (unos 2–25 ns) al crecer n, lo que coincide con la teoría.
- `tail` elimina el costo lineal de `pushBack`, pero solo la lista doble con tail resuelve también `popBack` en tiempo constante.
- `find`, `erase`, `addBefore` y `addAfter` crecen proporcionalmente a n en todas las listas. Entre 10⁶ y 10⁷ el costo por nodo aumenta, hipótesis: la lista deja de caber en caché (no se midieron contadores de hardware).
- `push`/`pop` en `MyStack` y `enqueue`/`dequeue` en `MyQueue` se mantienen en 3–12 ns sin depender de n.
- Frente a las listas, pila y cola sobre arreglo obtienen menores tiempos absolutos en operaciones equivalentes gracias al acceso contiguo a memoria y a que no asignan nodos. Con 10⁷ elementos, `delete` toma ≈ 12,2 ms en `MyStack` y ≈ 8,5 ms en `MyQueue`, frente a 25–34 ms de `erase` en las listas.

Las tablas completas y las gráficas (escala logarítmica) están en el informe.

## Cuándo usar cada estructura

- **Pila (LIFO):** lista simple sin tail (solo usa el inicio) o arreglo dinámico. Ejemplos: "Deshacer" (Ctrl + Z), botón "Atrás" del navegador, pila de ejecución del sistema operativo.
- **Cola (FIFO):** lista simple con tail o arreglo dinámico circular. Ejemplos: gestión de impresión, reproducción de música y video, peticiones o eventos.
- **Deque (acceso eficiente a ambos extremos):** lista doble con tail.
- **Búsqueda o modificación por valor frecuente:** ninguna de las opciones es conveniente, todas son O(n).

### Arreglos dinámicos vs. listas enlazadas

| | Ventajas | Desventajas |
|---|---|---|
| **Arreglo dinámico** | Memoria contigua (mejor uso de caché), menor sobrecarga por elemento, acceso por índice O(1). | Copia O(n) al llenarse y puede quedar capacidad sin usar; insertar o eliminar en el medio o al inicio obliga a desplazar elementos. |
| **Lista enlazada** | Sin redimensionamiento, costo estable por operación, O(1) en los extremos con `tail` y enlace doble, inserción o borrado O(1) si se tiene el nodo. | Búsqueda y acceso por posición O(n); un objeto por nodo (más memoria y carga para el recolector de basura) y nodos dispersos en memoria. |

## Estructura del repositorio
.
├── src/
│   ├── SinglyLinkedListNoTail.java
│   ├── SinglyLinkedListWithTail.java
│   ├── DoublyLinkedListNoTail.java
│   ├── DoublyLinkedListWithTail.java
│   ├── MyStack.java
│   └── MyQueue.java
├── informe/            # Informe del proyecto (PDF)
└── README.md

## Referencias

1. S. Rose. (2025) *Big O*. Consultado: 28-09-2026. [Online]. Disponible en: https://samwho.dev/big-o
