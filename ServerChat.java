import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class ServerChat extends UnicastRemoteObject implements IServerChat {
    private ArrayList<String> listaSalas;

    private JFrame frame;
    private JButton botaoFecharSala;
    private JList<String> areaListaSalas;
    private JScrollPane rolagemLista;
    private JPanel painel;

    private void configurarGUI() {
        frame = new JFrame("Servidor");
        frame.setBounds(120, 120, 290, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        botaoFecharSala = new JButton("Fechar Sala");
        botaoFecharSala.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> salasParaFechar = new ArrayList<String>(areaListaSalas.getSelectedValuesList());

                for (String nomeSala : salasParaFechar) {
                    String urlSala = "rmi://localhost:2020/" + nomeSala;

                    listaSalas.remove(nomeSala);

                    try {
                        IRoomChat sala = (IRoomChat) Naming.lookup(urlSala);
                        sala.fecharSala();
                    } catch (MalformedURLException | RemoteException | NotBoundException e1) {
                        e1.printStackTrace();
                    }
                }

                String[] listaSalasArray = listaSalas.toArray(new String[0]);
                areaListaSalas.setListData(listaSalasArray);
            }
        });
        botaoFecharSala.setBounds(10, 120, 120, 23);
        frame.getContentPane().add(botaoFecharSala);

        areaListaSalas = new JList<String>();
        painel = new JPanel(new BorderLayout());
        painel.setBounds(10, 10, 120, 100);
        rolagemLista = new JScrollPane();
        rolagemLista.setViewportView(areaListaSalas);
        areaListaSalas.setLayoutOrientation(JList.VERTICAL);
        painel.add(rolagemLista);
        frame.getContentPane().add(painel);

        frame.setVisible(true);
    }

    public ServerChat() throws RemoteException {
        super();
        listaSalas = new ArrayList<String>();
        configurarGUI();
    }

    @Override
    public ArrayList<String> listarSalas() {
        return this.listaSalas;
    }

    @Override
    public void criarSala(String nomeSala) throws RemoteException {
        String urlSala = "rmi://localhost:2020/" + nomeSala;
        RoomChat salaChat = new RoomChat(nomeSala);

        if (!listaSalas.contains(nomeSala) && !nomeSala.equals("")) {
            try {
                Naming.rebind(urlSala, salaChat);
                listaSalas.add(nomeSala);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String[] listaSalasArray = listaSalas.toArray(new String[0]);
        areaListaSalas.setListData(listaSalasArray);
    }

    public static void main(String args[]) {
        try {
            ServerChat servidor = new ServerChat();
            LocateRegistry.createRegistry(2020);
            Naming.rebind("rmi://localhost:2020/Servidor", servidor);
            System.out.println("Servidor está rodando...");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}
