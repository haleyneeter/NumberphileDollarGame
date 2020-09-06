package edu.umsl;

import java.util.HashMap;
import java.util.*;

public class Graph<Vertex> {

    //list of vertices
    private Map<Vertex, List<Vertex> > map = new HashMap<>();


    public void  addVert(Vertex vert)
    {
        map.put(vert, new LinkedList<>());
    }



    public void addEdge(Vertex start, Vertex finish){
        map.get(start).add(finish);
        map.get(finish).add(start);
    }


}
