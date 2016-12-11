package west.car;

import java.sql.Date;

class CarDTO {
	private String cid, cname, res;
	private int price;
	private Date resdate;
	
	public CarDTO() { super(); }
	
	public CarDTO(String cid, String cname, int price, 
			String res, Date resDate) {
		super();
		this.cid = cid; this.cname = cname;
		this.price = price; this.res = res;
		this.resdate = resDate; 
	}
	
	public CarDTO(String cid, String cname, Date resDate) {
		this.cid = cid;
		this.cname = cname;
		this.resdate = resDate;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public Date getResdate() {
		return resdate;
	}
	public void setResdate(Date resdate) {
		this.resdate = resdate;
	}

}
