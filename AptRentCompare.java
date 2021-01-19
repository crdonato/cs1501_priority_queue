
import java.util.Comparator;

public class AptRentCompare implements Comparator<Apt>{
	
	public int compare(Apt a, Apt b){
		if(a.getAptRent() < b.getAptRent()){
			return -1;
		}else if(a.getAptRent() > b.getAptRent()){
			return 1;
		}else{
			return 0;
		}
	}
}