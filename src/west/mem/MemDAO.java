package west.mem;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import west.util.DbUtil;
import west.util.SqlUtil;
import west.view.AdminView;
import west.view.CarLogin;
import west.view.UserView;

public class MemDAO {
	Connection con;
	PreparedStatement psmt;
	Statement stmt;
	ResultSet rs;
	CarLogin login;
	IdPassSearchDialogue csd;

	public MemDAO() {
		con = DbUtil.getConnection();
	}

	public MemDAO(CarLogin login) {
		con = DbUtil.getConnection();
		this.login = login;
	}

	public MemDAO(IdPassSearchDialogue csd) {
		con = DbUtil.getConnection();
		this.csd = csd;
	}

	public void Login() {
		String id = login.tf_id.getText().trim();
		String pwd = login.tf_pwd.getText().trim();
		String mid ="";
		String mpwd="";
		String mname="";
		
		if (id.length() == 0) {
			JOptionPane.showMessageDialog(login, "ID를 입력하세요!");
			login.tf_pwd.setText(null);
			login.tf_id.requestFocus();
			return;
		} else if (pwd.length() == 0) {
			JOptionPane.showMessageDialog(login, "비밀번호를 입력하세요!");
			login.tf_pwd.requestFocus();
			return;
		}

		String sql = "select * from member where mid ='" + id + "'";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while(rs.next()){
				mid = rs.getString(1);
				mpwd = rs.getString(2);
				mname = rs.getString(3);
			}			
		} catch (Exception e) {
			System.out.println("ID, pass 검색 실패!" + e);
		}
		
		if (mid.equals("admin")) {
			JOptionPane.showMessageDialog(login, "관리자님 환영합니다.!");
			login.setVisible(false);
			new AdminView();
			return;
		}else if(pwd.equals(mpwd.trim())){
			JOptionPane.showMessageDialog(login, mname+"님 로그인을 환영합니다.");
			login.setVisible(false);
			new UserView(mid, pwd);
			return;
		}else if((pwd!=mpwd.trim())||(id!=mid.trim())){
			JOptionPane.showMessageDialog(login, "아이디 또는 비밀번호가 틀렸습니다.");
			login.tf_id.setText("");
			login.tf_id.requestFocus();
			login.tf_pwd.setText("");
		}

	}
	
	public int MemInsert(MemDTO dto) {
		int result = 0;
		if (dto == null) return result;
		String sql = "insert into member(mid, pwd, mname,license,tel,addr,age,email)" + "Values(?,?,?,?,?,?,?,?)";
		try {
			psmt = con.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getLicense());
			psmt.setString(5, dto.getTel());
			psmt.setString(6, dto.getAddr());
			psmt.setInt(7, dto.getAge());
			psmt.setString(8, dto.getEmail());

			result = psmt.executeUpdate();

			JOptionPane.showMessageDialog(login, "회원가입을 환영합니다.");

		} catch (Exception e) {
			System.err.println("가입시 오류");
		}
		return result;
	}
	
	public MemDTO selectUser(String mid) {
		MemDTO mDTO = null;
		try {
			psmt = con.prepareStatement(SqlUtil.MEM_IDSEARCH);
			psmt.setString(1, mid);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				mDTO = new MemDTO(rs.getString("MID"), rs.getString("PWD"), 
						rs.getString("MNAME"), rs.getString("LICENSE"), rs.getString("TEL"), 
						rs.getString("ADDR"), rs.getInt("AGE"), rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			System.err.println("유저조회예외");
		}
		return mDTO;
	}
		
	public void updateData(UserView mDial) {
		try {
			if (loginCheck(mDial)) {
					PreparedStatement pstmt = 
							con.prepareStatement(SqlUtil.MEM_UPDATE);
					pstmt.setString(1, mDial.tf_pwd.getText());
					pstmt.setString(2, mDial.tf_name.getText());
					pstmt.setString(3, mDial.tf_license.getText());
					pstmt.setString(4, mDial.tf_tel.getText());
					pstmt.setString(5, mDial.tf_addr.getText());
					pstmt.setInt(6, Integer.parseInt(mDial.tf_age.getText()));
					pstmt.setString(7, mDial.tf_email.getText());
					pstmt.setString(8, mDial.tf_id.getText());
					int val = pstmt.executeUpdate();
					
					if (val > 0)
						JOptionPane.showMessageDialog(mDial, 
								"수정되었습니다.", "확인", 
								JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public void deleteData(String mid) {
		System.out.println(mid);
		try {
			PreparedStatement pstmt = con.prepareStatement(SqlUtil.USER_UPDATEDROP);
			pstmt.setString(1, mid);
			pstmt.executeUpdate();
				
			pstmt = con.prepareStatement(SqlUtil.RES_MEM_DELETE);
			System.out.println(SqlUtil.RES_MEM_DELETE);
			pstmt.setString(1, mid);
			pstmt.executeUpdate();
		
			pstmt = con.prepareStatement(SqlUtil.MEM_DELETE);
			
			pstmt.setString(1, mid);
			pstmt.executeUpdate();
				
		} catch (SQLException se) {
			System.err.println("삭제오류");
		}
	}
	
	public int update(MemDTO dto) {
		int affectedRow = 0;
		
		try {
			psmt = con.prepareStatement(SqlUtil.MEM_UPDATE);
			psmt.setString(8, dto.getId());
			psmt.setString(1, dto.getPwd());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getLicense());
			psmt.setString(4, dto.getTel());
			psmt.setString(5, dto.getAddr());
			psmt.setInt(6, dto.getAge());
			psmt.setString(7, dto.getEmail());
			affectedRow=psmt.executeUpdate();
		} catch (Exception e) {
			System.err.println("수정 실패 : "+e.getMessage());
		}
		return affectedRow;
	}
	
	public int delete(String mid) {
		int affectedRow = 0;
		
		try {
			psmt = con.prepareStatement(SqlUtil.MEM_DELETE);
			psmt.setString(1, mid); 
			affectedRow=psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("삭제 실패 : "+e.getMessage());
		}
		return affectedRow;
	}

	public boolean isDuplicate(String mid) {	
			for(int i = 0;i<mid.length();i++){
				if (mid.charAt(i)<'a'||mid.charAt(i)>'z'){
					
				} else {
					try {
						String sql = "select count(*) from member where mid=?";
						psmt = con.prepareStatement(sql);
						psmt.setString(1, mid);
						rs = psmt.executeQuery();
						rs.next();
						int count = rs.getInt(1);
						if (count == 1) return true;
					} catch (Exception e) {
						System.err.println("중복예외");
					}
					break;
				}
			}
			return false;
	}

	public String check(String mid) {
		return mid;
	}

	public String SearchCheck(String lis) {
		
		String schId ="";
		String lisChk = "";
		
		try {
			String sql = "select * from member where license=?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, lis);
			rs = psmt.executeQuery();
			while(rs.next()){
				schId = rs.getString(1);
				lisChk = rs.getString(4);
			}

			if (lis.equals(lisChk)) {
				JOptionPane.showMessageDialog(login, "고객의 아이디는 " + schId + " 입니다.");
			} else {
				JOptionPane.showMessageDialog(login, "일치하는 고객정보가 없습니다.");
			}

		} catch (Exception e) {
			System.err.println("아이디조회 예외");
		}
		return lis;
	}

	public String SearchCheck(String id, String lis) {
		String scPwd="";
		String scLis = "";
		String scId="";
		try {
			String sql = "select * from member where mid=? and license = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, lis);
			rs = psmt.executeQuery();
			
			while(rs.next()){
			scId = rs.getString(1);
			scPwd = rs.getString(2);
			scLis = rs.getString(4);
			}
			if (id.equals(scId)&&lis.equals(scLis)){
				JOptionPane.showMessageDialog(login, "고객의 비밀번호는 " + scPwd + " 입니다.");
			} else {
				JOptionPane.showMessageDialog(login, "일치하는 고객정보가 없습니다.");
			}
		} catch (SQLException e) {
			System.err.println("sql오류");
		}
		return id;
	}
	
	public void getUserSearch(DefaultTableModel dt_Center
			, String searchField, String word) {
		String condition = "";
		
		switch (searchField) {
		case "회원아이디" :
			condition = "mid";
			break;
		case "회원이름" :
			condition = "mname";
			break;
		case "면허번호" :
			condition = "license";
			break;
		}
		
		String sql = "select * from member where lower(" 
			+ condition + ") like lower('%" + word + "%')";
		
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			for (int i = 0; i < dt_Center.getRowCount();)
				dt_Center.removeRow(0);
			
			while (rs.next()) {
				Object data[] = { 
						rs.getString("MID")
						, rs.getString("PWD"), rs.getString("MNAME")
						, rs.getString("LICENSE"), rs.getString("TEL")
						, rs.getString("ADDR"), rs.getInt("AGE") 
						, rs.getString("EMAIL")
				};
				dt_Center.addRow(data);
			}
		} catch (SQLException e) {
			System.err.println(e + " => getUserSearch fail");
		}
	}
	public boolean loginCheck(Object obj) {
		ServiceJoinDialogue iDia = null;
		UserView uDia = null;
		JTextField jtf_pwd, jtf_name, jtf_license, jtf_tel, jtf_addr,
			jtf_age, jtf_id;
		boolean flag = false;
		
		
		if (obj instanceof ServiceJoinDialogue) {
			iDia = (ServiceJoinDialogue) obj;
			jtf_id = iDia.tf_id;
			jtf_pwd = iDia.tf_pwd;
			jtf_name = iDia.tf_name;
			jtf_license = iDia.tf_license;
			jtf_tel = iDia.tf_tel; 
			jtf_addr = iDia.tf_addr;
			jtf_age = iDia.tf_age; 
		} else {
			uDia = (UserView) obj;
			jtf_id = uDia.tf_id;
			jtf_pwd = uDia.tf_pwd;
			jtf_name = uDia.tf_name;
			jtf_license = uDia.tf_license;
			jtf_tel = uDia.tf_tel; 
			jtf_addr = uDia.tf_addr;
			jtf_age = uDia.tf_age; 
		}
		
		String id = jtf_id.getText().trim();
		String pwd = jtf_pwd.getText().trim();
		String name = jtf_name.getText().trim();
		String license = jtf_license.getText().trim();
		String tel = jtf_tel.getText().trim();
		String addr = jtf_addr.getText().trim();
		String age = jtf_age.getText();
		
		if (id.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "ID를 입력해주세요!");
			jtf_pwd.setText("");
			jtf_id.requestFocus();
			return flag;
		} else if (pwd.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "비밀번호를 입력해주세요!");
			jtf_pwd.requestFocus();
			return flag;
		}else if (pwd.length() >=6) {
			JOptionPane.showMessageDialog((Component) obj, "6글자이상 입력해주세요.");
			jtf_pwd.requestFocus();
			return flag;
		} else if (pwd.length() > 15) {
			JOptionPane.showMessageDialog((Component) obj, "15글자를 초과할수 없습니다.");
			jtf_pwd.requestFocus();
			return flag;
		}else if (name.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "이름를 입력해주세요!");
			jtf_name.requestFocus();
			return flag;
		} else if (license.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "면허번호를 입력해주세요!");
			jtf_license.requestFocus();
			return flag;
		} else if (tel.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "전화번호를 입력해주세요!");
			jtf_tel.requestFocus();
			return flag;
		} else if (addr.length() == 0) {
			JOptionPane.showMessageDialog((Component) obj, "주소를 입력해주세요!");
			jtf_addr.requestFocus();
			return flag;
		} else if (age.equals("-1")) {
			JOptionPane.showMessageDialog((Component) obj, "나이를 입력해주세요!");
			jtf_age.requestFocus();
			return flag;
		}
		return true;
	}
	
	public void setDialTextField(UserView mDia, MemDTO mDTO) {
		//TODO: 복수레코드선택문제
//		if (frame.jt_Center.getSelectedRows().length > 1)
//			JOptionPane.showMessageDialog(rDia, "레코드는 한줄만 선택가능합니다."
//					, "선택에러", JOptionPane.WARNING_MESSAGE);
		
		mDia.tf_id.setText(mDTO.getId());
		mDia.tf_pwd.setText(mDTO.getPwd());
		mDia.tf_name.setText(mDTO.getName());
		mDia.tf_license.setText(mDTO.getLicense());
		mDia.tf_tel.setText(mDTO.getTel());
		mDia.tf_addr.setText(mDTO.getAddr());
		mDia.tf_age.setText(String.valueOf(mDTO.getAge()));
		mDia.tf_email.setText(mDTO.getEmail());
		
		mDia.tf_id.setEditable(false);
		mDia.tf_pwd.setEditable(false);
		
		mDia.tf_id.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_pwd.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_name.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_license.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_tel.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_addr.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_age.setHorizontalAlignment(SwingConstants.CENTER);
		mDia.tf_email.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void setTableData(DefaultTableModel tableModel, JTable jTable, String query, String mid) {
		try {
			Connection con = DbUtil.getConnection();
			psmt = con.prepareStatement(query);
			psmt.setString(1, mid);
			ResultSet rs = psmt.executeQuery();
			Vector<String> carColNames = DbUtil.getColumnNames(rs);
			Vector<Vector<Object>> carData = DbUtil.getRecordAll(carColNames, rs);

			int rowCount = tableModel.getRowCount();
			int columnCount = tableModel.getColumnCount();

			if (!carData.isEmpty())
				tableModel.setDataVector(carData, carColNames);
			else
				for (int i = 0; i < rowCount; i++)
					tableModel.removeRow(i);

			TableColumnModel tcm = jTable.getColumnModel();
			jTable.setRowHeight(25);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(JLabel.CENTER);

			for (int i = 1; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setCellRenderer(dtcr);
			}

			for (int i = 0; i < rowCount; i++)
				for (int j = 1; j < columnCount; j++)
					tableModel.isCellEditable(i, j);
		} catch (SQLException e) {
			System.out.println("테이블셋팅 에러");
		}
	}
	
}
