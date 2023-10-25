package testautomation.apitesting.pojos;

public class Booking {
	private String firstname;
	private String lastname;
	private String additionalneeds;
	private int totalprice;
	private boolean depositpaid;
	private BookingDates bdates;
	public Booking()
	{
		
	}
	public Booking(String Fname,String Lname,String Aneeds,int tPrice,boolean dPaid,BookingDates bDates)
	{
		setFirstname(Fname);
		setLastname(Lname);
		setAdditionalneeds(Aneeds);
		setTotalprice(tPrice);
		setDepositpaid(dPaid);
		setBookingdates(bDates);
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public boolean isDepositpaid() {
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	public BookingDates getBookingdates() {
		return bdates;
	}
	public void setBookingdates(BookingDates bookingdates) {
		this.bdates = bookingdates;
	}
	
}
