# Implementación y Análisis de Complejidad: List, Stack y Queue en Java

Proyecto de la asignatura **Estructuras de Datos (2026-2)**, Facultad de Ingeniería, Universidad Nacional de Colombia.

- **Autora:** Clelia Edithzoe Alzate León
- **Docente:** David Alberto Herrera Alvarez
- **Fecha:** 28 de septiembre de 2026

## Objetivo

Analizar y comparar el comportamiento de las estructuras de datos **List**, **Stack** y **Queue**, implementadas con **listas enlazadas** (`LinkedList`) y **arreglos dinámicos** (`DinamicArray`) en Java. Se busca identificar las condiciones más adecuadas para usar cada una, evaluando ventajas, desventajas y aplicaciones en escenarios reales.

## Contenido del proyecto

### Listas enlazadas (`int`)

| Implementación | Atributos | Descripción |
|---|---|---|
| `SinglyLinkedListNoTail` | `head`, `size` | Lista simple sin referencia al último nodo |
| `SinglyLinkedListWithTail` | `head`, `tail`, `size` | Lista simple con referencia al último nodo |
| `DoublyLinkedListNoTail` | `head`, `size` (+ `prev` en `Node`) | Lista doble sin referencia al último nodo |
| `DoublyLinkedListWithTail` | `head`, `tail`, `size` (+ `prev` en `Node`) | Lista doble con referencia al último nodo |

Métodos: `pushFront`, `pushBack`, `popFront`, `popBack`, `find`, `erase`, `addBefore`, `addAfter`, `front`, `back`, `isEmpty`, `size`.

### Estructuras sobre arreglos dinámicos (genéricas `<T>`)

- **`MyStack<T>`**: pila (LIFO) sobre arreglo dinámico que duplica su capacidad al llenarse.
  Métodos: `push`, `pop`, `peek`, `isEmpty`, `size`, `delete`.
- **`MyQueue<T>`**: cola (FIFO) sobre arreglo circular con redimensionamiento por duplicación.
  Métodos: `enqueue`, `dequeue`, `peek`, `isEmpty`, `size`, `delete`.

## Complejidad temporal

Las operaciones `find`, `erase`, `addBefore` y `addAfter` incluyen la búsqueda del valor, por lo que son O(n) en las cuatro listas.

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
|---|---|---|---|
| `MyStack<T>` | `push`, `pop`: O(1) amortizado | O(1) | O(n) |
| `MyQueue<T>` | `enqueue`, `dequeue`: O(1) amortizado | O(1) | O(n) |

## Metodología experimental

- Se midió el tiempo promedio por operación para tamaños **n = 10¹ … 10⁷**.
- Todos los tiempos se reportan en **nanosegundos**, ya que las operaciones O(1) duran decenas de ns y en milisegundos se redondearían a 0. Esta unidad permite comparar en una misma medición operaciones O(1) y O(n).
- Las mediciones de `pushBack` y `popBack` con n = 10⁶ y 10⁷ se **omitieron** en las listas donde son O(n), por el tiempo de ejecución (extrapolación: ~15 minutos con 10⁶ y más de un día con 10⁷ para `pushBack` sin tail).
- Los resultados se graficaron en escala logarítmica.

## Resultados destacados

| Comparación (n = 10⁵) | Resultado |
|---|---|
| `pushBack` sin tail vs. con tail | ≈ 9,3 ms vs. ≈ 0,47 ms |
| `popBack` lista simple con tail | ≈ 9,4 ms (sigue siendo O(n)) |
| `popBack` lista doble con tail | ≈ 0,54 ms (O(1)) |
| `pushFront`, `popFront`, `push`, `pop`, `enqueue`, `dequeue` | ≈ 3–24 ns, sin depender de n |

- Insertar y eliminar en los extremos cuesta unos pocos nanosegundos en todas las estructuras (cuando la operación es O(1)).
- `find`, `erase`, `addBefore` y `addAfter` crecen linealmente: entre 25 y 34 ms por llamada con 10⁷ elementos en las listas.
- `delete` con 10⁷ elementos: ≈ 12,2 ms en `MyStack` y ≈ 8,5 ms en `MyQueue`, unas 3 veces más rápido que `erase` en las listas.
- La pila y la cola con arreglo tienen tiempos absolutos menores que las listas en operaciones equivalentes (`push` vs. `pushFront`, `enqueue` vs. `pushBack`) por la ausencia de asignación de nodos y el acceso contiguo a memoria.

## Conclusiones

- **`DoublyLinkedListWithTail`** es la mejor lista: las cuatro operaciones en los extremos son O(1). Es la opción natural para una **deque**.
- El puntero `tail` mejora `pushBack`, pero en la lista simple **no** resuelve `popBack`, porque hay que llegar al penúltimo nodo.
- Para **pila** y **cola** de uso general, el arreglo dinámico es una excelente opción por su localidad de caché y menor sobrecarga por elemento. Una lista simple con tail también sirve para la cola.
- Si se necesita buscar o modificar por valor con frecuencia, ninguna de las implementaciones es conveniente (todas son O(n)).

### Arreglos dinámicos vs. listas enlazadas

| | Ventajas | Desventajas |
|---|---|---|
| **Arreglos dinámicos** | Memoria contigua (mejor uso de caché), menor sobrecarga por elemento, acceso por índice O(1) | Copia O(n) al redimensionar, capacidad sin usar, desplazamientos al insertar o eliminar en medio o al inicio |
| **Listas enlazadas** | Sin redimensionamiento, costo estable por operación, O(1) en los extremos (con tail o doble enlace), inserción y borrado O(1) si se tiene el nodo | Acceso por posición y búsqueda O(n), un objeto por nodo (más memoria y carga para el recolector de basura), nodos dispersos en memoria |

## Aplicaciones reales

- **Pilas (LIFO):** "Deshacer" (Ctrl + Z) en editores de texto, botón "Atrás" de los navegadores y pila de ejecución del sistema operativo.
- **Colas (FIFO):** filas de atención, gestión de impresión, reproducción de música y video, y manejo de peticiones o eventos.

## Informe

El análisis completo, con tablas de tiempos y gráficas, está en el informe en PDF incluido en el repositorio (`Stack-Queue-Java-ED-1096063373.pdf/`).

## Referencias

1. S. Rose, "Big O", 2025. [https://samwho.dev/big-o](https://samwho.dev/big-o)
