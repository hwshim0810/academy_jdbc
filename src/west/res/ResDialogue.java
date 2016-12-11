package west.res;
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
import javax.swing.SwingConstants;

import west.view.AdminView;

public class ResDialogue extends JDialog 
						implements ActionListener, WindowListener {
	private static final long serialVersionUID = 6100276035967062393L;

	JPanel pn_Center, pnCenter_North, pnCenter_NorthRight, 
		pnCenter_NorthLeft,	pnCenter_South, pnCenter_SouthRight, 
		pnCenter_SouthMid, pnCenter_SouthLeft, pn_North, pn_South, 
		pnCenter_SouthUp, pnCenter_SouthDown, pnCenter_East;
	
	JLabel lb_Info, lb_Rid, lb_Cid, lb_Mid, lb_Cname, lb_ResDate, 
		lb_StartDate, lb_EndDate, lb_Year, 
		lb_Month, lb_Day, lb_Blank, lb_Blank2, lb_Blank3, lb_Blank4;
	
	JTextField jtf_Rid, jtf_Cid, jtf_Mid, jtf_Cname, jtf_ResYear,
		jtf_ResMonth, jtf_ResDay, jtf_StYear, jtf_StMonth,
		jtf_StDay, jtf_EdYear, jtf_EdMonth,	jtf_EdDay;
	
	JButton btn_Confirm, btn_Cancel, btn_CarSearch;
	public static String cid;
	Image logo;
	
	ResDAO rDao;
	static AdminView frame;
	static ActionEvent ae;
	
	public static final int OPENINNER = 5;
	public static final int NOTOPENDIA = 6;
	public static int innerDiaState;
	
	ResDialogue(Object[] obj) {
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
		if (ae.getActionCommand() == "삭제") pnCenter_East.setVisible(false);
		setBounds(1200,150, 250, 250);
		setResizable(false);
		setSize(300, 350);
		setVisible(true);
		innerDiaState = NOTOPENDIA;
	}
	
	public ResDialogue(AdminView frame, ActionEvent ae) {
		super(frame);
		ResDialogue.frame = frame;
		ResDialogue.ae = ae;
		
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
			setTitle("예약수정");			
		}else{
			setTitle("예약삭제");
		}
		if (ae.getActionCommand() == "삭제") pnCenter_East.setVisible(false);
		setBounds(1200,150,300,350);
		setResizable(false);
		setVisible(true);
		ResDialogue.frame = frame;
		innerDiaState = NOTOPENDIA;
		
		cid=jtf_Cid.getText();
	}
	
	private void createPanel() {
		setLayout(new BorderLayout());
		pn_North = new JPanel(new FlowLayout());
		pn_South = new JPanel(new FlowLayout());
		pn_Center = new JPanel(new BorderLayout());
		
		pnCenter_North = new JPanel(new BorderLayout());
		pnCenter_NorthRight = new JPanel(new GridLayout(4, 1));
		pnCenter_NorthLeft = new JPanel(new GridLayout(4, 1));
		pnCenter_South = new JPanel(new BorderLayout());
		pnCenter_SouthUp = new JPanel(new GridLayout(1, 4));
		pnCenter_East = new JPanel(new GridLayout(4, 1));
		pnCenter_SouthDown = new JPanel(new GridLayout(3, 4));
	}
	
	private void createBtn(String btnName) {
		btn_Cancel = new JButton("취소");
		btn_CarSearch = new JButton("차량검색");
		
		if (btnName == "수정")
			btn_Confirm = new JButton(btnName);
		else
			btn_Confirm = new JButton(btnName);
	}
	
	private void createLabel() {
		lb_Info = new JLabel("예약 정보", SwingConstants.CENTER);
		lb_Rid = new JLabel("예약 번호");
		lb_Cid = new JLabel("차량번호");
		lb_Cname = new JLabel("차량명");
		lb_Mid = new JLabel("회원 아이디");
		lb_ResDate = new JLabel("예약 일자", SwingConstants.CENTER);
		lb_StartDate = new JLabel("대여 시작일", SwingConstants.CENTER);
		lb_EndDate = new JLabel("대여 마감일", SwingConstants.CENTER);
		lb_Year = new JLabel("년", SwingConstants.CENTER);
		lb_Month = new JLabel("월", SwingConstants.CENTER);
		lb_Day = new JLabel("일", SwingConstants.CENTER);
		lb_Blank = new JLabel("");
		lb_Blank2 = new JLabel("");
		lb_Blank3 = new JLabel("");
		lb_Blank4 = new JLabel("");
	}
	
	private void createTextField() {
		jtf_Rid = new JTextField();
		jtf_Cid = new JTextField();
		jtf_Cname = new JTextField();
		jtf_Mid = new JTextField();
		jtf_ResYear = new JTextField(4);
		jtf_ResMonth = new JTextField(2);
		jtf_ResDay = new JTextField(2);
		jtf_StYear = new JTextField(4);
		jtf_StMonth = new JTextField(2);
		jtf_StDay = new JTextField(2);
		jtf_EdYear = new JTextField(4);
		jtf_EdMonth = new JTextField(2);
		jtf_EdDay = new JTextField(2);
	}
	
	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		logo = kit.getImage("img/Caricon.png"); 
		setIconImage(logo); 
	}
	
	private void addBtnETC() {
		pn_North.add(lb_Info);
		pn_South.add(btn_Confirm);
		pn_South.add(btn_Cancel);
		pnCenter_East.add(lb_Blank2);
		pnCenter_East.add(lb_Blank3);
		pnCenter_East.add(lb_Blank4);
		pnCenter_East.add(btn_CarSearch);
	}
	
	private void addTextField() {
		pnCenter_NorthLeft.add(lb_Rid);
		pnCenter_NorthLeft.add(lb_Mid);
		pnCenter_NorthLeft.add(lb_Cid);
		pnCenter_NorthLeft.add(lb_Cname);
		pnCenter_NorthRight.add(jtf_Rid);
		pnCenter_NorthRight.add(jtf_Mid);
		pnCenter_NorthRight.add(jtf_Cid);
		pnCenter_NorthRight.add(jtf_Cname);
		
		pnCenter_SouthUp.add(lb_Blank);
		pnCenter_SouthUp.add(lb_Year);
		pnCenter_SouthUp.add(lb_Month);
		pnCenter_SouthUp.add(lb_Day);
		
		pnCenter_SouthDown.add(lb_ResDate);
		pnCenter_SouthDown.add(jtf_ResYear);
		pnCenter_SouthDown.add(jtf_ResMonth);
		pnCenter_SouthDown.add(jtf_ResDay);
		
		pnCenter_SouthDown.add(lb_StartDate);
		pnCenter_SouthDown.add(jtf_StYear);
		pnCenter_SouthDown.add(jtf_StMonth);
		pnCenter_SouthDown.add(jtf_StDay);
		
		pnCenter_SouthDown.add(lb_EndDate);
		pnCenter_SouthDown.add(jtf_EdYear);
		pnCenter_SouthDown.add(jtf_EdMonth);
		pnCenter_SouthDown.add(jtf_EdDay);
		
	}
	
	private void addPanel() {
		pn_Center.add(pnCenter_North, BorderLayout.NORTH);
		pn_Center.add(pnCenter_South, BorderLayout.CENTER);
		pnCenter_North.add(pnCenter_NorthLeft, BorderLayout.WEST);
		pnCenter_North.add(pnCenter_NorthRight, BorderLayout.CENTER);
		pnCenter_North.add(pnCenter_East, BorderLayout.EAST);
		pnCenter_South.add(pnCenter_SouthUp, BorderLayout.NORTH);
		pnCenter_South.add(pnCenter_SouthDown, BorderLayout.CENTER);
		
		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}
	
	private void setTextField(AdminView frame, ActionEvent ae) {
		int row = frame.jt_Center.getSelectedRow();
		
		jtf_Rid.setText(frame.jt_Center.getValueAt(row, 0).toString());
		jtf_Cid.setText(frame.jt_Center.getValueAt(row, 1).toString());
		jtf_Mid.setText(frame.jt_Center.getValueAt(row, 2).toString());
		jtf_Cname.setText(frame.jt_Center.getValueAt(row, 3).toString());
		
		jtf_StYear.setText(frame.jt_Center.getValueAt(row, 4)
										   .toString().substring(0, 4));
		jtf_StMonth.setText(frame.jt_Center.getValueAt(row, 4)
											.toString().substring(5, 7));
		jtf_StDay.setText(frame.jt_Center.getValueAt(row, 4)
											.toString().substring(8, 10));
		
		jtf_EdYear.setText(frame.jt_Center.getValueAt(row, 5)
											.toString().substring(0, 4));
		jtf_EdMonth.setText(frame.jt_Center.getValueAt(row, 5)
											.toString().substring(5, 7));
		jtf_EdDay.setText(frame.jt_Center.getValueAt(row, 5) 
											.toString().substring(8, 10));

		jtf_ResYear.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(0, 4));
		jtf_ResMonth.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(5, 7));
		jtf_ResDay.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(8, 10));
		
		setTextFieldEdit(ae);
		setTextFieldAlign();
	}
	
	private void setTextFieldAlign() {
		jtf_ResYear.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_ResMonth.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_ResDay.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_StYear.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_StMonth.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_StDay.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_EdYear.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_EdMonth.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_EdDay.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private void setTextFieldEdit(ActionEvent ae) {
		jtf_Rid.setEditable(false);
		jtf_Mid.setEditable(false);
		jtf_ResYear.setEditable(false);
		jtf_ResMonth.setEditable(false);
		jtf_ResDay.setEditable(false);
		jtf_Cid.setEditable(false);
		jtf_Cname.setEditable(false);
		
		if (ae.getActionCommand() == "삭제") {
			jtf_StYear.setEditable(false);
			jtf_StMonth.setEditable(false);
			jtf_StDay.setEditable(false);
			jtf_EdYear.setEditable(false);
			jtf_EdMonth.setEditable(false);
			jtf_EdDay.setEditable(false);
		} 
	}
	
	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_CarSearch.addActionListener(this);
		btn_Cancel.addActionListener(this);
		this.addWindowListener(this);
	}
	
	public void updateTextField(Object[] obj) {
		jtf_Cid.setText(obj[0].toString());
		jtf_Cname.setText(obj[1].toString());
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "수정") {
			int val = JOptionPane.showConfirmDialog(this, 
					"예약번호 : " + this.jtf_Rid.getText() +
					"\n예약정보가 수정됩니다.\n수정하시겠습니까?", 
					"예약수정", JOptionPane.YES_NO_OPTION);
			
			
			//TODO : 수상함
			if (val == JOptionPane.YES_OPTION) {
				rDao = new ResDAO(frame);
				AdminView.dialogueState = AdminView.NOTOPEN;				
				if (rDao.updateData(this ,frame) == ResDAO.NO) {
					AdminView.dialogueState = AdminView.NOTOPEN;
					dispose();
				}
			}
		} else if (ae.getActionCommand() == "삭제") {
			
			int val = JOptionPane.showConfirmDialog(this, 
						"예약번호 : " + this.jtf_Rid.getText() +
						"\n예약정보가 삭제됩니다.\n삭제하시겠습니까?", 
						"예약삭제", JOptionPane.YES_NO_OPTION);
			
			if (val == JOptionPane.YES_OPTION) {
				rDao = new ResDAO(frame);
				rDao.deleteData(jtf_Rid.getText(), frame);
				JOptionPane.showMessageDialog(this, 
						"삭제되었습니다.", "확인", 
						JOptionPane.INFORMATION_MESSAGE);
				AdminView.dialogueState = AdminView.NOTOPEN;
				dispose();
			}
			
		} else if (ae.getActionCommand() == "차량검색") {
			rDao = new ResDAO();
			if (rDao.isPossible()) {
				innerDiaState = OPENINNER;
				new PossibleCarDialogue(this);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this
						, "예약가능한 차량이 존재하지 않습니다.", "차량부족"
						, JOptionPane.WARNING_MESSAGE);
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
