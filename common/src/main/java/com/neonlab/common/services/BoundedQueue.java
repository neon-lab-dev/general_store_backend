package com.neonlab.common.services;
import lombok.Data;
import java.util.LinkedList;
import java.util.PriorityQueue;




/**
 *
 * Adds an element to the priority queue. If the queue is at maximum capacity,
 * the oldest element is removed before adding the new element.
 *
 */

@Data
public class BoundedQueue<T> {
    private PriorityQueue<T>pq;
    private LinkedList<T>order;
    private final Integer CAPACITY;

    public BoundedQueue(Integer CAPACITY){
        this.CAPACITY = CAPACITY;
        this.pq = new PriorityQueue<>();
        this.order = new LinkedList<>();
    }

    public T add(T element){
        T oldest = null;
        if(order.size() == CAPACITY){
            oldest = order.removeFirst();
            pq.remove(oldest);
        }
        pq.add(element);
        order.addLast(element);
        return oldest;
    }
}
