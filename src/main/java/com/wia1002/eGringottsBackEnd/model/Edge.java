package com.wia1002.eGringottsBackEnd.model;

import org.springframework.stereotype.Component;

@Component
public class Edge {
   Vertex toVertex;
   Double value;
   Double processingFee;
   Edge nextEdge;
  
   public Edge() {
       toVertex = null;
       value = null;
       nextEdge = null;
   }
  
   public Edge(Vertex destination, Double currency, Double fee, Edge edge) {
       toVertex = destination;
       value = currency;
       processingFee = fee;
       nextEdge = edge;
   }
}
