package src.main.java.com.smartcare;
public class Queue
{
	private LinkedList list;

	public Queue()
	{	list = new LinkedList();	}
	
	public boolean isEmpty()
	{	return list.isEmpty();	}
	
	public void enqueue(Object elem)
	{	list.insertAtBack(elem);	}
	
	public Object dequeue()
	{	return list.removeFromFront();	}
	
	public Object getFront()
	{	return list.getFirst();	}
	
	// Safe inspection loop that reads the tail without destructive removal operations
	public Object getEnd()
	{
		if (list.isEmpty())
			return null;
			
		Object currentItem = list.getFirst();
		Object lastItem = currentItem;
		
		while (currentItem != null)
		{
			lastItem = currentItem;
			currentItem = list.getNext();
		}
		return lastItem;
	}
}
