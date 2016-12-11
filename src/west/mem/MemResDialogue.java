package west.mem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import west.car.CarDAO;
import west.res.ResDAO;
import west.util.SqlUtil;
import west.view.UserView;
	
public class MemResDialogue extends JDialog
				implements ActionListener, WindowListener {
	private static final long serialVersionUID = -9184333323441595230L;

	JPanel pn_North, pn_Center, pn_South, pnNorth_North,
		pnNorth_South;

	JScrollPane jsp_Center;
	public JTable jt_InnerJSP;
	public DefaultTableModel dt_InnerJT;
	String[] columnName = {};

	JButton btn_Confirm, btn_Cancel;
	JRadioButton rb_Cid, rb_Cname, rb_SDate;
	ButtonGroup btngroup_Rb;
	
	JLabel lb_Info;
	
	Image logo;
	
	UserView view;
	CarDAO cDao;
	ResDAO rDao;
	MemDAO mDao;
	
	public MemResDialogue(UserView view) {
		createPanel();
		createBtn();
		createLabel();
		createTable(columnName);
		setLogo();
		addBtnETC();
		addActionListener();
		addPanel();
		
		//ResDialogue.innerDiaState = ResDialogue.OPENINNER;
		setSize(300, 350);
		setVisible(true);
		this.view = view;
		rDao = new ResDAO();
		mDao = new MemDAO();
		rDao.setTableData(this.dt_InnerJT
				, this.jt_InnerJSP, SqlUtil.RESCAR_SEARCH);
	}

	private void createPanel() {
		setLayout(new BorderLayout());
		pn_North = new JPanel(new BorderLayout());
		pn_South = new JPanel(new GridLayout(1, 2));
		pn_Center = new JPanel(new BorderLayout());
		
		pnNorth_North = new JPanel(new FlowLayout());
		pnNorth_South = new JPanel(new FlowLayout());
	}
	
	private void createBtn() {
		btn_Cancel = new JButton("취소");
		btn_Confirm = new JButton("수정");
	}
	
	private void createLabel() {
		lb_Info = new JLabel("예약 정보", SwingConstants.CENTER);
	}
	
	private void createTable(String[] columnName) {
		dt_InnerJT = new DefaultTableModel(columnName, 0) {
			private static final long serialVersionUID = -709380741528598670L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt_InnerJSP = new JTable(dt_InnerJT);
		jsp_Center = new JScrollPane(jt_InnerJSP);
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
	
	private void addPanel() {
		pn_North.add(pnNorth_North, BorderLayout.NORTH);
		pn_North.add(pnNorth_South, BorderLayout.SOUTH);
		pn_Center.add(jsp_Center, BorderLayout.CENTER);
		
		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}
	
	private void addActionListener() {
		btn_Confirm.addActionListener(this);
		btn_Cancel.addActionListener(this);
		this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "수정") {
				if (jt_InnerJSP.getSelectedRow() == -1)
					JOptionPane.showMessageDialog(this
							, "레코드를 선택해주세요!", "레코드 선택요망"
							, JOptionPane.ERROR_MESSAGE);
				else {
					int selectRow = jt_InnerJSP.getSelectedRow();
					
					Object[] selectRowData = 
							new Object[jt_InnerJSP.getColumnCount()];
					
					for(int i = 0; i < jt_InnerJSP.getColumnCount(); i++) {
						selectRowData[i] = (Object) jt_InnerJSP.getModel()
												.getValueAt(selectRow, i);
					}
					
					UserView.jtf_UserCid.setText(
							selectRowData[0].toString());
					UserView.jtf_UserCName.setText(
							selectRowData[1].toString());
					//ResDialogue.innerDiaState = ResDialogue.NOTOPENDIA;
					dispose();
				}
			//}
		} else if (ae.getActionCommand() == "취소") {
			//ResDialogue.innerDiaState = ResDialogue.NOTOPENDIA;
			dispose();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//ResDialogue.innerDiaState = ResDialogue.NOTOPENDIA;
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
