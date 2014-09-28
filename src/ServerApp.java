import com.gs.server.GameServer;


public class ServerApp {
	public static void main(String[] args) {
		new Thread(new GameServer(8001,843)).start();
	}
}
