
import java.util.Comparator;
import java.lang.*;

//public class Apt implements Comparator<Apt>, Comparable<Apt> 
public class Apt{
	// Data to store in the Apt class for apartments
	
	// A street address (e.g., 4200 Forbes Ave.)
	// An apartment number (e.g., 3601)
	// The city the apartment is in (e.g., Pittsburgh)
	// The apartment's ZIP code (e.g., 15213)
	// The price to rent (in US dollars per month)
	// The square footage of the apartment
	
	private String apartment_address;
	private String apartment_number;
	private String apartment_city;
	private int    apartment_zip_code;
	private long   apartment_rent; 		// in US dollars per month
	private long   apartment_sqft;		// square footage
	
	public Apt(){
		apartment_address = "";
		apartment_number = "";
		apartment_city = "";
		apartment_zip_code = 0;
		apartment_rent = 0;
		apartment_sqft = 0;
	}
	
	public Apt(String address, String apt_num, String city, int zip, long rent, long sqft){
		apartment_address = address;
		apartment_number = apt_num;
		apartment_city = city;
		apartment_zip_code = zip;
		apartment_rent = rent;
		apartment_sqft = sqft;		
	}
	
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public void setAptAddress(String address){
		apartment_address = address;
	}
	
	public void setAptNumber(String apt_num){
		apartment_number = apt_num;
	}
	
	public void setAptCity(String city){
		apartment_city = city;
	}
	
	public void setAptZip(int zip){
		apartment_zip_code = zip;
	}
	
	public void setAptRent(long rent){
		apartment_rent = rent;
	}
	
	public void setAptSqft(long sqft){
		apartment_sqft = sqft;
	}
	
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public String getAptAddress(){
		return apartment_address;
	}
	
	public String getAptNumber(){
		return apartment_number;
	}
	
	public String getAptCity(){
		return apartment_city;
	}
	
	public int getAptZip(){
		return apartment_zip_code;
	}
	
	public long getAptRent(){
		return apartment_rent;
	}
	
	public long getAptSqft(){
		return apartment_sqft;
	}
	
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public String toString(){
		String output = "Address: " + apartment_address + 
						" | Number: " + apartment_number + 
						" | City: " + apartment_city +
						" | Zip Code: " + apartment_zip_code + 
						" | Rent($): " + apartment_rent + 
						" | Sqft: " + apartment_sqft;
		
		return output;
	}
	
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	/*
	public int compare(Apt a, Apt b){
		return (int) (a.apartment_sqft - b.apartment_sqft);
	}

	public int compareTo(Apt b){
		return (int)((this.apartment_rent) - (b.apartment_rent));
	}
	
	public int compareRent(Apt b){
		return (int)((this.apartment_rent) - (b.apartment_rent));
	}
	
	public int compareSqft(Apt b){
		return (int) (this.apartment_sqft - b.apartment_sqft);
	}
	*/
	
}