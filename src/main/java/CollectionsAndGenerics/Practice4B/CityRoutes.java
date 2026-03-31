package CollectionsAndGenerics.Practice4B;

import java.util.*;

public class CityRoutes {
    private Map<String, Set<String>> routes;

    public CityRoutes() {
        this.routes = new HashMap<>();
    }

    public void addCity (String city) {
        routes.putIfAbsent(city, new HashSet<>());
    }

    public void addRoute(String from, String to) {
        if (!routes.containsKey(from)) {
            routes.put(from, new HashSet<>());
        }
        routes.get(from).add(to);
        if (!routes.containsKey(to)) {
            routes.put(to, new HashSet<>());
        }
        routes.get(to).add(from);
    }

    public Set<String> getDirectRoutes(String city) {
        return routes.get(city);
    }

    public boolean canTravel(String from, String to) {
        if (!routes.containsKey(from)) {
            return false;
        }
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(to)) {
                return true;
            }
            for (String next : routes.get(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        return false;
    }

    public List<String> findRoute (String from, String to) {

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> cameFrom = new HashMap<>();
        List<String> result = new ArrayList<>();

        queue.add(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(to)) {
                String step = to;
                while (step != null) {
                    result.add(step);
                    step = cameFrom.get(step);
                }
            }
            for (String next : routes.get(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                    cameFrom.put(next, current);
                }
            }
        }
        Collections.reverse(result);
        return result;
    }
}
