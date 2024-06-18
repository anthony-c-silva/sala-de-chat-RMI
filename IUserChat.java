import java.rmi.RemoteException;

public interface IUserChat extends java.rmi.Remote {
    public void receberMensagem(String nomeRemetente, String mensagem) throws RemoteException;
}
