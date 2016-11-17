package com.stephen.crawler;

//package DataStructures;

// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// Comparable find( x )   --> Return item that matches x
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AvlTree
{
    /**
     * Construct the tree.
     */
    public AvlTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( Comparable x )
    {
        root = insert( x, root );
    }


    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( Comparable x )
    {
     	remove(x, root);
    }

    
    
    /**
     * Remove x from the tree. Nothing is done if x is not found.
     * Written by Ananya Das 7/09/10
     */
     public void remove(Comparable x, AvlNode t)
     {
       if( t == null ) // never found x
         return; // do nothing
       else
    	 if(x.compareTo(t.element) < 0) //if x is less than element, go to left  
           remove( x, t.left);
         else
           if( x.compareTo(t.element) > 0 )//if x is greater than element, go to right
             remove( x, t.right);
           else // x = current (root) value
           {
        	 if(t.right != null  && t.left != null) // if two children
             {
               t.element = findMin(t.right).element; 
               // set this node to minimum of right subtree
               remove(t.element, t.right);  
               // remove the element we just duplicated from the right side of the tree
             }  
             else // if one or no children
             {
               AvlNode temp = null; // default is being a leaf
               if(t.left != null)  // only left child
                 temp = t.left;
               else
                 if(t.right != null) // only right child
                   temp = t.right;

               //delete t;
               t = temp;
             }  // else less than two children
           } // found x
       // Now check and restore AVL property.  This is done for whole path from root.
       if(t != null)
    	   //Case 1
         if( height( t.left ) - height( t.right ) == 2 )  // left subtree too high
           if( height(t.left.left) >  height(t.left.right))
             rotateWithLeftChild( t );
           else
             doubleWithLeftChild( t );
           else //just flip side of above
             if( height( t.left ) - height( t.right ) == -2 ) // right subtree too high
               if( height(t.right.right) > height(t.right.left))
                 rotateWithRightChild( t );
               else
                 doubleWithRightChild( t );

       if(t!=null)
         t.height = max(height(t.left), height( t.right)) + 1;
     } // remove()
  
    
      
    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public Comparable findMin( )
    {
        return elementAt( findMin( root ) );
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public Comparable findMax( )
    {
        return elementAt( findMax( root ) );
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public Comparable find( Comparable x )
    {
        return elementAt( find( x, root ) );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to get element field.
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private Comparable elementAt( AvlNode t )
    {
        return t == null ? null : t.element;
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private AvlNode insert( Comparable x, AvlNode t )
    {
        if( t == null )
            t = new AvlNode( x, null, null );
        else if( x.compareTo( t.element ) < 0 )
        {
            t.left = insert( x, t.left );
            if( height( t.left ) - height( t.right ) == 2 )
                if( x.compareTo( t.left.element ) < 0 )
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
        }
        else if( x.compareTo( t.element ) > 0 )
        {
            t.right = insert( x, t.right );
            if( height( t.right ) - height( t.left ) == 2 )
                if( x.compareTo( t.right.element ) > 0 )
                    t = rotateWithRightChild( t );
                else
                    t = doubleWithRightChild( t );
        }
        else
            ;  // Duplicate; do nothing
        t.height = max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode findMin( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private AvlNode find( Comparable x, AvlNode t )
    {
        while( t != null )
            if( x.compareTo( t.element ) < 0 )
                t = t.left;
            else if( x.compareTo( t.element ) > 0 )
                t = t.right;
            else
                return t;    // Match

        return null;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode t )
    {
  	
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private static int height( AvlNode t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Return maximum of lhs and rhs.
     */
    private static int max( int lhs, int rhs )
    {
        return lhs > rhs ? lhs : rhs;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private static AvlNode rotateWithLeftChild( AvlNode k2 )
    {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private static AvlNode rotateWithRightChild( AvlNode k1 )
    {
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private static AvlNode doubleWithLeftChild( AvlNode k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private static AvlNode doubleWithRightChild( AvlNode k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }


    public void levelorder()
    {
      AvlNode node;
      Queue queue = new Queue();

      queue.enqueue(root);

      while(!queue.isEmpty())
      {
        node = (AvlNode) queue.dequeue();
        System.out.println(node.element+" ");
        if(node.left != null)
          queue.enqueue(node.left);
        if(node.right != null)
          queue.enqueue(node.right);
      }  // while more in the queue

    }  // levelorder()


    
    
      /** The tree root. */
    public static AvlNode root;


    
    
    
    
    
}