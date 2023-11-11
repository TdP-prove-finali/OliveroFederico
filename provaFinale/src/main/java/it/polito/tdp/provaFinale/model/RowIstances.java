package it.polito.tdp.provaFinale.model;

public class RowIstances {
	
	private String i1;
	private String i2;
	private String i3;
	private String i4;
	private String i5;
	
	public RowIstances(String i1, String i2, String i3, String i4, String i5) {
		super();
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		this.i4 = i4;
		this.i5 = i5;
	}
	public String getI1() {
		return i1;
	}
	public void setI1(String i1) {
		this.i1 = i1;
	}
	public String getI2() {
		return i2;
	}
	public void setI2(String i2) {
		this.i2 = i2;
	}
	public String getI3() {
		return i3;
	}
	public void setI3(String i3) {
		this.i3 = i3;
	}
	public String getI4() {
		return i4;
	}
	public void setI4(String i4) {
		this.i4 = i4;
	}
	public String getI5() {
		return i5;
	}
	public void setI5(String i5) {
		this.i5 = i5;
	}
	@Override
	public String toString() {
		return  this.i1+", "+this.i2+", "+this.i3+", "+this.i4+", "+this.i5;
	}
	
	

}
