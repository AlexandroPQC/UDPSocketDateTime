import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

public class UDPClient {
	private int puerto;
	private String mensajeLeer;
	private String recibido;
	protected InetAddress servidorDestino;
	protected DatagramSocket socketUDP;
	protected DatagramPacket paqueteRecibido;
	protected DatagramPacket paqueteAEnviar;
	private byte mensaje[];
	private byte respuesta[];
	
	UDPClient(String host,int port) throws UnknownHostException {
		servidorDestino = InetAddress.getByName(host);
		puerto = port;
	}
	
	void iniciar() throws IOException{
		Scanner leer = new Scanner(System.in);
		socketUDP = new DatagramSocket();
		System.out.println("---Hora del Servidor---");
		System.out.println("\n Elija una opción:\n f: Fecha actual \n h: Hora actual \n d: Día de la semana");
		mensajeLeer = leer.nextLine();
		mensaje = new byte[1024];
		mensaje = mensajeLeer.getBytes();
		paqueteAEnviar = new DatagramPacket(mensaje, mensaje.length, servidorDestino, puerto);
		socketUDP.send(paqueteAEnviar);
		respuesta = new byte[1024];
		
		paqueteRecibido = new DatagramPacket(respuesta, respuesta.length);
		socketUDP.receive(paqueteRecibido);
		recibido = new String(paqueteRecibido.getData());
		System.out.println("Respuesta del servidor : "+recibido);
		finalizar();	
	}
	
	void finalizar(){
		try {
			socketUDP.close();
			System.out.println("La conexion ha sido finalizada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
