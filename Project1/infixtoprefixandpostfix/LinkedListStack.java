package infixtoprefixandpostfix;

import java.util.EmptyStackException;

//implements the Stack Interface to require certain methods
public class LinkedListStack<T> implements StackInterface<T>
{
    private Node first_node;
    private int number_of_entries;

    //initializes the first node as null and no entries
    public LinkedListStack()
    {
        first_node = null;
        number_of_entries = 0;
    }

    //adds a new node to the stack
    public void push(T newEntry)
    {
        Node<T> new_node = new Node<>(newEntry, first_node);
        first_node = new_node;
        number_of_entries++;
    }

    //takes the first node data from the stack and move the first node pointer
    public T pop()
    {
        T result = null;
        if(!isEmpty())
        {
            @SuppressWarnings("unchecked")
            T temp_result = (T)first_node.getData();
            result = temp_result;
            first_node = first_node.next_node;
            number_of_entries--;
        }
        else
        {
            throw new EmptyStackException();
        }
        return result;
    }

    //returns the first node data without removing the first node
    public T peek()
    {
        T result = null;
        if(!isEmpty())
        {
            @SuppressWarnings("unchecked")
            T temp_result = (T)first_node.getData();
            result = temp_result;
        }
        else
        {
            throw new EmptyStackException();
        }
        return result;
    }

    //checks if there is nothing in the stack
    public boolean isEmpty()
    {
        return(number_of_entries == 0);
    }

    //clear the stack to have nothing in it
    public void clear()
    {
        first_node = null;
        number_of_entries = 0;
    }

    //creates a node class to store data and point to other nodes
    private class Node<T>
    {
        private Node next_node;
        private T data;

        public Node(T data)
        {
            this.data = data;
            this.next_node = null;
        }
        public Node(T data, Node next_node)
        {
            this.data = data;
            this.next_node = next_node;
        }

        public T getData()
        {
            return this.data;
        }
    }
}