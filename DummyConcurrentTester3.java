import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class DummyConcurrentTester3
{
    public static int clientId = 6;

    public DummyConcurrentTester3()
    {
    }

    public static void main(String[] args) 
    {

        String host = null; 
        SorterClient.clientId = (args.length < 1) ? 0 : Integer.parseInt(args[0]);
        
        try
        {
            Registry registry = LocateRegistry.getRegistry(host); 
            Sorter stub = (Sorter) registry.lookup("SorterServer"); 

            for(int i = 0; i < 50000; i++)
            {
                stub.syncCall(clientId, "push", "200"); //continuously push 200 to client stack 6
            }
            
        }
        catch (Exception e)
        {   
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
