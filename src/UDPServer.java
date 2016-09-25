import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPServer {
	private Date ahora = new Date();
	private int puerto;
	private String mensajeSalida;
	private String mensajeEntrada;
	protected DatagramSocket UDPSocket;
	protected DatagramPacket pkgRecibido;
	protected DatagramPacket pkgEnviado;

	UDPServer(int port) {
		puerto = port;
	}
	
	void start(){
		try {
			UDPSocket = new DatagramSocket(puerto);
			System.out.println("Esperando un cliente...");
			while(true){
				pkgRecibido = new DatagramPacket(new byte[1024],1024);
				UDPSocket.receive(pkgRecibido);
				mensajeEntrada = new String(pkgRecibido.getData());
				System.out.println("Una opción ha sido solicitada: "+mensajeEntrada);
				SimpleDateFormat formateando;
				
				switch (mensajeEntrada.charAt(0)) {
				case 'f':
					formateando = new SimpleDateFormat("'Hoy es' EEEEEEEEE dd 'de' MMMMM 'de' yyyy");	
					mensajeSalida = formateando.format(ahora);
					break;
				case 'h':
					formateando = new SimpleDateFormat("hh:mm:ss");	
					mensajeSalida = "Son las "+ formateando.format(ahora);
					break;
				case 'd':
					formateando = new SimpleDateFormat("EEEE");	
					mensajeSalida = "Hoy es "+ formateando.format(ahora);
					break;
				default:
					mensajeSalida = "Opción inválida";
					break;
				}					
				
				byte mensajeEnviar[] = new byte[1024];
				System.out.println(mensajeSalida);
				mensajeEnviar = mensajeSalida.getBytes();
				pkgEnviado = new DatagramPacket(mensajeEnviar,mensajeEnviar.length,pkgRecibido.getAddress(),pkgRecibido.getPort());
				UDPSocket.send(pkgEnviado);
			}
		}catch(Exception e){ 	
			e.printStackTrace();
		}
	}
	void finalizar(){
		try{
			UDPSocket.close();
			System.out.println("La conexión ha finalizado");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
