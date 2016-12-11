package west.util;

public interface SqlUtil {
	public static final String RES_TOTAL = "SELECT r.rid \"예약번호\", "
			+ "r.cid \"차량번호\", "
			+ "r.mid \"회원아이디\", r.cname \"차종\", "
			+ "r.startdate \"대여시작일\", r.enddate \"대여마감일\", "
			+ "r.enddate-r.startdate+1 \"대여일수\", "
			+ "c.price*(r.enddate-r.startdate+1) \"총랜트비\", "
			+ "r.resdate \"차량예약일\" "
			+ "FROM car c, reserve r "
			+ "WHERE c.cid = r.cid "
			+ "ORDER BY r.resdate desc";
	
	public static final String CAR_TOTAL = "SELECT cid \"차량번호\", "
			+ "cname \"차종\", "
			+ "price \"대여료\", res \"예약여부\", resdate \"등록날짜\" FROM car "
			+ "ORDER BY cname";
	
	public static final String MEM_TOTAL = "SELECT MID \"회원아이디\", "
			+ "PWD \"비밀번호\", "
			+ "MNAME \"회원이름\", LICENSE \"면허번호\", TEL \"전화번호\", "
			+ "ADDR \"주소\", AGE \"나이\", EMAIL \"이메일주소\" "
			+ "FROM MEMBER ORDER BY MID";
	
	public static final String RESCAR_SEARCH = "SELECT cid \"차량번호\", "
			+ "cname \"차종\", "
			+ "resdate \"등록날짜\" FROM car WHERE res = 'N'";
	
	public static final String ISRES_SEARCH = "SELECT cid, res "
			+ "FROM car WHERE cid = ? and res ='Y'";
	
	public static final String ISPOSIIBLE = "SELECT cid \"차량번호\", cname \"차종\", price \"렌트비용\", res \"예약여부(Y/N)\" FROM car "
			+ "WHERE res = 'N'";
	
	public static final String RES_DELETE = "DELETE FROM reserve "
			+ "WHERE rid = ?";
	
	public static final String RES_INSERT = "INSERT INTO reserve "
			+ "VALUES(seq_res_rid.nextval, ?, ?, ?, ?, ?, sysdate)";
	
	public static final String RES_UPDATE = "UPDATE reserve "
			+ "SET cid = ?, cname = ?, startdate = ?, enddate = ?, "
			+ "resdate = sysdate WHERE rid = ?";
	
	public static final String RES_CARUPDATE = "UPDATE car "
			+ "SET res = ? WHERE cid = ?";
	
	public static final String CAR_INSERT = "INSERT INTO car values "
			   + "(?,?,?,?,?)";
			 
	 public static final String CAR_DELETE = "DELETE FROM car "
			   + "WHERE cid = ?";
			 
	 public static final String CAR_UPDATE = "UPDATE car "
			   + "SET cname = ?, price = ?, res = ?, "
			   + "resdate = ? WHERE cid = ?";
	 
	 public static final String MEM_IDSEARCH = "SELECT * FROM member "
			 + "WHERE mid = ?";
	 
	 public static final String MEM_UPDATE = "UPDATE member "
			 + "SET pwd = ?, mname = ?, license = ?, tel = ?, addr = ?, "
			 + "age = ?, email = ? WHERE mid = ?";
	 
	 public static final String MEM_DELETE = "DELETE FROM member "
			 + "WHERE mid = ?";
	 
	 public static final String RES_MEM_DELETE = "DELETE FROM reserve "
			 + "WHERE mid = ?";
	 
	 public static final String User_ResAll = "SELECT r.rid \"예약번호\", "
	 		+ "mid \"회원아이디\", r.cid \"차량번호\", r.cname \"차종\", "
	 		+ "c.price \"대여료\", r.resdate \"차량예약일\", "
	 		+ "r.startdate \"대여시작일\", r.enddate \"대여마감일\", "
			+ "(r.enddate-r.startdate+1) \"대여일수\" , "
			+ "c.price*(r.enddate-r.startdate+1) \"총랜트비\" "										
			+ "FROM car c, reserve r "
			+ "WHERE c.cid = r.cid and r.mid = ? "
			+ "ORDER BY r.resdate";
	 
	 public static final String UserRES_DELETE = "DELETE FROM reserve "
				+ "WHERE rid = ?";
	 
	 public static final String USERMAIN = "select cid \"차량번호\", "
	 		+ "cname \"차종\", price \"대여료\", res \"예약여부\" from car";
	 
	 public static final String MEM_INSERT = "INSERT INTO member values "
			   + "(?,?,?,?,?,?,?,?)";
	 public static final String USER_UPDATEDROP ="update car set res = 'N' where cid in(select cid from reserve where mid = ?)";
	 public static final String ISUSERSEE = "select * from reserve where mid=?";
	 
}
