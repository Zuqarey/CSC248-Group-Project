package com.smartcare.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.smartcare.Appointment;
import com.smartcare.util.LinkedList;
import com.smartcare.MedicalRecord;
import com.smartcare.Patient;
public class DoctorFileHandler {
    File fileName;

    public DoctorFileHandler(String filePath) {
        fileName = new File(filePath);
    }

    public LinkedList parseRecord(){
        LinkedList doctorLL = new LinkedList();

        try (Scanner inputFile = new Scanner(fileName)) {
            while (inputFile.hasNextLine()) {
                StringTokenizer tokens = new StringTokenizer(inputFile.nextLine(), ",");

                String nric = tokens.nextToken();
                String name = tokens.nextToken();
                String phoneNumber = tokens.nextToken();
                String doctorID = tokens.nextToken();
                String specialization = tokens.nextToken();
                double consultationFee = Double.parseDouble(tokens.nextToken());
                boolean isAvailable = Boolean.parseBoolean(tokens.nextToken());

                Doctor doctor = new Doctor(nric, name, phoneNumber, doctorID, specialization, consultationFee, isAvailable);

                doctorLL.insertAtBack(doctor);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return doctorLL;

    }

    public boolean saveRecord(LinkedList doctorList) {
        if (doctorList == null) {
            return false;
        }
        // Don't overwrite the file with an empty list — preserve existing data
        if (doctorList.isEmpty()) {
            return false;
        }

        try (PrintWriter outputFile = new PrintWriter(fileName)) {
            Doctor obj = (Doctor) doctorList.getFirst();
            while (obj != null) {
               outputFile.println(obj.getNric() + "," +
                obj.getName() + "," +
                obj.getPhoneNumber() + "," +
                obj.getStaffId() + "," +
                obj.getPassword() + "," +
                obj.getRole()+ "," +
                obj.getAppointment().getAppointmentId() + "," +
                obj.getAppointment().getDate() + "," +
                obj.getAppointment().getTime());
                obj = (Doctor) doctorList.getNext();
                }   
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
                }         
    }
}
