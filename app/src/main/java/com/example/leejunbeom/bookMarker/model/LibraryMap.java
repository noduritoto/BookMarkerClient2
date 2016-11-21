package com.example.leejunbeom.bookMarker.model;

import java.util.List;

/**
 * Created by noduritoto on 2016. 11. 14..
 */

public class LibraryMap {
    /*
    MapGraph libraryMapGraph;
    List<String> optimalPath;

    public List getOptimalPath(String start, String end){
        return libraryMapGraph.calculateShortestPath(start, end);
    }

}

// 맵 그래프에 관한 코드////////////////////////////////////////////////////

class MapGraph {
    HipsterGraph<String, Integer> graph;
    List<String> optimalPath;
    MapSpot[] mapSpots;

    public MapGraph(){
        graph = GraphBuilder.<String, Integer>create()

                .connect("1").to("2").withEdge(1)
                .connect("2").to("3").withEdge(1)
                .connect("3").to("4").withEdge(1)
                .connect("4").to("5").withEdge(1)
                .connect("5").to("6").withEdge(1)
                .connect("5").to("21").withEdge(1)
                .connect("6").to("7").withEdge(1)
                .connect("21").to("20").withEdge(1)

                .createUndirectedGraph();

        mapSpots = new MapSpot[]{
                new MapSpot(1, 2, "3"),
        };

    }

    public List calculateShortestPath(String start, String destination){

        SearchProblem p = GraphSearchProblem
                .startingFrom(start)
                .in(graph)
                .takeCostsFromEdges()
                .build();

        // Search the shortest path from "start" to "destination"
        System.out.println(Hipster.createAStar(p).search(destination));
        optimalPath = Hipster.createAStar(p).search(destination).getOptimalPaths();
        return optimalPath;
    }
}

///////////////////////////////////////////////////////////////////

// 맵 지점에 관한 코드////////////////////////////////////////////////

class MapSpot{
    private int pos_x;
    private int pos_y;
    private String myName;

    MapSpot(int pos_x, int pos_y, String name){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.myName = name;
    }

    public void setPos_x(int x){
        this.pos_x = x;
    }

    public void setPos_y(int y){
        this.pos_y = y;
    }

    public void setMyName(String name){
        this.myName = name;
    }

    public int getPos_x(){
        return this.pos_x;
    }

    public int getPos_y(){
        return this.pos_y;
    }

     public String getMyName(){
         return this.myName;
     }

}

/////////////////////////////////////////////////////////////////////

    */
}