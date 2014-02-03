package view;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.Server;
import model.ServerEvent;
import model.ServerHelper;
import model.ServerListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
public class ServerWindow implements ServerListener{
	private JFrame frame;
	private JTextField textField;
	private Server server;
	private JTextArea textarea;
	private JButton btnIniciar, btnDetener;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ServerWindow() {
		SetLookAndFeel();
		initialize();
		
	}
	public void SetLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    
		}
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 418, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(56, 11, 154, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPuerto.setBounds(9, 10, 57, 30);
		frame.getContentPane().add(lblPuerto);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int port = Integer.parseInt(ServerWindow.this.textField.getText());
					server = new Server(port);
					server.AddServerListener(ServerWindow.this);
					btnIniciar.setEnabled(false);
					btnDetener.setEnabled(true);
				} catch (Exception ex) {
					DisplayError("El numero de puerto debe ser un numero entero");
				}
			}
		});
		btnIniciar.setBounds(240, 10, 69, 34);
		frame.getContentPane().add(btnIniciar);
		
		btnDetener = new JButton("Detener");
		btnDetener.setEnabled(false);
		btnDetener.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				server.StopServer();
				btnIniciar.setEnabled(true);
				btnDetener.setEnabled(false);
			}
		});
		btnDetener.setBounds(309, 10, 83, 34);
		frame.getContentPane().add(btnDetener);
		
		textarea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textarea);
		scrollPane.setBounds(10, 53, 382, 197);
		frame.getContentPane().add(scrollPane);
	}
	@Override
	public void OccurredEvent(ServerEvent event) {
		switch (event.getType()) {
		case ServerHelper.Error:
			DisplayError(event.getParam().toString());
			break;
		case ServerHelper.NewUserConnected:
			break;
		case ServerHelper.UserDisconnected:
			break;
		case ServerHelper.DisplayText:
			textarea.append(event.getParam().toString() + "\n");
			break;
		}
	}
	public void DisplayError(String error) {
		JOptionPane.showMessageDialog(frame, error);
	}
}
