/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author Dell
 */
public class Graph {
    
    private TreeMap<String, Vertex> vertices;
    private ArrayList<String> edges;
    private static String fileName = "edges_undir.txt";
    //private static String fileName = "edges.txt";
    private static String rootId = "A";
    
    
    private int time;
    
    public Graph() throws IOException
    {   
        this.edges = new ArrayList<String>();
        
        this.vertices = new TreeMap<String, Vertex>();
        
        BufferedReader inputStream = null;
        String line;
        
        //read edges from file
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            while( (line = inputStream.readLine()) != null)
            {
                this.edges.add(line);
            }
        }
        catch (IOException e){}
        finally{
             if (inputStream != null) {
                inputStream.close();
            }
        }
        
        //split edges up into corresponding vertices
        for (int i=0; i<this.edges.size(); i++)
        {
            String[] lineParts = this.edges.get(i).split("\\s+");//any number of whitespace characters as delimiter
            
            String tail = lineParts[0];
            String head = lineParts[1];
            
            Vertex headV = null;
            Vertex tailV = null;
            
            if(this.vertices.get(head) == null)
            {
                headV = new Vertex(head);
                this.vertices.put(head, headV);
            }
            else
                headV = this.vertices.get(head);
                
            
            if(this.vertices.get(tail) == null)
            {
                tailV = new Vertex(tail);
                this.vertices.put(tail, tailV);
            }
            else
                tailV = this.vertices.get(tail);
                
            
            tailV.getAdjList().put(head, headV);//add the head as tail's adjacent
        }
        
        
    }
    
    public static void main(String[] args) throws IOException
    {
        Graph graph = new Graph();
        
        //graph.adjLists();
        
        //graph.DFS();
        
        //graph.DFSStack();
        
        /*System.out.print("BFS FIFO: ");
        graph.BFS();
        System.out.println();
        
        System.out.print("BFS LIFO: ");
        graph.BFSLIFO();
        System.out.println();*/
        
        graph.BFSearch("E");
        
        
        
        
    }
    
    /**
     * Prints out the depth-first traversal for the graph object
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     */
    public void DFS()
    {
        time = 0;
        this.colorWhite();
        
        for (String key: this.vertices.keySet())
        {
            Vertex vertex = this.vertices.get(key);
            if("white".equals(vertex.getColor()))
                DFSVisit(vertex);
        }
        
        //print the result
        for (String key: this.vertices.keySet())
        {
            Vertex vertex = this.vertices.get(key);
            System.out.print(key+": start "+vertex.getStartTime()+" end "+vertex.getEndTime());
            System.out.println();
            
        }
    }
    
    /**
     * Helper function for the DFS
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     * @param vertex  - vertex to be traversed
     */
    public void DFSVisit(Vertex vertex)
    {
        time++;
        vertex.setColor("gray");
        vertex.setStartTime(time);
        
        TreeMap<String, Vertex> adjList = vertex.getAdjList();
            
        for(String index: adjList.keySet())
        {
            Vertex adj = adjList.get(index);
            if("white".equals(adj.getColor()))
            {
                DFSVisit(adj);
            }    
        }
        
        time++;
        vertex.setEndTime(time);
        
    }
    
    /**
     * DFS using a stack
     * HINT: http://www.codeproject.com/Articles/32212/Introduction-to-Graph-with-Breadth-First-Search-BF
     */
    public void DFSStack()
    {
        this.colorWhite();
        
        Stack stack = new Stack();
        Vertex root = this.vertices.get(rootId);
        
        stack.push(root);
        
        root.setColor("gray");
        System.out.println(root.getId());
        
        while (stack.isEmpty() == false)
        {
            Vertex v = (Vertex)stack.peek();//get the first vertex from the stack
            
            //else: there are no adjecants so just pop the node
            if(v.getAdjList() != null)
            {
                Object[] keys = v.getAdjList().keySet().toArray();
                boolean whites = false;//boolean to determine whether or not there are any white(unvisited) adjecant nodes left
                
                for(int i=0; i<keys.length; i++){
                    Vertex adj = v.getAdjList().get((String)keys[i]);

                    //take the first unvisited node, color it gray, print it out and push it in the stack
                    if("white".equals(adj.getColor()))
                    {
                        whites = true;
                        adj.setColor("gray");   
                        stack.push(adj);
                        System.out.println(adj.getId());
                        break;
                    }       
                }
                //if there are no unvisited adjacent nodes pop the vertex
                if(whites == false)
                    stack.pop();  
            }
            else
            {
                stack.pop();
            }       
        }    
    }
    
    /**
     * Pints out the BFS-traversal for the given graph using a FIFO queue
     */
    public void BFS()
    {
        this.colorWhite();
        
        ArrayDeque queue = new ArrayDeque();
        
        Vertex root = this.vertices.get(rootId);
        
        queue.add(root);
        
        root.setColor("gray");
        
        while (queue.isEmpty() == false)
        {
            Vertex v = (Vertex)queue.poll();//pull from the head of the queue
            
            if(v.getAdjList() != null)
            {
                Object[] keys = v.getAdjList().keySet().toArray();
                
                for(int i=0; i<keys.length; i++){
                    Vertex adj = v.getAdjList().get((String)keys[i]);
                    if("white".equals(adj.getColor()))
                    {
                        queue.add(adj);//add to the end of the queue
                        adj.setColor("gray");   
                    }       
                }
            }
            
            System.out.print(v.getId()+", ");
        }
    }
    
    /**
     * BFS-traversal using LIFO queue
     */
    public void BFSLIFO()
    {
        this.colorWhite();
        
        ArrayDeque queue = new ArrayDeque();
        
        Vertex root = this.vertices.get(rootId);
        
        queue.add(root);
        
        root.setColor("gray");
        
        while (queue.isEmpty() == false)
        {
            Vertex v = (Vertex)queue.pollLast();//pull from the head of the queue
            
            if(v.getAdjList() != null)
            {
                Object[] keys = v.getAdjList().keySet().toArray();
                
                for(int i=0; i<keys.length; i++){
                    Vertex adj = v.getAdjList().get((String)keys[i]);
                    if("white".equals(adj.getColor()))
                    {
                        queue.add(adj);//add to the end of the queue
                        adj.setColor("gray");   
                    }       
                }
            }
            
            System.out.print(v.getId()+", ");
        }
    }
    
    /**
     * Prints out all possible randomized search orders starting from pre-defined root to the given vertex
     * HINT: http://stackoverflow.com/questions/58306/graph-algorithm-to-find-all-connections-between-two-arbitrary-vertices
     * @param id - id of the destination vertex
     */
    public void BFSearch(String id)
    {
        ArrayDeque queue = new ArrayDeque();
        
        Vertex root      = this.vertices.get(rootId);
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        
        path.add(root);
        queue.add(path);
        
        while (queue.isEmpty() == false)
        {
            ArrayList<Vertex> p = (ArrayList<Vertex>)queue.poll();
            Vertex last = p.get(p.size()-1);
            
            if(last.getAdjList() != null)//else: we have reached a dead end, discard it
            {
                Object[] keys = last.getAdjList().keySet().toArray();
                
                for(int i=0; i<keys.length; i++){
                    Vertex adj = last.getAdjList().get((String)keys[i]);
                    
                    if(p.contains(adj) == false)//else: we have a cycle, discard it
                    {
                        //create a new path, copy predecessors into it and add the adjacent
                        ArrayList<Vertex> new_path = new ArrayList<Vertex>();
                        new_path.addAll(p);
                        new_path.add(adj);
                        
                        //if adjacent is what we are looking for: print the path and discard it
                        if(id.equals(adj.getId()) == true)
                        {
                            for(int j=0; j<new_path.size(); j++)
                            {
                                System.out.print(new_path.get(j).getId()+" ");
                            }
                            System.out.println();
                        }
                        //otherwise: add it to the queue of paths to investigate it further
                        else
                        {
                            queue.add(new_path);
                        }
                        
                    } 
                }
            }
            
        }
    }
    
    /**
     * Helper to set the color of all vertices to white prior to traversal
     */
    public void colorWhite()
    {
        for (String key: this.vertices.keySet())
            this.vertices.get(key).setColor("white");
         
    }
    
    /**
     * Helper to print out adjacency lists of the given graph
     */
    public void adjLists()
    {
        for (String key: this.vertices.keySet())
        {
            System.out.print(key+": ");
            
            TreeMap<String, Vertex> adjList = this.vertices.get(key).getAdjList();
            
            for(String index: adjList.keySet())
                System.out.print(adjList.get(index).getId()+" ");
            
            System.out.println();
        }
    }

    
}
