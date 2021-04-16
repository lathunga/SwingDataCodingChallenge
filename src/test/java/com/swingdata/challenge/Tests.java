package com.swingdata.challenge;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class Tests {

	
	private final CSVController controller = new CSVController();
	

    @Test
    void testSearch()  throws NumberFormatException, IOException {
    	MultipartFile swingData = new MockMultipartFile("/lathunga-SwingDataCodingChallenge/src/test/java/com/swingdata/challenge/latestSwing.csv","/lathunga-SwingDataCodingChallenge/src/test/java/com/swingdata/challenge/latestSwing.csv","/lathunga-SwingDataCodingChallenge/src/test/java/com/swingdata/challenge/latestSwing.csv",
                new FileInputStream(new File("/lathunga-SwingDataCodingChallenge/src/test/java/com/swingdata/challenge/latestSwing.csv")));
    	controller.uploadCSVFile(swingData, null);
    	assertEquals(21, controller.searchContinuityAboveValue(controller.swingD.ax, 0, 100, .1, 8));
    	assertEquals(44, controller.backSearchContinuityWithinRange(controller.swingD.ay, 100, 0, 1, 7, 6));
    	assertEquals(20, controller.searchContinuityAboveValueTwoSignals(controller.swingD.timestamp, controller.swingD.wy, 0, 100, 23000,-12.5, 4));
    	assertArrayEquals(new int[] {1,5}, controller.searchMultiContinuityWithinRange(controller.swingD.wz, 0, 100, -6.5, -5.97, 3).get(0));
    }

}
