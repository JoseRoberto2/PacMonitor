
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;



public class ClassicPcapExample extends UnicastRemoteObject implements PacMonitor{

    /**
     * @param args the command line arguments
     */
    
    public ClassicPcapExample() throws RemoteException{
        super();
    }
    
    public void chama(PcapPacketHandler<String> jpacketHandler ) throws RemoteException{
        
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
        StringBuilder errbuf = new StringBuilder(); // For any error msgs  

        /**
         * *************************************************************************
         * First get a list of devices on this system
         * ************************************************************************
         */
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.ERROR || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
            return;
        }

        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : alldevs) {
            String description
                    = (device.getDescription() != null) ? device.getDescription()
                            : "No description available";
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
        }

        PcapIf device = alldevs.get(0); // We know we have atleast 1 device  
        System.out
                .printf("\nChoosing '%s' on your behalf:\n",
                        (device.getDescription() != null) ? device.getDescription()
                                : device.getName());

        /**
         * *************************************************************************
         * Second we open up the selected device
         * ************************************************************************
         */
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000;           // 10 seconds in millis  
        Pcap pcap
                = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return;
        }
        /**
         * *************************************************************************
         * Third we create a packet handler which will receive packets from the
         * libpcap loop.
         * ************************************************************************
         */
        

        /**
         * *************************************************************************
         * Fourth we enter the loop and tell it to capture 10 packets. The loop
         * method does a mapping of pcap.datalink() DLT value to JProtocol ID,
         * which is needed by JScanner. The scanner scans the packet buffer and
         * decodes the headers. The mapping is done automatically, although a
         * variation on the loop method exists that allows the programmer to
         * sepecify exactly which protocol ID to use as the data link type for
         * this pcap interface. 
         *************************************************************************
         */
        int j=0;
        while(j<=50){
        pcap.loop(1, jpacketHandler,"testanto aki");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClassicPcapExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        j++;
        
        }

        /**
         * *************************************************************************
         * Last thing to do is close the pcap handle 
         *************************************************************************
         */
        pcap.close();

    }

    

        
}