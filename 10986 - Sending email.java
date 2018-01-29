import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in.nextInt(), in, out);
        out.close();
    }

    static class Task {
        PrintWriter out;

        void solve(int testNumber, Scanner in, PrintWriter out) {
            this.out = out;
            int TC = testNumber;
            while (TC > 0) {

                int n = in.nextInt();
                int m = in.nextInt();
                int source = in.nextInt();
                int destination = in.nextInt();

                Graph graph = new Graph(n);

                for (int i = 0; i < m; i++) {
                    Integer u = in.nextInt();
                    Integer v = in.nextInt();
                    Integer cost = in.nextInt();

                    graph.addEdge(u, v, cost);
                    graph.addEdge(v, u, cost);
                }
                graph.startDisjkstra(source);
                graph.printCost(destination, testNumber - TC + 1);

                --TC;
            }

        }

        public class Graph {
            List<Edge>[] edges;
            Long[] distance;
            Integer[] path;

            Graph(Integer size) {
                edges = new ArrayList[size + 1];
                distance = new Long[size + 1];
                path = new Integer[size + 1];

                for (int i = 0; i <= size; i++) {
                    edges[i] = new ArrayList<>();
                }

                Arrays.fill(distance, Long.MAX_VALUE);
                Arrays.fill(path, null);
            }

            private void dijkstra(Integer startNode) {
                PriorityQueue<Integer> nodesQueue = new PriorityQueue<>(java.util.Comparator.comparingLong(o -> distance[o]));
                nodesQueue.add(startNode);

                Integer from;
                distance[startNode] = 0L;

                while (!nodesQueue.isEmpty()) {
                    from = nodesQueue.poll();

                    for (Edge edge : edges[from]) {

                        if (distance[from] + edge.weight < distance[edge.destination]) {
                            distance[edge.destination] = (long) distance[from] + edge.weight;
                            nodesQueue.add(edge.destination);
                            path[edge.destination] = from;
                        }
                    }
                }

            }

            public void startDisjkstra(Integer start) {
                dijkstra(start);
            }

            public void addEdge(Integer from, Integer to, Integer cost) {
                edges[from].add(new Edge(to, cost));
            }

            public void printCost(int destination, int testCaseNumber) {

                out.println(distance[destination] == Long.MAX_VALUE ? "Case #" + testCaseNumber + ": unreachable" :
                        "Case #" + testCaseNumber + ": " + distance[destination]);
            }

            class Edge {
                Integer destination;
                Integer weight;

                Edge(Integer destination, Integer weight) {
                    this.weight = weight;
                    this.destination = destination;
                }
            }
        }
    }

    static class Scanner {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public Scanner(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}
