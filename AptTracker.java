

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class AptTracker{
	
	public static void main(String[] args){
		int apt_init_size = 11;
		
		In apt_file = new In("apartments.txt");
		
		Comparator<Apt> RentCompare = new AptRentCompare();
		Comparator<Apt> SqftCompare = new AptSqftCompare();
		Comparator<Apt> AptCompare = new AptCompare();
		
		AptMinPQ<Apt> pq_min = new AptMinPQ<Apt>(RentCompare, AptCompare);
		AptMaxPQ<Apt> pq_max = new AptMaxPQ<Apt>(SqftCompare, AptCompare);
		
		Apt[] apts_array = new Apt[apt_init_size];
		
		String street = "";
		String number = "";
		String city = "";
		int zip = 0;
		long price = 0;
		long sqft = 0;
		
		int apt_size = 1;
		
		if(apt_file != null){
			
			apt_file.readLine();
			
			while(!apt_file.isEmpty()){
				String fileRead = apt_file.readLine();
				
				String[] tokenize = fileRead.split(":");
				
				street = tokenize[0];
				number = tokenize[1];
				city = tokenize[2];
				zip = Integer.parseInt(tokenize[3]);
				price = Long.parseLong(tokenize[4]);
				sqft = Long.parseLong(tokenize[5]);
				
				apts_array[apt_size] = new Apt(street, number, city, zip, price, sqft);
				
				pq_min.insert(apts_array[apt_size]);
				pq_max.insert(apts_array[apt_size]);
				
				apt_size++;
				
				if(apt_size == apts_array.length){
					resize(2 * apts_array.length, apts_array, apt_size);
				}
			}
		}
		
		int user_input = 10;
		
		while(user_input != 0){
			aptMenu();
			
			user_input = StdIn.readInt();
			
			if(user_input == 1){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				
				if(apt_size == apts_array.length - 1){
					apts_array = resize(2 * apts_array.length, apts_array, apt_size);
				}
				
				Apt addedApt = addApt();
				apts_array[apt_size] = addedApt;
				pq_min.insert(addedApt);
				pq_max.insert(addedApt);
				apt_size++;
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 2){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Update an Apartment");
				aptList(apts_array, apt_size);
				user_input = StdIn.readInt();
				int max_update = pq_max.aptIndex(apts_array[user_input]);
				int min_update = pq_min.aptIndex(apts_array[user_input]);
				Apt apts_update = aptUpdate(apts_array[user_input]);
				pq_max.updateApt(max_update, apts_update);
				pq_min.updateApt(min_update, apts_update);
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 3){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Remove an Apartment");
				aptList(apts_array, apt_size);
				user_input = StdIn.readInt();
				Apt apts_del = apts_array[user_input];
				pq_max.delApt(apts_del);
				pq_min.delApt(apts_del);
				apts_array[user_input] = null;
				
				for(int d = user_input; d <= apt_size; d++){
					apts_array[user_input] = apts_array[user_input + 1];
				}
				
				apt_size--;
				
				StdOut.println("Apartment at Address: " + apts_del.getAptAddress() + " has been removed");
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 4){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Lowest Price Apartment");
				StdOut.println(pq_min.min());
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 5){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Lowest Price Apartment by city.");
				String[] cities = aptCity(apts_array, apt_size);
				user_input = StdIn.readInt();
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				String city_choice = cities[user_input];
				AptMinPQ<Apt> pq_min_city = new AptMinPQ<Apt>(RentCompare, AptCompare);
				String city_compare;
				for(int i = 1; i < apt_size; i++){
					city_compare = apts_array[i].getAptCity();
					if(city_compare.equals(city_choice)){
						pq_min_city.insert(apts_array[i]);
					}
				}
				StdOut.println(pq_min_city.min());
				while(!pq_min_city.isEmpty()){
					pq_min_city.delMin();
				}
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 6){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Highest square Footage Apartment");
				StdOut.println(pq_max.max());
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 7){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("Highest square Footage Apartment by city.");
				String[] cities = aptCity(apts_array, apt_size);
				user_input = StdIn.readInt();
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				String city_choice = cities[user_input];
				AptMaxPQ<Apt> pq_max_city = new AptMaxPQ<Apt>(SqftCompare, AptCompare);;
				String city_compare;
				for(int i = 1; i < apt_size; i++){
					city_compare = apts_array[i].getAptCity();
					if(city_compare.equals(city_choice)){
						pq_max_city.insert(apts_array[i]);
					}
				}
				StdOut.println(pq_max_city.max());
				while(!pq_max_city.isEmpty()){
					pq_max_city.delMax();
				}
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else if(user_input == 8){
				StdOut.println();
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println("All Apartments");
				for(int k = 1; k < apt_size; k++){
					StdOut.println("Apartment " + k);
					StdOut.println("\t\t" + apts_array[k].toString());
				}
				StdOut.println("-------------------------------------------------------------------------------------");
				StdOut.println();
			}else{
				assert (user_input == 0);
				System.exit(0);
			}
		}
	}
	
//-------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------
	
	public static Apt[] resize(int capacity, Apt[] apts, int size) {
        Apt[] temp = new Apt[capacity];
		
        for (int i = 1; i <= size; i++) {
            temp[i] = apts[i];
        }
		
        apts = temp;
		
		return apts;
    }
	
	public static void aptMenu(){
		StdOut.println("Apartments search");
		StdOut.println("Choose an option number from below:");
		StdOut.println("(1) Add an apartment");
		StdOut.println("(2) Update an apartment");
		StdOut.println("(3) Remove an apartment");
		StdOut.println("(4) Retrieve the Lowest price apartment");
		StdOut.println("(5) Retrieve the Lowest price apartment by CITY");
		StdOut.println("(6) Retrieve the Highest square footage apartment");
		StdOut.println("(7) Retrieve the Highest square footage apartment By CITY");
		StdOut.println("(8) Retrieve a list of all apartments");
		StdOut.println("(0) Exit");
	}
	
	public static Apt addApt(){
		String address;
		String number;
		String city;
		int zip_code;
		long rent;
		long sqft;
		
		String comsuer = StdIn.readLine();
		
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the address of the apartment: ");
		
		address = StdIn.readLine();
		
		StdOut.println();
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the apartment number: ");
		
		number = StdIn.readLine();
		
		StdOut.println();
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the apartment's city: ");
		
		city = StdIn.readLine();
		
		StdOut.println();
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the apartment zip code: ");
		
		zip_code = StdIn.readInt();
		
		StdOut.println();
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the apartments monthly rent in US dollars ($): ");
		
		rent = StdIn.readLong();

		StdOut.println();
		StdOut.println("-------------------------------------------------");
		StdOut.println("Enter the apartments square footage: ");
		
		sqft = StdIn.readLong();
		
		Apt newApt = new Apt(address, number, city, zip_code, rent, sqft);
		
		return newApt;
	}
	
	public static void aptList(Apt[] apts, int size){
		for(int i = 1; i < size; i++){
			StdOut.println(i + " Apartment Address: " + apts[i].getAptAddress() + " - Apartment Number: " + apts[i].getAptNumber());
		}
	}
	
	public static Apt aptUpdate(Apt apt){
		String address;
		String number;
		String city;
		int zip_code;
		long rent;
		long sqft;
		
		String comsuer = StdIn.readLine();
		StdOut.println(apt.toString());
		
		StdOut.println("-------------------------------------------------");
		StdOut.println("Choose what to update.");
		StdOut.println("(1) Update Apartment Address.");
		StdOut.println("(2) Update Apartment Number.");
		StdOut.println("(3) Update Apartment City.");
		StdOut.println("(4) Update Apartment Zip Code.");
		StdOut.println("(5) Update Apartment Rent.");
		StdOut.println("(6) Update Apartment Square Footage.");
		StdOut.println("(7) Update all.");
		StdOut.println("-------------------------------------------------");
		
		int update_option = StdIn.readInt();
		
		if(update_option == 1){
			StdOut.println("Enter the updated apartment address: ");
			apt.setAptAddress(StdIn.readLine());
		}else if(update_option == 2){
			StdOut.println("Enter the updated apartment number: ");
			apt.setAptAddress(StdIn.readLine());
		}else if(update_option == 3){
			StdOut.println("Enter the updated apartment city: ");
			apt.setAptCity(StdIn.readLine());
		}else if(update_option == 4){
			StdOut.println("Enter the updated apartment zip code: ");
			apt.setAptZip(StdIn.readInt());
		}else if(update_option == 5){
			StdOut.println("Enter the updated apartment rent: ");
			apt.setAptRent(StdIn.readLong());
		}else if(update_option == 6){
			StdOut.println("Enter the updated apartment square footage: ");
			apt.setAptSqft(StdIn.readLong());
		}else if(update_option == 7){
			StdOut.println("Enter the updated apartment address: ");
			apt.setAptAddress(StdIn.readLine());
			
			StdOut.println("Enter the updated apartment number: ");
			apt.setAptAddress(StdIn.readLine());
			
			StdOut.println("Enter the updated apartment city: ");
			apt.setAptCity(StdIn.readLine());
			
			StdOut.println("Enter the updated apartment zip code: ");
			apt.setAptZip(StdIn.readInt());
			
			StdOut.println("Enter the updated apartment rent: ");
			apt.setAptRent(StdIn.readLong());
			
			StdOut.println("Enter the updated apartment square footage: ");
			apt.setAptSqft(StdIn.readLong());
		}else{
		}
		
		return apt;
	}
	
	public static String[] aptCity(Apt[] apts, int size){
		String[] cities = new String[size];
		int num_cities = 0;
		String city1;
		String city2;
		boolean flag = true;
		
		cities[1] = apts[1].getAptCity();
		num_cities++;
		
		for(int i = 2; i < size; i++){
			city2 = apts[i].getAptCity();
			for(int k = 1; k <= num_cities; k++){
				city1 = cities[k];
				if(city2.equals(city1)){
					flag = false;
					break;
				}
			}
			
			if(flag == true){
				cities[num_cities + 1] = apts[i].getAptCity();
				num_cities++;
			}
			
			flag = true;
		}

		for(int c = 1; c <= num_cities; c++){
			StdOut.println(c + " " + cities[c]);
		}
		
		return cities;
	}
	
	
}