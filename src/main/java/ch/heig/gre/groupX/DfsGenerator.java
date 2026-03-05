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
    Stack<Integer> buffer = new Stack<>();

    // add start point
    buffer.push(0);

    while (!buffer.empty()){

      int i = buffer.peek();

      if(visited.get(i)){
        // mark the vertex as finish
        buffer.pop();
      }

      // get neighbors
      //List<Integer> neighbors = g.neighbors(i); //Arrays.asList(g.neighbors(i).);

      // peek random neighbor
      int j;
      do {
        j = (int) (Math.random() * neighbors.length);
      }
      while();


      // destroy the wall


      // mark i as visited




    }

    for (int i = 0; i < g.nbVertices(); i++) {
      // get neighbors
      int[] neighbors = g.neighbors(i);

      // peek random neighbor


      // destroy the wall


      // mark i as visited

      //

    }
    System.out.println("test");
    // Mise à jour de l'interface graphique :
    // builder.progressions().setLabel(..., ...);
  }
}
