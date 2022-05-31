package entities;

import java.util.Date;

public class NhanVien {
	private int id;
	private String hoTen;
	private String cMND;
	private Date ngaySinh;
	private int gioiTinh;
	private String sDT;
	private String diaChi;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getcMND() {
		return cMND;
	}
	public void setcMND(String cMND) {
		this.cMND = cMND;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	
	
	public int getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(int gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getsDT() {
		return sDT;
	}
	public void setsDT(String sDT) {
		this.sDT = sDT;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public NhanVien(int id, String hoTen, String cMND, Date ngaySinh, int gioiTinh, String sDT, String diaChi) {
		super();
		this.id = id;
		this.hoTen = hoTen;
		this.cMND = cMND;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.sDT = sDT;
		this.diaChi = diaChi;
	}
	public NhanVien(int id) {
		super();
		this.id = id;
	}
	@Override
	public String toString() {
		return "NhanVien [id=" + id + ", hoTen=" + hoTen + ", cMND=" + cMND + ", ngaySinh=" + ngaySinh + ", gioiTinh="
				+ gioiTinh + ", sDT=" + sDT + ", diaChi=" + diaChi + "]";
	}
	
	
	
	
	
}
