package entities;

public class DichVu {
	private int maDV;
	private String tenDV;
	private String donVi;
	private String loai;
	private int soLuongCo;
	private int giaDV;
	public int getMaDV() {
		return maDV;
	}
	public void setMaDV(int maDV) {
		this.maDV = maDV;
	}
	public String getTenDV() {
		return tenDV;
	}
	public void setTenDV(String tenDV) {
		this.tenDV = tenDV;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public int getSoLuongCo() {
		return soLuongCo;
	}
	public void setSoLuongCo(int soLuongCo) {
		this.soLuongCo = soLuongCo;
	}
	public int getGiaDV() {
		return giaDV;
	}
	public void setGiaDV(int giaDV) {
		this.giaDV = giaDV;
	}
	public DichVu(int maDV, String tenDV, String donVi, String loai, int soLuongCo, int giaDV) {
		super();
		this.maDV = maDV;
		this.tenDV = tenDV;
		this.donVi = donVi;
		this.loai = loai;
		this.soLuongCo = soLuongCo;
		this.giaDV = giaDV;
	}
	public DichVu() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DichVu [maDV=" + maDV + ", tenDV=" + tenDV + ", donVi=" + donVi + ", loai=" + loai + ", soLuongCo="
				+ soLuongCo + ", giaDV=" + giaDV + "]";
	}
	
	
	
	
	
}
