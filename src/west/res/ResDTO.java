package west.res;
import java.sql.Date;

class ResDTO {
	private String cid, mid, cname;
	private Date resDate, startDate, endDate;
	private int rid, priceDate, priceTotal;
	
	public ResDTO() {}
	
	public ResDTO(int rid, String cid, String mid, String cname, 
			Date startDate, Date endDate, 
			int priceDate, int priceTotal, Date resDate) {
		this.rid = rid;
		this.cid = cid;
		this.mid = mid;
		this.cname = cname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceDate = priceDate;
		this.priceTotal = priceTotal;
		this.resDate = resDate;
	}
	
	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getResdate() {
		return resDate;
	}

	public void setResdate(Date resdate) {
		this.resDate = resdate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(int priceDate) {
		this.priceDate = priceDate;
	}
	
	public int getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(int priceTotal) {
		this.priceTotal = priceTotal;
	}

	@Override
	public String toString() {
		return "ResDTO [rid=" + rid +", cid=" + cid + ", mid=" + mid 
				+ ", cname=" + cname + ", resdate=" + resDate + ", startDate="
				+ startDate + ", endDate=" + endDate
				+ ", priceDate=" + priceDate + ", priceTototal=" + priceTotal +"]";
	}
	
}
