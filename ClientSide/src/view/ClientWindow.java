package view;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import model.User;
import common.JsonConverter;
import common.MessageEvent;
import common.MessageListener;
import common.UserInformation;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class ClientWindow extends MouseAdapter  implements MessageListener{
	private JFrame frame;
	private JTextField txtUsername;
	private JTextField txtIPServer;
	private JTextField txtPort;
	private DefaultListModel<Object> usersmodel;
	private JTabbedPane tabbedPane;
	private HashMap<String, ChatControl> tabs;
	
	private User user;
	private HashMap<String, UserInformation> userslist;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ClientWindow() {
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				user.CloseSession();
			}
		});
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
		panel.add(lblNombreUsuario);
		
		txtUsername = new JTextField();
		txtUsername.setText("Yerson");
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblIpServidor = new JLabel("IP Servidor");
		panel.add(lblIpServidor);
		
		txtIPServer = new JTextField();
		txtIPServer.setText("localhost");
		panel.add(txtIPServer);
		txtIPServer.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		panel.add(lblPuerto);
		
		txtPort = new JTextField();
		txtPort.setText("8000");
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addMouseListener(this);
		panel.add(btnConectar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 10, 10, 5));
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios Conectados");
		panel_1.add(lblUsuariosConectados, BorderLayout.NORTH);
		
		JButton btnAdicionar = new JButton("Adicionar");
		panel_1.add(btnAdicionar, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		usersmodel = new DefaultListModel<>();
		JList<Object> list = new JList<Object>(usersmodel);
		scrollPane.setViewportView(list);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabs = new HashMap<String, ChatControl>();
		ChatControl chatcontrol = new ChatControl();
		tabs.put("ALL", chatcontrol);
		tabbedPane.addTab("Chat Grupal", tabs.get("ALL"));
	}
	public void AddTab(UserInformation user) {
		ChatControl chatcontrol = new ChatControl();
		tabs.put(user.getUserIP(), chatcontrol);
		tabbedPane.addTab(user.getUsername(), tabs.get(user.getUserIP()));
	}
	public void UpdateList(ArrayList<UserInformation> users) {
		usersmodel.clear();
		for (UserInformation userInformation : users) {
			usersmodel.addElement(userInformation.getUsername());
		}
	}
	@Override
	public void MessageReceived(MessageEvent e) {
		if (e.getMessage().getSourceIP().equals("SERVER")) {
			if (e.getMessage().getDestinationIP().equals("USERS")) {
				ArrayList<UserInformation> users = JsonConverter.JsonStringToList(e.getMessage().getText());
				userslist = new HashMap<>();
				for (UserInformation userInformation : users) {
					userslist.put(userInformation.getUserIP(), userInformation);
				}
				UpdateList(users);
			}
		}else {
			if (!tabs.containsKey(e.getMessage().getSourceIP())) {
				AddTab(this.userslist.get(e.getMessage().getSourceIP()));
			}
			ChatControl chatcontrol = tabs.get(e.getMessage().getDestinationIP());
			chatcontrol.AddMessage(this.userslist.get(e.getMessage().getSourceIP()).getUsername(), e.getMessage().getText());
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		String username = this.txtUsername.getText();
		String serverip = this.txtIPServer.getText();
		int port = Integer.parseInt(this.txtPort.getText());
		user = new User(username, serverip, port);
		user.AddMessageListener(this);
		user.StartReadMessages();
	}
}
