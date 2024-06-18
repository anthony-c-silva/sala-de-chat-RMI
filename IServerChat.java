import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServerChat extends java.rmi.Remote {
    public ArrayList<String> listarSalas() throws RemoteException;
    public void criarSala(String nomeSala) throws RemoteException;
}
