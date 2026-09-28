// SinglyLinkedListWithTail implementation in Java

import java.util.NoSuchElementException;

public class SinglyLinkedListWithTail {

     private static class Node {
        int data; // Valor almacenado en el nodo
        Node next; // Refencia al siguiente nodo en la lista
        Node(int data) { 
            this.data = data; 
        }
    }

    private Node head; // Refencia al primer nodo de la lista
    private Node tail; // Refencia al último nodo de la lista
    private int size;  // Tamaño de la lista

    // Inserta un nuevo nodo al frente de la lista
    public void pushFront(int item) {
        Node newNode = new Node(item);
        newNode.next = head;
        head = newNode;
        size++;
        if (tail == null) { 
            tail = head;
        }
    }

    // Inserta un nuevo nodo al final de la lista
    public void pushBack(int item) {
        Node newNode = new Node(item);
        newNode.next = null;
        if (tail == null) { 
            tail = newNode;
            head = newNode;
            size++; 
            return; 
        }
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    // Elimina y devuelve el valor del primer nodo de la lista
    public int popFront() {
        if (head == null) {
            return -1;
        }
        int item = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return item;
    }

    // Elimina y devuelve el valor del último nodo de la lista
    public int popBack() {
        if (head == null) {
            return -1;
        }
        if (head.next == null) {
            int item = head.data;
            tail = null;
            head = null;
            size--;
            return item;
        }
        Node actual = head;
        while (actual.next.next != null) {
            actual = actual.next;
        }
        int item = actual.next.data;
        actual.next = null;
        tail = actual;
        size--;
        return item;
    }
      
    // Busca un nodo con el valor especificado y devuelve una referencia a él
    public Node find(int item) {
        Node actual = head;
        while (actual != null) {
            if (actual.data == item)  
                return actual;
            actual = actual.next;
        }
        return null;
    }

    // Elimina el primer nodo con el valor especificado y devuelve true si se eliminó, false si no se encontró
    public boolean erase(int item) {
        if (head == null) 
            return false;
        if (head.data == item) { 
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--; 
            return true; 
        }
        Node prev = head;
        actual = head.next;
        while (actual != null) {
            if (actual.data == item) { 
                prev.next = actual.next;
                if (actual.next == null) {
                    tail = prev;
                }
                size--; 
                return true; 
            }
            prev = actual; 
            actual = actual.next;
        }
        return false;
    }

    // Inserta un nuevo nodo con el valor newItem antes del primer nodo con el valor target
    public void addBefore(int target, int newItem) {
        if (head == null) 
            return;
        if (head.data == target) { 
            pushFront(newItem); 
            return; 
        }
        Node prev = head;
        Node actual = head.next;
        while (actual != null) {
            if (actual.data == target) {
                Node newNode = new Node(newItem);
                newNode.next = actual;
                prev.next = newNode;
                size++;
                return;
            }
            prev = actual; 
            actual = actual.next;
        }
    }

    // Inserta un nuevo nodo con el valor newItem después del primer nodo con el valor target
    public void addAfter(int target, int newItem) {
        if (head == null)
            return;
        Node actual = head;
        while (actual != null) {
            if (actual.data == target) {
                Node newNode = new Node(newItem);
                newNode.next = actual.next;
                actual.next = newNode;
                if (actual == tail) {
                    tail = newNode;
                }
                size++;
                return;
            }
            actual = actual.next;
        }
    }

    // Devuelve true si la lista está vacía, false en caso contrario
    public boolean isEmpty() { 
        return size == 0; 
    }

    // Devuelve el tamaño de la lista
    public int size() { 
        return size; 
    }

    // Devuelve el valor del primer nodo de la lista
    public int front() {
        if (head == null) {
            return -1;
        }
        return head.data;
    }

    // Devuelve el valor del último nodo de la lista
    public int back() {
        if (tail == null) {
            return -1;
        }
        return tail.data;
    }
}