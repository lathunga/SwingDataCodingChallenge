package com.swingdata.challenge;

import java.util.ArrayList;

public class SwingData {
	ArrayList<Double> timestamp = new ArrayList<>();
	ArrayList<Double> ax = new ArrayList<>();
	ArrayList<Double> ay = new ArrayList<>();
	ArrayList<Double> az = new ArrayList<>();
	ArrayList<Double> wx = new ArrayList<>();
	ArrayList<Double> wy = new ArrayList<>();
	ArrayList<Double> wz = new ArrayList<>();

    public SwingData(ArrayList<Double> timestamp, ArrayList<Double> ax, ArrayList<Double> ay, ArrayList<Double> az, ArrayList<Double> wx, ArrayList<Double> wy, ArrayList<Double> wz) {
        this.timestamp = timestamp;
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.wx = wx;
        this.wy = wy;
        this.wz = wz;
    }
    
    public SwingData() {
    }
    
    public ArrayList<Double> getCol(String col)
    {
    	switch(col)
    	{
    		case "timestamp":
    			return timestamp;
    		case "ax":
    			return ax;
    		case "ay":
    			return ay;
    		case "az":
    			return az;
    		case "wx":
    			return wx;
    		case "wy":
    			return wy;
    		case "wz":
    			return wz;
    	}
    	return null;
    }
    
}
