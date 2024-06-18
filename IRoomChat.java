import java.rmi.RemoteException;

public interface IRoomChat extends java.rmi.Remote {
    public void enviarMensagem(String nomeUsuario, String mensagem) throws RemoteException;
    public void entrarNaSala(String nomeUsuario, IUserChat usuario) throws RemoteException;
    public void sairDaSala(String nomeUsuario) throws RemoteException;
    public void fecharSala() throws RemoteException;
    public String obterNomeSala() throws RemoteException;
}
