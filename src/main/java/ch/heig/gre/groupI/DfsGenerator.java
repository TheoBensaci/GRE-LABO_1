/**
 *   Autheur: Theo Bensaci, Thomas Nguyen
 *   Date: 11:40 05.03.2026
 *   Description: maze generator
 */

package ch.heig.gre.groupI;

import ch.heig.gre.graph.Graph;
import ch.heig.gre.maze.MazeBuilder;
import ch.heig.gre.maze.MazeGenerator;
import ch.heig.gre.maze.Progression;
import java.util.*;

// TODO : classe à compléter et documenter
public final class DfsGenerator implements MazeGenerator {
  @Override
  public void generate(MazeBuilder builder, int from) {
    Graph g = builder.topology();
    List<Boolean> visited = new ArrayList<>(g.nbVertices());    // array of bool use to track if a vertex as been all ready visited
    Deque<Integer> buffer = new ArrayDeque<>(g.nbVertices());                                  // buffer use to know witch vertex is to be treated next

    // init data
    for (int i = 0; i < g.nbVertices(); i++) {
      visited.add(false);
    }

    // add start point
    buffer.push(from);

    while (!buffer.isEmpty()){
      // get the buffer head
      int i = buffer.peek();

      // mark i as visited
      visited.set(i,true);

      // update UI
      builder.progressions().setLabel(i,Progression.PROCESSING);

      // get neighbors
      int[] neighbors= g.neighbors(i);

      // peek random neighbor
      int index_neighbor=(int) (Math.random() * neighbors.length);
      int j = 0;
      for (; visited.get(neighbors[index_neighbor]) && j < neighbors.length; j++) {
        index_neighbor=(index_neighbor+1)%neighbors.length;
      }

      //if no available neighbor => vertex is done and we need to back track
      if(j==neighbors.length){
        builder.progressions().setLabel(i,Progression.PROCESSED);
        buffer.pop();
        continue;
      }

      // destroy the wall
      builder.removeWall(i,neighbors[index_neighbor]);

      // add target to the stack
      buffer.push(neighbors[index_neighbor]);

      // update UI
      builder.progressions().setLabel(neighbors[index_neighbor],Progression.PROCESSING);
    }
  }
}
