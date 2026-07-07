package com.smartcare.util;

public class LinkedList
{
    private Node first;
    private Node last;
    private Node current;

    public LinkedList()
    {
        first = null;
        last = null;
        current = null;
    }

    public boolean isEmpty()
        { return (first == null); }

    public void insertAtFront(Object insertItem)
    {
        Node newNode = new Node(insertItem);
            if (isEmpty())
            {

                first = newNode;
                last = newNode;
            }
            else
            {
                newNode.next = first;
                first = newNode;
            }
    }

    public void insertAtBack(Object insertItem)
    {
        Node newNode = new Node(insertItem);

        if (isEmpty())
        {
            first = newNode;
            last = newNode;
        }
        
        else
        {
            last.next = newNode;
            last = newNode;
        }
    }

    public Object removeFromFront()
    {

        Object removeItem = null;

        if (isEmpty())
        { return removeItem; }
            removeItem = first.data;
        if(first == last)
        {
            first = null;
            last = null;
        }
        else
        { first = first.next; }
        return removeItem;
    }

    public Object removeFromBack()
    {
        Object removeItem = null;
        if (isEmpty())
        {   return removeItem; }
        removeItem = last.data;

        if (first == last)
        {
            first = null;
            last = null;
        }
        else
        {
            current = first;

            while(current.next != last)
                {   current = current.next;}
                last = current;
                last.next = null;
        }
        return removeItem;
    }

    public Object getFirst()
    {
        if(isEmpty())
            return null;
        else
        {
            current = first;
            return current.data;
        }
    }

    public Object getNext()
    {
        // Safe null check sequence implemented to permanently block NullPointerExceptions during lookups
        if(current == null || current == last)
        {
            return null;
        }
        else
        {
            current = current.next;
            return current.data;
        }
    }

    public Object getSecond()
    {
        if(isEmpty() || first.next == null)
        {
            return null;
        }
        else
        {
            current = first.next;
            return current.data;
        }
    }

    public boolean remove(Object removeItem) {
        // Case 1: The list is empty, nothing to remove
        if (isEmpty() || removeItem == null) {
            return false;
        }

        // Case 2: The item is at the very front of the list
        if (first.data.equals(removeItem)) {
            removeFromFront(); // Reuse your working method
            return true;
        }

        // Case 3: The item is somewhere in the middle or at the back
        Node previous = first;
        Node searchNode = first.next;

        // Traverse the list to look for a match
        while (searchNode != null) {
            if (searchNode.data.equals(removeItem)) {
                
                // If it is the last node, update the 'last' pointer to the previous node
                if (searchNode == last) {
                    last = previous;
                    last.next = null;
                } else {
                    // It's in the middle: bypass 'searchNode' by connecting previous directly to next
                    previous.next = searchNode.next;
                }
                
                // Safety reset: if the internal iterator ('current') was pointing to the deleted node,
                // reset it so getNext() doesn't break later.
                if (current == searchNode) {
                    current = previous;
                }
                
                return true; // Successfully found and removed
            }
            
            // Move pointers forward
            previous = searchNode;
            searchNode = searchNode.next;
        }

        return false; // Item wasn't found in the list
    }
}