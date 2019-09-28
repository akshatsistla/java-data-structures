import java.util.List;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class GraphAlgorithms {

    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "One or more of the inputs are null values.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                    "Start is not found in the graph");
        }
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
        List<Vertex<T>> ret = new ArrayList<Vertex<T>>();

        vs.add(start);
        q.add(start);
        ret.add(start);
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        while (!q.isEmpty() && vs.size() != graph.getVertices().size()) {
            Vertex<T> temp = q.remove();
            List<VertexDistance<T>> check = adjList.get(temp);
            while (!check.isEmpty()) {
                VertexDistance<T> temp2 = check.remove(0);
                if (!vs.contains(temp2.getVertex())) {
                    vs.add(temp2.getVertex());
                    ret.add(temp2.getVertex());
                    q.add(temp2.getVertex());
                }
            }
        }
        return ret;
    }

    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "One or more of the inputs are null values.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                    "Start is not found in the graph");
        }

        List<Vertex<T>> ret = new ArrayList<Vertex<T>>();
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();

        dfsHelper(start, ret, vs, q, graph);
        return ret;
    }

    private static <T> void dfsHelper(Vertex<T> v, List<Vertex<T>> ret,
                                      HashSet<Vertex<T>> vs, Queue<Vertex<T>> q,
                                      Graph<T> graph) {
        vs.add(v);
        ret.add(v);
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<VertexDistance<T>> check = adjList.get(v);
        while (!check.isEmpty()) {
            VertexDistance<T> temp2 = check.remove(0);
            if (!vs.contains(temp2.getVertex())) {
                dfsHelper(temp2.getVertex(), ret, vs, q, graph);
            }
        }
    }

    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "One or more of the inputs are null values.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                    "Start is not found in the graph");
        }

        Map<Vertex<T>, Integer> ret = new HashMap<Vertex<T>, Integer>();
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
        vs.add(start);
        q.add(start);
        Set<Vertex<T>> vert = graph.getVertices();
        for (Vertex<T> v: vert) {
            ret.put(v, Integer.MAX_VALUE);
        }
        ret.put(start, 0);

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        while (!q.isEmpty() && vs.size() != graph.getVertices().size()) {
            Vertex<T> temp = q.remove();
            List<VertexDistance<T>> check = adjList.get(temp);
            Vertex<T> min = null;
            while (!check.isEmpty()) {
                VertexDistance<T> temp2 = check.remove(0);
                if (!vs.contains(temp2.getVertex())) {
                    if (min == null) {
                        min = temp2.getVertex();
                    }
                    if (ret.get(temp) + temp2.getDistance()
                            < ret.get(temp2.getVertex())) {
                        ret.replace(temp2.getVertex(),
                                ret.get(temp) + temp2.getDistance());
                    }
                    if (ret.get(temp2.getVertex()) < ret.get(min)) {
                        min = temp2.getVertex();
                    }
                }
            }
            if (min != null) {
                q.add(min);
            }
        }
        return ret;
    }

    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "One or more of the inputs are null values.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                    "Start is not found in the graph");
        }

        Set<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Set<Edge<T>> mst = new HashSet<Edge<T>>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
        for (VertexDistance v : adjList.get(start)) {
            pq.add(new Edge<T>(start, v.getVertex(), v.getDistance()));
        }
        vs.add(start);
        while (!pq.isEmpty() && vs.size() != graph.getVertices().size()) {
            Edge<T> e = pq.poll();
            if (!vs.contains(e.getV())) {
                vs.add(e.getV());
                mst.add(new Edge<T>(e.getU(), e.getV(), e.getWeight()));
                mst.add(new Edge<T>(e.getV(), e.getU(), e.getWeight()));
                for (VertexDistance<T> v : adjList.get(e.getV())) {
                    pq.add(new Edge<T>(e.getV(), v.getVertex(),
                            v.getDistance()));
                }
            }
        }
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
}
