package com.swingdata.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CSVController {
	
	SwingData swingD = new SwingData();
	
	@GetMapping("/")
    public String index() {
        return "index";
    }
	
	@PostMapping("/uploadCSVfile")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) throws NumberFormatException, IOException {
		ArrayList<Double>[] swingData = new ArrayList[7];
		for (int i = 0; i < 7; i++)
		{
            swingData[i] = new ArrayList<Double>();
        }
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
		    String[] cols = line.split(",");
		    swingData[0].add(Double.parseDouble(cols[0]));
		    swingData[1].add(Double.parseDouble(cols[1]));
		    swingData[2].add(Double.parseDouble(cols[2]));
		    swingData[3].add(Double.parseDouble(cols[3]));
		    swingData[4].add(Double.parseDouble(cols[4]));
		    swingData[5].add(Double.parseDouble(cols[5]));
		    swingData[6].add(Double.parseDouble(cols[6]));
		}
		swingD = new SwingData(swingData[0], swingData[1], swingData[2], swingData[3], swingData[4], swingData[5], swingData[6]);
		return "upload";
	}
	
	@PostMapping("/upload")
    public String uploadCSVFile(@RequestParam("operation") String operation) {
		return operation;
	}
	
	@PostMapping("/1")
    public String uploadCSVFile(@RequestParam("data") String data, @RequestParam("indexBegin") int indexBegin, @RequestParam("indexEnd") int indexEnd, @RequestParam("threshold") String threshold, @RequestParam("winLength") int winLength, Model model) {
		if(data.equals("0"))
		{
			return "errorData";
		}
		int result = searchContinuityAboveValue(swingD.getCol(data), indexBegin, indexEnd, Double.parseDouble(threshold), winLength);
		model.addAttribute("result", result);
		return "result";
	}
	
	@PostMapping("/2")
    public String uploadCSVFile(@RequestParam("data") String data, @RequestParam("indexBegin") int indexBegin, @RequestParam("indexEnd") int indexEnd, @RequestParam("thresholdLo") String thresholdLo, @RequestParam("thresholdHi") String thresholdHi, @RequestParam("winLength") int winLength, Model model) {
		if(data.equals("0"))
		{
			return "errorData";
		}
		int result = backSearchContinuityWithinRange(swingD.getCol(data), indexBegin, indexEnd, Double.parseDouble(thresholdLo),Double.parseDouble(thresholdHi),  winLength);
		model.addAttribute("result", result);
		return "result";
	}
	
	@PostMapping("/3")
    public String uploadCSVFile(@RequestParam("data1") String data1, @RequestParam("data2") String data2, @RequestParam("indexBegin") int indexBegin, @RequestParam("indexEnd") int indexEnd, @RequestParam("threshold1") String threshold1, @RequestParam("threshold2") String threshold2, @RequestParam("winLength") int winLength, Model model) {
		if(data1.equals("0") || data2.equals("0"))
		{
			return "errorData";
		}
		int result = searchContinuityAboveValueTwoSignals(swingD.getCol(data1), swingD.getCol(data2), indexBegin, indexEnd, Double.parseDouble(threshold1), Double.parseDouble(threshold2),  winLength);
		model.addAttribute("result", result);
		return "result";
	}
	
	@PostMapping("/4")
    public String uploadCSV2File(@RequestParam("data") String data, @RequestParam("indexBegin") int indexBegin, @RequestParam("indexEnd") int indexEnd, @RequestParam("thresholdLo") String thresholdLo, @RequestParam("thresholdHi") String thresholdHi, @RequestParam("winLength") int winLength, Model model) {
		if(data.equals("0"))
		{
			return "errorData";
		}
		ArrayList<int[]> result = searchMultiContinuityWithinRange(swingD.getCol(data), indexBegin, indexEnd, Double.parseDouble(thresholdLo),Double.parseDouble(thresholdHi),  winLength);
		model.addAttribute("result", result);
		return "result2";
	}
	
	@PostMapping("/result")
    public void uploadCSVFile(Model model) {
		
	}
	
	public static int searchContinuityAboveValue(ArrayList<Double> data, int indexBegin, int indexEnd, double threshold, int winLength)
	{
		ArrayList<Double> thresholdLoArr = new ArrayList<Double>();
		thresholdLoArr.add(threshold);
		ArrayList<ArrayList<Double>> dataArr = new ArrayList<ArrayList<Double>>();
		dataArr.add(data);
		return searchHelper(dataArr, indexBegin, indexEnd, thresholdLoArr, Double.MAX_VALUE, winLength);
	}
	
	public static int backSearchContinuityWithinRange(ArrayList<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		Collections.reverse(data);
		indexBegin = data.size()-1-indexBegin;
		indexEnd = data.size()-1-indexEnd;
		ArrayList<Double> thresholdLoArr = new ArrayList<Double>();
		thresholdLoArr.add(thresholdLo);
		return data.size()-1-searchHelper(new ArrayList<ArrayList<Double>>(Arrays.asList(data)), indexBegin, indexEnd, thresholdLoArr, thresholdHi, winLength);
	}
			
	public static int searchContinuityAboveValueTwoSignals(ArrayList<Double> data1, ArrayList<Double> data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength)
	{
		ArrayList<Double> thresholdLoArr = new ArrayList<Double>();
		thresholdLoArr.add(threshold1);
		thresholdLoArr.add(threshold2);
		return searchHelper(new ArrayList<ArrayList<Double>>(Arrays.asList(data1, data2)), indexBegin, indexEnd, thresholdLoArr, Double.MAX_VALUE, winLength);
	}
	
	public static ArrayList<int[]> searchMultiContinuityWithinRange(ArrayList<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		ArrayList<int[]> indexes = new ArrayList<int[]>();
		int i = indexBegin;
		while(indexEnd-i>=winLength-1)
		{
			int j = i;
			int count = 0;
			while(data.get(j)>thresholdLo && data.get(j)<thresholdHi)
			{
				count++;
				j++;
			}
			if(count>=winLength)
			{
				int[] newIndexes = {i, j-1};
				indexes.add(newIndexes);
			}
			i = j+1;
		}
		return indexes;
	}
	
	public static int searchHelper(ArrayList<ArrayList<Double>> data, int indexBegin, int indexEnd, ArrayList<Double> thresholdLo, Double thresholdHi, int winLength)
	{
		int i = indexBegin;
		while(indexEnd-i>=winLength-1)
		{
			int j = i;
			int count = 0;
			while(checkData(data, indexBegin, indexEnd, thresholdLo, thresholdHi, winLength, j) && count<winLength)
			{
				count++;
				j++;
			}
			if(count==winLength)
			{
				return i;
			}
			i=j+1;
		}
		return -1;
	}
	
	public static boolean checkData(ArrayList<ArrayList<Double>> data, int indexBegin, int indexEnd, ArrayList<Double> thresholdLo, Double thresholdHi, int winLength, int curr)
	{
		for(int i = 0; i<data.size(); i++)
		{
			if(data.get(i).get(curr)<thresholdLo.get(i) || data.get(i).get(curr)>thresholdHi)
			{
				return false;
			}
		}
		return true;
	}
}
