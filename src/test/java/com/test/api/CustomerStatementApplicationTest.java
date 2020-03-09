package com.test.api;

import static com.test.api.constants.Constants.CSV_FILE_OUTPUT_PATH;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.test.api.models.Report;
import com.test.api.scheduler.CustomerRecordsCSV;
import com.test.api.scheduler.CustomerRecordsXML;
import com.test.api.scheduler.CustomerStatementProcessor;

public class CustomerStatementApplicationTest {

	private CustomerStatementProcessor customerStatementProcessor;
	private CustomerRecordsCSV customerRecordsCSV = new CustomerRecordsCSV();
	private CustomerRecordsXML customerRecordsXML = new CustomerRecordsXML();

	@Before()
	public void setup() throws IOException {
		new File("./batch.running").deleteOnExit();
		new File("./input/records.csv").deleteOnExit();
		new File("./input/records.xml").deleteOnExit();
		new File("./output/reports.xml").deleteOnExit();
		FileCopyUtils.copy(new File("records.csv"), new File("./input/records.csv"));
		FileCopyUtils.copy(new File("records.xml"), new File("./input/records.xml"));
		customerStatementProcessor = new CustomerStatementProcessor(customerRecordsCSV, customerRecordsXML);
	}

	@Test
	public void testCustomerStatementApplicationReport() throws IOException, CsvException {
		customerStatementProcessor.startCustomerStatementProcessor();
		List<Report> reportsFromFile = new ArrayList<>();
		FileReader filereader = new FileReader(CSV_FILE_OUTPUT_PATH);
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> allRows = csvReader.readAll();
		int failedStatusCount = 0;
		Map<Integer, Integer> refCount = new HashMap<>();
		for (String[] row : allRows) {
			Report report = new Report();
			report.setDescription(row[0]);
			report.setStatus(row[1]);
			if (row[1].equals("Failed")) {
				failedStatusCount++;
			}
			report.setTransactionReferenceNumber(Integer.parseInt(row[2]));
			refCount.put(Integer.parseInt(row[2]), refCount.getOrDefault(Integer.parseInt(row[2]), 0) + 1);
			reportsFromFile.add(report);
		}
		assertEquals(reportsFromFile.size(), 6);
		assertEquals(failedStatusCount, 6);
		assertEquals(refCount.get(112806).intValue(), 3);
		assertEquals(refCount.get(167875).intValue(), 1);
		assertEquals(refCount.get(165102).intValue(), 1);
		assertEquals(refCount.get(183356).intValue(), 1);
	}
}
