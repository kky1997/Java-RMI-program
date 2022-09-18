import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SorterClient 
{
    public static int clientId = 1;
    public static String funcRequest; //holds method request for switch statement
    public static int pushVal = 0; //holds int value to be pushed to stack
    public static String pushOperator; //holds string operator for pushOperator() method
    public static int delayTime = 0; //holds milliseconds for delayPop()


    public SorterClient()
    {
    }

    //method to print client side, taking a String return value from server
    public static void clientPrint(String returnVal)
    {
        System.out.println(returnVal);
    }

    public static void main(String[] args) 
    {
        String host = null; //either use arg 127.0.0.1 loopback address or leave as null so default port used 1099
        clientId = Integer.parseInt(args[0]); //client ID always first arg input
        funcRequest = args[1]; //2nd arg represents what method client intends to use

        //if statements to determine logical 3rd arg input, arg[2], depending on the initial method requested (push, pop etc)
        if(funcRequest.equals("push")) 
        {
            pushVal = Integer.parseInt(args[2]);
        }
        else if(funcRequest.equals("pushop"))
        {
            pushOperator = args[2];
        }
        else if(funcRequest.equals("delayPop"))
        {
            delayTime = Integer.parseInt(args[2]);
        }

        try
        {
            Registry registry = LocateRegistry.getRegistry(host); //default port (1099) used to get registry
            Sorter stub = (Sorter) registry.lookup("SorterServer"); //lookup registry using registry stub, from registry we obtain stub for remote object (server)
            
            /*switch statment to evaluate CLI arg input, all client requests are handled through syncCall() (the server side synchronized method),
             different parameters are passed into syncCall() depending on user input via arg[1]*/
            switch(funcRequest) 
            {
                case "push" :
                stub.syncCall(clientId, funcRequest, Integer.toString(pushVal));
                break;

                case "pushop" :
                stub.syncCall(clientId, funcRequest, pushOperator);
                break;

                case "pop" :
                System.out.printf("Client " + clientId + ": ");
                clientPrint(stub.syncCall(clientId, funcRequest, pushOperator));
                break;

                case "isEmpty" :
                System.out.printf("Client " + clientId + ": ");
                clientPrint(stub.syncCall(clientId, funcRequest, pushOperator)); 
                break;

                case "delayPop" :
                System.out.printf("Client " + clientId + ": ");
                clientPrint(stub.syncCall(clientId, funcRequest, Integer.toString(pushVal)));
                break;

                case "print" :
                System.out.printf("Client " + clientId + ": ");
                clientPrint(stub.syncCall(clientId, funcRequest, pushOperator));
                break;
            }
           
        }
        catch (Exception e)
        {   
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}