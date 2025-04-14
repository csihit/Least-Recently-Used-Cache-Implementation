import java.util.HashMap;

class Node {
    int key;
    int value;
    Node next;
    Node prev;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class LRUCache {
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> map = null;
    private int cap = 0;

    public LRUCache(int capacity) {
        this.cap = capacity;
        this.map = new HashMap<>();
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            removeNode(node);
            putOnTop(node);
            return node.value;
        }
        return -1; // Key not found
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; // Update value
            removeNode(node);
            putOnTop(node);
        } else {
            if (map.size() >= cap) {
                map.remove(tail.key); // Remove LRU from map
                removeNode(tail); // Remove LRU from list
            }
            Node node = new Node(key, value);
            map.put(key, node);
            putOnTop(node);
        }
    }

    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode; // Update head if node is at the front
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode; // Update tail if node is at the end
        }
    }

    private void putOnTop(Node node) {
        node.next = head;
        node.prev = null;

        if (head != null) {
            head.prev = node;
        }
        head = node;

        if (tail == null) {
            tail = node; // Update tail if the list was empty
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        cache.put(1, 3);
        cache.put(4, 2);
        System.out.println(cache.get(1)); // Output: 3
        cache.put(5, 6);
        System.out.println(cache.get(7)); // Output: -1
        System.out.println(cache.get(5)); // Output: 6
        cache.put(7, 4);
        System.out.println(cache.get(4)); // Output: -1 (Evicted due to capacity)
        System.out.println(cache.get(1)); // Output: 3
        System.out.println(cache.get(5)); // Output: 6
    }
}