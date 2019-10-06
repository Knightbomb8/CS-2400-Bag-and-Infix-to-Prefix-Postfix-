package infixtoprefixandpostfix;

import java.util.EmptyStackException;
import java.util.Vector;

public class VectorStack<T> implements StackInterface<T>
{
    private Vector<T> stack;
    private boolean integrity_ok;
    private static final int DEFAULT_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    public VectorStack()
    {
        this(DEFAULT_CAPACITY);
    }

    public VectorStack(int initial_capacity)
    {
        integrity_ok = false;
        checkCapacity(initial_capacity);
        stack = new Vector<>(initial_capacity);
        integrity_ok = true;
    }

    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
        {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds maximum limit of: " + MAX_CAPACITY);
        }
    }

    private void checkIntegrity()
    {
        if(!integrity_ok)
        {
            throw new SecurityException("ArrayBag of Students is corrupt");
        }
    }

    public void push(T newEntry)
    {
        checkIntegrity();
        stack.add(newEntry);
    }

    public T pop()
    {
        checkIntegrity();
        if (isEmpty())
        {
            throw new EmptyStackException();
        }
        else
        {
            return stack.remove(stack.size() - 1);
        }
    }

    public T peek()
    {
        checkIntegrity();
        if(isEmpty())
        {
            throw new EmptyStackException();
        }
        else
        {
            return stack.lastElement();
        }
    }

    public boolean isEmpty()
    {
        checkIntegrity();
        return stack.isEmpty();
    }

    public void clear()
    {
        checkIntegrity();
        stack.clear();
    }
}