package com.example.persistance;

 public class Name {

     //Variables
    private String first;
    private String last;

    //Constructors
    public Name(){
       first = "null";
       last = "null";
    }
    public Name(String first, String last){
        this.first = first;
        this.last = last;
    }

     //Accessor methods
     public void setFirst(String first) {
         this.first = first;
     }
     public void setLast(String last) {
         this.last = last;
     }
     public String getFirst() {
         return first;
     }
     public String getLast() {
         return last;
     }
 }