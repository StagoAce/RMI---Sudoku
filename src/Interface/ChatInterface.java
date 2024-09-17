package Interface;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    public void recibirMensaje(String msg) throws RemoteException;
    public JTextArea chat() throws RemoteException;
    public void registrarNombre(String nombre) throws RemoteException;
    public String getNombre() throws RemoteException;
}
