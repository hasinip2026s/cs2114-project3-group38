import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Tests the FIFO queue independently of BrewQueue's UI. */
public class LinkedQueueTest
{
    @Test
    public void testNewQueue()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertTrue(queue.toList().isEmpty());
    }


    @Test
    public void testEnqueue()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        queue.enqueue("Taylor");
        assertEquals(3, queue.size());
        assertEquals(Arrays.asList("Alex", "Sam", "Taylor"), queue.toList());
    }


    @Test
    public void testEnqueueNull()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertEquals(1, queue.size());
        assertEquals("Alex", queue.peek());
    }


    @Test
    public void testDequeue()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        queue.enqueue("Taylor");
        assertEquals("Alex", queue.dequeue());
        assertEquals("Sam", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("Taylor", queue.peek());
    }


    @Test
    public void testDequeueEmpty()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        assertThrows(NoSuchElementException.class, queue::dequeue);
        assertTrue(queue.isEmpty());
    }


    @Test
    public void testPeek()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        assertEquals("Alex", queue.peek());
        assertEquals(2, queue.size());
        assertEquals("Alex", queue.peek());
    }


    @Test
    public void testPeekEmpty()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }


    @Test
    public void testIsEmpty()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue("Alex");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }


    @Test
    public void testSize()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        assertEquals(0, queue.size());
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        queue.enqueue("Taylor");
        assertEquals(3, queue.size());
        queue.dequeue();
        assertEquals(2, queue.size());
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
    }


    @Test
    public void testToList()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        queue.enqueue("Taylor");
        assertEquals(Arrays.asList("Alex", "Sam", "Taylor"), queue.toList());
        assertEquals(3, queue.size());
    }


    @Test
    public void testToListEmpty()
    {
        assertTrue(new LinkedQueue<String>().toList().isEmpty());
    }


    @Test
    public void testSingleOrder()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        assertEquals("Alex", queue.dequeue());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        queue.enqueue("Sam");
        assertEquals("Sam", queue.peek());
    }


    @Test
    public void testRepeatedOperations()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        assertEquals("Alex", queue.dequeue());
        queue.enqueue("Taylor");
        assertEquals("Sam", queue.dequeue());
        assertEquals("Taylor", queue.dequeue());
        assertTrue(queue.isEmpty());
    }


    @Test
    public void testToListIsCopy()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("Alex");
        queue.enqueue("Sam");
        List<String> copy = queue.toList();
        copy.clear();
        assertEquals(2, queue.size());
        assertEquals("Alex", queue.peek());
    }


    @Test
    public void testIntegerQueue()
    {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(Integer.valueOf(10), queue.dequeue());
        assertEquals(Integer.valueOf(20), queue.peek());
        assertEquals(2, queue.size());
    }


    /** D4 Exceeds: check FIFO after multiple interleaved operations. */
    @Test
    public void testInterleavedEnqueueDequeueMaintainsFifoOrder()
    {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals("A", queue.dequeue());
        queue.enqueue("C");
        queue.enqueue("D");
        assertEquals("B", queue.dequeue());
        queue.enqueue("E");
        assertEquals(Arrays.asList("C", "D", "E"), queue.toList());
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
        assertEquals("E", queue.dequeue());
        assertTrue(queue.isEmpty());
    }
}
