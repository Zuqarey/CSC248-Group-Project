package com.smartcare.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import src.main.java.com.smartcare.Appointment;
import src.main.java.com.smartcare.LinkedList;
import src.main.java.com.smartcare.MedicalRecord;
import src.main.java.com.smartcare.Patient;

public class PatientFileHandler {
    File fileName;

    public PatientFileHandler(String filePath) {
        fileName = new File(filePath);
    }

    public LinkedList parseRecord() {
        LinkedList patientLL = new LinkedList();

        try (Scanner inputFile = new Scanner(fileName)) {
            while (inputFile.hasNextLine()) {
                StringTokenizer tokens = new StringTokenizer(inputFile.nextLine(), ",");

                String nric = tokens.nextToken();
                String name = tokens.nextToken();
                String phoneNumber = tokens.nextToken();
                String patientID = tokens.nextToken();
                String careType = tokens.nextToken();
                String details = tokens.nextToken();
                double medicalFee = Double.parseDouble(tokens.nextToken());
                boolean isCritical = Boolean.parseBoolean(tokens.nextToken());
                String appointmentId = tokens.nextToken();;
                String date = tokens.nextToken();; 
                String time = tokens.nextToken();; 

                MedicalRecord medRec = new MedicalRecord(careType, details, medicalFee, isCritical); 
                Appointment App = new Appointment(appointmentId, date, time);    
                Patient patient = new Patient(nric, name, phoneNumber, patientID, medRec, App);

                patientLL.insertAtBack(patient);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return patientLL;
    }

    public boolean saveRecord(LinkedList patientList) {
        if (patientList == null) {
            return false;
        }
        // Don't overwrite the file with an empty list — preserve existing data
        if (patientList.isEmpty()) {
            return false;
        }

        try (PrintWriter outputFile = new PrintWriter(fileName)) {
            Patient obj = (Patient) patientList.getFirst();
            while (obj != null) {
                // output for every data in the patient object
                outputFile.println(obj.getNRIC() + "," + 
                    obj.getName() + "," +
                    obj.getPhoneNumber() + "," + 
                    obj.getPatientID() + "," +
                    obj.getMedicalRecord().getCareType() + "," + 
                    obj.getMedicalRecord().getDetails() + "," +
                    obj.getMedicalRecord().getMedicalFee() + "," + 
                    obj.getMedicalRecord().getIsCritical() + "," +
                    obj.getAppointment().getAppointmentId() + "," + 
                    obj.getAppointment().getDate() + "," +
                    obj.getAppointment().getTime());
                obj = (Patient) patientList.getNext();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}