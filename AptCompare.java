
import java.util.Comparator;

public class AptCompare implements Comparator<Apt>{
	
	public int compare(Apt a, Apt b){
		if(a.getAptAddress() == b.getAptAddress() && a.getAptNumber() == b.getAptNumber()){
			return 0;
		}
		return 1;
	}
}