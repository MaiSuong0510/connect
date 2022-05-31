package entities;

import java.util.Date;

public class HoaDon {
	private int maHoaDon;
	private int maNV;
	private double tongTien;
	private Date ngayLapHD;
	private int tinhTrangHD;
	public int getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public int getMaNV() {
		return maNV;
	}
	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	public Date getNgayLapHD() {
		return ngayLapHD;
	}
	public void setNgayLapHD(Date ngayLapHD) {
		this.ngayLapHD = ngayLapHD;
	}
	public int getTinhTrangHD() {
		return tinhTrangHD;
	}
	public void setTinhTrangHD(int tinhTrangHD) {
		this.tinhTrangHD = tinhTrangHD;
	}
	public HoaDon(int maHoaDon, int maNV, double tongTien, Date ngayLapHD, int tinhTrangHD) {
		super();
		this.maHoaDon = maHoaDon;
		this.maNV = maNV;
		this.tongTien = tongTien;
		this.ngayLapHD = ngayLapHD;
		this.tinhTrangHD = tinhTrangHD;
	}
	
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HoaDon(double tongTien, Date ngayLapHD) {
		super();
		this.tongTien = tongTien;
		this.ngayLapHD = ngayLapHD;
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", maNV=" + maNV + ", tongTien=" + tongTien + ", ngayLapHD=" + ngayLapHD
				+ ", tinhTrangHD=" + tinhTrangHD + "]";
	}
	
	
}	
