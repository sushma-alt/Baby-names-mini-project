
/*
 * This program determines what a baby's name would be if she/he were born in a different year. 
 * That is, determining the rank of the baby's name in the year they were born, and then printing the name born in the newYear 
 * that is at the same rank and same gender.
 * For example, using the files "yob2012short.csv" and "yob2014short.csv", notice that in 2012 Isabella is the third most 
 * popular girl's name. If Isabella was born in 2014 instead, she would have been named Sophia, the third most popular girl's 
 * name that year.
 * 
 */
 
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
public class BabyNames {
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int girlsBirths = 0;
        int boysBirths = 0;
        int totalNames = 0;
        int girlsNames = 0;
        int boysNames = 0;
        for(CSVRecord rec : fr.getCSVParser(false)) {
            int numBirths = Integer.parseInt(rec.get(2));
            totalBirths += numBirths; 
            totalNames += 1;
            if((rec.get(1).equals("F"))) {
                girlsBirths += numBirths;
                girlsNames += 1;
            }
            else {
                boysBirths += numBirths;
                boysNames += 1;
            }
            
        }
        System.out.println("Total no. of births : " + totalBirths);
        System.out.println("Total no. of boysBirths : " + boysBirths);
        System.out.println("Total no. of girlsBirths : " + girlsBirths);
        System.out.println("Total no. of names : " + totalNames);
        System.out.println("Total no. of boysNames : " + boysNames);
        System.out.println("Total no. of girlsNames : " + girlsNames);
        
    }
    public int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource();
        int rank = 0;
        for(CSVRecord rec : fr.getCSVParser()) {
            if(rec.get(1).contains(gender)) {
                rank += 1;
                if(rec.get(0).contains(name)) {
                    return rank;
                }
                             
            }
        }
        return -1;
    
    }
    public String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource();
        int rankCount = 0;
        for(CSVRecord rec : fr.getCSVParser()) {
            if(rec.get(1).contains(gender)) {
                rankCount += 1;
                if(rankCount == rank) {
                    return rec.get(0);
                }
                             
            }
        }
        return "NO NAME";
    
    }
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
       
       int rank = getRank(year, name, gender);
       String newName = getName(newYear, rank, gender);
       System.out.println(name + " born in " + year + " would be " + newName + " if born in " + newYear + ".");
              
    }
    public int yearOfHighestRank(String name, String gender) {
    int yearHigh = -1;
    int rank = 1000000000;
    int newRank = 0;
    DirectoryResource dr = new DirectoryResource();
    for(File f : dr.selectedFiles()) {
        String fileName = f.getName();
	int year = Integer.parseInt(fileName.replaceAll("[\\D]", ""));
        FileResource fr = new FileResource(f);
        for(CSVRecord rec : fr.getCSVParser(false)) { 
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                newRank = getRank(year,name,gender);
                if(newRank < rank && newRank != -1) {
                    rank = newRank;
                    yearHigh = year;
                }
                 
            }
        }
    }
    return yearHigh;
    }
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int rank = 0;
        double avgRank = 0;
        double count = 0;
        for(File f : dr.selectedFiles()) {
            String filename = f.getName();
            int year = Integer.parseInt(filename.replaceAll("[\\D]",""));
            FileResource fr = new FileResource(f);
            for(CSVRecord rec : fr.getCSVParser(false)) {
                if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                    rank += getRank(year,name,gender);
                    count += 1;
                }   
            }
        }
        if(rank == 0) 
        {
            return -1.0;
        }
    return (double)rank/count;
    }
    public int totalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource();
        int births = 0;
        for(CSVRecord rec : fr.getCSVParser()) {
            if(rec.get(1).equals(gender)) {
                
                if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                    break;
                }
                else {
                    births += Integer.parseInt(rec.get(2));
                }
            }
        }
    return births;
    }
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    public void testGetRank() {
        int year = 1971;
        String name = "Frank";
        String gender = "M";
        int getRank = getRank(year,name,gender);
        System.out.println("The rank of the name" + name + " in " + year +" is " + getRank); 
    }
    public void testGetName() {
        int year = 1982;
        int rank = 450;
        String gender = "M";
        String getName = getName(year,rank,gender);
        System.out.println("The name of the person in " + year+" at rank " + rank + " is " + getName); 
    }
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    public void testYearofHighestRank() {
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name,gender);
        System.out.println("Year of highest rank of name " + name +" is " + year); 
    }
    public void testGetAverageRank() {
        String name = "Robert";
        String gender = "M";
        double avg_rank = getAverageRank(name,gender);
        System.out.println("Average rank of the name " + name + " is " + avg_rank);
    }
    public void testTotalBirthsRankedHigher() {
        int year = 1990;
        String name = "Emily";
        String gender = "F";
        int total_births = totalBirthsRankedHigher(year,name,gender);
        System.out.println("Total births ranked higher than " + name + " are " + total_births);
    }
}