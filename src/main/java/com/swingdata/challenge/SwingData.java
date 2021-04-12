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
    
    public ArrayList<Double> getCol(int col)
    {
    	switch(col)
    	{
    		case 1:
    			return timestamp;
    		case 2:
    			return ax;
    		case 3:
    			return ay;
    		case 4:
    			return az;
    		case 5:
    			return wx;
    		case 6:
    			return wy;
    		case 7:
    			return wz;
    	}
    	return null;
    }
    
}
