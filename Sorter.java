import java.rmi.Remote;
import java.rmi.RemoteException;

//sorter (remote interface)
//extends java rmi remote class
public interface Sorter extends Remote
{
    //will be implemented as synchronized method to handle all client requests
    public String syncCall(int id, String funReq, String operator) throws RemoteException;

    public void createStack(int id) throws RemoteException;

    public void getStack(int id) throws RemoteException;

    public void pushValue(int val) throws RemoteException;

    public void pushOperator(String operator) throws RemoteException;

    public int pop() throws RemoteException;

    public boolean isEmpty() throws RemoteException;

    public int delayPop(int millis) throws RemoteException;

    public String getStackToClient() throws RemoteException;

}
