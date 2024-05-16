package com.wia1002.eGringottsBackEnd.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wia1002.eGringottsBackEnd.repository.CurrencyRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Component
public class Graph {

   private Vertex head;

   @Autowired
   CurrencyRepository currencyRepository;

   public void loadDatabase() {
      List<Currency> list = currencyRepository.findAll();
       for (int i = 0; i < list.size(); i++) {
           addVertex(list.get(i).getSourceCoin());
           addVertex(list.get(i).getDestinationCoin());
           addEdge(list.get(i).getSourceCoin(), list.get(i).getDestinationCoin(), list.get(i).getValue(), list.get(i).getProcessingFee());
           addEdge(list.get(i).getDestinationCoin(), list.get(i).getSourceCoin(), 1 / list.get(i).getValue(), list.get(i).getProcessingFee());
       }
   }

   public Double[] computeCurrency(String source, String destination, double amount) {
      Double[] result = {findValue(source, destination, amount), findProcessingFee(source, destination, amount)};
      return result;
   }

   public Double[] printCurrency(String source, String destination, double amount) {
      Double[] result = {findValue(source, destination, amount), findProcessingFee(source, destination, amount)};
      return result;
   }
   
   public Double findValue(String source, String destination, double amount) {
       return dfsValue(getVertex(source), getVertex(destination), amount, new ArrayList<>());
   }
   
   public Double dfsValue(Vertex source, Vertex destination, Double amount, ArrayList<Vertex> visited) {
       
       double hold = amount;
       visited.add(source);
       Edge currentEdge = source.firstEdge;
       
       if (currentEdge.toVertex.vertexInfo.equals(destination.vertexInfo)) {
           amount *= currentEdge.value;
           return amount;
       }
       
       while (currentEdge != null) {
           if (visited.contains(currentEdge.toVertex)) {
               currentEdge = currentEdge.nextEdge;
               continue;
           }
           
           if (currentEdge.toVertex.vertexInfo.equals(destination.vertexInfo)) {
               amount *= currentEdge.value;
               return amount;
           }
           
           amount = dfsValue(currentEdge.toVertex, destination, amount, visited);
           
           if (amount != hold) {
               amount *= currentEdge.value;
               return amount;
           }
           currentEdge = currentEdge.nextEdge;
       }
       return amount;
   }

   public Double findProcessingFee(String source, String destination, double amount) {
      return dfsProcessingFee(getVertex(source), getVertex(destination), amount, new ArrayList<>());
   }
  
  public Double dfsProcessingFee(Vertex source, Vertex destination, Double amount, ArrayList<Vertex> visited) {
      double fee = 0;
      visited.add(source);
      Edge currentEdge = source.firstEdge;
      
      if (currentEdge.toVertex.vertexInfo.equals(destination.vertexInfo)) {
          fee += currentEdge.processingFee;
          return fee;
      }
      
      while (currentEdge != null) {
          if (visited.contains(currentEdge.toVertex)) {
              currentEdge = currentEdge.nextEdge;
              continue;
          }
          
          if (currentEdge.toVertex.vertexInfo.equals(destination.vertexInfo)) {
              fee += currentEdge.processingFee;
              return fee;
          }
          
          fee = dfsProcessingFee(currentEdge.toVertex, destination, amount, visited);
          
          if (fee != 0) {
              fee += currentEdge.processingFee;
              return fee;
          }
          currentEdge = currentEdge.nextEdge;
      }
      return fee ;
  }
   
   public void clear() {   
      head=null;
   }
   
   public int getIndeg(String v)  {
      if (hasVertex(v)==true)	{
         Vertex temp = head;
         while (temp!=null) {
            if ( temp.vertexInfo.equals( v ) )
               return temp.indeg;
            temp=temp.nextVertex;
         }
      }
      return -1;
   }
         
   public int getOutdeg(String v)  {
      if (hasVertex(v)==true)	{
         Vertex temp = head;
         while (temp!=null) {
            if ( temp.vertexInfo.equals( v ) )
               return temp.outdeg;
            temp=temp.nextVertex;
         }
      }
      return -1;
   }

   public boolean hasVertex(String v)	{
      if (head==null)
         return false;
      Vertex temp = head;
      while (temp!=null)	{
         if ( temp.vertexInfo.equals( v ) )
            return true;
         temp=temp.nextVertex;
      }
      return false;
   }

   public boolean addVertex(String v)	{
      if (hasVertex(v)==false)	{
         Vertex temp=head;
         Vertex newVertex = new Vertex(v, null);
         if (head==null)   
            head=newVertex;
         else {
            Vertex previous=head;
            while (temp!=null)  {
               previous=temp;
               temp=temp.nextVertex;
            }
            previous.nextVertex=newVertex;
         }
         return true;
      }
      else
         return false;
   }
   
   public int getIndex(String v) {
      Vertex temp = head;
      int pos=0;
      while (temp!=null)	{
         if ( temp.vertexInfo.equals( v ) )
            return pos;
         temp=temp.nextVertex;
         pos++;
      }
      return -1;
   }
   
   public ArrayList<String> getAllVertexObjects() {
      ArrayList<String> list = new ArrayList<>();
      Vertex temp = head;
      while (temp!=null)	{
         list.add(temp.vertexInfo);
         temp=temp.nextVertex;
      }
      return list;
   }

   public ArrayList<Vertex> getAllVertices() {
      ArrayList<Vertex> list = new ArrayList<>();
      Vertex temp = head;
      while (temp!=null)	{
         list.add(temp);
         temp=temp.nextVertex;
      }
      return list;
   }
   
   public Vertex getVertex(String info) {
       Vertex temp = head;
       while (temp != null) {
           if (info.equals(temp.vertexInfo)) {
               return temp;
           }
           temp = temp.nextVertex;
       }
       return null;
   }

   public boolean addEdge(String source, String destination, Double value, Double fee)   {
      if (head==null)
         return false;
      if (!hasVertex(source) || !hasVertex(destination)) 
         return false;
      Vertex sourceVertex = head;
      while (sourceVertex!=null)	{
         if ( sourceVertex.vertexInfo.equals( source ) )   {
            // Reached source vertex, look for destination now
            Vertex destinationVertex = head;
            while (destinationVertex!=null)	{
               if ( destinationVertex.vertexInfo.equals( destination ) )   {
                  // Reached destination vertex, add edge here
                  Edge currentEdge = sourceVertex.firstEdge;
                  Edge newEdge = new Edge(destinationVertex, value, fee, currentEdge);
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
   
   public boolean hasEdge(String source, String destination) {
      if (head==null)
         return false;
      if (!hasVertex(source) || !hasVertex(destination)) 
         return false;
      Vertex sourceVertex = head;
      while (sourceVertex!=null)	{
         if ( sourceVertex.vertexInfo.equals( source ) )   {
            // Reached source vertex, look for destination now 
            Edge currentEdge = sourceVertex.firstEdge;
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
   
   public Double getEdgeValue(String source, String destination) {
      Double notFound=null;
      if (head==null)
         return notFound;
      if (!hasVertex(source) || !hasVertex(destination)) 
         return notFound;
      Vertex sourceVertex = head;
      while (sourceVertex!=null)	{
         if ( sourceVertex.vertexInfo.equals( source ) )   {
            // Reached source vertex, look for destination now 
            Edge currentEdge = sourceVertex.firstEdge;
            while (currentEdge != null) {
               if (currentEdge.toVertex.vertexInfo.equals(destination)) 
               // destination vertex found 
                  return currentEdge.value;
               currentEdge=currentEdge.nextEdge;
            }
         }
         sourceVertex=sourceVertex.nextVertex;
      }
      return notFound;
   }
   
   public ArrayList<String> getNeighbours (String v)  {
      if (!hasVertex(v))
         return null;
      ArrayList<String> list = new ArrayList<String>();
      Vertex temp = head;
      while (temp!=null)	{
         if ( temp.vertexInfo.equals( v ) )   {
            // Reached vertex, look for destination now
            Edge currentEdge = temp.firstEdge;
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
      Vertex temp=head;
      while (temp!=null) {
         System.out.print("# " + temp.vertexInfo + " : " );
         Edge currentEdge = temp.firstEdge;
         while (currentEdge != null) {
            System.out.print("[" + temp.vertexInfo + "->" + currentEdge.toVertex.vertexInfo + "=" + currentEdge.value + ", " + currentEdge.processingFee + "] " );
            currentEdge=currentEdge.nextEdge;
         }
         System.out.println();
         temp=temp.nextVertex;
      }  
   }

}