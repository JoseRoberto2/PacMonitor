
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
public class PacServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(1169);
        reg.rebind("PacServer",new ClassicPcapExample());
        System.out.println("Servidor funcionando.!.!.!");
    }
    
}
