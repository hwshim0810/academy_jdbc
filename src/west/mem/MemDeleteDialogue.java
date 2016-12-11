package west.mem;

import java.awt.GridLayout;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import west.res.ResDAO;
import west.util.SqlUtil;
import west.view.AdminView;

@SuppressWarnings("serial")
public class MemDeleteDialogue extends JDialog 
						implements ActionListener, WindowListener {
	JPanel pw;
	JPanel pc;
	JPanel ps;
	
	JLabel lb_Mid, lb_Pwd, lb_Mname, lb_License, lb_Tel, lb_Addr, lb_Age, lb_Email;
	
	JTextField jtf_Mid, jtf_Pwd, jtf_Mname, jtf_License, jtf_Tel, jtf_Addr, jtf_Age, jtf_Email;
	
	JButton btn_Confirm, btn_Cancel;

	Image logo;
	
	ResDAO rDao = new ResDAO();
	MemDTO dto = new MemDTO();
	MemDAO dao = new MemDAO();
	static AdminView frame;
	static ActionEvent ae;
	
	public static final int OPENINNER = 5;
	public static final int NOTOPENDIA = 6;
	public static int innerDiaState;
	
	MemDeleteDialogue(Object[] obj) {
		super(frame);
		
		createPanel();
		createBtn(ae.getActionCommand());
		createLabel();
		createTextField();
		setLogo();
		addBtnETC();
		addActionListener();
		addTextField();
		addPanel();
		setTextField(frame, ae);
		updateTextField(obj);

		setBounds(1200,150,400, 500);
		setVisible(true);
		innerDiaState = NOTOPENDIA;
	}
	public MemDeleteDialogue(AdminView frame, ActionEvent ae) {
		super(frame);
		MemDeleteDialogue.frame = frame;
		MemDeleteDialogue.ae = ae;
		
		createPanel();
		createBtn(ae.getActionCommand());
		createLabel();
		createTextField();
		setLogo();
		addBtnETC();
		addActionListener();
		addTextField();
		addPanel();
		setTextField(frame, ae);
		
		if(ae.getActionCommand().equals("수정")){
			setTitle("회원수정");
		}else{
			setTitle("회원삭제");
		}
		
		setBounds(1200,150,260,300);
		setVisible(true);
		MemDeleteDialogue.frame = frame;
		innerDiaState = NOTOPENDIA;
	}
	
	private void createPanel() {
		pw = new JPanel(new GridLayout(8, 1));
		pc = new JPanel(new GridLayout(8, 1));
		ps = new JPanel();
	}
	
	private void createBtn(String btnName) {
		btn_Cancel = new JButton("취소");
		
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
		
		pc.add(jtf_Mid);
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
	
	private void setTextField(AdminView frame, ActionEvent ae) {
		int row = frame.jt_Center.getSelectedRow();
		
		jtf_Mid.setText(frame.jt_Center.getValueAt(row, 0).toString());
		jtf_Pwd.setText(frame.jt_Center.getValueAt(row, 1).toString());
		jtf_Mname.setText(frame.jt_Center.getValueAt(row, 2).toString());
		jtf_License.setText(frame.jt_Center.getValueAt(row, 3).toString());
		jtf_Tel.setText(frame.jt_Center.getValueAt(row, 4).toString());
		jtf_Addr.setText(frame.jt_Center.getValueAt(row, 5).toString());
		jtf_Age.setText(frame.jt_Center.getValueAt(row, 6).toString());
		jtf_Email.setText(frame.jt_Center.getValueAt(row, 7).toString());
		
		setTextFieldEdit(ae);
		setTextFieldAlign();
	}
	
	private void setTextFieldAlign() {
		jtf_Mid.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Pwd.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Mname.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_License.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Tel.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Addr.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Age.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Email.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private void setTextFieldEdit(ActionEvent ae) {
		jtf_Mid.setEditable(false);
		jtf_Pwd.setEditable(false);
		jtf_Mname.setEditable(false);
		jtf_License.setEditable(false);
		jtf_Tel.setEditable(false);
		jtf_Addr.setEditable(false);
		jtf_Age.setEditable(false);
		jtf_Email.setEditable(false);
		
		if (ae.getActionCommand() == "삭제") {
			jtf_Mid.setEditable(false);
			jtf_Pwd.setEditable(false);
			jtf_Mname.setEditable(false);
			jtf_License.setEditable(false);
			jtf_Tel.setEditable(false);
			jtf_Addr.setEditable(false);
			jtf_Age.setEditable(false);
			jtf_Email.setEditable(false);
			
		}
		if (ae.getActionCommand() == "수정") {
			jtf_Mid.setEditable(false);
			jtf_Pwd.setEditable(true);
			jtf_Mname.setEditable(true);
			jtf_License.setEditable(true);
			jtf_Tel.setEditable(true);
			jtf_Addr.setEditable(true);
			jtf_Age.setEditable(true);
			jtf_Email.setEditable(true);
			
		}
	}
	public void updateTextField(Object[] obj) {
		jtf_Mid.setText(obj[0].toString());
		jtf_Pwd.setText(obj[1].toString());
	}
	
	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_Cancel.addActionListener(this);
		this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "수정") {
			String mid = jtf_Mid.getText().trim();
			String pwd = jtf_Pwd.getText().trim();
			String mname = jtf_Mname.getText().trim();
			String license = jtf_License.getText().trim();
			String tel = jtf_Tel.getText().trim();
			String addr = jtf_Addr.getText().trim();
			int age = Integer.parseInt(jtf_Age.getText().trim());
			String email = jtf_Email.getText().trim();
			MemDTO dto = new MemDTO(mid, pwd, mname, license, tel, addr, age, email);
			int affectedRow = dao.update(dto);
			if(affectedRow ==1){
				JOptionPane.showMessageDialog(this, "수정되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center
						, SqlUtil.MEM_TOTAL);
			}
			
		} else if (ae.getActionCommand() == "취소") {
			AdminView.dialogueState = AdminView.NOTOPEN;
			dispose();
		} else if (ae.getActionCommand() == "삭제") {
			int val = JOptionPane.showConfirmDialog(this, 
					"회원ID : " + this.jtf_Mid.getText() +
					"\n회원이 삭제됩니다.\n삭제하시겠습니까?", 
					"회원삭제", JOptionPane.YES_NO_OPTION);
			
			if (val == JOptionPane.YES_OPTION) {
				dao.delete(jtf_Mid.getText());
				JOptionPane.showMessageDialog(this, 
						"삭제되었습니다.", "확인", 
						JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center
						, SqlUtil.MEM_TOTAL);
			}
			
		} else if (ae.getActionCommand() == "취소") {
			AdminView.dialogueState = AdminView.NOTOPEN;
			dispose();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		AdminView.dialogueState = AdminView.NOTOPEN;
	}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
}
