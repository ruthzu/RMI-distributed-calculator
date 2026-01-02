import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Division by zero");
        }
        return a / b;
    }

    public static void main(String[] args) {
        try {
            // Create the RMI registry on port 1099 inside this JVM
            LocateRegistry.createRegistry(1099);

            // Create and bind the remote object
            CalculatorImpl server = new CalculatorImpl();
            Naming.rebind("Calculator", server);

            System.out.println("Calculator Server is ready and waiting...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}