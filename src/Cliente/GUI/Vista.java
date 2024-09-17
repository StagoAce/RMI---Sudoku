package Cliente.GUI;

import Cliente.SudokuCliente;
import Implement.ChatImplement;
import Implement.ServerChatImplement;
import Interface.ServerChatInterface;
import Interface.SudokuInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vista {

    private static JButton b4x4;
    private static JButton b9X9;
    private static JButton b16X16;
    private static JButton exit;
    private static JButton sendButton;
    private static JTextArea chatArea;
    private static JTextField messageField;
    private static JFrame frame;
    private static JPanel panel;
    private static JPanel mainPanel;
    private static JPanel matriz;
    private static JPanel chatPanel;
    private static JTable table;
    private static ChatImplement chatCliente;
    private static ServerChatInterface serverChat;

    public void pantalla() {
        frame = new JFrame("Mi Aplicación");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        matriz = new JPanel();
        chatPanel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 600));

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 5, Color.BLACK),
                BorderFactory.createLineBorder(Color.BLACK)));

        b4x4 = new JButton("Matriz 4x4");
        b9X9 = new JButton("Matriz 9x9");
        b16X16 = new JButton("Matriz 16x16");
        exit = new JButton("Salir");

        b4x4.addActionListener(this::actionPerformed);
        b9X9.addActionListener(this::actionPerformed);
        b16X16.addActionListener(this::actionPerformed);
        exit.addActionListener(this::actionPerformed);

        b4x4.setAlignmentX(JButton.LEFT_ALIGNMENT);
        b9X9.setAlignmentX(JButton.LEFT_ALIGNMENT);
        b16X16.setAlignmentX(JButton.LEFT_ALIGNMENT);
        exit.setAlignmentX(JButton.LEFT_ALIGNMENT);

        panel.add(b4x4);
        panel.add(Box.createVerticalStrut(20)); // Espacio entre botones
        panel.add(b9X9);
        panel.add(Box.createVerticalStrut(20)); // Espacio entre botones
        panel.add(b16X16);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exit);

        try {
            SudokuInterface sudokuInterface = (SudokuInterface) Naming.lookup("Sudoku");
            table = sudokuInterface.creacionTable(0);

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setDefaultRenderer(Object.class, new RendererComponentTable(2));

        matriz.setLayout(new BorderLayout());
        matriz.add(new JScrollPane(table), BorderLayout.CENTER);

        chatPanel.setLayout(new BorderLayout());

        try {
            serverChat = (ServerChatInterface) Naming.lookup("chat");

            chatCliente = new ChatImplement();
            chatCliente.registrarNombre(serverChat.obtenerAsignacionNombre());

            // Modificar el título del JFrame para incluir el nombre de usuario
            frame.setTitle("Mi Aplicación - " + chatCliente.getNombre());

            chatArea = chatCliente.chat();
            serverChat.RegistrarCliente(chatCliente);

            // Iniciar un hilo que escuche constantemente mensajes
            new Thread(() -> {
                while (true) {
                    try {
                        chatArea = chatCliente.chat();
                        Thread.sleep(1000); // Tiempo de espera entre chequeos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        messageField = new JTextField();
        sendButton = new JButton("Enviar");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    try {
                        serverChat.enviarMensaje("- " + chatCliente.getNombre().concat(": ").concat(message));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    messageField.setText("");
                }
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(matriz);
        mainPanel.add(chatPanel);

        frame.add(panel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            try {
                serverChat.cerrarSesionCLiente(chatCliente.getNombre());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            System.exit(0);
        } else if (e.getSource() == b4x4) {
            repintarTabla(4, 2);
        } else if (e.getSource() == b9X9) {
            repintarTabla(9, 3);
        } else if (e.getSource() == b16X16) {
            repintarTabla(16, 4);
        }
    }

    public void repintarTabla(int tamañoTabla, int tamañoBloques) {
        try {
            SudokuInterface sudokuInterface = (SudokuInterface) Naming.lookup("Sudoku");
            table = sudokuInterface.creacionTable(tamañoTabla);

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setDefaultRenderer(Object.class, new RendererComponentTable(tamañoBloques));

        matriz.removeAll();
        matriz.add(new JScrollPane(table), BorderLayout.CENTER);
        matriz.revalidate();
        matriz.repaint();
    }

}
