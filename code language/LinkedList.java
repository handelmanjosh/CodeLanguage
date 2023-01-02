public class LinkedList {
    private Node head;
    private Node tail;
    public int size;
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    public class Node {
        Node next;
        Node previous;
        int[] value;
        public Node() {
            this.value  = new int[4];
            this.next = null;
            this.previous = null;
        }
        public Node deepCopy() {
            Node n = new Node();
            n.value = this.value;
            n.next = this.next;
            n.previous = this.previous;
            return n;
        }
        public String toString() {
            StringBuilder local = new StringBuilder();
            local.append('[');
            for (int i : this.value) {
                local.append(i).append(", ");
            }
            local.replace(local.length() - 2, local.length(), "]");
            return local.toString();
        }
        public String toASCIIString() {
            StringBuilder local = new StringBuilder();
            for (int i : value) {
                char c = (char) i;
                local.append(c);
            }
            return local.toString();
        }
        public void addFirst() {
            this.value[0]++;
        }
        public void addSecond() {
            this.value[1]++;
        }
        public void addThird() {
            this.value[2]++;
        }
        public void addFourth() {
            this.value[3]++;
        }
        public void subtractFirst() {
            this.value[0]--;
        }
        public void substractSecond() {
            this.value[1]--;
        }
        public void subtractThird() {
            this.value[2]--;
        }
        public void subtractFourth() {
            this.value[3]--;
        }
        public void clear() {
            this.value = new int[4];
        }
    }
    public void add() {
        Node n = new Node();
        if (this.size == 0) {
            this.head = n;
            this.tail = n;
        } else {
            this.tail.next = n;
            n.previous = this.tail;
            this.tail = n;
        }
        this.size++;
    }
    public Node getHead() {
        return this.head;
    }
    public void addAfter(Node position) {
        //Node n is a node in this LinkedList
        Node n = new Node();
        n.next = position.next;
        n.previous = position;
        if (position.next != null) {
            position.next.previous = n;
        }
        position.next = n;
    }
    public void addBefore(Node position) {
        //Node n is a node in this linked list
        Node n = new Node();
        n.next = position;
        n.previous = position.previous;
        if (position.previous != null) {
            position.previous.next = n;
        }
        position.previous = n;
    }
    public void clear(Node n) {
        n.value = new int[4];
    }
    private Node findNodeWithIndex(int i) {
        if (i >= this.size) throw new IndexOutOfBoundsException();
        int count = 0;
        Node current = this.head;
        while (count < i) {
            current = current.next;
            count++;
        }
        return current;
    }
    public void remove(Node n) {
        if (size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            Node removeable = n;
            if (removeable == this.head) {
                removeable.next.previous = null;
                removeable.next = this.head;
            } else if (removeable == this.tail) {
                removeable.previous.next = null;
                this.tail = removeable.previous;
            } else {
                removeable.next.previous = removeable.previous;
                removeable.previous.next = removeable.next;
            }
        }
        this.size--;
    }
    public void remove(int index) {
        Node removeable = findNodeWithIndex(index);
        remove(removeable);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node current = this.head;
        while (current != null) {
            sb.append(current.toString());
            sb.append(", ");
            current = current.next;
        }
        
        sb.replace(sb.length() - 2, sb.length(), "}");
        return sb.toString();
    }
    public String toASCIIString() {
        StringBuilder sb = new StringBuilder();
        Node current = this.head;
        while (current != null) {
            sb.append(current.toASCIIString());
            current = current.next;
        }
        return sb.toString();
    }
}
