/**
 *   Autheur: Theo Bensaci, Thomas Nguyen
 *   Date: 11:40 05.03.2026
 *   Description: maze solver
 */

package ch.heig.gre.groupI;

import ch.heig.gre.Keys;
import ch.heig.gre.graph.Graph;
import ch.heig.gre.graph.GridGraph2D;
import ch.heig.gre.graph.PositiveWeightFunction;
import ch.heig.gre.graph.VertexLabelling;
import ch.heig.gre.maze.MazeSolver;
import ch.heig.gre.maze.Metadata;
import java.util.*;

// TODO : compléter cette classe et documenter. Voir documentation de l'interface MazeSolver et des classes associées
//  pour la description des paramètres.
public final class BfsSolver implements MazeSolver {
  @Override
  public Result solve(GridGraph2D grid, PositiveWeightFunction weights, int source, int destination, VertexLabelling<Integer> distances) {
    // Ne pas modifier
    return solve((Graph) grid, source, destination, distances);
  }

  public Result solve(Graph graph, int source, int destination, VertexLabelling<Integer> distances) {

    List<Integer> parent = new ArrayList<>(graph.nbVertices());              // record parents of vertex
    List<Integer> vertex_distance = new ArrayList<>(graph.nbVertices());     // record distances of vertex from source
    List<Integer> n_alt_path = new ArrayList<>(graph.nbVertices());           // record the number of alt path possible to reach this vertex
    Queue<Integer> buffer = new ArrayDeque<>(graph.nbVertices());                                        // buffer use as a fifo

    // init datas
    for (int i = 0; i < graph.nbVertices(); i++) {
      parent.add(-1);
      vertex_distance.add(-1);
      n_alt_path.add(0);
    }

    // add the source
    buffer.add(source);
    vertex_distance.set(source,0);
    distances.setLabel(source, 0);

    while (!buffer.isEmpty()){

      // get a vertex
      int i = buffer.poll();

      // pre-compute distance of hes children
      int next_distance = vertex_distance.get(i)+1;

      // if vertex = destination => found
      if(i==destination){
        // found
        break;
      }

      // get neighbors
      int[] neighbors= graph.neighbors(i);

      for (int neighbor : neighbors) {

        // if vertex distance is greater than 0 => vertex all ready visited
        if (vertex_distance.get(neighbor)<0) {
          // add vertex to the buffer
          buffer.add(neighbor);

          // set parent as the actual vertex
          parent.set(neighbor, i);

          // set the vertex distance
          vertex_distance.set(neighbor,next_distance);

          // update UI
          distances.setLabel(neighbor, next_distance);
        }
        else if(vertex_distance.get(neighbor)==next_distance){
          // if the vertex as been reach before and the distance is the same
          // there for, a alternate path is possible
          n_alt_path.set(neighbor,n_alt_path.get(neighbor)+1);
        }
      }
    }

    // recover the path
    int path_length = vertex_distance.get(destination)+1;
    List<Integer> path = new ArrayList<>(Collections.nCopies(path_length, -1));

    long alt_path=0;          // number of alternative path
    int v = destination;
    for (int j = path_length-1; j >= 0; j--) {
      path.set(j,v);
      // we add the number of alt path of every node use in the path to know how many alt was possible
      alt_path+=n_alt_path.get(v);
      v = parent.get(v);
    }


    // Création des métadonnées à retourner avec le résultat
    Metadata metadata = new Metadata();
    metadata.put(Keys.LENGTH, path.size());
    metadata.put(Keys.NB_OPTIMAL_PATHS, alt_path);

    return new Result(path, metadata);
  }
}
