/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    
    private int time;
    
    public Graph(String fileName) throws IOException
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
        Graph graph = new Graph("edges.txt");
        
        //graph.adjLists();
        
        //graph.DFS();
        
        graph.DFSStack();
        
        
        
        
    }
    
    /**
     * Prints out the depth-first traversal for the graph object
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     */
    public void DFS()
    {
        time = 0;
        
        for (String key: this.vertices.keySet())
        {
            Vertex vertex = this.vertices.get(key);
            if(vertex.getColor() == "white")
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
            if(adj.getColor() == "white")
            {
                DFSVisit(adj);
            }    
        }
        
        vertex.setColor("black");
        time++;
        vertex.setEndTime(time);
        
    }
    
    /**
     * DFS using a stack
     * HINT: http://www.codeproject.com/Articles/32212/Introduction-to-Graph-with-Breadth-First-Search-BF
     */
    public void DFSStack()
    {
        Stack stack = new Stack();
        Vertex root = this.vertices.get("A");//root is "A"
        
        stack.push(root);
        
        root.setStartTime(time);
        root.setColor("gray");
        
        
        while (stack.isEmpty() == false)
        {
            
            Vertex v = (Vertex)stack.pop();
            
            if(v.getAdjList() != null)
            {
                Object[] keys = v.getAdjList().keySet().toArray();//because we have a stack we push the adjacents in reverse order
                
                for(int i=keys.length-1; i>=0;i--){
                    Vertex adj = v.getAdjList().get((String)keys[i]);
                    if(adj.getColor() == "white")
                    {
                        stack.push(adj);
                        adj.setColor("gray");   
                    }       
                }
            }    
            
            System.out.println(v.getId());
        }
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
