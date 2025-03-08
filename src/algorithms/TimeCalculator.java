package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TimeCalculator {

	private static String file_src = "./samples/test-";
	private static String file_type = ".points";
	public TimeCalculator() {
		// TODO Auto-generated constructor stub
	}
	
	public void calcAverage() {
		ArrayList<Point> points = new ArrayList<>();
		try {
			for(int i = 2; i <= 1664;i++) {
				points.clear();
				System.out.println(file_src + i + file_type);
				BufferedReader 	input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file_src + i + file_type)));
				
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
		        
		        long startTime = System.currentTimeMillis();
		        System.out.println("Start time : " + startTime);
                
                Welzl welz = new Welzl();
                welz.Welzl(points, new ArrayList<>());
                long endTime = System.currentTimeMillis();
                System.out.println("end time = " + endTime);
                long duration = (endTime - startTime); // Convert to m
                
                System.out.println("Result : " + file_src + i + " Duration : " + duration);
		        
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
	}
	
	public static void main(String[]args) {
		System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
		
		TimeCalculator timeC = new TimeCalculator();
		timeC.calcAverage();
	}

}
