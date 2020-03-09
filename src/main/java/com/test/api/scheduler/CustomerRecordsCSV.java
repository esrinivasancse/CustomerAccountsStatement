package com.test.api.scheduler;

import static com.test.api.constants.Constants.CSV_FILE_PATH;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.test.api.models.Record;

/**
 * The Class CustomerRecordsCSV.
 */
@Component
public class CustomerRecordsCSV {

	/** The logger. */
	Logger logger = LogManager.getLogger(CustomerRecordsCSV.class);
	
	/**
	 * Load customer records from csv.
	 *
	 * @return the list
	 */
	public List<Record> loadCustomerRecordsFromCsv() {
		List<Record> records = new ArrayList<Record>();
		try {
			FileReader filereader = new FileReader(CSV_FILE_PATH);
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();
			for (String[] row : allData) {
				Record record = new Record();
				record.setTransactionReferenceNumber(Integer.parseInt(row[0]));
				record.setAccountNumber(row[1]);
				record.setDescription(row[2]);
				record.setStartBalance(Double.parseDouble(row[3]));
				record.setMutation(Double.parseDouble(row[4]));
				record.setEndBalance(Double.parseDouble(row[5]));
				records.add(record);
			}
		} catch (Exception e) {
			logger.error("Exception occured while processing CSV Records : {}", e.getMessage());
			records = Collections.emptyList();
		}
		return records;
	}
}
