package west.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import west.mem.ServiceJoinDialogue;
import west.mem.MemDAO;
import west.mem.IdPassSearchDialogue;

public class CarLogin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6151792609571975253L;
	
	JLabel l_id, l_pwd, l_title;
	public TextField tf_id, tf_pwd;
	JButton b_login, b_gaip, b_search, b_cancle;	
	Container ct;
	JPanel jp1, jp2, jp3, background;
	Image logo;
	
	public CarLogin(){
		super("West 렌터카");
		ct = getContentPane();		
		createPanel();
		createBtn();
		createLabel();
		createTextField();
		setLogo();
		setLayout();
		addBtnETC();
		addPanel();
		addActionListener();
		
		
		
		//setSize(700,500);
		setResizable(false);
		setVisible(true);
		setBounds(600,300,700,500);		
	}	
	
	private void createPanel() {
		
		jp1 = new JPanel();
		jp1.setBackground(new Color(0, 0, 0, 0));
		jp2 = new JPanel();
		jp2.setBackground(new Color(0, 0, 0, 0));
		jp3 = new JPanel();
		jp3.setBackground(new Color(0, 0, 0, 0));
		
		ImageIcon ic  = new ImageIcon("img/carMain.jpg");
		Image img = ic.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
	     ic.setImage(img);
		background = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = -4111349909624554062L;

			public void paintComponent(Graphics g) {              
                g.drawImage(ic.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
	}
	
	private void createBtn() {
		b_login = new JButton("로그인");
		b_gaip = new JButton("회원가입");
		b_search = new JButton("아이디/비밀번호 찾기");
		b_cancle = new JButton("종료");
	}
	
	private void createLabel() {
		l_title = new JLabel("West Car Service");
		l_title.setBackground(new Color(0, 0, 0, 0));
		l_title.setForeground(Color.WHITE);
		l_id= new JLabel("아이디 : ");
		l_id.setBackground(new Color(0, 0, 0, 0));
		l_id.setForeground(Color.WHITE);
		l_pwd= new JLabel("비밀번호 :");		
		l_pwd.setBackground(new Color(0, 0, 0, 0));
		l_pwd.setForeground(Color.WHITE);
		
		
	}

	private void createTextField() {
		tf_id = new TextField(10);	
		tf_pwd = new TextField(10);
		
	}
	
	private void setLayout() {
		tf_pwd.setEchoChar('*');
		jp2.setLayout(new FlowLayout());
		l_title.setFont(new Font("고딕",Font.BOLD,30));
		
	}
	
	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		logo = kit.getImage("img/Caricon.png"); 
		setIconImage(logo); 
	}
	
	private void addBtnETC() {
		jp1.add(l_title);
		jp2.add(l_id);
		jp2.add(tf_id);
		jp2.add(l_pwd);
		jp2.add(tf_pwd);
		jp2.add(b_login);
		jp3.add(b_gaip);
		jp3.add(b_search);
		jp3.add(b_cancle);
	}
	
	private void addPanel() {
		background.add(jp1,BorderLayout.NORTH);
		background.add(jp2,BorderLayout.CENTER);
		background.add(jp3,BorderLayout.SOUTH);
		ct.add(background);
		//ct.add(jp1,BorderLayout.NORTH);
		//ct.add(jp2,BorderLayout.CENTER);
		//ct.add(jp3,BorderLayout.SOUTH);
	}
	
	private void addActionListener() {
		b_gaip.addActionListener(this);
		b_search.addActionListener(this);
		b_cancle.addActionListener(this);
		b_login.addActionListener(this);
//		jtf_Search.addFocusListener(new FocusListener(){
//			@Override
//			public void focusGained(FocusEvent e) {
//				if (e.getComponent() == jtf_Search)
//					jtf_Search.setText("");
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {}
//		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_gaip) {
			new ServiceJoinDialogue();
		}else if(e.getSource()==b_login){	
			MemDAO dao = new MemDAO(this);
			dao.Login();
		}else if(e.getSource()==b_search){
			new IdPassSearchDialogue();
		}else if(e.getSource()==b_cancle){
			System.exit(0);
		}
	}
	
}
