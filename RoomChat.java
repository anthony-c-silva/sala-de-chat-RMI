import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RoomChat extends UnicastRemoteObject implements IRoomChat {
    private Map<String, IUserChat> listaUsuarios;
    private String nomeSala;

    public RoomChat(String nomeSala) throws RemoteException {
        super();
        listaUsuarios = new HashMap<String, IUserChat>();
        this.nomeSala = nomeSala;
    }

    public void enviarMensagem(String nomeUsuario, String mensagem) throws RemoteException {
        for (Map.Entry<String, IUserChat> usuario : listaUsuarios.entrySet()) {
            if (!usuario.getKey().equals(nomeUsuario))
                usuario.getValue().receberMensagem(nomeUsuario, mensagem);
            else
                usuario.getValue().receberMensagem("Você", mensagem);
        }
    }

    public void entrarNaSala(String nomeUsuario, IUserChat usuario) throws RemoteException {
        listaUsuarios.put(nomeUsuario, usuario);
        enviarMensagem("Servidor", nomeUsuario + " entrou!");
    }

    public void sairDaSala(String nomeUsuario) throws RemoteException {
        if (listaUsuarios.containsKey(nomeUsuario)) {
            listaUsuarios.remove(nomeUsuario);
            enviarMensagem("Servidor", nomeUsuario + " saiu!");
        }
    }

    public void fecharSala() throws RemoteException {
        enviarMensagem("Servidor", "Sala fechada pelo servidor.");
        listaUsuarios.clear();
        String urlSala = "rmi://localhost:2020/" + nomeSala;
        try {
            Naming.unbind(urlSala);
            UnicastRemoteObject.unexportObject(this, false);
        } catch (MalformedURLException | NotBoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String obterNomeSala() {
        return nomeSala;
    }

    public Map<String, IUserChat> getListaUsuarios() {
        return listaUsuarios;
    }
}
