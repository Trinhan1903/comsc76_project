import java.util.LinkedList;
import java.util.Queue;
public class Checkout {
    private final Queue<Customer> queue;
    private final int numStations;
    private final Thread[] stations;

    public Checkout(int numStations) {
        this.queue = new LinkedList<>();
        this.numStations = numStations;
        this.stations = new Thread[numStations];

        for (int i = 0; i < numStations; i++) {
            stations[i] = new Thread(new Station());
            stations[i].start();
        }
    }

    public static void main(String[] args) {
        Checkout checkout = new Checkout(5);

        // Simulate adding customers to the queue
        for (int i = 0; i < 10; i++) {
            Customer customer = Customer.generateRandomCustomer();
            checkout.addCustomer(customer);
        }
    }

    public void addCustomer(Customer customer) {
        synchronized (this) {
            queue.add(customer);
            System.out.println("Added customer: " + customer);
            this.notifyAll();
        }
    }

    private class Station implements Runnable {
        public void run() {
            while (true) {
                Customer customer;
                synchronized (Checkout.this) {
                    while (queue.isEmpty()) {
                        try {
                            Checkout.this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    customer = queue.poll();
                }
                processCustomer(customer);
            }
        }

        private void processCustomer(Customer customer) {
            int numberOfItems = customer.getNumberOfItems();
            long processDuration = numberOfItems * 5 * 1000L; // assume it takes 5 seconds per item to process

            try {
                System.out.println("Processing customer: " + customer);
                Thread.sleep(processDuration);
                System.out.println("Finished processing customer: " + customer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}


