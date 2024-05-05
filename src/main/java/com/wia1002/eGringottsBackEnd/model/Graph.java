package com.wia1002.eGringottsBackEnd.model;

import java.util.ArrayList;

public class Graph<T, N extends Comparable <N>> {
    Vertex<T,N> head;
    int size;
     
    public Graph()	{
       head=null;
       size=0;
    }
    
    public void clear() {   
       head=null;
    }
 
    public int getSize()   {
       return this.size;
    }
    
    public int getIndeg(T v)  {
       if (hasVertex(v)==true)	{
          Vertex<T,N> temp = head;
          while (temp!=null) {
             if ( temp.vertexInfo.equals( v ) )
                return temp.indeg;
             temp=temp.nextVertex;
          }
       }
       return -1;
    }
          
    public int getOutdeg(T v)  {
       if (hasVertex(v)==true)	{
          Vertex<T,N> temp = head;
          while (temp!=null) {
             if ( temp.vertexInfo.equals( v ) )
                return temp.outdeg;
             temp=temp.nextVertex;
          }
       }
       return -1;
    }
 
    public boolean hasVertex(T v)	{
       if (head==null)
          return false;
       Vertex<T,N> temp = head;
       while (temp!=null)	{
          if ( temp.vertexInfo.equals( v ) )
             return true;
          temp=temp.nextVertex;
       }
       return false;
    }
 
    public boolean addVertex(T v)	{
       if (hasVertex(v)==false)	{
          Vertex<T,N> temp=head;
          Vertex<T,N> newVertex = new Vertex<>(v, null);
          if (head==null)   
             head=newVertex;
          else {
             Vertex<T,N> previous=head;;
             while (temp!=null)  {
                previous=temp;
                temp=temp.nextVertex;
             }
             previous.nextVertex=newVertex;
          }
          size++;
          return true;
       }
       else
          return false;
    }
    
    public int getIndex(T v) {
       Vertex<T,N> temp = head;
       int pos=0;
       while (temp!=null)	{
          if ( temp.vertexInfo.equals( v ) )
             return pos;
          temp=temp.nextVertex;
          pos++;
       }
       return -1;
    }
    
    public ArrayList<T> getAllVertexObjects() {
       ArrayList<T> list = new ArrayList<>();
       Vertex<T,N> temp = head;
       while (temp!=null)	{
          list.add(temp.vertexInfo);
          temp=temp.nextVertex;
       }
       return list;
    }
 
    public ArrayList<Vertex<T,N>> getAllVertices() {
       ArrayList<Vertex<T,N>> list = new ArrayList<>();
       Vertex<T,N> temp = head;
       while (temp!=null)	{
          list.add(temp);
          temp=temp.nextVertex;
       }
       return list;
    }
    
    public T getVertex(int pos) {
       if (pos>size-1 || pos<0) 
          return null;
       Vertex<T,N> temp = head;
       for (int i=0; i<pos; i++)
          temp=temp.nextVertex;
       return temp.vertexInfo;
    }
 
    public boolean addEdge(T source, T destination, N w)   {
       if (head==null)
          return false;
       if (!hasVertex(source) || !hasVertex(destination)) 
          return false;
       Vertex<T,N> sourceVertex = head;
       while (sourceVertex!=null)	{
          if ( sourceVertex.vertexInfo.equals( source ) )   {
             // Reached source vertex, look for destination now
             Vertex<T,N> destinationVertex = head;
             while (destinationVertex!=null)	{
                if ( destinationVertex.vertexInfo.equals( destination ) )   {
                   // Reached destination vertex, add edge here
                   Edge<T,N> currentEdge = sourceVertex.firstEdge;
                   Edge<T,N> newEdge = new Edge<>(destinationVertex, w, currentEdge);
                   sourceVertex.firstEdge=newEdge;
                   sourceVertex.outdeg++;
                   destinationVertex.indeg++;
                   return true;
                }
                destinationVertex=destinationVertex.nextVertex;
             }
          }
          sourceVertex=sourceVertex.nextVertex;
       }
       return false;
    }
    
    public boolean hasEdge(T source, T destination) {
       if (head==null)
          return false;
       if (!hasVertex(source) || !hasVertex(destination)) 
          return false;
       Vertex<T,N> sourceVertex = head;
       while (sourceVertex!=null)	{
          if ( sourceVertex.vertexInfo.equals( source ) )   {
             // Reached source vertex, look for destination now 
             Edge<T,N> currentEdge = sourceVertex.firstEdge;
             while (currentEdge != null) {
                if (currentEdge.toVertex.vertexInfo.equals(destination)) 
                // destination vertex found 
                   return true;
                currentEdge=currentEdge.nextEdge;
             }
          }
          sourceVertex=sourceVertex.nextVertex;
       }
       return false;
    }
    
    public N getEdgeWeight(T source, T destination) {
       N notFound=null;
       if (head==null)
          return notFound;
       if (!hasVertex(source) || !hasVertex(destination)) 
          return notFound;
       Vertex<T,N> sourceVertex = head;
       while (sourceVertex!=null)	{
          if ( sourceVertex.vertexInfo.equals( source ) )   {
             // Reached source vertex, look for destination now 
             Edge<T,N> currentEdge = sourceVertex.firstEdge;
             while (currentEdge != null) {
                if (currentEdge.toVertex.vertexInfo.equals(destination)) 
                // destination vertex found 
                   return currentEdge.weight;
                currentEdge=currentEdge.nextEdge;
             }
          }
          sourceVertex=sourceVertex.nextVertex;
       }
       return notFound;
    }
    
    public ArrayList<T> getNeighbours (T v)  {
       if (!hasVertex(v))
          return null;
       ArrayList<T> list = new ArrayList<T>();
       Vertex<T,N> temp = head;
       while (temp!=null)	{
          if ( temp.vertexInfo.equals( v ) )   {
             // Reached vertex, look for destination now
             Edge<T,N> currentEdge = temp.firstEdge;
             while (currentEdge != null) {
                list.add(currentEdge.toVertex.vertexInfo);
                currentEdge=currentEdge.nextEdge;
             }
          }
          temp=temp.nextVertex;
       }
       return list;   
    }
    
    public void print()   {
       Vertex<T,N> temp=head;
       while (temp!=null) {
          System.out.print("# " + temp.vertexInfo + " : " );
          Edge<T,N> currentEdge = temp.firstEdge;
          while (currentEdge != null) {
             System.out.print("[" + temp.vertexInfo + "->" + currentEdge.toVertex.vertexInfo + "=" + currentEdge.weight + "] " );
             currentEdge=currentEdge.nextEdge;
          }
          System.out.println();
          temp=temp.nextVertex;
       }  
    }
 
 }

class Vertex<T, N> {
    T vertexInfo;
    int indeg;
    int outdeg;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;
     
    public Vertex() {
       vertexInfo=null;
       indeg=0;
       outdeg=0;
       nextVertex = null;
       firstEdge = null;
    }
     
    public Vertex(T vInfo, Vertex<T,N> next) {
       vertexInfo = vInfo;
       indeg=0;
       outdeg=0;
       nextVertex = next;
       firstEdge = null;
    }	
 }

class Edge<T, N> {
	Vertex<T,N> toVertex;
	N weight;
	Edge<T,N> nextEdge;
	
	public Edge()	{
		toVertex = null;
		weight = null;
		nextEdge = null;
	}
	
	public Edge(Vertex<T,N> destination, N w, Edge<T,N> edge)	{
		toVertex = destination;
		weight = w;
		nextEdge = edge;
	}

}