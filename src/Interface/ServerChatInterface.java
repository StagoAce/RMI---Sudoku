package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerChatInterface extends Remote {

    public void RegistrarCliente(ChatInterface cliente) throws RemoteException;
    public void cerrarSesionCLiente(String name) throws RemoteException;
    public void enviarMensaje(String msg) throws RemoteException;
    public String obtenerAsignacionNombre() throws RemoteException;
}
