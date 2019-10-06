package infixtoprefixandpostfix;

//a stack interface to use for any stack implementation
public interface StackInterface<T>
{
    public void push(T newEntry);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public void clear();
}