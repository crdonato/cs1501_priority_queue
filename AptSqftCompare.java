
import java.util.Comparator;

public class AptSqftCompare implements Comparator<Apt>{
	
	public int compare(Apt a, Apt b){
		if(a.getAptSqft() < b.getAptSqft()){
			return -1;
		}else if(a.getAptSqft() > b.getAptSqft()){
			return 1;
		}else{
			return 0;
		}
	}
}