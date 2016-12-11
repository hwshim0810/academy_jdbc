package west.mem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServiceJoinDialogue extends JDialog implements ActionListener, KeyListener {
	private static final long serialVersionUID = -235882319625547411L;
	
	String id, pwd, pwdChk, name, license, tel, addr;
	String email = "";
	int age;

	MemDAO mDao;

	JPanel pw = new JPanel(new GridLayout(8, 1));
	JPanel pc = new JPanel(new GridLayout(8, 1));
	JPanel ps = new JPanel();
	JLabel l_id = new JLabel(" 아이디");
	JLabel l_pwd = new JLabel(" 비밀번호");
	JLabel l_name = new JLabel(" 이름");
	JLabel l_license = new JLabel(" 면허번호");
	JLabel l_tel = new JLabel(" 전화번호");
	JLabel l_addr = new JLabel(" 주소");
	JLabel l_age = new JLabel(" 나이");
	JLabel l_email = new JLabel(" 이메일");

	JTextField tf_id = new JTextField();
	JTextField tf_pwd = new JTextField();
	JTextField tf_name = new JTextField();
	JTextField tf_license = new JTextField();
	JTextField tf_tel = new JTextField();
	JTextField tf_addr = new JTextField();
	JTextField tf_age = new JTextField();
	JTextField tf_email = new JTextField();

	JPanel idCkP = new JPanel(new BorderLayout());
	JButton idCkBtn = new JButton("중복확인");
	JButton gaip = new JButton("가입");
	JButton cancle = new JButton("취소");

	public ServiceJoinDialogue() {
		
		pw.add(l_id);
		pw.add(l_pwd);
		pw.add(l_name);
		pw.add(l_license);
		pw.add(l_tel);
		pw.add(l_addr);
		pw.add(l_age);
		pw.add(l_email);

		idCkP.add(tf_id, "Center");
		idCkP.add(idCkBtn, "East");
		pc.add(idCkP);
		pc.add(tf_pwd);
		pc.add(tf_name);
		pc.add(tf_license);
		pc.add(tf_tel);
		pc.add(tf_addr);
		pc.add(tf_age);
		pc.add(tf_email);
		ps.add(gaip);
		ps.add(cancle);

		add(pw, "West");
		add(pc, "Center");
		add(ps, "South");

		setTitle("회원가입");
		setBounds(1200,300,260,330);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		gaip.addActionListener(this);
		cancle.addActionListener(this);// 취소 이벤트 등록
		idCkBtn.addActionListener(this);// id중복 체크 이벤트 등록
		tf_id.addKeyListener(this);
		tf_name.addKeyListener(this);
		tf_tel.addKeyListener(this);
		tf_age.addKeyListener(this);
		mDao = new MemDAO();
	}

	
	//TODO: 수정했는지 긴가민가
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();
		if (btnLabel.equals("가입")) {
			String id = tf_id.getText().trim();
			String pwd = tf_pwd.getText().trim();
			String name = tf_name.getText().trim();
			String license = tf_license.getText().trim();
			String tel = tf_tel.getText().trim();
			String addr = tf_addr.getText().trim();
			if(tf_age.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "입력되지 않은 값이 있습니다.");
				return;
			}
			int age = Integer.parseInt(tf_age.getText().trim());
			String email = tf_email.getText().trim();
			mDao.loginCheck(this);
			MemDTO dto = new MemDTO(id, pwd, name, license, tel, addr, age, email);
			mDao.MemInsert(dto);
			dispose();
		} else if (btnLabel.equals("취소")) {
			dispose();
		} else if (btnLabel.equals("중복확인")) {
			if (tf_id.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(idCkBtn, "먼저 아이디를 입력하세요!");
				tf_id.requestFocus();
				return;
			}
			
			boolean duplicate = mDao.isDuplicate(tf_id.getText().trim());			
			if (duplicate) {
				JOptionPane.showMessageDialog(idCkBtn, "존재하는 아이디 입니다!");
				tf_id.setText("");
				tf_id.requestFocus();
				return;
			} else if(!duplicate){
				JOptionPane.showMessageDialog(idCkBtn, "사용가능한 아이디 입니다!");
			}

		}
	}

	public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);
	}
	@Override
	public void keyPressed(KeyEvent arg0) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent ke) {
		JTextField jtf = (JTextField) ke.getSource();
		if (jtf == tf_id) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
					|| (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z')
					|| (ke.getKeyChar() >= 'A' && ke.getKeyChar() <= 'Z') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(tf_id, "영어,숫자만 입력할 수 있습니다.");
				tf_id.setText("");
				tf_id.requestFocus();
			}
		} else if (jtf == tf_name) {
			if (!((ke.getKeyChar() >= 'ㄱ' && ke.getKeyChar() <= 'ㅎ')
					|| (ke.getKeyChar() >= 'ㅏ' && ke.getKeyChar() >= 'ㅣ')
					|| (ke.getKeyChar() >= 'a' && ke.getKeyChar() >= 'z')
					|| (ke.getKeyChar() >= 'A' && ke.getKeyChar() >= 'Z')
					|| ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(tf_name, "한글과 영어만 입력할 수 있습니다..");
				tf_name.setText("");
				tf_name.requestFocus();
			}
		} else if (jtf == tf_license) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
					|| ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(tf_license, "숫자만 입력할 수 있습니다.");
				tf_license.setText("");
				tf_license.requestFocus();
			}
		}else if (jtf == tf_tel) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
					|| ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(tf_tel, "숫자만 입력할 수 있습니다.");
				tf_tel.setText("");
				tf_tel.requestFocus();
			}
		}else if (jtf == tf_age) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
					|| ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(tf_age, "숫자만 입력할 수 있습니다.");
				tf_age.setText("");
				tf_age.requestFocus();
			}
		}
	}

}