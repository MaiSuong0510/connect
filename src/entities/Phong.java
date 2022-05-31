package entities;

public class Phong {
	private int id;
	private String loaiPhong;
	private String ghiChu;
	private int giaPhong;
	private int tinhTrangPhong;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public int getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(int giaPhong) {
		this.giaPhong = giaPhong;
	}
	
	public int getTinhTrangPhong() {
		return tinhTrangPhong;
	}
	public void setTinhTrangPhong(int tinhTrangPhong) {
		this.tinhTrangPhong = tinhTrangPhong;
	}
	public Phong() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Phong(int id) {
		super();
		this.id = id;
	}
	public Phong(int id, String loaiPhong, String ghiChu, int giaPhong, int tinhTrangPhong) {
		super();
		this.id = id;
		this.loaiPhong = loaiPhong;
		this.ghiChu = ghiChu;
		this.giaPhong = giaPhong;
		this.tinhTrangPhong = tinhTrangPhong;
	}
	
	public Phong(int id, String loaiPhong, String ghiChu, int giaPhong) {
		super();
		this.id = id;
		this.loaiPhong = loaiPhong;
		this.ghiChu = ghiChu;
		this.giaPhong = giaPhong;
	}
	@Override
	public String toString() {
		return "Phong [id=" + id + ", loaiPhong=" + loaiPhong + ", ghiChu=" + ghiChu + ", giaPhong=" + giaPhong
				+ ", tinhTrangPhong=" + tinhTrangPhong + "]";
	}
	
	
	
	
}
