package ch.heig.gre.groupX;

import ch.heig.gre.graph.Graph;
import ch.heig.gre.graph.VertexLabelling;
import ch.heig.gre.maze.MazeBuilder;
import ch.heig.gre.maze.MazeGenerator;
import ch.heig.gre.maze.Progression;

import java.util.*;

// TODO : classe à compléter et documenter
public final class DfsGenerator implements MazeGenerator {
  @Override
  public void generate(MazeBuilder builder, int from) {
    Graph g = builder.topology();
    List<Boolean> visited = new ArrayList<>(Collections.nCopies(g.nbVertices(), false));
    Deque<Integer> buffer = new ArrayDeque<>(g.nbVertices());

    // add start point
    buffer.push(from);

    while (!buffer.isEmpty()){

      int i = buffer.peek();

      if(visited.get(i)){
        // mark the vertex as finish
        builder.progressions().setLabel(i,Progression.PROCESSED);
        buffer.pop();
        continue;
      }



      builder.progressions().setLabel(i,Progression.PROCESSING);



      // get neighbors
      int[] neighbors= g.neighbors(i);

      // peek random neighbor
      int index_neighbor=(int) (Math.random() * neighbors.length);
      int j = 0;
      for (; visited.get(neighbors[index_neighbor]) && j < neighbors.length; j++) {
        index_neighbor=(index_neighbor+1)%neighbors.length;
      }

      // mark i as visited
      visited.set(i,true);

      if(j==neighbors.length){
        // no avaliable neighbor
        builder.progressions().setLabel(i,Progression.PROCESSED);
        buffer.pop();
        if(!buffer.isEmpty())visited.set(buffer.peek(),false);
        continue;
      }

      // destroy the wall
      builder.removeWall(i,neighbors[index_neighbor]);

      // add target to the stack
      buffer.push(neighbors[index_neighbor]);

      builder.progressions().setLabel(neighbors[index_neighbor],Progression.PROCESSING);
    }
    // Mise à jour de l'interface graphique :
    // builder.progressions().setLabel(..., ...);
  }
}
