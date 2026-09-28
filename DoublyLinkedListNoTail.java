// DoublyLinkedListNoTail implementation in Java

public class DoublyLinkedListNoTail {

    private static class Node {
        int data; // Valor almacenado en el nodo
        Node next; // Refencia al siguiente nodo en la lista
        Node prev; // Referencia al nodo anterior en la lista
        Node(int data) { 
            this.data = data; 
        }
    }

    private Node head; // Refencia al primer nodo de la lista
    private int size;  // Tamaño de la lista

    // Inserta un nuevo nodo al frente de la lista
    public void pushFront(int item) {
        Node newNode = new Node(item);
        newNode.next = head;
        newNode.prev = null;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    // Inserta un nuevo nodo al final de la lista
    public void pushBack(int item) {
        Node newNode = new Node(item);
        newNode.prev = null;    
        newNode.next = null;
        if (head == null) { 
            head = newNode; 
            size++; 
            return; 
        }
        Node actual = head;
        while (actual.next != null) {
            actual = actual.next;
        }
            actual.next = newNode;
            newNode.prev = actual;
            size++;
    }

    // Elimina y devuelve el valor del primer nodo de la lista
    public int popFront() {
        if (head == null) {
            return -1;
        }
        int item = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
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
            head = null; 
            size--; 
            return item;
        }
        Node actual = head;
        while (actual.next != null) {
            actual = actual.next;
        }
        int item = actual.data;
        actual.prev.next = null;
        size--;

        return item;
    }

    // Devuelve el nodo que contiene el valor especificado, o null si no se encuentra
    public Node find(int item) {
        Node actual = head;
        while (actual != null) {
            if (actual.data == item) {  
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }

    // Elimina el primer nodo que contiene el valor especificado y devuelve true si se eliminó, o false si no se encontró
    public boolean erase(int item) {
        if (head == null) 
            return false;
        if (head.data == item) { 
            head = head.next; 
            if (head != null) {
                head.prev = null;
            }
            size--; 
            return true; 
        }
        Node prev = head, actual = head.next;
        while (actual != null) {
            if (actual.data == item) { 
                prev.next = actual.next;
                if (actual.next != null) {
                    actual.next.prev = prev;
                }    
                size--; 
                return true; 
            }
            prev = actual; 
            actual = actual.next;
        }
        return false;
    }

    // Inserta un nuevo nodo con el valor newItem antes del primer nodo que contiene el valor target
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
                newNode.prev = prev;
                prev.next = newNode;
                actual.prev = newNode;
                size++;
                return;
            }
            prev = actual; 
            actual = actual.next;
        }
    }

    // Agrega un nuevo nodo con el valor newItem después del primer nodo que contiene el valor target
    public void addAfter(int target, int newItem) {
        Node actual = head;
        while (actual != null) {
            if (actual.data == target) {
                Node newNode = new Node(newItem);
                newNode.next = actual.next;
                newNode.prev = actual;
                actual.next = newNode;
                if (newNode.next != null) {
                    newNode.next.prev = newNode;
                }
                size++;
                return;
            }
            actual = actual.next;
        }
    }

    // Devuelve true si la lista está vacía, o false en caso contrario
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
        if (head == null) {
            return -1;
        }
        Node actual = head;
        while (actual.next != null) 
            actual = actual.next;
        return actual.data;
    }
}