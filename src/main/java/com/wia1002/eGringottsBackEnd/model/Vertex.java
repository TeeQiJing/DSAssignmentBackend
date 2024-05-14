package com.wia1002.eGringottsBackEnd.model;

import org.springframework.stereotype.Component;


@Component
public
class Vertex {
   String vertexInfo;
   int indeg;
   int outdeg;
   Vertex nextVertex;
   Edge firstEdge;
    
   public Vertex() {
      vertexInfo=null;
      indeg=0;
      outdeg=0;
      nextVertex = null;
      firstEdge = null;
   }
    
   public Vertex(String vInfo, Vertex next) {
      vertexInfo = vInfo;
      indeg=0;
      outdeg=0;
      nextVertex = next;
      firstEdge = null;
   }	
}
