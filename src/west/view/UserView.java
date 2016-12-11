package west.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import west.mem.MemDAO;
import west.mem.MemDTO;
import west.mem.MemResDialogue;
import west.mem.MemResInsertDialogue;
import west.res.ResDAO;
import west.util.SqlUtil;

public class UserView extends JFrame {

	private static final long serialVersionUID = 271496541161517045L;

	JPanel pn_North, pn_South, pn_Center;
	
	JPanel pnNorth_Right, pnNorth_Left, pnSouth_Right, pnCenter_Center, 
		pnSouth_Left, pnCenterSouth_Inner, pnCenter_Info,
		pnCenter_South_Center, pnCenter_South_Left, pnButton_Inner,
		pnCenter_South_Right,carSearch, pnNorth_South,
		pnNorthSouth_North, pnNorthSouth_West, pnNorthSouth_Center,
		pnCenterSouth_South, pnNorthSouth_South, pnNorthSouth_East;
	JScrollPane jsp_Center;
	
	public JTable jt_Center;
	public DefaultTableModel dt_Center;
	
	JMenuBar jmb;
	JMenu jm_manage, jm_help;
	JMenuItem item_UserResChk,item_UserRes, item_UserUpdate,
				item_Logout, item_About, item_Exit;
	
	JButton btn_UserResChk, btn_UserRes, btn_UserUpdate, btn_Logout, 
		btn_UserResUpdate, btn_UserResCancle,
		btn_UpdateChk, btn_CarSearch, btn_Delete, btn_MemDrop,
		btn_PassUpdate, btn_Update;
	
	JLabel lb_Welcome, lb_ResTitle, lb_id, lb_CarName, lb_TotalPrice, 
		lb_TotalDay, lb_Year, lb_Month, lb_Day, lb_ResDay, lb_UserCid,
		lb_StartDay, lb_EndDay, lb_empty1, l_id, l_pwd, l_name, 
		l_license, l_tel, l_addr, l_age, l_email, lb_empty2;
	JLabel[] l_Blank = new JLabel[15];
	String member = "";
	private String pass, mid;
	
	Image logo;
	JComboBox<String> jcmb_Search;
	String[] colname_Init = {};
	String[] list_Init = {};
	String[] list_CarSearch = {"모두보기", "차번호", "차종"};
	String[] list_ResSearch = {"모두보기", "차번호", "차종", "회원아이디", "예약일자"};
	String[] list_MemSearch = {"모두보기", "회원아이디", "회원이름", "면허번호"};
	
	public JTextField jtf_Search, jtf_id, jtf_CarName, jtf_TotalPrice, 
		jtf_TotalDay, jtf_Year1, jtf_Month1, jtf_Day1, rid,
		jtf_Year2, jtf_Month2, jtf_Day2, jtf_Year3, jtf_Month3, jtf_Day3;
	public static JTextField jtf_UserCid, jtf_UserCName;
		
	public JTextField tf_id, tf_pwd, tf_pwdChk, tf_name, tf_license, 
		tf_tel, tf_addr, tf_age, tf_email;
	
	public static JLabel lb_empty;
	
	ResDAO rDao;
	MemDAO mDao;
	
	public static final int NOTOPEN = 0;
	public static final int OPENRES = 1;
	public static final int OPENCAR = 2;
	public static final int OPENMEM = 3;
	public static final int OPENDIALOGUE = 4;
	
	public static int frameState;

	public static int dialogueState;
	
	public UserView(String mid, String pass) {
		super("West RentCar Serivce");
		this.pass = pass;
		this.mid = mid;
		
		setLogo();
		createMenuBar();
		createPanel();
		createBtn();
		createLabel(mid);
		createTextField();
		setLayoutPanel();
		addCenter_South_Left();
		addCenter_South_Right();
		addBtnETC();
		setTableColumn(colname_Init);
		addPanel();
		addActionListener();
		
		setPanelSize();
		setVisible(true);
		setBounds(400,150,1000,500);
		//setSize(1000, 800);
		setResizable(false);
		setUnvisible();
		frameState = NOTOPEN;
		dialogueState = NOTOPEN;
		rDao = new ResDAO(this);
		mDao = new MemDAO();
		
		MemDTO mDTO = mDao.selectUser(mid);
		mDao.setDialTextField(this, mDTO);
		
		if (frameState != OPENRES) {
			mDao.setTableData(this.dt_Center, this.jt_Center, 
					SqlUtil.User_ResAll, this.mid);
			setVisible();
			pnCenter_Center.setVisible(false);
			frameState = OPENRES;
		}
		
		if (rDao.isUserSee(mid)) {
			rDao.setTableData(dt_Center, jt_Center, SqlUtil.ISPOSIIBLE);
			setVisible();
			pnCenter_Center.setVisible(false);
		} else {
			if (frameState != OPENRES) {
				mDao.setTableData(this.dt_Center, this.jt_Center, 
						SqlUtil.User_ResAll, this.mid);
				setVisible();
				pnCenter_Center.setVisible(false);
				frameState = OPENRES;
			}
		}
	}
	
	private void createMenuBar() {
		jmb = new JMenuBar();
		jm_manage = new JMenu("Menu");
		jm_help = new JMenu("Help");
		
		item_UserResChk = new JMenuItem("예약현황");
		item_UserRes = new JMenuItem("예약하기");
		item_UserUpdate = new JMenuItem("회원정보수정");
		item_Logout = new JMenuItem("로그아웃");
		item_Exit = new JMenuItem("종료");
		item_About = new JMenuItem("만든사람들");
		
		jm_manage.add(item_UserResChk);
		jm_manage.add(item_UserRes);
		jm_manage.add(item_UserUpdate);
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
		
		pnCenter_Info = new JPanel();
		pnCenter_Center = new JPanel(new BorderLayout(10, 10));
		pnCenter_South_Center = new JPanel(new GridLayout(1, 2, 10, 30));
		pnCenter_South_Left = new JPanel(new GridLayout(5, 2, 10, 15));
		pnCenter_South_Right = new JPanel(new GridLayout(4, 4, 10, 10));
		carSearch = new JPanel(new BorderLayout());
		pnCenterSouth_Inner = new JPanel(new GridLayout(1, 2));
		pnButton_Inner = new JPanel(new GridLayout(1, 3));
		pnNorth_Left = new JPanel();
		pnNorth_Right = new JPanel();
		pnSouth_Left = new JPanel();
		pnSouth_Right = new JPanel(new GridLayout(1, 2));
		
		//TODO: 통합본 수정바람
		pnNorth_South = new JPanel(new BorderLayout());
		pnNorthSouth_North = new JPanel(new FlowLayout()); 
		pnNorthSouth_South = new JPanel(new GridLayout(1, 7, 10, 1));
		pnNorthSouth_West = new JPanel(new GridLayout(9, 1, 23, 23));
		pnNorthSouth_Center = new JPanel(new GridLayout(9, 1, 23, 23));
		pnNorthSouth_East = new JPanel(new FlowLayout());
		pnCenterSouth_South = new JPanel(new GridLayout(1, 3));
	}
	
	private void createBtn() {
		btn_UserResChk = new JButton("예약현황");
		btn_UserRes = new JButton("예약하기");
		btn_UserUpdate = new JButton("회원정보수정");
		btn_Logout = new JButton("로그아웃");
		btn_UpdateChk = new JButton("예약변경");
		btn_UserResUpdate = new JButton("일정변경");
		btn_UserResCancle = new JButton("취소");
		btn_CarSearch = new JButton("차량검색");
		btn_Delete = new JButton("예약삭제");
		
		btn_Update = new JButton("수정");
		btn_PassUpdate = new JButton("비밀번호 수정");
		btn_MemDrop = new JButton("회원 탈퇴");
	}
	
	private void createLabel(String member) {
		Font ft_Label = new Font("돋움", Font.BOLD, 18);
		lb_Welcome = new JLabel(member + " 님 환영합니다.                                      "
				+ "                                                                        "
				+ "                ");
		lb_empty = new JLabel("");
		lb_empty1 = new JLabel("");
		lb_ResTitle = new JLabel("예약정보");
		lb_ResTitle.setFont(new Font("바탕",Font.BOLD,30));
		lb_id = new JLabel("회원아이디",SwingConstants.RIGHT);
		lb_UserCid = new JLabel("차량번호",SwingConstants.RIGHT);
		lb_CarName = new JLabel("차량종류",SwingConstants.RIGHT);
		lb_TotalPrice = new JLabel("총 가격",SwingConstants.RIGHT);
		lb_TotalDay = new JLabel("빌린기간",SwingConstants.RIGHT);
		lb_Year = new JLabel("년",SwingConstants.CENTER);
		lb_Month = new JLabel("월",SwingConstants.CENTER);
		lb_Day = new JLabel("일",SwingConstants.CENTER);
		lb_ResDay = new JLabel("예약날짜",SwingConstants.RIGHT);
		lb_StartDay = new JLabel("차량대여일",SwingConstants.RIGHT);
		lb_EndDay = new JLabel("반납일",SwingConstants.RIGHT);
		lb_UserCid = new JLabel("차량번호",SwingConstants.RIGHT);
		          
		l_id = new JLabel("                                        아이디");
		l_pwd = new JLabel("                                        비밀번호");
		l_name = new JLabel("                                        이름");
		l_license = new JLabel("                                        면허번호");
		l_tel = new JLabel("                                        전화번호");
		l_addr = new JLabel("                                        주소");
		l_age = new JLabel("                                        나이");
		l_email = new JLabel("                                        이메일");
		
		lb_Welcome.setForeground(Color.WHITE);
		l_id.setFont(ft_Label);
		l_pwd.setFont(ft_Label);
		l_name.setFont(ft_Label);
		l_license.setFont(ft_Label);
		l_tel.setFont(ft_Label);
		l_addr.setFont(ft_Label);
		l_age.setFont(ft_Label);
		l_email.setFont(ft_Label);
		
		for (int i = 0; i < l_Blank.length; i++) 
			l_Blank[i] = new JLabel("");
		lb_empty = new JLabel();
		lb_empty2 = new JLabel();
	}
	
	private void createTextField() {
		jtf_Search = new JTextField("검색내용을 입력해주세요", 15);
		jtf_id = new JTextField();
		jtf_CarName = new JTextField();
		jtf_TotalPrice = new JTextField();
		jtf_TotalDay = new JTextField();
		jtf_Year1 = new JTextField();
		jtf_Month1 = new JTextField();
		jtf_Day1 = new JTextField();
		jtf_Year2 = new JTextField();
		jtf_Month2 = new JTextField();
		jtf_Day2 = new JTextField();
		jtf_Year3 = new JTextField();
		jtf_Month3 = new JTextField();
		jtf_Day3 = new JTextField();
		
		jtf_UserCid = new JTextField();
		jtf_UserCName = new JTextField();
		rid = new JTextField();
		
		tf_id = new JTextField();
		tf_pwd = new JTextField();
		tf_pwdChk = new JTextField();
		tf_name = new JTextField();
		tf_license = new JTextField();
		tf_tel = new JTextField();
		tf_addr = new JTextField();
		tf_age = new JTextField();
		tf_email = new JTextField();
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
	
	private void addCenter_South_Left() {
		pnCenter_South_Left.add(lb_id);
		pnCenter_South_Left.add(jtf_id);
		pnCenter_South_Left.add(lb_UserCid);
		pnCenter_South_Left.add(jtf_UserCid);
		pnCenter_South_Left.add(lb_CarName);
		carSearch.add(jtf_UserCName,"Center");
		carSearch.add(btn_CarSearch,"East");
		pnCenter_South_Left.add(carSearch);
		pnCenter_South_Left.add(lb_TotalPrice);
		pnCenter_South_Left.add(jtf_TotalPrice);
		pnCenter_South_Left.add(lb_TotalDay);
		pnCenter_South_Left.add(jtf_TotalDay);
	}

	private void addCenter_South_Right() {
		pnCenter_South_Right.add(lb_empty1);
		pnCenter_South_Right.add(lb_Year);
		pnCenter_South_Right.add(lb_Month);
		pnCenter_South_Right.add(lb_Day);

		pnCenter_South_Right.add(lb_ResDay);
		pnCenter_South_Right.add(jtf_Year1);
		pnCenter_South_Right.add(jtf_Month1);
		pnCenter_South_Right.add(jtf_Day1);

		pnCenter_South_Right.add(lb_StartDay);
		pnCenter_South_Right.add(jtf_Year2);
		pnCenter_South_Right.add(jtf_Month2);
		pnCenter_South_Right.add(jtf_Day2);

		pnCenter_South_Right.add(lb_EndDay);
		pnCenter_South_Right.add(jtf_Year3);
		pnCenter_South_Right.add(jtf_Month3);
		pnCenter_South_Right.add(jtf_Day3);
	}
	
	private void addBtnETC() {
		pnNorth_Left.add(lb_Welcome);
		
		pnNorth_Right.add(btn_UserResChk);
		pnNorth_Right.add(btn_UserRes);
		pnNorth_Right.add(btn_UserUpdate);
		pnNorth_Right.add(btn_Logout);
		
		pnButton_Inner.add(btn_UserResUpdate);
		pnButton_Inner.add(btn_Delete);
		pnButton_Inner.add(btn_UserResCancle);

		pnNorthSouth_West.add(l_id);
		pnNorthSouth_West.add(l_pwd);
		pnNorthSouth_West.add(l_name);
		pnNorthSouth_West.add(l_license);
		pnNorthSouth_West.add(l_tel);
		pnNorthSouth_West.add(l_addr);
		pnNorthSouth_West.add(l_age);
		pnNorthSouth_West.add(l_email);
		pnNorthSouth_West.add(l_Blank[0]);
		
		pnNorthSouth_Center.add(tf_id);
		pnNorthSouth_Center.add(l_Blank[5]);
		pnNorthSouth_Center.add(tf_pwd);
		pnNorthSouth_Center.add(l_Blank[6]);
		pnNorthSouth_Center.add(tf_name);
		pnNorthSouth_Center.add(l_Blank[7]);
		pnNorthSouth_Center.add(tf_license);
		pnNorthSouth_Center.add(l_Blank[8]);
		pnNorthSouth_Center.add(tf_tel);
		pnNorthSouth_Center.add(l_Blank[9]);
		pnNorthSouth_Center.add(tf_addr);
		pnNorthSouth_Center.add(l_Blank[10]);
		pnNorthSouth_Center.add(tf_age);
		pnNorthSouth_Center.add(l_Blank[11]);
		pnNorthSouth_Center.add(tf_email);
		pnNorthSouth_Center.add(l_Blank[1]);
		pnNorthSouth_Center.add(l_Blank[1]);
		
		pnNorthSouth_South.add(l_Blank[2]);
		pnNorthSouth_South.add(l_Blank[3]);
		pnNorthSouth_South.add(btn_Update);
		pnNorthSouth_South.add(btn_PassUpdate);
		pnNorthSouth_South.add(btn_MemDrop);
		
		pnSouth_Left.add(lb_empty);
		pnSouth_Right.add(btn_UpdateChk);
		
		pnNorthSouth_East.add(l_Blank[4]);
	}
	
	private void addPanel() {
		pn_North.add(pnNorth_Left, BorderLayout.WEST);
		pn_North.add(pnNorth_Right, BorderLayout.EAST);
		pn_North.add(pnNorth_South, BorderLayout.SOUTH);

		pn_Center.add(jsp_Center, BorderLayout.NORTH);
		pn_Center.add(pnCenter_Center, BorderLayout.CENTER);
		
		pnCenter_Center.add(lb_ResTitle, BorderLayout.NORTH);
		pnCenter_Center.add(pnCenterSouth_Inner, BorderLayout.SOUTH);
		pnCenter_Center.add(pnCenter_South_Center, BorderLayout.CENTER);
		pnCenter_South_Center.add(pnCenter_South_Left, BorderLayout.CENTER);
		pnCenter_South_Center.add(pnCenter_South_Right, BorderLayout.CENTER);
		
		pnNorth_South.add(pnNorthSouth_North, BorderLayout.NORTH);
		pnNorth_South.add(pnNorthSouth_South, BorderLayout.SOUTH);
		pnNorth_South.add(pnNorthSouth_West, BorderLayout.WEST);
		pnNorth_South.add(pnNorthSouth_Center, BorderLayout.CENTER);
		pnNorth_South.add(pnNorthSouth_East, BorderLayout.EAST);

		pnCenter_Center.add(lb_ResTitle, BorderLayout.NORTH);
		pnCenter_Center.add(pnCenter_South_Center, BorderLayout.CENTER);
		pnCenter_South_Center.add(pnCenter_South_Left, BorderLayout.CENTER);
		pnCenter_South_Center.add(pnCenter_South_Right, BorderLayout.CENTER);
		pnCenter_Center.add(pnCenterSouth_Inner, BorderLayout.SOUTH);
		pnCenterSouth_Inner.add(lb_empty2, BorderLayout.CENTER);
		pnCenterSouth_Inner.add(pnButton_Inner,BorderLayout.EAST);		

		pn_South.add(pnSouth_Left, BorderLayout.CENTER);
		pn_South.add(pnSouth_Right, BorderLayout.EAST);

		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}
	
	private void setUnvisible() {
		pnNorth_South.setVisible(false);
		pn_Center.setVisible(false);
		pn_South.setVisible(false);
	}
	
	private void setVisible() {
		pn_Center.setVisible(true);
		pnSouth_Left.setVisible(true);
		pnNorth_South.setVisible(false);
		pn_South.setVisible(true);
	}
	
	private void setTextField() {
		int row = jt_Center.getSelectedRow();
		
		
		rid.setText(jt_Center.getValueAt(row, 0).toString());
		
		jtf_id.setText(jt_Center.getValueAt(row, 1).toString());
		jtf_id.setEditable(false);
		jtf_UserCid.setText(jt_Center.getValueAt(row, 2).toString());
		jtf_UserCid.setEditable(false);
		jtf_UserCName.setText(jt_Center.getValueAt(row, 3).toString());
		jtf_UserCName.setEditable(false);
		jtf_Year1.setText(jt_Center.getValueAt(row, 5)
				.toString().substring(0, 4));
		jtf_Year1.setEditable(false);
		jtf_Month1.setText(jt_Center.getValueAt(row, 5)
				.toString().substring(5, 7));
		jtf_Month1.setEditable(false);
		jtf_Day1.setText(jt_Center.getValueAt(row, 5)
				.toString().substring(8, 10));
		jtf_Day1.setEditable(false);

		jtf_Year2.setText(jt_Center.getValueAt(row, 6)
				.toString().substring(0, 4));
		jtf_Month2.setText(jt_Center.getValueAt(row, 6)
				.toString().substring(5, 7));
		jtf_Day2.setText(jt_Center.getValueAt(row, 6)
				.toString().substring(8, 10));

		
		jtf_Year3.setText(jt_Center.getValueAt(row, 7)
				.toString().substring(0, 4));
		jtf_Month3.setText(jt_Center.getValueAt(row, 7)
				.toString().substring(5, 7));
		jtf_Day3.setText(jt_Center.getValueAt(row, 7)
				.toString().substring(8, 10));

		jtf_TotalDay.setText(jt_Center.getValueAt(row, 8).toString());
		jtf_TotalDay.setEditable(false);
		jtf_TotalPrice.setText(jt_Center.getValueAt(row, 9).toString());
		jtf_TotalPrice.setEditable(false);
		
		// setTextFieldEdit(ae);
		// setTextFieldAlign();
	}
	
	private void addActionListener() {
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		ActionListener addBtnItemEvent = (e) -> {
			switch (e.getActionCommand()) {
			case "예약변경":
				if (jt_Center.getSelectedRow() == -1)
					JOptionPane.showMessageDialog(this, "일정을 선택해주세요 ", "West 렌터카", JOptionPane.ERROR_MESSAGE);
				else {
					pnCenter_Center.setVisible(true);
					pn_South.setVisible(false);
					setTextField();
				}
				break;
			case "예약현황":
				if (frameState != OPENRES) {
					rDao.setTableData(this.dt_Center, this.jt_Center, 
							SqlUtil.User_ResAll);
					// setVisibleSearch(list_UserResSearch);
					setVisible();
					pnCenter_Center.setVisible(false);
					frameState = OPENRES;
				}
				break;
			case "회원정보수정":
				String confrimPass = "";
				JPasswordField jpf_ConfirmPass = new JPasswordField(15);
				Box box_Confirm = getPassBox(jpf_ConfirmPass);
				
				int value = JOptionPane.showConfirmDialog(this, box_Confirm
						, "비밀번호 확인", JOptionPane.YES_NO_OPTION);
				
				if (value == JOptionPane.YES_OPTION)
					confrimPass = String.valueOf(jpf_ConfirmPass.getPassword());
				
				if (confrimPass.equals(pass) && confrimPass != "") {
					if (frameState != OPENMEM) {
						setUnvisible();
						pnNorth_South.setVisible(true);
						frameState = OPENMEM;
					}
				} else if (confrimPass.equals("")) {
					JOptionPane.showMessageDialog(this, 
							"비밀번호를 입력해주십시오.",
							"비밀번호 확인", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, 
							"비밀번호가 다릅니다.",
							"비밀번호 확인", JOptionPane.WARNING_MESSAGE);
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
				break;
			case "차량검색":
				mDao = new MemDAO();
				if (rDao.isPossible()) {
					new MemResDialogue(this);					
				} else {
					JOptionPane.showMessageDialog(this
							, "예약가능한 차량이 존재하지 않습니다.", "차량부족"
							, JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "일정변경":
				int val1 = JOptionPane.showConfirmDialog(this, 
						"예약번호 : " + this.rid.getText() +
						"\n예약정보가 수정됩니다.\n수정하시겠습니까?", 
						"예약수정", JOptionPane.YES_NO_OPTION);
				if (val1 == JOptionPane.YES_OPTION) {
					rDao.UserResUpdate(this);
					mDao.setTableData(this.dt_Center, this.jt_Center, 
							SqlUtil.User_ResAll, this.mid);
					
				}
				break;
				//TODO:통합본수정
			case "예약삭제":
				int val2 = JOptionPane.showConfirmDialog(this, "예약이 취소됩니다."
						+ "\n예약 취소하시겠습니까???", 
						"West렌터카",JOptionPane.YES_NO_OPTION);
				if (val2 == JOptionPane.YES_OPTION) {
					rDao.UserResDelete(this, mid);
					JOptionPane.showMessageDialog(this, 
							"삭제되었습니다.", "확인", 
							JOptionPane.INFORMATION_MESSAGE);
					mDao.setTableData(this.dt_Center, this.jt_Center, 
							SqlUtil.User_ResAll, this.mid);
					pnCenter_Center.setVisible(false);
					pn_South.setVisible(true);
				}
				break;
			case "예약하기":
				dialogueState = OPENDIALOGUE;
				new MemResInsertDialogue(mid, this);
				break;
			case "취소":
				pnCenter_Center.setVisible(false);
				pn_South.setVisible(true);
				break;
			default:
					break;
			}
		};
		
		item_UserResChk.addActionListener(addBtnItemEvent);
		btn_UserResChk.addActionListener(addBtnItemEvent);
		item_UserRes.addActionListener(addBtnItemEvent);
		btn_UserRes.addActionListener(addBtnItemEvent);
		item_UserUpdate.addActionListener(addBtnItemEvent);
		btn_UserUpdate.addActionListener(addBtnItemEvent);
		item_Logout.addActionListener(addBtnItemEvent);
		btn_Logout.addActionListener(addBtnItemEvent);
		btn_UpdateChk.addActionListener(addBtnItemEvent);
		btn_UserResUpdate.addActionListener(addBtnItemEvent);
		btn_UserResCancle.addActionListener(addBtnItemEvent);
		btn_Delete.addActionListener(addBtnItemEvent);
		btn_CarSearch.addActionListener(addBtnItemEvent);
		
		
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
		
		btn_Update.addActionListener(e -> {
			mDao.updateData(this);
			this.pass = tf_pwd.getText();
			tf_pwd.setEditable(false);
			pnNorth_South.setVisible(false);
			frameState = NOTOPEN;
		});
		
		btn_PassUpdate.addActionListener(e -> {
			tf_pwd.setEditable(true);
		});
		
		btn_MemDrop.addActionListener(e -> {
			String confrimPass = "";
			JPasswordField jpf_ConfirmPass = new JPasswordField(15);
			Box box_Confirm = UserView.getPassBox(jpf_ConfirmPass);
			
			int value = JOptionPane.showConfirmDialog(this, box_Confirm
					, "비밀번호 확인", JOptionPane.YES_NO_OPTION);
			
			if (value == JOptionPane.YES_OPTION)
				confrimPass = String.valueOf(jpf_ConfirmPass.getPassword());
			
			if (confrimPass.equals(tf_pwd.getText()) && confrimPass != "") {
				int val = JOptionPane.showConfirmDialog(this,
							"모든 예약정보가 삭제됩니다.\n탈퇴하시겠습니까?", "회원탈퇴",
							JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION) {
					mDao.deleteData(tf_id.getText());
					JOptionPane.showMessageDialog(this
							, "그 동안 이용해주셔서 감사합니다.", "회원탈퇴",
							JOptionPane.PLAIN_MESSAGE);
					dispose();
					new CarLogin();
				}
			} else if (confrimPass.equals("")) {
				JOptionPane.showMessageDialog(this, 
						"비밀번호를 입력해주십시오.",
						"비밀번호 확인", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, 
						"비밀번호가 다릅니다.",
						"비밀번호 확인", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		tf_license.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if (!((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
						|| ke.getKeyChar() == '\b')) {
					ke.consume();
					JOptionPane.showMessageDialog(tf_license, "숫자만 입력할 수 있습니다.");
					tf_license.setText("");
					tf_license.requestFocus();
				}				
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
	}
	
	public static Box getPassBox(JPasswordField jpf_ConfirmPass) {
		
		JLabel lb_ConfirmPass = new JLabel("비밀번호를 확인합니다 : ");
		Box box_Confirm = Box.createHorizontalBox();
		
		box_Confirm.add(lb_ConfirmPass);
		box_Confirm.add(jpf_ConfirmPass);
		
		return box_Confirm;
	}
	
	//TODO:수정중
	private void setPanelSize() {
		pnNorth_Right.setBackground(Color.DARK_GRAY);
		pnNorth_Left.setBackground(Color.DARK_GRAY);
		jsp_Center.setPreferredSize(new Dimension(200, 200));
		pnCenter_Center.setPreferredSize(new Dimension(200, 500));
	}
}
