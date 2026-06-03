import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class Graph {

    int V;
    List<List<Edge>> adj;
    List<Edge> edges;

    Graph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        edges = new ArrayList<>();

        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>());
    }

    void addEdge(int src, int dest, int weight) {

        adj.get(src).add(new Edge(src, dest, weight));
        adj.get(dest).add(new Edge(dest, src, weight));

        edges.add(new Edge(src, dest, weight));
    }

    // BFS
    void BFS(int start) {

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.print("BFS Traversal: ");

        while (!queue.isEmpty()) {

            int node = queue.poll();
            System.out.print(node + " ");

            for (Edge edge : adj.get(node)) {

                if (!visited[edge.dest]) {
                    visited[edge.dest] = true;
                    queue.add(edge.dest);
                }
            }
        }
        System.out.println();
    }

    // DFS
    void DFS(int start) {

        boolean[] visited = new boolean[V];

        System.out.print("DFS Traversal: ");
        dfsUtil(start, visited);
        System.out.println();
    }

    void dfsUtil(int node, boolean[] visited) {

        visited[node] = true;
        System.out.print(node + " ");

        for (Edge edge : adj.get(node)) {

            if (!visited[edge.dest])
                dfsUtil(edge.dest, visited);
        }
    }

    // Prim's MST
    void primMST() {

        boolean[] inMST = new boolean[V];

        PriorityQueue<Edge> pq =
                new PriorityQueue<>();

        pq.add(new Edge(-1, 0, 0));

        int totalCost = 0;

        System.out.println("\nPrim's MST:");

        while (!pq.isEmpty()) {

            Edge current = pq.poll();

            int u = current.dest;

            if (inMST[u])
                continue;

            inMST[u] = true;
            totalCost += current.weight;

            if (current.src != -1)
                System.out.println(
                        current.src + " - " +
                        current.dest +
                        " : " +
                        current.weight);

            for (Edge edge : adj.get(u)) {

                if (!inMST[edge.dest])
                    pq.add(edge);
            }
        }

        System.out.println(
                "Total Construction Cost = "
                        + totalCost);
    }

    // Kruskal MST
    void kruskalMST() {

        Collections.sort(edges);

        int[] parent = new int[V];

        for (int i = 0; i < V; i++)
            parent[i] = i;

        int totalCost = 0;

        System.out.println("\nKruskal MST:");

        for (Edge edge : edges) {

            int root1 =
                    find(parent, edge.src);

            int root2 =
                    find(parent, edge.dest);

            if (root1 != root2) {

                System.out.println(
                        edge.src + " - " +
                        edge.dest +
                        " : " +
                        edge.weight);

                totalCost += edge.weight;

                union(parent, root1, root2);
            }
        }

        System.out.println(
                "Total Construction Cost = "
                        + totalCost);
    }

    int find(int[] parent, int x) {

        if (parent[x] == x)
            return x;

        return parent[x] =
                find(parent, parent[x]);
    }

    void union(
            int[] parent,
            int x,
            int y) {

        parent[x] = y;
    }

    // Redundancy Check:
    // Count different paths between source and destination

    int countPaths(
            int source,
            int destination) {

        boolean[] visited =
                new boolean[V];

        return countDFS(
                source,
                destination,
                visited);
    }

    int countDFS(
            int current,
            int destination,
            boolean[] visited) {

        if (current == destination)
            return 1;

        visited[current] = true;

        int count = 0;

        for (Edge edge :
                adj.get(current)) {

            if (!visited[edge.dest]) {

                count += countDFS(
                        edge.dest,
                        destination,
                        visited);
            }
        }

        visited[current] = false;

        return count;
    }
}

public class BangaloreMetroMST {

    public static void main(String[] args) {

        /*
        Station Mapping

        0 = Majestic
        1 = MG Road
        2 = Indiranagar
        3 = Whitefield
        4 = KR Puram
        5 = Silk Board
        */

        Graph metro = new Graph(6);

        metro.addEdge(0, 1, 5);
        metro.addEdge(1, 2, 4);
        metro.addEdge(2, 3, 7);
        metro.addEdge(0, 4, 8);
        metro.addEdge(4, 3, 6);
        metro.addEdge(1, 5, 3);
        metro.addEdge(5, 3, 5);
        metro.addEdge(2, 4, 2);

        System.out.println(
                "Bangalore Metro Phase-3");

        metro.BFS(0);
        metro.DFS(0);

        metro.primMST();

        metro.kruskalMST();

        int paths =
                metro.countPaths(0, 3);

        System.out.println(
                "\nPaths between Majestic and Whitefield = "
                        + paths);

        if (paths >= 2)
            System.out.println(
                    "Redundancy Requirement Satisfied");
        else
            System.out.println(
                    "Redundancy Requirement NOT Satisfied");
    }
}
