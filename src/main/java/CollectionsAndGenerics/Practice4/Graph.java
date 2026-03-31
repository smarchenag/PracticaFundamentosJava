package CollectionsAndGenerics.Practice4;

import java.util.*;

public class Graph<T> {
    private final Map<T, List<T>> conections;

    public Graph() {
        this.conections = new HashMap<>();
    }

    public void addNode(T node) {
        conections.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(T start, T end) {
        if (!conections.containsKey(start)) {
            this.addNode(start);
        }
        if (!conections.containsKey(end)) {
            this.addNode(end);
        }
        if (!conections.get(start).contains(end)) conections.get(start).add(end);
        if (!conections.get(end).contains(start)) conections.get(end).add(start);
    }

    public List<T> getNeighbors(T node) {
        if (!conections.containsKey(node)) {
            throw new IllegalArgumentException("El nodo no existe : " + node);
        }
        return conections.get(node);
    }

    public boolean hasNode (T node) {
        return conections.containsKey(node);
    }

    public boolean hasEdge (T start, T end) {
        if (!conections.containsKey(start)) {
            throw new IllegalArgumentException("El nodo no existe : " + start);
        }
        return conections.get(start).contains(end);
    }

    public List<T> bfs(T start) {
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            T node = queue.poll();
            result.add(node);
            if (conections.containsKey(node)) {
                for (T neighbor : conections.get(node)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return result;
    }

    public List<T> dfs(T start) {
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Deque<T> deque = new ArrayDeque<>();
        deque.push(start);
        visited.add(start);
        while (!deque.isEmpty()) {
            T node = deque.pop();
            result.add(node);
            if (conections.containsKey(node)) {
                for (T neighbor : conections.get(node)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        deque.push(neighbor);
                    }
                }
            }
        }
        return result;
    }

    public boolean hasPath(T start, T end) {
        if (!conections.containsKey(start)) {
            return false;
        }
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (current.equals(end)) {
                return true;
            }
            for (T next : conections.get(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        return false;
    }
}
