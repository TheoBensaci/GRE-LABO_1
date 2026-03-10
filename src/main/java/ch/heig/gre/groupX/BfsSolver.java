package ch.heig.gre.groupX;

import ch.heig.gre.Keys;
import ch.heig.gre.graph.Graph;
import ch.heig.gre.graph.GridGraph2D;
import ch.heig.gre.graph.PositiveWeightFunction;
import ch.heig.gre.graph.VertexLabelling;
import ch.heig.gre.maze.MazeSolver;
import ch.heig.gre.maze.Metadata;
import ch.heig.gre.maze.Progression;

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

    List<Integer> parent = new ArrayList<>(Collections.nCopies(graph.nbVertices(), -1));              // record parents of vertex
    List<Integer> vertex_distance = new ArrayList<>(Collections.nCopies(graph.nbVertices(), -1));     // record distances of vertex from source
    List<Integer> n_alt_path = new ArrayList<>(Collections.nCopies(graph.nbVertices(), 0));           // record the number of alt path possible to reach this vertex
    Queue<Integer> buffer = new ArrayDeque<>(graph.nbVertices());                                        // buffer use as a fifo


    buffer.add(source);
    vertex_distance.set(source,0);
    distances.setLabel(source, 0);

    while (!buffer.isEmpty()){
      int i = buffer.poll();
      int next_distance = vertex_distance.get(i)+1;


      if(i==destination){
        // found
        break;
      }

      int[] neighbors= graph.neighbors(i);
      for (int neighbor : neighbors) {
          if (vertex_distance.get(neighbor)<0) {
            // add vertex to visited
            buffer.add(neighbor);

            // set parent as the actual vertex
            parent.set(neighbor, i);

            vertex_distance.set(neighbor,next_distance);

            distances.setLabel(neighbor, next_distance);
          }
          else if(vertex_distance.get(neighbor)==next_distance){
            // if the vertex as been reach before and the distance is the same
            // there for, a alternate path is possible
            n_alt_path.set(neighbor,n_alt_path.get(neighbor)+1);
          }
        }
    }

    // get path
    int path_length = vertex_distance.get(destination)+1;
    List<Integer> path = new ArrayList<>(Collections.nCopies(path_length, -1));
    long alt_path=0;
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
