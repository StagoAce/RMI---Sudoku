package Implement;

import Interface.ChatInterface;
import Interface.ServerChatInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerChatImplement extends UnicastRemoteObject implements ServerChatInterface {

    private List<ChatInterface> usuarios;
    private int nUsuario;

    public ServerChatImplement() throws RemoteException {
        usuarios = new ArrayList<>();
        nUsuario = 0;
    }

    @Override
    public void RegistrarCliente(ChatInterface cliente) throws RemoteException {
        usuarios.add(cliente);
    }

    @Override
    public void cerrarSesionCLiente(String name) throws RemoteException {
        Iterator<ChatInterface> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            ChatInterface usuario = iterator.next();
            if (usuario.getNombre().equals(name)) {
                iterator.remove();
                break;
            }
        }
    }


    @Override
    public void enviarMensaje(String msg) throws RemoteException {
        for (ChatInterface usuario: usuarios) {
            usuario.recibirMensaje(msg);
        }
    }

    @Override
    public String obtenerAsignacionNombre() throws RemoteException {
        nUsuario+=1;
        return "User"+(nUsuario);
    }
}
