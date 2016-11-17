package com.stephen.crawler;

public class Queue {
    /**
     * Construct the queue.
     */
    public Queue( ) {
        front = back = null;
    }
    
    /**
     * Test if the queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return front == null;
    }
    
    /**
     * Insert a new item into the queue.
     * @param x the item to insert.
     */
    public void enqueue( Object x ) {
        if( isEmpty( ) )    // Make queue of one element
            back = front = new ListNode( x );
        else                // Regular case
            back = back.next = new ListNode( x );
    }
    
    /**
     * Return and remove the least recently inserted item
     * from the queue.
     * @return the least recently inserted item in the queue.
     * @throws UnderflowException if the queue is empty.
     */
    public Object dequeue( ) {
        if( isEmpty( ) )
        	System.err.println("Queue empty!");
        
        Object returnValue = front.element;
        front = front.next;
        return returnValue;
    }

    //put in new for Index.java
    public Object peek()
    {
    	if(front != null)
    		return front.element;
    	else
    		return null;
    }
    
    
    private ListNode front;
    private ListNode back;
}

/*
/**
 * Exception class for access in empty containers
 * such as stacks, queues, and priority queues.
 * @author Mark Allen Weiss

public class UnderflowException extends RuntimeException {
   
    public UnderflowException( String message ) {
        super( message );
    }
}
*/

// Basic node stored in a linked list
// Note that this class is not accessible outside
// of package weiss.nonstandard

class ListNode {
    // Constructors
    public ListNode( Object theElement ) {
        this( theElement, null );
    }
    
    public ListNode( Object theElement, ListNode n ) {
        element = theElement;
        next    = n;
    }
    
    public Object   element;
    public ListNode next;
}