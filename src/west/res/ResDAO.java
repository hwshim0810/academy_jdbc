package west.res;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import west.mem.MemDAO;
import west.mem.MemResInsertDialogue;
import west.util.DbUtil;
import west.util.SqlUtil;
import west.view.AdminView;
import west.view.UserView;

public class ResDAO implements SqlUtil {
	Connection con;
	PreparedStatement pstmt, psmtScroll;
	Statement stmt;
	DatabaseMetaData meta;
	Object[][] rowData;
	AdminView frame;
	UserView userFrame;
	private UserView view;
	public static final int OK = 1;
	public static final int NO = -1;
	
	public ResDAO() {
		con = DbUtil.getConnection();
	}
	
	public ResDAO(UserView userFrame) {
		this.userFrame = userFrame;
		con = DbUtil.getConnection();
	}	
	
	public ResDAO(AdminView frame) {
		this.frame = frame;
		con = DbUtil.getConnection();
	}
	
	public int updateData(ResDialogue rDial, AdminView frame) {
		this.frame = frame;
		int result = 0;
		boolean flag = false;
		
		Date updateSDate = toDate(rDial.jtf_StYear.getText()
				+ rDial.jtf_StMonth.getText()
				+ rDial.jtf_StDay.getText());
		Date updateEDate = toDate(rDial.jtf_EdYear.getText()
				+ rDial.jtf_EdMonth.getText()
				+ rDial.jtf_EdDay.getText());
		
		flag = updateSDate.compareTo(updateEDate) > 0 ? false : true;
		
		if (flag) {
			try {
				
					if (!ResDialogue.cid.equals(rDial.jtf_Cid.getText())) {
						pstmt = con.prepareStatement(RES_CARUPDATE);
						pstmt.setString(1, "N");
						pstmt.setString(2, ResDialogue.cid);
						pstmt.executeUpdate();

						pstmt = con.prepareStatement(RES_CARUPDATE);
						pstmt.setString(1, "Y");
						pstmt.setString(2, rDial.jtf_Cid.getText());
						pstmt.executeUpdate();
					}
					
					pstmt = con.prepareStatement(RES_UPDATE);
					pstmt.setString(1, rDial.jtf_Cid.getText());
					pstmt.setString(2, rDial.jtf_Cname.getText());
					pstmt.setDate(3, updateSDate);
					pstmt.setDate(4, updateEDate);
					pstmt.setInt(5, Integer.parseInt(rDial.jtf_Rid.getText()));
					result = pstmt.executeUpdate();
					
					if (result > 0) setTableData(frame.dt_Center
							, frame.jt_Center, SqlUtil.RES_TOTAL);
					
					JOptionPane.showMessageDialog(rDial, 
							"수정되었습니다.", "확인", 
							JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException se) {
				System.err.println("수정오류");
			} 
				return OK;
		} else {
			JOptionPane.showMessageDialog(rDial, 
					"시작일이 종료일이후 입니다. 다시 입력해주세요.", 
					"날짜입력오류", JOptionPane.ERROR_MESSAGE);
			return NO;
		}
	}
	
	public void deleteData(String rid, AdminView frame) {
		this.frame = frame;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(RES_DELETE);
			pstmt.setInt(1, Integer.parseInt(rid));
			result = pstmt.executeUpdate();
			
			if (result > 0) setTableData(frame.dt_Center
					, frame.jt_Center, SqlUtil.RES_TOTAL);
		} catch (SQLException se) {
			System.err.println("삭제오류");
		}
	}

	
	public boolean insertData(MemResInsertDialogue iDial) {
		int result = 0;
		boolean flag = false;
		String mid;
		
		String cid = MemResInsertDialogue.jtf_Cid.getText().trim();
		if (iDial.adFrame != null) mid = MemResInsertDialogue.jtf_Mname.getText();
		else mid = iDial.mid;
		String cname = MemResInsertDialogue.jtf_Cname.getText().trim();
		
		if (cid.equals("") || cname.equals("")) return flag;
		
		java.util.Date today = new java.util.Date();
		
		Date updateSDate = toDate(
				String.valueOf(iDial.jcb_Year.getSelectedItem().toString()) + 
				String.valueOf(iDial.jcb_Month.getSelectedItem().toString()) +
				String.valueOf(iDial.jcb_Day.getSelectedItem().toString()));
		Date updateEDate = toDate(
				String.valueOf(iDial.jcb_Year2.getSelectedItem().toString()) + 
				String.valueOf(iDial.jcb_Month2.getSelectedItem().toString()) +
				String.valueOf(iDial.jcb_Day2.getSelectedItem().toString()));
		
		flag = updateSDate.compareTo(updateEDate) > 0 ? false : true;
		flag = updateSDate.compareTo(today) < 0 ? false : true;
		
		if (flag) {
			try {
				pstmt = con.prepareStatement(RES_INSERT);
				pstmt.setString(1, cid);
				pstmt.setString(2, mid);
				pstmt.setString(3, cname);
				pstmt.setDate(4, updateSDate);
				pstmt.setDate(5, updateEDate);
				
				result = pstmt.executeUpdate();
				
				if (result > 0)  {
					pstmt = con.prepareStatement(RES_CARUPDATE);
					pstmt.setString(1, "Y");
					pstmt.setString(2, cid);
					result = pstmt.executeUpdate();
				}
				
			} catch (SQLException se) {
				System.err.println("추가오류");
				se.printStackTrace();
			}
		}
		return flag;
	}
	
	public Date toDate(String date) {
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
	
	private boolean isReserve(String cid) {
		try {
			pstmt = con.prepareStatement(ISRES_SEARCH);
			pstmt.setString(1, cid);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) return true;
		} catch (SQLException e) {
			System.err.println("존재하는 레코드");
		}
		return false;
	}
	
	public boolean isUserSee(String mid) {
		try {
			pstmt = con.prepareStatement(ISUSERSEE);
			pstmt.setString(1, mid);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) return false;
		} catch (SQLException e) {
			System.err.println("유저찾기 예외");
		}
		return true;
	}
	
	public boolean isPossible() {
		try {
			ResultSet rs = con.prepareStatement(SqlUtil.ISPOSIIBLE)
								.executeQuery();
			if (rs.next()) return true;
		} catch (SQLException e) {
			System.err.println("존재하는 레코드");
		}
		return false;
	}
	
	public void setTableData(DefaultTableModel tableModel, JTable jTable, 
			String query) {
		try {
			Connection conn = DbUtil.getConnection();
			ResultSet rs = conn.prepareStatement(query).executeQuery();
			Vector<String> carColNames = DbUtil.getColumnNames(rs);
			Vector<Vector<Object>> carData = 
					DbUtil.getRecordAll(carColNames, rs);
			
			int rowCount = tableModel.getRowCount();
			int columnCount = tableModel.getColumnCount();
			
			if(!carData.isEmpty())
				tableModel.setDataVector(carData, carColNames);
			else 
				for(int i = 0; i < rowCount; i++)
					tableModel.removeRow(i);
			
			TableColumnModel tcm = jTable.getColumnModel();		
			jTable.setRowHeight(25);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			DefaultTableCellRenderer header = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(JLabel.CENTER);
			header.setBackground(Color.DARK_GRAY);
			header.setForeground(Color.WHITE);
			header.setHorizontalAlignment(JLabel.CENTER);

			for(int i = 0; i < tcm.getColumnCount(); i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
				tcm.getColumn(i).setHeaderRenderer(header);
			}
			
			for(int i = 0; i < rowCount; i++)
				for(int j = 1; j < columnCount; j++)
					tableModel.isCellEditable(i, j);
		} catch (SQLException e) {
			System.err.println("테이블셋팅 에러");
		}
	}
	
	public void getUserSearch(DefaultTableModel dt_Center
			, String searchField, String word) {
		String condition = "";
		
		switch (searchField) {
		case "차량번호" :
			condition = "r.cid";
			break;
		case "차종" :
			condition = "r.cname";
			break;
		case "회원아이디" :
			condition = "r.mid";
			break;
		case "예약일자" : 
			condition = "r.resdate";
		}
		
		String sql = "SELECT r.rid, r.cid, "
				+ "r.mid, r.cname, r.startdate, r.enddate, "
				+ "r.enddate-r.startdate+1 \"pd\", "
				+ "c.price*(r.enddate-r.startdate+1) \"pl\", "
				+ "r.resdate "
				+ "FROM car c, reserve r "
				+ "WHERE c.cid = r.cid AND "
				+ condition + " like '%" + word + "%' "
				+ "ORDER BY resdate";
		
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			for (int i = 0; i < dt_Center.getRowCount();)
				dt_Center.removeRow(0);
			while (rs.next()) {
				Object data[] = { 
						rs.getInt("RID")
						, rs.getString("CID"), rs.getString("MID")
						, rs.getString("CNAME"), rs.getString("STARTDATE")
						, rs.getString("ENDDATE"), rs.getString("RESDATE") 
						, rs.getString("pd"), rs.getInt("pl")
						, rs.getString("RESDATE")
						};
				dt_Center.addRow(data);
			}
		} catch (SQLException e) {
			System.err.println(e + " => getUserSearch fail");
		}
	}
	
	public void setDialTextField(AdminView frame, ResDialogue rDia,
			ActionEvent ae) {
		//TODO: 복수레코드선택문제 일단 놔둠 수정한거아님
//		if (frame.jt_Center.getSelectedRows().length > 1)
//			JOptionPane.showMessageDialog(rDia, "레코드는 한줄만 선택가능합니다."
//					, "선택에러", JOptionPane.WARNING_MESSAGE);
		int row = frame.jt_Center.getSelectedRow();
		
		rDia.jtf_Rid.setText(frame.jt_Center.getValueAt(row, 0).toString());
		rDia.jtf_Cid.setText(frame.jt_Center.getValueAt(row, 1).toString());
		rDia.jtf_Mid.setText(frame.jt_Center.getValueAt(row, 2).toString());
		rDia.jtf_Cname.setText(frame.jt_Center.getValueAt(row, 3).toString());
		
		rDia.jtf_StYear.setText(frame.jt_Center.getValueAt(row, 4)
										   .toString().substring(0, 4));
		rDia.jtf_StMonth.setText(frame.jt_Center.getValueAt(row, 4)
											.toString().substring(5, 7));
		rDia.jtf_StDay.setText(frame.jt_Center.getValueAt(row, 4)
											.toString().substring(8, 10));
		
		rDia.jtf_EdYear.setText(frame.jt_Center.getValueAt(row, 5)
											.toString().substring(0, 4));
		rDia.jtf_EdMonth.setText(frame.jt_Center.getValueAt(row, 5)
											.toString().substring(5, 7));
		rDia.jtf_EdDay.setText(frame.jt_Center.getValueAt(row, 5) 
											.toString().substring(8, 10));

		rDia.jtf_ResYear.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(0, 4));
		rDia.jtf_ResMonth.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(5, 7));
		rDia.jtf_ResDay.setText(frame.jt_Center.getValueAt(row, 8)
											.toString().substring(8, 10));
		
		rDia.jtf_Rid.setEditable(false);
		rDia.jtf_Mid.setEditable(false);
		rDia.jtf_ResYear.setEditable(false);
		rDia.jtf_ResMonth.setEditable(false);
		rDia.jtf_ResDay.setEditable(false);
		rDia.jtf_Cid.setEditable(false);
		rDia.jtf_Cname.setEditable(false);
		
		if (ae.getActionCommand() == "삭제") {
			rDia.jtf_StYear.setEditable(false);
			rDia.jtf_StMonth.setEditable(false);
			rDia.jtf_StDay.setEditable(false);
			rDia.jtf_EdYear.setEditable(false);
			rDia.jtf_EdMonth.setEditable(false);
			rDia.jtf_EdDay.setEditable(false);
		} 
		
		rDia.jtf_ResYear.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_ResMonth.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_ResDay.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_StYear.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_StMonth.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_StDay.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_EdYear.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_EdMonth.setHorizontalAlignment(SwingConstants.CENTER);
		rDia.jtf_EdDay.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void UserResUpdate(UserView view1) {
		this.view = view1;

		int result = 0;
		boolean flag = false;

		Date updateSDate = toDate(view1.jtf_Year2.getText() + view1.jtf_Month2.getText() + view1.jtf_Day2.getText());
		Date updateEDate = toDate(view1.jtf_Year3.getText() + view1.jtf_Month3.getText() + view1.jtf_Day3.getText());

		flag = updateSDate.compareTo(updateEDate) > 0 ? false : true;

		if (flag) {
			try {
				if (isReserve(view.rid.getText()))
					;
				else {
					pstmt = con.prepareStatement(SqlUtil.RES_CARUPDATE);
					pstmt.setString(1, "Y");
					pstmt.setString(2, view.rid.getText());
					pstmt.executeUpdate();

					pstmt = con.prepareStatement(SqlUtil.RES_UPDATE);
					pstmt.setString(1, UserView.jtf_UserCid.getText());
					pstmt.setString(2, UserView.jtf_UserCName.getText());
					pstmt.setDate(3, updateSDate);
					pstmt.setDate(4, updateEDate);
					pstmt.setInt(5, Integer.parseInt(view1.rid.getText()));
					result = pstmt.executeUpdate();

					if (result > 0) {
						setTableData(view1.dt_Center, view1.jt_Center, SqlUtil.User_ResAll);
						JOptionPane.showMessageDialog(view1, "수정되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
						
					}
				}
			} catch (SQLException se) {
				System.err.println("수정오류");
			}
			return;
		} else {
			JOptionPane.showMessageDialog(view1, "시작일이 종료일이후 입니다. 다시 입력해주세요.", "날짜입력오류", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void UserResDelete(UserView view, String mid) {
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		this.view = view;
		int result = 0;
		try {
			pstmt1 = con.prepareStatement(SqlUtil.UserRES_DELETE);
			pstmt1.setInt(1, Integer.parseInt(view.rid.getText()));
			pstmt1.executeUpdate();
			
			pstmt2 = con.prepareStatement(SqlUtil.RES_CARUPDATE);
			pstmt2.setString(1, "N");
			pstmt2.setString(2, UserView.jtf_UserCid.getText());
			result = pstmt2.executeUpdate();
			
			MemDAO mDao = new MemDAO();
			if (result > 0)
				mDao.setTableData(view.dt_Center, view.jt_Center, SqlUtil.User_ResAll
						, mid);
		} catch (SQLException se) {
			System.err.println("삭제오류");
		} finally {
			DbUtil.close(pstmt1);
			DbUtil.close(pstmt2);
		}
	}
	
}