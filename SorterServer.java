import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Stack;
import java.util.HashMap;

//SorterServer inherits implemented methods from SorterImplementation
public class SorterServer extends SorterImplementation 
{
    //holds reference to the stack belonging to client currently accessing server
    public static Stack<Integer> cStack = null; 

    //HashMap to keep individual stacks for clients (hash keys are the clients IDs)
    public static HashMap<Integer, Stack<Integer>> clientStacks = new HashMap<Integer, Stack<Integer>>();

    public SorterServer()
    {
        super();
    }
    
    public static void main(String[] args) 
    {
        try
        {
            SorterServer server = new SorterServer(); //create remote object

            Sorter stub = (Sorter) UnicastRemoteObject.exportObject(server,1099); //export remote object (server) to "stub"

            Registry registry = LocateRegistry.createRegistry(1099); //create RMI registry with default port (create), this starts registry inside server JVM instead of having to start RMI registry seperately in command line

            registry.bind("SorterServer", stub); //bind stub (Sorter implementation) to server name "SorterServer"

            System.out.println("Server is online");
        }
        catch (Exception e)
        {
            System.err.print("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
