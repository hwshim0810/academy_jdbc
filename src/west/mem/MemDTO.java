package west.mem;


public class MemDTO {
	private String id, pwd, name, license, tel, addr, email;
	private int age;
	
	MemDTO(){}
	
	MemDTO(String id, String pwd, String name, String license,
			String tel, String addr, int age, String email){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.license = license;
		this.tel = tel;
		this.addr = addr;
		this.age = age;
		this.email = email;
	}
	@Override
	public String toString() {
		return "CarDTO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", license=" + license + ", tel=" + tel
				+ ", addr=" + addr + ", age=" + age + ", email=" + email + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
