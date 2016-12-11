package west.car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import west.util.DbUtil;
import west.util.SqlUtil;
import west.view.AdminView;

public class CarDAO implements SqlUtil {
	Connection con;
	Statement st;
	PreparedStatement ps;
	AdminView frame;
	Object[][] rowData;
	
	public CarDAO() {
		con = DbUtil.getConnection();
	}

	public CarDAO(AdminView frame) {
		this.frame = frame;
		con = DbUtil.getConnection();
	}

	public int insert(CarDTO dto) {
		int affectedRow = 0;
		
		try {
			ps = con.prepareStatement(SqlUtil.CAR_INSERT);
			ps.setString(1,  dto.getCid());
			ps.setString(2,  dto.getCname());
			ps.setInt(3,  dto.getPrice());
			ps.setString(4,  dto.getRes());
			ps.setDate(5,  dto.getResdate());
			affectedRow = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("입력 실패 : " + e.getMessage());
		}
		return affectedRow;
	}
	public int update(CarDTO dto) {
		int affectedRow = 0;
		
		try {
			ps = con.prepareStatement(SqlUtil.CAR_UPDATE);
			ps.setString(5, dto.getCid());
			ps.setString(1, dto.getCname());
			ps.setInt(2, dto.getPrice());
			ps.setString(3, dto.getRes());
			ps.setDate(4, dto.getResdate());
			affectedRow=ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("수정 실패 : "+e.getMessage());
		}
		return affectedRow;
	}
	public int delete(String cid) {
		int affectedRow = 0;
		
		try {
			ps = con.prepareStatement(SqlUtil.CAR_DELETE);
			ps.setString(1, cid);
			affectedRow=ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("삭제 실패 : "+e.getMessage());
		}
		return affectedRow;
	}
	
	public void getUserSearch(DefaultTableModel dt_Center
			, String searchField, String word) {
		String condition = "";
		
		switch (searchField) {
		case "차량번호" :
			condition = "cid";
			break;
		case "차종" :
			condition = "cname";
			break;
		case "예약여부" :
			condition = "res";
			break;
		}
		
		String sql = "SELECT * FROM car where lower(" 
			+ condition + ") like lower('%" + word + "%')";
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			for (int i = 0; i < dt_Center.getRowCount();)
				dt_Center.removeRow(0);
			
			while (rs.next()) {
				Object data[] = { rs.getString(1)
						, rs.getString(2), rs.getInt(3), rs.getString(4) };
				dt_Center.addRow(data);
			}
		} catch (SQLException e) {
			System.err.println(e + " => getUserSearch fail");
		}
	}

}