/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw9;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Dell
 */
public class Vertex {
    private String id;
    private TreeMap<String, Vertex> adjList;
    private String color;
    private int startTime;
    private int endTime;
    
    public Vertex(String id)
    {
        this.id = id;
        this.color = "white";//by default all vertex are white
        this.startTime = 0;
        this.endTime = 0;
        this.adjList = new TreeMap<String, Vertex>();
    }
    
    public TreeMap<String, Vertex> getAdjList()
    {
        return this.adjList;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public void setColor(String color)
    {
        this.color = color;
    }
    
    public String getColor()
    {
        return this.color;
    }
    
    public void setStartTime(int time)
    {
        this.startTime = time;
    }
    
    public int getStartTime()
    {
        return this.startTime;
    }
    
    public void setEndTime(int time)
    {
        this.endTime = time;
    }
    
    public int getEndTime()
    {
        return this.endTime;
    }
}
