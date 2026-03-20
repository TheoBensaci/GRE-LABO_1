package ch.heig.gre.groupI;

import ch.heig.gre.graph.Graph;
import ch.heig.gre.maze.MazeBuilder;
import ch.heig.gre.maze.MazeGenerator;
import ch.heig.gre.maze.Progression;
import ch.heig.gre.util.ArrayUtil;

import java.util.ArrayDeque;
import java.util.Deque;

public final class DfsGenerator implements MazeGenerator {

  @Override
  public void generate(MazeBuilder builder, int from) {
    
    // génération du labyrinthe 
    Graph g = builder.topology();
    int n = g.nbVertices();
    boolean[] visited = new boolean[n];

    // Stack pour la gestion des voisins
    Deque<int[]> dfsDeque = new ArrayDeque<>();

    visited[from] = true;
    builder.progressions().setLabel(from, Progression.PROCESSING);
    dfsDeque.push(new int[]{from});

    // Tant qu'il y a des noeuds à traiter dans la pile, on continue
    while(!dfsDeque.isEmpty()) {
      int cur = dfsDeque.peek()[0];

      // mélange tous les voisins du noeud en traitement
      int[] neighbors = g.neighbors(cur);
      ArrayUtil.shuffle(neighbors);


      // récupère le prochain voisin non visité
      int next = 0;
      for (int i = 0; i < neighbors.length; i++) {
        if (!visited[neighbors[i]]) {
            next = neighbors[i];
            break;
        }
      }

      // ajout du mur pour le noeud en traitement et empiler le noeud dans la pile
      if (next != 0) {
        builder.removeWall(cur, next);
        visited[next] = true;
        builder.progressions().setLabel(next, Progression.PROCESSING);
        dfsDeque.push(new int[]{next});

      } else {

        // tous les voisins ont été traité, on passe au suivant
        dfsDeque.pop();
        builder.progressions().setLabel(cur, Progression.PROCESSED);
      }
    }
  }
}