    package graph.problem;

    import java.util.*;

// Clase genérica para representar un grafo dirigido con pesos
class Graph<T> {
    private final Map<T, List<Edge<T>>> adjList = new HashMap<>();

    public void addNode(T node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(T from, T to, int weight) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(new Edge<>(to, weight));
    }

    public List<Edge<T>> getEdges(T node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    public Set<T> getNodes() {
        return adjList.keySet();
    }

    // Remueve todas las aristas que estén en un conjunto específico
    public void removeEdges(Set<Pair<T, T>> toRemove) {
        for (T node : adjList.keySet()) {
            adjList.get(node).removeIf(edge -> toRemove.contains(new Pair<>(node, edge.to)));
        }
    }
}

// Clase genérica para representar una arista
class Edge<T> {
    T to;
    int weight;

    public Edge(T to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

// Clase auxiliar para representar pares de valores
class Pair<U, V> {
    U first;
    V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

// Implementación de Dijkstra genérico
class Dijkstra<T> {
    public Map<T, Integer> shortestPath(Graph<T> graph, T source, Map<T, List<T>> parents) {
        Map<T, Integer> dist = new HashMap<>();
        PriorityQueue<Pair<Integer, T>> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.first));

        for (T node : graph.getNodes()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(source, 0);
        pq.add(new Pair<>(0, source));

        while (!pq.isEmpty()) {
            Pair<Integer, T> current = pq.poll();
            int d = current.first;
            T u = current.second;

            if (d > dist.get(u)) continue;

            for (Edge<T> edge : graph.getEdges(u)) {
                T v = edge.to;
                int newDist = dist.get(u) + edge.weight;

                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    pq.add(new Pair<>(newDist, v));
                    parents.put(v, new ArrayList<>(List.of(u)));
                } else if (newDist == dist.get(v)) {
                    parents.get(v).add(u);
                }
            }
        }
        return dist;
    }
}

public class AlmostShortestPath {
    // Marca todas las aristas que están en algún camino más corto
    private static <T> void markShortestEdges(T dest, Map<T, List<T>> parents, Set<Pair<T, T>> toRemove) {
        Queue<T> q = new LinkedList<>();
        q.add(dest);

        while (!q.isEmpty()) {
            T node = q.poll();
            if (parents.containsKey(node)) {
                for (T p : parents.get(node)) {
                    toRemove.add(new Pair<>(p, node));
                    q.add(p);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            if (N == 0 && M == 0) break;

            int S = sc.nextInt();
            int D = sc.nextInt();

            Graph<Integer> graph = new Graph<>();

            for (int i = 0; i < N; i++) graph.addNode(i);

            for (int i = 0; i < M; i++) {
                int U = sc.nextInt();
                int V = sc.nextInt();
                int P = sc.nextInt();
                graph.addEdge(U, V, P);
            }

            // Primer Dijkstra
            Map<Integer, List<Integer>> parents = new HashMap<>();
            Dijkstra<Integer> dijkstra = new Dijkstra<>();
            Map<Integer, Integer> dist = dijkstra.shortestPath(graph, S, parents);

            if (dist.get(D) == Integer.MAX_VALUE) {
                System.out.println(-1);
                continue;
            }

            // Marcar las aristas de los caminos más cortos
            Set<Pair<Integer, Integer>> toRemove = new HashSet<>();
            markShortestEdges(D, parents, toRemove);

            // Eliminar esas aristas
            graph.removeEdges(toRemove);

            // Segundo Dijkstra
            parents.clear();
            dist = dijkstra.shortestPath(graph, S, parents);

            if (dist.get(D) == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(dist.get(D));
            }
        }
    }
}

