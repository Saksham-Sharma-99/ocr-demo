package com.example.demo;

import com.example.ocr.ReadReceipt;
import com.example.ocr.ReceiptInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@GetMapping(path = "/")
	public static HashMap<String,String> home(@RequestParam String imagePath){
		ReadReceipt receiptData = new ReadReceipt();
		ReceiptInfo receiptInfo = receiptData.DoOCR(imagePath);
		HashMap<String,String> response = new HashMap<>();
		response.put("date",receiptInfo.getDate());
		response.put("amount",receiptInfo.getAmount());

		return response;
	}

	@PostMapping(path = "/")
	public HashMap<String,String> receiptInfo(@RequestParam("imageFile") MultipartFile file){

		Path root = Paths.get("uploads");

		String fileName = file.getOriginalFilename().split("\\.")[0]
				+ "-" + (new Date().toString()) + "."
				+ file.getOriginalFilename().split("\\.")[1];

		try {
			Files.copy(file.getInputStream(), root.resolve(fileName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		ReadReceipt receiptData = new ReadReceipt();
		ReceiptInfo receiptInfo = receiptData.DoOCR("uploads/"+fileName);
		HashMap<String,String> response = new HashMap<>();
		response.put("date",receiptInfo.getDate());
		response.put("amount",receiptInfo.getAmount());
		response.put("merchant",receiptInfo.getMerchant());

		return response;
	}

	@PostMapping(path = "/aprise")
	public String receiptInfoAprise(@RequestParam("imageFile") MultipartFile file){

		Path root = Paths.get("uploads");

		String fileName = file.getOriginalFilename().split("\\.")[0]
				+ "-" + (new Date().toString()) + "."
				+ file.getOriginalFilename().split("\\.")[1];

		try {
			Files.copy(file.getInputStream(), root.resolve(fileName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		ReadReceipt receiptData = new ReadReceipt();
		return receiptData.DoOCRAprise("uploads/"+fileName);
	}



}
