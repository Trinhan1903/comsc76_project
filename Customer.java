
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.lang.Runnable;
public class Customer {
    private int numberOfItems;
    private double checkoutDuration;

    
    public Customer(int numberOfItems, double checkoutDuration) {
        this.numberOfItems = numberOfItems;
        this.checkoutDuration = checkoutDuration;
    }

    
    public int getNumberOfItems() {
        return numberOfItems;
    }

    
    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    
    public double getCheckoutDuration() {
        return checkoutDuration;
    }

    
    public void setCheckoutDuration(double checkoutDuration) {
        this.checkoutDuration = checkoutDuration;
    }

    /**
     * Returns a string representation of the customer.
     * @return a string representation of the customer
     */
    @Override
    public String toString() {
        return "Customer [numberOfItems=" + numberOfItems + ", checkoutDuration=" + checkoutDuration + "]";
    }
    public static void main(String[] args) {
        long averageItem = 0;
        long averageCheOutTime = 0;
        int customer_Served = 0;
        Queue<Customer> customersQueue = new Queue<>();
        long startTime = System.currentTimeMillis();
        long duration = 2 * 60 * 60 * 1000L; // 2 hours in milliseconds
        long interval = 2 * 60 * 100L; // 2 minutes in milliseconds
        Checkout checkout = new Checkout(5);
        

        while (System.currentTimeMillis() - startTime < duration) {
            // Generate a new customer
            Customer newCustomer = Customer.generateRandomCustomer();
            averageItem += newCustomer.getCheckoutDuration();
            averageCheOutTime +=newCustomer.getCheckoutDuration();
            
            customersQueue.enqueue(newCustomer);

            checkout.addCustomer(customersQueue.dequeue());
            
            customer_Served ++;
            
            System.out.println("New customer added: " + newCustomer);
            System.out.println("Current Queue: " + customersQueue);

          
            try {
                Thread.sleep(interval);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }

        // After 2 hours, the loop exits
        System.out.println("Simulation ended after 2 hours.");
        System.out.println("Customers Served : " + customer_Served + " || " +  " Average Check Out Time : " + averageCheOutTime + "Avarge Item purchase : " + averageItem);

    }
    public static Customer generateRandomCustomer() {
        Random rand = new Random();
        int numberOfItems = rand.nextInt(30) + 1; // between 1 and 30 itmes
        double checkoutDuration = numberOfItems * 0.5; 
        return new Customer(numberOfItems, checkoutDuration);
        
    }
}

class Queue<T>{
    
    /*
     * The tail of the queue is at the beginning
     * of the ArrayList; the head is the last item
     */
    ArrayList<T> items;
    
    /*
     * Create a new Queue
     */
    public Queue() {
        this.items = new ArrayList<T>();
    }
    
    /*
     * Returns true if there are no items in the queue;
     * false otherwise.
     */
    public boolean isEmpty() {
        return (this.items.isEmpty());
    }
    
    /*
     * Add an item to the tail of the queue
     */
    public void enqueue(T item) {
        this.items.add(0, item);
    }
    
    /*
     * Remove the item at the head of the queue and return it.
     * If the queue is empty, throws an exception.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return this.items.remove(this.size() - 1);
    }

    /*
     * Return the item at the head of the queue, but do not remove it.
     * If the queue is empty, throws an exception.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return this.items.get(this.size() - 1);
    }
    
    /*
     * Returns the number of items in the queue.
     */
    public int size() {
        return this.items.size();
    }
    
    /*
     * Convert to string as an array from tail to head
     */
    public String toString() {
        
        if (!this.items.isEmpty()) {
            String arrString = this.items.toString();
            return "tail ->" + arrString + "-> head";
        } else {
            return "<<empty queue>>";
        }
    }
}
