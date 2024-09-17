package Server;

import Implement.ServerChatImplement;
import Implement.SudokuImplement;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SudokuServer {
    public static void main(String []args) throws RemoteException{
        Registry reg= LocateRegistry.createRegistry(1099);
        SudokuImplement sudokuImplement=new SudokuImplement();
        ServerChatImplement serverChat = new ServerChatImplement();

        //nombre objeto
        reg.rebind("Sudoku", sudokuImplement);
        reg.rebind("chat", serverChat);
        System.out.println("Servidor iniciado");
    }
}
