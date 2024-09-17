package Interface;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SudokuInterface extends Remote {

    public JTable creacionTable(int tamañoTabla) throws RemoteException;


}
