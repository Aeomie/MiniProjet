package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TimeCalculator {

	private static String file_src = "./samples/";
	private static String file_type = ".points";
	private static int nb_file = 1664;
	
	/**
	 * Calculates Average Welzl algorithm Execution time
	 * The time print call is commented out by default.
	 * in the end we divide by nb_file -1 
	 * because we start the loop from 2 as the first file is too big and will cause a StackOverFlow.
	 */
	public double calcAverageWelz() {
		
		/*
		 * Defines the variables to use
		 */
		ArrayList<Point> points = new ArrayList<>();
		String outputfile = "Welz.txt";
		Mec mec = new Mec();
		double total_time = 0;
		
		try {
			for(int i = 2; i <= nb_file;i++) {
				
				points.clear();
				String filename = "test-" + i + file_type;
				String file = file_src + filename;
				
				// Reads points from the file
				
				BufferedReader 	input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));
				
		        String line;
		        try {
					while ((line = input.readLine()) != null) {
					  String[] coordinates = line.split("\\s+");
					  points.add(new Point(Integer.parseInt(coordinates[0]), 
					        Integer.parseInt(coordinates[1])));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        
		        // Calculates Time in Nano  to get Milli in floats.
                double time = System.nanoTime();
                mec.Welzl(points, new ArrayList<>());
                time = (System.nanoTime() - time) / 1_000_000.0;
                total_time += time;
                
                // Writes the result into a txt file in csv format.
                String result = "Welz,"+filename+","+time + "\n";
                Writer(outputfile,result);
                //System.out.println(filename+ " exec time: "  + time );
		        
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		return total_time/(nb_file - 1);
	}
	
	/**
	 * Calculates Average Naive algorithm Execution time
	 * The time print call is commented out by default.
	 * in the end we divide by nb_file -1 
	 * because we start the loop from 2 as the first file is too big and would take too long.
	 */
	public double calcAverageNaive() {
		
		/*
		 * Defines the variables to use
		 */
		ArrayList<Point> points = new ArrayList<>();
		Mec mec = new Mec();
		String outputfile = "Naive.txt";
		double total_time = 0;
		
		try {
			for(int i = 2; i <= nb_file;i++) {
				points.clear();
				String filename = "test-" + i + file_type;
				String file = file_src + filename;
				
				
				// Reads points from the point file
				
				BufferedReader 	input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));
				
		        String line;
		        try {
					while ((line = input.readLine()) != null) {
					  String[] coordinates = line.split("\\s+");
					  points.add(new Point(Integer.parseInt(coordinates[0]), 
					        Integer.parseInt(coordinates[1])));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        
		        // Calculates Time in Nano  to get Milli in floats.
                double time = System.nanoTime();
                mec.calculCercleMin_Naive(points);
                time = (System.nanoTime() - time) / 1_000_000.0;
                total_time += time;
                
                // Writes the result into a txt file in csv format.
                String result = "Naive,"+filename +","+time + "\n";
                Writer(outputfile, result);
                
                //System.out.println(filename + " exec time: "  + time );
		        
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		return total_time/(nb_file - 1 );
	}
	
	
	/**
	 * Writer function
	 * Takes as parameter filename , and data to write inside of it.
	 * It writes the data into a file
	 */
	public void Writer(String filename, String data) {
		
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data); // Write the data
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public static void main(String[]args) {
		System.out.println("Started Calculating");
		TimeCalculator timeC = new TimeCalculator();
		double time = 0;
		time = timeC.calcAverageWelz();
		timeC.Writer("Resultat.txt", "Welzl Total exec time :" + time + "\n");
		time = timeC.calcAverageNaive();	
		timeC.Writer("Resultat.txt", "Naive Total exec time :" + time + "\n");
		System.out.println("Done");
	}

}
