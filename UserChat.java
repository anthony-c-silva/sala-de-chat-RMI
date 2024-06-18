import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class UserChat extends UnicastRemoteObject implements IUserChat {

    String nomeUsuario;
    IServerChat servidor;
    IRoomChat salaAtual;

    private JFrame frame;
    private JTextField campoTexto;
    private JTextArea areaMensagens;
    private JButton botaoEnviar;
    private JButton botaoEntrarSala;
    private JButton botaoSairSala;
    private JButton botaoMostrarSalas;
    private JButton botaoCriarSala;
    private JPanel painel;
    private JScrollPane painelRolagem;
    private JList<String> listaSalas;
    private DefaultListModel<String> modeloListaSalas;

    public UserChat(String nomeUsuario) throws RemoteException {
        super();
        this.nomeUsuario = nomeUsuario;
    }

    private void inicializarGUI(IUserChat usuario) {
        frame = new JFrame(nomeUsuario);
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        areaMensagens = new JTextArea();
        painel = new JPanel(new BorderLayout());
        painel.setBounds(10, 11, 312, 206);
        painelRolagem = new JScrollPane();
        painelRolagem.setViewportView(areaMensagens);
        painel.add(painelRolagem);
        frame.getContentPane().add(painel);

        campoTexto = new JTextField();
        campoTexto.setBounds(10, 228, 312, 22);
        frame.getContentPane().add(campoTexto);
        campoTexto.setColumns(10);

        botaoEnviar = new JButton("Enviar");
        botaoEnviar.setBounds(335, 228, 89, 23);
        frame.getContentPane().add(botaoEnviar);
        botaoEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mensagem = campoTexto.getText();
                try {
                    salaAtual.enviarMensagem(nomeUsuario, mensagem);
                    campoTexto.setText("");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        botaoEntrarSala = new JButton("Entrar");
        botaoEntrarSala.setBounds(332, 11, 92, 23);
        frame.getContentPane().add(botaoEntrarSala);
        botaoEntrarSala.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeSala = listaSalas.getSelectedValue();
                String urlSala = "rmi://localhost:2020/" + nomeSala;
                try {
                    salaAtual = (IRoomChat) Naming.lookup(urlSala);
                    salaAtual.entrarNaSala(nomeUsuario, usuario);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        botaoSairSala = new JButton("Sair");
        botaoSairSala.setBounds(332, 45, 92, 23);
        frame.getContentPane().add(botaoSairSala);
        botaoSairSala.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (salaAtual != null) {
                        salaAtual.sairDaSala(nomeUsuario);
                        salaAtual = null;
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        botaoMostrarSalas = new JButton("Salas");
        botaoMostrarSalas.setBounds(332, 80, 92, 23);
        frame.getContentPane().add(botaoMostrarSalas);
        botaoMostrarSalas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> salas = servidor.listarSalas();
                    modeloListaSalas.clear();
                    for (String sala : salas) {
                        modeloListaSalas.addElement(sala);
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        botaoCriarSala = new JButton("Criar");
        botaoCriarSala.setBounds(332, 115, 92, 23);
        frame.getContentPane().add(botaoCriarSala);
        botaoCriarSala.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    servidor.criarSala(campoTexto.getText());
                    campoTexto.setText("");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        modeloListaSalas = new DefaultListModel<>();
        listaSalas = new JList<>(modeloListaSalas);
        JScrollPane rolagemListaSalas = new JScrollPane(listaSalas);
        rolagemListaSalas.setBounds(10, 260, 414, 90);
        frame.getContentPane().add(rolagemListaSalas);

        frame.setVisible(true);
    }

    @Override
    public void receberMensagem(String nomeRemetente, String mensagem) {
        this.areaMensagens.append(nomeRemetente + ": " + mensagem + "\n");
    }

    public static void main(String[] args) {
        try {
            String nomeUsuario = args[0];
            IUserChat usuario = new UserChat(nomeUsuario);

            Naming.rebind("rmi://localhost:2020/" + nomeUsuario, usuario);
            System.out.println("Cliente conectado: " + nomeUsuario);

            IServerChat servidor = (IServerChat) Naming.lookup("rmi://localhost:2020/Servidor");
            ((UserChat) usuario).servidor = servidor;
            ((UserChat) usuario).inicializarGUI(usuario);

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage() + " Adicione nome como parametro");
            e.printStackTrace();
        }
    }
}
