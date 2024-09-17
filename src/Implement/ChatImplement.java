package Implement;

import Interface.ChatInterface;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImplement extends UnicastRemoteObject implements ChatInterface {

    public ChatImplement() throws RemoteException {
        chatArea = new JTextArea();
        nombreUsuario = "";
    }

    private JTextArea chatArea;
    private String nombreUsuario;

    @Override
    public void recibirMensaje(String msg) throws RemoteException {
        chatArea.append(msg + "\n");
    }

    @Override
    public JTextArea chat() throws RemoteException {
        return chatArea;
    }

    @Override
    public void registrarNombre(String nombre) throws RemoteException {
        nombreUsuario = nombre;
    }

    @Override
    public String getNombre() throws RemoteException {
        return nombreUsuario;
    }
}
