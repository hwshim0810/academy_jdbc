package west.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import west.car.CarDAO;
import west.car.CarDialogue;
import west.car.InsertCarDialogue;
import west.mem.InsertMemberDialog;
import west.mem.MemDAO;
import west.mem.MemDeleteDialogue;
import west.mem.MemResInsertDialogue;
import west.res.ResDAO;
import west.res.ResDialogue;
import west.util.SqlUtil;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 271496541161517045L;

	JPanel pn_North, pn_South, pn_Center;
	
	JPanel pnNorth_Right, pnNorth_Left, pnSouth_Right, 
			pnCenter_South, pnSouth_Left, pnCenterSouth_Inner;
	JScrollPane jsp_Center;
	
	public JTable jt_Center;
	public DefaultTableModel dt_Center;
	
	JMenuBar jmb;
	JMenu jm_manage, jm_help;
	JMenuItem item_ViewCar, item_ViewRes, item_ViewMem, 
				item_Logout, item_About, item_Exit;
	
	JButton btn_ViewCar, btn_ViewRes, btn_ViewMem, btn_Logout,
			btn_Update, btn_Delete, btn_Select, btn_Insert;
	
	JLabel lb_Welcome ,lb_Blank ,lb_Blank2 ,lb_Blank3;
	String member = "";
	
	Image logo;
	
	JComboBox<String> jcmb_Search;
	DefaultComboBoxModel<String> model;
	String[] colname_Init = {};
	String[] list_CarSearch = {"검색타입", "차량번호", "차종", "예약여부"};
	String[] list_ResSearch = {"검색타입", "차량번호", "차종", "회원아이디", "예약일자"};
	String[] list_MemSearch = {"검색타입", "회원아이디", "회원이름", "면허번호"};
	
	JTextField jtf_Search;
	ResDAO rDao;
	CarDAO cDao;
	MemDAO mDao;
	
	public static final int NOTOPEN = 0;
	public static final int OPENRES = 1;
	public static final int OPENCAR = 2;
	public static final int OPENMEM = 3;
	public static final int OPENDIALOGUE = 4;
	
	public static int frameState;

	public static int dialogueState;
	
	public AdminView() {
		super();
		
		setLogo();
		createMenuBar();
		createPanel();
		createBtn();
		createLabel("관리자");
		createTextField();
		setLayoutPanel();
		addBtnETC();
		setTableColumn(colname_Init);
		addPanel();
		addActionListener();
		setPanelSize();
		
		setVisible(true);
		setBounds(400,150,1000,500);
		setResizable(false);
		pnCenter_South.setVisible(false);
		pnSouth_Left.setVisible(false);
		btn_Select.setVisible(false);
		frameState = NOTOPEN;
		dialogueState = NOTOPEN;
		rDao = new ResDAO(this);
		cDao = new CarDAO(this);
		mDao = new MemDAO();
		
		if (frameState != OPENRES) {
			rDao.setTableData(this.dt_Center
					, this.jt_Center, SqlUtil.RES_TOTAL);
			setVisibleSearch(list_ResSearch, jcmb_Search);
			frameState = OPENRES;
		}
	}
	
	private void createMenuBar() {
		jmb = new JMenuBar();
		jm_manage = new JMenu("Manage");
		jm_help = new JMenu("Help");
		
		item_ViewCar = new JMenuItem("차량관리");
		item_ViewRes = new JMenuItem("예약관리");
		item_ViewMem = new JMenuItem("회원관리");
		item_Logout = new JMenuItem("로그아웃");
		item_Exit = new JMenuItem("종료");
		item_About = new JMenuItem("만든사람들");
		
		jm_manage.add(item_ViewCar);
		jm_manage.add(item_ViewRes);
		jm_manage.add(item_ViewMem);
		jm_manage.addSeparator();
		jm_manage.add(item_Logout);
		jm_manage.add(item_Exit);
		
		jm_help.add(item_About);
		
		jmb.add(jm_manage);
		jmb.add(jm_help);
		setJMenuBar(jmb);
	}
	
	private void createPanel() {
		pn_North = new JPanel(new BorderLayout(10, 10));
		pn_South = new JPanel(new BorderLayout(10, 10));
		pn_Center = new JPanel(new BorderLayout());
		
		pnCenter_South = new JPanel(new BorderLayout());
//		pnCenterSouth_Inner = new JPanel(new GridLayout(1, 3));
		pnNorth_Left = new JPanel();
		pnNorth_Right = new JPanel();
		pnSouth_Left = new JPanel();
		pnSouth_Right = new JPanel(new GridLayout(1, 5, 5, 5));
	}
	
	private void createBtn() {
		btn_ViewCar = new JButton("차량관리");
		btn_ViewRes = new JButton("예약관리");
		btn_ViewMem = new JButton("회원관리");
		btn_Logout = new JButton("로그아웃");
		btn_Select = new JButton("검색");
		btn_Update = new JButton("수정");
		btn_Delete = new JButton("삭제");
		btn_Insert = new JButton("추가");
		jcmb_Search = new JComboBox<>();
	}
	
	private void createLabel(String member) {
		lb_Welcome = new JLabel(member + " 님 환영합니다.                                      "
				+ "                                                                        "
				+ "                                                    ");
		lb_Welcome.setForeground(Color.WHITE);
		
		lb_Blank = new JLabel("");
		lb_Blank2 = new JLabel("");
		lb_Blank3 = new JLabel("");
	}
	
	private void createTextField() {
		jtf_Search = new JTextField("검색내용을 입력해주세요", 15);
	}
	
	private void setLayoutPanel() {
		pnNorth_Right.setLayout(new GridLayout(1, 4, 10, 10));
		pnSouth_Left.setLayout(new GridLayout(1, 1, 10, 10));
	}
	
	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		logo = kit.getImage("img/Caricon.png"); 
		setIconImage(logo); 
	}
	
	private void setTableColumn(String[] columnName) {
		dt_Center = new DefaultTableModel(columnName, 0) {
			private static final long serialVersionUID = -9012771782751174418L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt_Center = new JTable(dt_Center);
		jsp_Center = new JScrollPane(jt_Center);
	}
	
	private void addBtnETC() {
		pnNorth_Left.add(lb_Welcome);
		
		pnNorth_Right.add(btn_ViewCar);
		pnNorth_Right.add(btn_ViewRes);
		pnNorth_Right.add(btn_ViewMem);
		pnNorth_Right.add(btn_Logout);
		
//		pnCenterSouth_Inner.add(btn_Insert);
//		pnCenterSouth_Inner.add(btn_Update);
//		pnCenterSouth_Inner.add(btn_Delete);
		
//		pnSouth_Right.add(lb_Blank);
//		pnSouth_Right.add(lb_Blank2);
//		pnSouth_Right.add(lb_Blank3);
		pnSouth_Right.add(btn_Select);
		pnSouth_Right.add(btn_Insert);
		pnSouth_Right.add(btn_Update);
		pnSouth_Right.add(btn_Delete);
	}
		
	private void addPanel() {
		pn_North.add(pnNorth_Left, BorderLayout.WEST);
		pn_North.add(pnNorth_Right, BorderLayout.EAST);

		pn_Center.add(jsp_Center, BorderLayout.CENTER);
//		pnCenter_South.add(pnCenterSouth_Inner, BorderLayout.EAST);
		pn_Center.add(pnCenter_South, BorderLayout.SOUTH);
		
		pn_South.add(pnSouth_Left, BorderLayout.CENTER);
		pn_South.add(pnSouth_Right, BorderLayout.EAST);
		
		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}
	
	private void setVisibleSearch(String[] search_List, 
			JComboBox<String> jcmb) {
		
		btn_Insert.setVisible(true);
		pnCenter_South.setVisible(true);
		pnSouth_Left.setVisible(true);
		btn_Select.setVisible(true);
		
		jcmb.removeAllItems();
		model = new DefaultComboBoxModel<>(search_List);
		jcmb_Search.setModel(model);
		pnSouth_Left.add(jcmb_Search);
		pnSouth_Left.add(jtf_Search);
	}
	
	private void addActionListener() {
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		
		ActionListener addBtnItemEvent = (e) -> {
			switch (e.getActionCommand()) {
			case "차량관리":
				if (frameState != OPENCAR) {
					rDao.setTableData(this.dt_Center
							, this.jt_Center, SqlUtil.CAR_TOTAL);
					setVisibleSearch(list_CarSearch, jcmb_Search);
					frameState = OPENCAR;
				}
				break;
			case "예약관리":
				if (frameState != OPENRES) {
					rDao.setTableData(this.dt_Center
							, this.jt_Center, SqlUtil.RES_TOTAL);
					setVisibleSearch(list_ResSearch, jcmb_Search);
					frameState = OPENRES;
				}
				break;
			case "회원관리":
				if (frameState != OPENMEM) {
					rDao.setTableData(this.dt_Center
							, this.jt_Center, SqlUtil.MEM_TOTAL);
					setVisibleSearch(list_MemSearch, jcmb_Search);
					frameState = OPENMEM;
				}
				break;
			case "로그아웃":
				int val = JOptionPane.showConfirmDialog(this
							, "로그아웃 하시겠습니까?", "로그아웃확인"
							,JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(this
							, "로그아웃되었습니다.", "확인"
							, JOptionPane.INFORMATION_MESSAGE);
					dispose();
					new CarLogin();
				}
			default:
					break;
			}
		};
		
		item_ViewCar.addActionListener(addBtnItemEvent);
		btn_ViewCar.addActionListener(addBtnItemEvent);
		item_ViewRes.addActionListener(addBtnItemEvent);
		btn_ViewRes.addActionListener(addBtnItemEvent);
		item_ViewMem.addActionListener(addBtnItemEvent);
		btn_ViewMem.addActionListener(addBtnItemEvent);
		item_Logout.addActionListener(addBtnItemEvent);
		btn_Logout.addActionListener(addBtnItemEvent);
		
		item_Exit.addActionListener((e) -> {
			int val  = JOptionPane.showConfirmDialog(this 
					,"종료하시겠습니까?","종료확인"
					, JOptionPane.YES_NO_OPTION);
			
			if (val == JOptionPane.YES_OPTION) System.exit(0);
		});
		
		item_About.addActionListener((e) -> {
			JOptionPane.showMessageDialog(this
					, "Team West"
					, "만든이들..."
					, JOptionPane.PLAIN_MESSAGE);
		});
		
		btn_Select.addActionListener((e) -> {
			String searchField = jcmb_Search.getSelectedItem().toString();
			if (jtf_Search.getText().trim().equals("")) {
				AdminView.messageBox(this, "검색단어를 입력해주세요.");
				jtf_Search.requestFocus();
			} else if (searchField.equals("검색타입")) {
				AdminView.messageBox(this, "검색타입을 지정해주세요.");
				jtf_Search.requestFocus();
			} else {
				String searchWord = jtf_Search.getText().trim();
				
				switch (AdminView.frameState) {
				
				case AdminView.OPENCAR :
					cDao.getUserSearch(dt_Center
							, searchField, searchWord);
					if (dt_Center.getRowCount() > 0)
						jt_Center.setRowSelectionInterval(0, 0);
					break;
				case AdminView.OPENRES :
					rDao.getUserSearch(dt_Center
							, searchField, searchWord);
					if (dt_Center.getRowCount() > 0)
						jt_Center.setRowSelectionInterval(0, 0);
					break;
				case AdminView.OPENMEM :
					mDao.getUserSearch(dt_Center
							, searchField, searchWord);
					if (dt_Center.getRowCount() > 0)
						jt_Center.setRowSelectionInterval(0, 0);
					break;
				default:
					break;
				}

			}
		});
		
		ActionListener dbDML = (e) -> {
			if (dialogueState != OPENDIALOGUE) {
				if (jt_Center.getSelectedRow() == -1)
					JOptionPane.showMessageDialog(this
							, "레코드를 선택해주세요!", "레코드 선택요망"
							, JOptionPane.ERROR_MESSAGE);
				else {
					switch (AdminView.frameState) {
					case AdminView.OPENRES : 
						new ResDialogue(this, e);
						dialogueState = OPENDIALOGUE;
						break;
					case AdminView.OPENCAR :
						new CarDialogue(this, e);
						dialogueState = OPENDIALOGUE;
						break;
					case AdminView.OPENMEM :
						new MemDeleteDialogue(this, e);
						dialogueState = OPENDIALOGUE;
						break;
					}
				}
			}
		};
		
		ActionListener dbInsert = (e) -> {
					switch (AdminView.frameState) {
					case AdminView.OPENCAR :
							new InsertCarDialogue(this, e);
							dialogueState = OPENDIALOGUE;
							break;
							//TODO: 또고침
					case AdminView.OPENRES :
//							new InsertResDialogue(this, e);
							new MemResInsertDialogue("admin", this);
							dialogueState = OPENDIALOGUE;
							break;
					case AdminView.OPENMEM :
						new InsertMemberDialog(this, e);
						dialogueState = OPENDIALOGUE;
						break;
					}
			};
		
		btn_Update.addActionListener(dbDML);
		btn_Delete.addActionListener(dbDML);
		btn_Insert.addActionListener(dbInsert);
		
		jtf_Search.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				if (e.getComponent() == jtf_Search)
					jtf_Search.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {}
		});
	}
	
	public static void messageBox(Object obj, String message) {
		  JOptionPane.showMessageDialog((Component) obj, message);
	}
	
	//수정중
	private void setPanelSize() {
		pnNorth_Right.setBackground(Color.DARK_GRAY);
		pnNorth_Left.setBackground(Color.DARK_GRAY);
		jsp_Center.setPreferredSize(new Dimension(200, 200));
	}
	
}
