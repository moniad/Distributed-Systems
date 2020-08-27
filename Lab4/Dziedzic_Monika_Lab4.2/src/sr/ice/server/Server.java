package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {
    public void run(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // create communicator and object adapter
            communicator = Util.initialize(args);
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

            CustomServantLocator servantLocator = new CustomServantLocator();

            // add wrappers to ASM
            adapter.addServantLocator(servantLocator, "");

            // turn adapter on, start processing events
            adapter.activate();

            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println(e.toString());
            status = 1;
        }
        if (communicator != null) {
            // cleanup
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.toString());
                status = 1;
            }
        }
        System.exit(status);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run(args);
    }
}
