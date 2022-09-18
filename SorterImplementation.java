import java.rmi.RemoteException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

//implements sorter and is where abstract methods are implemented
public class SorterImplementation implements Sorter 
{

    public SorterImplementation()
    {
    }

    /*this is the single synchronised remote method which will handle all client requests via helper methods implemented below
    aims to ensure concurrent clients don't simultaneously get/manipulate stack, resulting in inconsistencies between client stacks. */
    public synchronized String syncCall(int id, String funcReq, String operator)
    {
        try
        {
            SorterServer s1 = new SorterServer(); 
            s1.createStack(id);
            s1.getStack(id);
            if(funcReq.equals("pushop"))
            {
                s1.pushOperator(operator);
            }
            else if(funcReq.equals("push"))
            {
                s1.pushValue(Integer.parseInt(operator)); //as paramater for this method is string, must pass them as int for push
            }
            else if(funcReq.equals("delayPop"))
            {
                return Integer.toString(s1.delayPop(Integer.parseInt(operator))); //as paramater for this method is string, must pass them as int for delayPop
            }
            else if(funcReq.equals("pop"))
            {
                return Integer.toString(s1.pop());
            }
            else if(funcReq.equals("isEmpty"))
            {
                
                return Boolean.toString(s1.isEmpty()); //return boolean result as string so client can print
            }
            else if(funcReq.equals("print"))
            {
                
                return s1.getStackToClient(); //return stack as string so client can print
            }
        }
        catch(Exception e)
        {
            System.err.println("call failed " + e.toString());
            e.printStackTrace();
        }
        return "";
    }

    /*createStack(), takes int arg from CLI to input client ID for hashmap key
    this is to allow for each client to have their own stack.
    First checks if client ID already exists in hash map,
    only if it doesn't, we create a stack and add to hashmap*/
    @Override
    public void createStack(int id) throws RemoteException 
    {
        if(!SorterServer.clientStacks.containsKey(id))
        {
            Stack<Integer> stack = new Stack <Integer>();
            SorterServer.clientStacks.put(id, stack);
        }
    }

    //cStack holds the reference to stack on hashmap on the server, so changes to cStack reflected on hashmap
    @Override
    public void getStack(int id) throws RemoteException
    {
        SorterServer.cStack = SorterServer.clientStacks.get(id); 
    }

    //push to stack referenced by cStack
    @Override
    public void pushValue(int val) throws RemoteException 
    {
        SorterServer.cStack.push(val);
    }

    //pushOperator method to handle ascending, descending, min, and max operator calls
    @Override
    public void pushOperator(String operator) throws RemoteException 
    {
        //local varialbe tcStack acts as reference to cStack so we don't need to constantly call from SorterServer.
        Stack <Integer> tcStack = SorterServer.cStack;

        //tmpStack to hold sorted stack
        Stack <Integer> tmpStack = new Stack<Integer>();

        //check to see what operator client input (convert to lower case so not case sensitive)
        if(operator.toLowerCase().equals("descending"))
        {
            while(!tcStack.isEmpty()) 
            {
                int tmpInt = tcStack.pop(); 

                while(!tmpStack.isEmpty() && tmpStack.peek() > tmpInt) 
                {
                    tcStack.push(tmpStack.pop()); 
                }
                tmpStack.push(tmpInt); 
            }
            SorterServer.cStack.clear(); 
            for(int i : tmpStack)
            {
                SorterServer.cStack.push(i); 
            }
        }

        else if(operator.toLowerCase().equals("ascending"))
        {
            while(!tcStack.isEmpty())
            {
                int tmpInt = tcStack.pop();
            
                while(!tmpStack.isEmpty() && tmpStack.peek() < tmpInt)
                {
                    tcStack.push(tmpStack.pop());
                }
                tmpStack.push(tmpInt);
            }
            SorterServer.cStack.clear();
            for(int i : tmpStack)
            {
                SorterServer.cStack.push(i);
            }
        }

        else if(operator.toLowerCase().equals("max"))
        {
            int stackSize = tcStack.size();
            int[] array = new int [stackSize]; 
            for(int i = 0; i < stackSize; i++)
            {   
                array[i] = tcStack.pop(); 
            }
            int max = array[0]; 
            for(int i = 0; i < stackSize; i++) {if(max < array[i]) max = array[i];} 
            tcStack.push(max); 
        }

        else if(operator.toLowerCase().equals("min"))
        {
            int stackSize = tcStack.size();
            int[] array = new int [stackSize];
            for(int i = 0; i < stackSize; i++)
            {   
                array[i] = tcStack.pop();
            }
            int min = array[0];
            for(int i = 0; i < stackSize; i++) {if(min > array[i]) min = array[i];}
            tcStack.push(min);
        }

        else
        {
            System.out.println("invalid operator");
        }
    }

    //pop from the stack currently held on server, return int value to caller
    @Override
    public int pop() throws RemoteException 
    {
        int popped = SorterServer.cStack.pop();
        return popped;
    }

    //check if stack currently held on server is empty, return boolean value to caller
    @Override
    public boolean isEmpty() throws RemoteException 
    {
        if(SorterServer.cStack.isEmpty())
        {
            return true;
        }
        return false;
    }

    //pop from current stack on server, but allows millisecond delay. Will sleep remote object for desired amount of time.
    @Override
    public int delayPop(int millis) throws RemoteException
    {
        try
        {
        TimeUnit.MILLISECONDS.sleep(millis);
        int popped = SorterServer.cStack.pop();
        return popped;
        }
        catch (InterruptedException e)
        {
            System.err.println("interrupted!");
            return -1;
        }
    }

    //returns the stack to the client as a String, so it can be printed by the client and not the server.
    @Override
    public String getStackToClient() throws RemoteException
    {
        Stack<Integer> tmpStack = SorterServer.cStack;
        return Arrays.toString(tmpStack.toArray()) + "<-- top of stack";
    }
    
}
