
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
public interface PacMonitor extends Remote{
    
    //public static void main(String[] args) throws InterruptedException;
    public void chama(PcapPacketHandler<String> jpacketHandler ) throws RemoteException;
}
