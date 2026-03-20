package ch.heig.gre.groupI;
 
import ch.heig.gre.Keys;
import ch.heig.gre.graph.Graph;
import ch.heig.gre.graph.GridGraph2D;
import ch.heig.gre.graph.PositiveWeightFunction;
import ch.heig.gre.graph.VertexLabelling;
import ch.heig.gre.maze.MazeSolver;
import ch.heig.gre.maze.Metadata;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// TODO : compléter cette classe et documenter. Voir documentation de l'interface MazeSolver et des classes associées
//  pour la description des paramètres.
public final class BfsSolver implements MazeSolver {
 
  @Override
  public Result solve(GridGraph2D grid, PositiveWeightFunction weights, int source, int destination, VertexLabelling<Integer> distances) {
    // Ne pas modifier
    return solve((Graph) grid, source, destination, distances);
  }
 
  public Result solve(Graph graph, int source, int destination, VertexLabelling<Integer> distances) {
    
    // n = nombre d'arc, d[u] = distance de la source à u, p[u] = parent de u dans l'arbre, N[u] = nombre de plus courts chemins jusqu'à u
    int n = graph.nbVertices();
    int[] d = new int[n];
    int[] p = new int[n];
    long[] N = new long[n];

    // Initialisation de base pour u != noeud source
    for (int u = 0; u < n; ++u) {
      d[u] = -1;
      p[u] = -1;
      N[u] = 0;
    }

    // Initialisation de la source
    d[source] = 0;
    N[source] = 1;
    distances.setLabel(source, 0);
    
    Deque<Integer> bfsDeque = new ArrayDeque<>(n);
    bfsDeque.add(source);
 
    while (!bfsDeque.isEmpty()) {
      int u = bfsDeque.poll();
 
      // Si le noeud final est atteint, on stoppe.
      if (u == destination) {
        break;
      }
 
      // Tant que u n'est pas vide, retirer le somment u de Q
      for (int neighbor : graph.neighbors(u)) {
        
        if (d[neighbor] == -1) {
          d[neighbor] = d[u] + 1;
          p[neighbor] = u;
          N[neighbor] = N[u];
          distances.setLabel(neighbor, d[u] + 1);
          bfsDeque.add(neighbor);

        } else if (d[neighbor] == (d[u] + 1)) {
      
          N[neighbor] += N[u];
        }
      }
    }
 
    // Reconstruction du chemin destination -> source via p, puis inversion
    int path_length = d[destination] + 1;
    List<Integer> path = new ArrayList<>(Collections.nCopies(path_length, -1));
    int v = destination;
    for (int j = path_length - 1; j >= 0; j--) {
      path.set(j, v);
      v = p[v];
    }
 
    // Métadonnées + résultats
    Metadata metadata = new Metadata();
    metadata.put(Keys.LENGTH, d[destination]);
    metadata.put(Keys.NB_OPTIMAL_PATHS, (long) N[destination]);
 
    return new Result(path, metadata);
  }
}