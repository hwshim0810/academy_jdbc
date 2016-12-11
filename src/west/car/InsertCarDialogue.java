package west.car;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import west.res.ResDAO;
import west.util.SqlUtil;
import west.view.AdminView;

@SuppressWarnings("serial")
public class InsertCarDialogue extends JDialog implements ActionListener, WindowListener {

	JPanel pn_Center, pnCenter_North, pnCenter_NorthRight, pnCenter_NorthLeft, pn_North, pn_South;

	JLabel lb_Cid, lb_Cname, lb_Price, lb_Res, lb_ResDate;

	JTextField jtf_Cid, jtf_Cname, jtf_Price, jtf_Res, jtf_ResDate;

	JButton btn_Confirm, btn_Cancel;

	Image logo;

	CarDAO cDao = new CarDAO();
	ResDAO rDao = new ResDAO();
	CarDTO dto = new CarDTO();
	AdminView frame;
	

	public InsertCarDialogue(AdminView frame, ActionEvent ae) {
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
		setBounds(1200,150, 300, 300);
		setResizable(false);
		setVisible(true);		
		setTitle("차량추가");
	}

	private void createPanel() {
		setLayout(new BorderLayout());
		pn_North = new JPanel(new FlowLayout());
		pn_South = new JPanel(new FlowLayout());
		pn_Center = new JPanel(new BorderLayout());

		pnCenter_NorthRight = new JPanel(new GridLayout(5, 1));
		pnCenter_NorthLeft = new JPanel(new GridLayout(5, 1));
	}

	private void createBtn(String btnName) {
		btn_Cancel = new JButton("취소");

		if (btnName == "수정")
			btn_Confirm = new JButton(btnName);
		else
			btn_Confirm = new JButton(btnName);
	}

	private void createLabel() {
		lb_Cid = new JLabel("차량번호");
		lb_Cname = new JLabel("차량명");
		lb_Price = new JLabel("가격");
		lb_Res = new JLabel("예약여부");
		lb_ResDate = new JLabel("등록일자");
	}

	private void createTextField() {
		jtf_Cid = new JTextField();
		jtf_Cname = new JTextField();
		jtf_Price = new JTextField();
		jtf_Res = new JTextField(1);
		jtf_ResDate = new JTextField(8);
	}

	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		logo = kit.getImage("img/Caricon.png");
		setIconImage(logo);
	}

	private void addBtnETC() {
		pn_South.add(btn_Confirm);
		pn_South.add(btn_Cancel);
	}

	private void addTextField() {
		pnCenter_NorthLeft.add(lb_Cid);
		pnCenter_NorthLeft.add(lb_Cname);
		pnCenter_NorthRight.add(jtf_Cid);
		pnCenter_NorthRight.add(jtf_Cname);

		pnCenter_NorthLeft.add(lb_Price);
		pnCenter_NorthLeft.add(lb_Res);
		pnCenter_NorthLeft.add(lb_ResDate);

		pnCenter_NorthRight.add(jtf_Price);
		pnCenter_NorthRight.add(jtf_Res);
		pnCenter_NorthRight.add(jtf_ResDate);

	}

	private void addPanel() {
		pn_Center.add(pnCenter_NorthLeft, BorderLayout.WEST);
		pn_Center.add(pnCenter_NorthRight, BorderLayout.CENTER);

		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}
	

	private void setTextField(AdminView frame) {
		jtf_Cid.setText("");
		jtf_Cname.setText("");
		jtf_Price.setText("");
		jtf_Res.setText("");
		jtf_ResDate.setText("");
	}

	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_Cancel.addActionListener(this);
		this.addWindowListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "추가") {
			String cid = jtf_Cid.getText().trim();
			String cname = jtf_Cname.getText().trim();
			int price = Integer.parseInt(jtf_Price.getText().trim());
			String res = jtf_Res.getText().trim();
			java.sql.Date resdate = rDao.toDate(jtf_ResDate.getText().trim());
			CarDTO dto = new CarDTO(cid, cname, price, res, resdate);
			int affectedRow = cDao.insert(dto);
			if(affectedRow ==1){				
				JOptionPane.showMessageDialog(this
						, "입력되었습니다.", "확인"
						, JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center
						, SqlUtil.CAR_TOTAL);
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
}