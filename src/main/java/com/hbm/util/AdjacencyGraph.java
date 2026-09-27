package com.hbm.util;

import java.util.*;

public class AdjacencyGraph<T> {
    private final Map<T, Set<T>> adjacencyMatrix;

    public AdjacencyGraph() {
        this.adjacencyMatrix = new HashMap<>();
    }

    public void add(T object, Set<T> adjNodes) {
        if (!contains(object)) {
            adjacencyMatrix.put(object, new HashSet<>(adjNodes));
            for (T next : adjNodes) {
                Set<T> set = adjacencyMatrix.get(next);
                if (set != null) {
                    set.add(object);
                }
            }
        }
    }

    public boolean contains(T node) {
        return adjacencyMatrix.containsKey(node);
    }

    public boolean isNeighbor(T a, T b) {
        Set<T> set = adjacencyMatrix.get(a);
        return set != null && set.contains(b);
    }

    public Set<T> getKeys() {
        return adjacencyMatrix.keySet();
    }

    public Set<T> getAllNodesConnectedToNode(T node) {
        Set<T> removableNodes = new HashSet<>();
        getAllNodesConnectedToBlock(node, removableNodes);
        return removableNodes;
    }

    private void getAllNodesConnectedToBlock(T node, Set<T> removableNodes) {
        Deque<T> stack = new ArrayDeque<>();
        stack.push(node);
        removableNodes.add(node);

        while (!stack.isEmpty()) {
            T stackElement = stack.pop();
            Set<T> neighbors = adjacencyMatrix.get(stackElement);
            if (neighbors != null) {
                for (T nextElement : neighbors) {
                    if (!removableNodes.contains(nextElement)) {
                        stack.push(nextElement);
                        removableNodes.add(nextElement);
                    }
                }
            }
        }
    }

    private boolean findPathToBlock(T from, T to, Set<T> visitedNodes) {
        Deque<T> stack = new ArrayDeque<>();
        stack.push(from);

        while (!stack.isEmpty()) {
            T stackElement = stack.pop();
            visitedNodes.add(stackElement);
            Set<T> neighbors = adjacencyMatrix.get(stackElement);
            if (neighbors != null) {
                for (T nextElement : neighbors) {
                    if (Objects.equals(to, nextElement)) return true;
                    if (!visitedNodes.contains(nextElement)) {
                        stack.push(nextElement);
                    }
                }
            }
        }
        return false;
    }

    public boolean doesPathExist(T from, T to) {
        return findPathToBlock(from, to, new HashSet<>());
    }

    public Collection<T> removeAllNodesConnectedTo(T node) {
        Set<T> removableNodes = getAllNodesConnectedToNode(node);
        for (T n : removableNodes) {
            adjacencyMatrix.remove(n);
        }
        return removableNodes;
    }

    public void remove(T node) {
        Set<T> set = adjacencyMatrix.get(node);
        if (set != null) {
            for (T next : set) {
                Set<T> neighborSet = adjacencyMatrix.get(next);
                if (neighborSet != null) {
                    neighborSet.remove(node);
                }
            }
        }
        adjacencyMatrix.remove(node);
    }

    public void clear() {
        adjacencyMatrix.clear();
    }

    public int size() {
        return adjacencyMatrix.size();
    }
}
