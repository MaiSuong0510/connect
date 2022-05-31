package entities;

import java.util.Date;

public class KhachHang {
	private int ma;
	private String hoTen;
	private String cMND;
	private Date ngaySinh;
	private int gioiTinh;
	private String sDT;
	
	

	public int getMa() {
		return ma;
	}

	public void setMa(int ma) {
		this.ma = ma;
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
	
	
	public KhachHang(int ma, String hoTen, String cMND, Date ngaySinh, int gioiTinh, String sDT) {
		super();
		this.ma = ma;
		this.hoTen = hoTen;
		this.cMND = cMND;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.sDT = sDT;
	}

	public KhachHang(String hoTen, String cMND) {
		super();
		this.hoTen = hoTen;
		this.cMND = cMND;
	}
	

	public KhachHang(int ma) {
		super();
		this.ma = ma;
	}

	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "KhachHang [ma=" + ma + ", hoTen=" + hoTen + ", cMND=" + cMND + ", ngaySinh=" + ngaySinh + ", gioiTinh="
				+ gioiTinh + ", sDT=" + sDT + "]";
	}

}
