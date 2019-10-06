package infixtoprefixandpostfix;

import java.util.EmptyStackException;
import java.util.Arrays;

//implements the interface to ensure we get the encessary methods
public class ArrayStack<T> implements StackInterface<T>
{
    //sets constraints on the stack to amke sure we do no exceed set limits
    private final int MAX_CAPACITY = 10000;
    private boolean integrity_ok = false;

    private int number_of_entries;

    private T[] stack;

    //runs non default constructor with a given size
    public ArrayStack()
    {
        this(25);
    }

    //constructs the stack as an array of given size, but if the give size is too big, we throw an error
    public ArrayStack(int size)
    {
        if(size <= MAX_CAPACITY)
        {
            @SuppressWarnings("unchecked")
            T[] temp_stack = (T[])(new Object[size]);
            stack = temp_stack;
            integrity_ok = true;
            number_of_entries = 0;
        }
        else
        {
            throw new IllegalStateException("Attempt to create a bag whose size exceeds maximum capacity");
        }

    }

    //adds a new entry to the stack
    public void push(T newEntry)
    {
        checkIntegrity();
        if(isFull())
        {
            doubleStackCapacity();
        }

        stack[number_of_entries] = newEntry;
        number_of_entries++;
    }

    //removes the top entry from the stack and returns it
    public T pop()
    {
        T result = null;
        if(!isEmpty())
        {
            result = stack[number_of_entries - 1];            
            number_of_entries--;
            stack[number_of_entries] = null;
        }
        else
        {
            throw new EmptyStackException();
        }
        return result;
    }

    //returns the top entry of the stack without removing it
    public T peek()
    {
        T result = null;
        if(!isEmpty())
        {
            result = stack[number_of_entries - 1];
        }
        else
        {
            throw new EmptyStackException();
        }
        return result;
    }

    //checks if the stack has 0 entries
    public boolean isEmpty()
    {
        return (number_of_entries == 0);
    }

    //resets stack contents
    public void clear()
    {
        while(!isEmpty())
        {
            this.pop();
        }
    }

    //doubles capacity of stack array if it becomes full
    private void doubleStackCapacity()
    {
        int new_length = 2 * stack.length;
        checkCapacity(new_length);
        stack = Arrays.copyOf(stack,new_length);
    }

    //decides if a given capacity is too big
    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
        {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds maximum limit of: " + MAX_CAPACITY);
        }
    }

    //checks if the stack is currently full in its current state
    private boolean isFull()
    {
        return (number_of_entries == stack.length);
    }

    private void checkIntegrity()
    {
        if(!integrity_ok)
        {
            throw new SecurityException("ArrayBag of Students is corrupt");
        }
    }
}