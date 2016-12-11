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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
public class CarDialogue extends JDialog 
						implements ActionListener, WindowListener {
	JPanel pn_Center, pnCenter_North, pnCenter_NorthRight, 
		pnCenter_NorthLeft, pn_North, pn_South;
	
	JLabel lb_Cid, lb_Cname, lb_Price, lb_Res, lb_ResDate;
	
	JTextField jtf_Cid, jtf_Cname, jtf_Price, jtf_Res, jtf_ResDate;
	
	JButton btn_Confirm, btn_Cancel;
	
	Image logo;
	
	ResDAO rDao = new ResDAO();
	CarDTO dto = new CarDTO();
	CarDAO dao = new CarDAO();
	static AdminView frame;
	static ActionEvent ae;
	
	public static final int OPENINNER = 5;
	public static final int NOTOPENDIA = 6;
	public static int innerDiaState;
	
	CarDialogue(Object[] obj) {
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

		setBounds(1300,150, 260,330);
		setResizable(false);
		setVisible(true);
		innerDiaState = NOTOPENDIA;
	}
	
	public CarDialogue(AdminView frame, ActionEvent ae) {
		super(frame);
		CarDialogue.frame = frame;
		CarDialogue.ae = ae;
		
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
		
		if (ae.getActionCommand().equals("수정")){
			setTitle("차량수정");			
		} else if(ae.getActionCommand().equals("삭제")){
			setTitle("차량삭제");
		}
		setBounds(1300,150, 260,330);
		setResizable(false);
		setVisible(true);
		CarDialogue.frame = frame;
		innerDiaState = NOTOPENDIA;
	}
	private java.sql.Date toDate(String date) {
		java.sql.Date sqlSDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			java.util.Date Date = format.parse(date);
			sqlSDate = new java.sql.Date(Date.getTime());
		} catch (ParseException pe) {
			System.err.println("날짜변환예외");
		}
		return sqlSDate;
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
	
	private void setTextField(AdminView frame, ActionEvent ae) {
		int row = frame.jt_Center.getSelectedRow();
		
		jtf_Cid.setText(frame.jt_Center.getValueAt(row, 0).toString());
		jtf_Cname.setText(frame.jt_Center.getValueAt(row, 1).toString());
		jtf_Price.setText(frame.jt_Center.getValueAt(row, 2).toString());
		jtf_Res.setText(frame.jt_Center.getValueAt(row, 3).toString());
		jtf_ResDate.setText(frame.jt_Center.getValueAt(row, 4).toString());
		
		setTextFieldEdit(ae);
		setTextFieldAlign();
	}
	
	private void setTextFieldAlign() {
		jtf_Cid.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Cname.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Price.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_Res.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_ResDate.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private void setTextFieldEdit(ActionEvent ae) {
		jtf_Cid.setEditable(false);
		jtf_Cname.setEditable(false);
		jtf_Price.setEditable(false);
		jtf_Res.setEditable(false);
		jtf_ResDate.setEditable(false);
		
		if (ae.getActionCommand() == "삭제") {
			jtf_Cid.setEditable(false);
			jtf_Cname.setEditable(false);
			jtf_Price.setEditable(false);
			jtf_Res.setEditable(false);
			jtf_ResDate.setEditable(false);
			
		}
		if (ae.getActionCommand() == "수정") {
			jtf_Cid.setEditable(false);
			jtf_Cname.setEditable(true);
			jtf_Price.setEditable(true);
			jtf_Res.setEditable(true);
			jtf_ResDate.setEditable(true);
		}
	}
	public void updateTextField(Object[] obj) {
		jtf_Cid.setText(obj[0].toString());
		jtf_Cname.setText(obj[1].toString());
	}
	
	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_Cancel.addActionListener(this);
		this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "수정") {
			String cid = jtf_Cid.getText().trim();
			String cname = jtf_Cname.getText().trim();
			int price = Integer.parseInt(jtf_Price.getText().trim());
			String res = jtf_Res.getText().trim();
			java.sql.Date resdate = toDate(jtf_ResDate.getText().trim());
			CarDTO dto = new CarDTO(cid, cname, price, res, resdate);
			int affectedRow = dao.update(dto);
			if(affectedRow ==1){
				JOptionPane.showMessageDialog(this, "수정되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center
						, SqlUtil.CAR_TOTAL);
			}
			
		} else if (ae.getActionCommand() == "취소") {
			AdminView.dialogueState = AdminView.NOTOPEN;
			dispose();
		} else if (ae.getActionCommand() == "삭제") {
			int val = JOptionPane.showConfirmDialog(this, 
						"차량정보가 삭제됩니다. 삭제하시겠습니까?", 
						"차량삭제", JOptionPane.YES_NO_OPTION);
			
			if (val == JOptionPane.YES_OPTION) {
				dao.delete(jtf_Cid.getText());
				JOptionPane.showMessageDialog(this, 
						"삭제되었습니다.", "확인", 
						JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
				rDao.setTableData(frame.dt_Center, frame.jt_Center
						, SqlUtil.CAR_TOTAL);
			}
			
		} else if (ae.getActionCommand() == "취소") {
			AdminView.dialogueState = AdminView.NOTOPEN;
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
