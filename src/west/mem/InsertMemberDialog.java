package west.mem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import west.res.ResDAO;
import west.util.SqlUtil;
import west.view.AdminView;

@SuppressWarnings("serial")
public class InsertMemberDialog extends JDialog implements ActionListener, WindowListener, KeyListener, SqlUtil {

	JPanel pw;
	JPanel pc;
	JPanel ps;
	JPanel idCkP;

	JLabel lb_Mid, lb_Pwd, lb_Mname, lb_License, lb_Tel, lb_Addr, lb_Age, lb_Email;

	JTextField jtf_Mid, jtf_Pwd, jtf_Mname, jtf_License, jtf_Tel, jtf_Addr, jtf_Age, jtf_Email;

	JButton btn_Confirm, btn_Cancel, idCkBtn;

	Image logo;

	ResDAO rDao = new ResDAO();
	MemDAO mDao = new MemDAO();
	MemDTO dto = new MemDTO();
	AdminView frame;

	public InsertMemberDialog(AdminView frame, ActionEvent ae) {
		super(frame);
		this.frame = frame;

		createPanel();
		createBtn(ae.getActionCommand());
		createLabel();
		createTextField();
		setLogo();
		addBtnETC();
		addActionListener();
		addTextField();
		addPanel();
		setTextField(frame);
		setTitle("회원추가");
		setBounds(1200,150,260,300);
		setResizable(false);
		setVisible(true);
	}

	private void createPanel() {
		pw = new JPanel(new GridLayout(8, 1));
		pc = new JPanel(new GridLayout(8, 1));
		ps = new JPanel();
		idCkP = new JPanel(new BorderLayout());

	}

	private void createBtn(String btnName) {
		btn_Cancel = new JButton("취소");
		idCkBtn = new JButton("중복확인");

		if (btnName == "수정")
			btn_Confirm = new JButton(btnName);
		else
			btn_Confirm = new JButton(btnName);
	}

	private void createLabel() {
		lb_Mid = new JLabel("아이디");
		lb_Pwd = new JLabel("비밀번호");
		lb_Mname = new JLabel("이름");
		lb_License = new JLabel("면허번호");
		lb_Tel = new JLabel("전화번호");
		lb_Addr = new JLabel("주소");
		lb_Age = new JLabel("나이");
		lb_Email = new JLabel("이메일");
	}

	private void createTextField() {
		jtf_Mid = new JTextField();
		jtf_Pwd = new JTextField();
		jtf_Mname = new JTextField();
		jtf_License = new JTextField();
		jtf_Tel = new JTextField();
		jtf_Addr = new JTextField();
		jtf_Age = new JTextField();
		jtf_Email = new JTextField();
	}

	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		logo = kit.getImage("img/Caricon.png");
		setIconImage(logo);
	}

	private void addBtnETC() {
		ps.add(btn_Confirm);
		ps.add(btn_Cancel);
	}

	private void addTextField() {
		pw.add(lb_Mid);
		pw.add(lb_Pwd);
		pw.add(lb_Mname);
		pw.add(lb_License);
		pw.add(lb_Tel);
		pw.add(lb_Addr);
		pw.add(lb_Age);
		pw.add(lb_Email);

		idCkP.add(jtf_Mid, "Center");
		idCkP.add(idCkBtn, "East");
		pc.add(idCkP);
		pc.add(jtf_Pwd);
		pc.add(jtf_Mname);
		pc.add(jtf_License);
		pc.add(jtf_Tel);
		pc.add(jtf_Addr);
		pc.add(jtf_Age);
		pc.add(jtf_Email);

	}

	private void addPanel() {
		add(pw, "West");
		add(pc, "Center");
		add(ps, "South");
	}

	private void setTextField(AdminView frame) {
		jtf_Mid.setText("");
		jtf_Pwd.setText("");
		jtf_Mname.setText("");
		jtf_License.setText("");
		jtf_Tel.setText("");
		jtf_Addr.setText("");
		jtf_Age.setText("");
		jtf_Email.setText("");
	}

	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_Cancel.addActionListener(this);
		idCkBtn.addActionListener(this);
		this.addWindowListener(this);
		jtf_Mid.addKeyListener(this);
		jtf_Pwd.addKeyListener(this);
		jtf_License.addKeyListener(this);
		jtf_Mname.addKeyListener(this);
		jtf_Tel.addKeyListener(this);
		jtf_Age.addKeyListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String btnLabel = ae.getActionCommand();
		if (ae.getActionCommand() == "추가") {
			String mid = jtf_Mid.getText().trim();
			String pwd = jtf_Pwd.getText().trim();
			String mname = jtf_Mname.getText().trim();
			String license = jtf_License.getText().trim();
			String tel = jtf_Tel.getText().trim();
			String addr = jtf_Addr.getText().trim();
			int age;
			if(jtf_Age.getText().trim().equals("")){
				age = -1;
			}
			age = Integer.parseInt(jtf_Age.getText().trim());
			String email = jtf_Email.getText().trim();
			mDao.loginCheck(this);
			MemDTO dto = new MemDTO(mid, pwd, mname, license, tel, addr, age, email);
			int affectedRow = mDao.MemInsert(dto);
			if (affectedRow == 1) {
				JOptionPane.showMessageDialog(this, "입력되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center, SqlUtil.MEM_TOTAL);			}

		} else if (ae.getActionCommand() == "취소") {
			AdminView.dialogueState = AdminView.NOTOPEN;
			dispose();
		} else if (btnLabel.equals("중복확인")) {
			if (jtf_Mid.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(idCkBtn, "먼저 아이디를 입력하세요!");
				jtf_Mid.requestFocus();
				return;
			}

			boolean duplicate = mDao.isDuplicate(jtf_Mid.getText().trim());
			if (duplicate) {
				JOptionPane.showMessageDialog(idCkBtn, "존재하는 아이디 입니다!");
				jtf_Mid.setText("");
				jtf_Mid.requestFocus();
				return;
			} else if (!duplicate) {
				JOptionPane.showMessageDialog(idCkBtn, "사용가능한 아이디 입니다!");
			}

		}
	}
	public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		AdminView.dialogueState = AdminView.NOTOPEN;
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		JTextField jtf = (JTextField) ke.getSource();
		if (jtf == jtf_Mid) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
					|| (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z')
					|| (ke.getKeyChar() >= 'A' && ke.getKeyChar() <= 'Z') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(jtf_Mid, "영어,숫자만 입력할 수 있습니다.");
				jtf_Mid.setText("");
				jtf_Mid.requestFocus();
			}
		} else if (jtf == jtf_Mname) {
			if (!((ke.getKeyChar() >= 'ㄱ' && ke.getKeyChar() <= 'ㅎ')
					|| (ke.getKeyChar() >= 'ㅏ' && ke.getKeyChar() >= 'ㅣ')
					|| (ke.getKeyChar() >= 'a' && ke.getKeyChar() >= 'z')
					|| (ke.getKeyChar() >= 'A' && ke.getKeyChar() >= 'Z') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(jtf_Mname, "한글과 영어만 입력할 수 있습니다..");
				jtf_Mname.setText("");
				jtf_Mname.requestFocus();
			}
		} else if (jtf == jtf_License) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(jtf_License, "숫자만 입력할 수 있습니다.");
				jtf_License.setText("");
				jtf_License.requestFocus();
			}
		} else if (jtf == jtf_Tel) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(jtf_Tel, "숫자만 입력할 수 있습니다.");
				jtf_Tel.setText("");
				jtf_Tel.requestFocus();
			}
		} else if (jtf == jtf_Age) {
			if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\b')) {
				ke.consume();
				JOptionPane.showMessageDialog(jtf_Age, "숫자만 입력할 수 있습니다.");
				jtf_Age.setText("");
				jtf_Age.requestFocus();
			}
		}
	}
}