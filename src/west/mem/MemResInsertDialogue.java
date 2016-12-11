package west.mem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import west.res.PossibleCarDialogue;
import west.res.ResDAO;
import west.util.SqlUtil;
import west.view.AdminView;
import west.view.UserView;

public class MemResInsertDialogue extends JDialog {
	private static final long serialVersionUID = -235882319625547411L;
	
	Date sDate, eDate;

	MemDAO mDao;
	ResDAO rDao;

	JPanel pn_North, pn_Center, pn_South, pnCenter_West, pnCenter_Center,
		pnCenter_South, pnSouth_West, pnSouth_Center, pnSouth_South,
		pnNorth_Center, pnNorth_South; 
	
	JLabel lb_Sdate, lb_Edate, lb_Year, lb_Month, lb_Day,
		lb_Cid, lb_Cname, lb_Title, lb_Mname;
	JLabel lb_Blank, lb_Blank2, lb_Blank3, lb_Blank4, lb_Blank5;
	
	Font ft_Title, ft_SideLabel, ft_UpperLabel, ft_Item;

	public static JTextField jtf_Cid, jtf_Cname, jtf_Mname;
	
	Image logo;

	JButton btn_CarSearch, btn_Confirm, btn_Cancel;
	
	public JComboBox<String> jcb_Year, jcb_Month, jcb_Day, 
			jcb_Year2, jcb_Month2, jcb_Day2;
	
	public String mid;
	
	UserView uvFrame = null;
	public AdminView adFrame = null;
	
	public static int InsertInnerState;
	public static final int INNEROPEN = 1;
	public static final int INNERCLOSE = 2;
	
	
	public MemResInsertDialogue(String mid, JFrame frame) {
		createPanel();
		createLabel();
		createBtn();
		createCombo();
		createTextField();
		setLogo();	
		addLabel();
		addBtn();
		addCombo();
		addTextField();
		addPanel();
		setFont();
		setSize();
		addActionEvent();
		mDao = new MemDAO();
		rDao = new ResDAO();
		this.mid = mid;
		InsertInnerState = INNERCLOSE;
		
		
		setTitle("예약하기");
		setBounds(1200,150,360,400);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if (frame instanceof UserView) {
			uvFrame = (UserView) frame;
			lb_Mname.setVisible(false);
			jtf_Mname.setVisible(false);
		}
		else adFrame = (AdminView) frame;
	}
	
	private void createPanel() {
		pn_North = new JPanel(new BorderLayout());
		pnNorth_Center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnNorth_South = new JPanel(new GridLayout(1, 4));
		
		pn_Center = new JPanel(new BorderLayout());
		pnCenter_West = new JPanel(new GridLayout(2, 1));
		pnCenter_Center = new JPanel(new GridLayout(2, 3, 10, 5));
		pnCenter_South = new JPanel(new GridLayout(1, 2, 10, 10));
		
		pn_South = new JPanel(new BorderLayout());
		pnSouth_Center = new JPanel(new GridLayout(3, 1, 10, 10));
		pnSouth_West = new JPanel(new GridLayout(3, 1));
		pnSouth_South = new JPanel(new GridLayout(1, 4, 10, 10));
	}
	
	private void setFont() {
		lb_Title.setFont(ft_Title);
		lb_Sdate.setFont(ft_SideLabel);
		lb_Edate.setFont(ft_SideLabel);
		lb_Cid.setFont(ft_SideLabel);
		lb_Cname.setFont(ft_SideLabel);
		lb_Mname.setFont(ft_SideLabel);
	}
	
	private void createLabel() {
		lb_Title = new JLabel("예 약 하 기");
		lb_Sdate = new JLabel("대여 시작일");
		lb_Edate = new JLabel("대여 마감일");
		lb_Year = new JLabel("연", SwingConstants.CENTER);
		lb_Month = new JLabel("월", SwingConstants.CENTER);
		lb_Day = new JLabel("일", SwingConstants.CENTER);
		lb_Cid = new JLabel("차량번호");
		lb_Cname = new JLabel("차량이름");
		lb_Blank = new JLabel("");
		lb_Blank2 = new JLabel("");
		lb_Blank3 = new JLabel("");
		lb_Blank4 = new JLabel("");
		lb_Blank5 = new JLabel("");
		lb_Mname = new JLabel("회원아이디");
	}
	
	private void createBtn() {
		btn_CarSearch = new JButton("차량검색");
		btn_Confirm = new JButton("예약");
		btn_Cancel = new JButton("취소");
		
	}
	
	private void createCombo() {
		String[] year = {"2016", "2017", "2018", "2019", "2020"};
		String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
						"10", "11", "12"};
		String[] day = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", 
				"19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31"};
		
		DefaultComboBoxModel<String> model_Year = new DefaultComboBoxModel<>(year);
		DefaultComboBoxModel<String> model_Month = new DefaultComboBoxModel<>(month);
		DefaultComboBoxModel<String> model_Day = new DefaultComboBoxModel<>(day);
		DefaultComboBoxModel<String> model_Year2 = new DefaultComboBoxModel<>(year);
		DefaultComboBoxModel<String> model_Month2 = new DefaultComboBoxModel<>(month);
		DefaultComboBoxModel<String> model_Day2 = new DefaultComboBoxModel<>(day);
		
		jcb_Year = new JComboBox<>(model_Year);
		jcb_Month = new JComboBox<>(model_Month);
		jcb_Day = new JComboBox<>(model_Day);

		jcb_Year2 = new JComboBox<>(model_Year2);
		jcb_Month2 = new JComboBox<>(model_Month2);
		jcb_Day2 = new JComboBox<>(model_Day2);
	}
	
	private void createTextField() {
		jtf_Cid = new JTextField();
		jtf_Cname = new JTextField();
		jtf_Mname = new JTextField();
	}
	
	private void addLabel() {
		pnNorth_Center.add(lb_Title);
		pnNorth_South.add(lb_Blank);
		pnNorth_South.add(lb_Year);
		pnNorth_South.add(lb_Month);
		pnNorth_South.add(lb_Day);
		
		pnCenter_West.add(lb_Sdate);
		pnCenter_West.add(lb_Edate);
		
		pnSouth_West.add(lb_Cid);
		pnSouth_West.add(lb_Cname);
		pnSouth_West.add(lb_Mname);
	}
	
	private void setLogo() {
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		logo = kit.getImage("img/Caricon.png"); 
		setIconImage(logo); 
	}
	
	private void addTextField() {
		pnSouth_Center.add(jtf_Cid);
		pnSouth_Center.add(jtf_Cname);
		pnSouth_Center.add(jtf_Mname);
		jtf_Cid.setEditable(false);
		jtf_Cname.setEditable(false);
	}
	
	private void addBtn() {
		pnCenter_South.add(lb_Blank2);
		pnCenter_South.add(lb_Blank5);
		pnCenter_South.add(btn_CarSearch);
		
		pnSouth_South.add(lb_Blank3);
		pnSouth_South.add(btn_Confirm);
		pnSouth_South.add(btn_Cancel);
		pnSouth_South.add(lb_Blank4);
	}
	
	private void addCombo() {
		pnCenter_Center.add(jcb_Year);
		pnCenter_Center.add(jcb_Month);
		pnCenter_Center.add(jcb_Day);
		
		pnCenter_Center.add(jcb_Year2);
		pnCenter_Center.add(jcb_Month2);
		pnCenter_Center.add(jcb_Day2);
	}
	
	private void addPanel() {
		pn_North.add(pnNorth_Center, BorderLayout.CENTER);
		pn_North.add(pnNorth_South, BorderLayout.SOUTH);
		
		pn_Center.add(pnCenter_Center, BorderLayout.CENTER);
		pn_Center.add(pnCenter_West, BorderLayout.WEST);
		pn_Center.add(pnCenter_South, BorderLayout.SOUTH);
		
		pn_South.add(pnSouth_Center, BorderLayout.CENTER);
		pn_South.add(pnSouth_West, BorderLayout.WEST);
		pn_South.add(pnSouth_South, BorderLayout.SOUTH);
		
		add(pn_North, BorderLayout.NORTH);
		add(pn_Center, BorderLayout.CENTER);
		add(pn_South, BorderLayout.SOUTH);
	}

	private void addActionEvent() {
		btn_Cancel.addActionListener(e -> {
			UserView.dialogueState = UserView.NOTOPEN;
			AdminView.dialogueState=AdminView.NOTOPEN;
			dispose();
		});
		
		btn_Confirm.addActionListener(e -> {
			   int val = JOptionPane.showConfirmDialog(this, 
			     "차량번호 : " + MemResInsertDialogue.jtf_Cid.getText() +
			     "\n차종 : " + MemResInsertDialogue.jtf_Cname.getText() +
			     "\n" +this.jcb_Year.getSelectedItem().toString() + "년 "
			     +this.jcb_Month.getSelectedItem().toString() + "월 "
			     +this.jcb_Day.getSelectedItem().toString() + "일"
			     +" ~ " + jcb_Year2.getSelectedItem().toString() + "년 "
			     +this.jcb_Month2.getSelectedItem().toString() + "월 "
			     +this.jcb_Day2.getSelectedItem().toString() + "일" +
			     "\n위 정보로 예약합니다.\n예약하시겠습니까?", 
			     "예약확인", JOptionPane.YES_NO_OPTION);
			   
			   if (val == JOptionPane.YES_OPTION) {
				   if (jtf_Mname.getText().equals("")&&adFrame != null) {
					   JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
					   return;
				   }
				     if (rDao.insertData(this)) {
					    JOptionPane.showMessageDialog(this, 
					      "예약되었습니다", "예약완료",
					      JOptionPane.INFORMATION_MESSAGE);
					    AdminView.dialogueState = AdminView.NOTOPEN;
					    if (uvFrame != null) 
					    	mDao.setTableData(uvFrame.dt_Center, uvFrame.jt_Center
					    			, SqlUtil.User_ResAll, mid);
					    else {
					    	rDao.setTableData(adFrame.dt_Center, adFrame.jt_Center
					    			, SqlUtil.RES_TOTAL);
					    }
					    dispose();
				     } else {
				    	 JOptionPane.showMessageDialog(this,
				    			 "차량과 날짜를 확인해주세요.", "확인",
				    			 JOptionPane.WARNING_MESSAGE);
				     }
			   }
			  });
		
		btn_CarSearch.addActionListener(e -> {
			InsertInnerState = INNEROPEN;
			new PossibleCarDialogue();
		});
	}
	
	private void setSize() {
		jcb_Year.setFont(ft_Item);
		
		pnNorth_South.setBorder(BorderFactory.createLineBorder(Color.black));
		pn_South.setBorder(BorderFactory.createLineBorder(Color.black));
		pnCenter_Center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pnSouth_South.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pnSouth_Center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pnCenter_South.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	}

}