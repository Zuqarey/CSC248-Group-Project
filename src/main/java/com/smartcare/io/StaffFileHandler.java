package com.smartcare.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.smartcare.Staff;
import com.smartcare.util.LinkedList;

public class StaffFileHandler {

    private File fileName;

    public StaffFileHandler(String filePath) {
        fileName = new File(filePath);
    }

    public LinkedList parseRecord() {

        LinkedList staffLL = new LinkedList();

        try (Scanner inputFile = new Scanner(fileName)) {

            while (inputFile.hasNextLine()) {

                String line = inputFile.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                StringTokenizer tokens = new StringTokenizer(line, ",");

                String nric = tokens.nextToken().trim();
                String name = tokens.nextToken().trim();
                String phoneNumber = tokens.nextToken().trim();
                int staffID = Integer.parseInt(tokens.nextToken().trim());
                String password = tokens.nextToken().trim();
                String role = tokens.nextToken().trim();

                Staff staff = new Staff(
                        nric,
                        name,
                        phoneNumber,
                        staffID,
                        password,
                        role);

                staffLL.insertAtBack(staff);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return staffLL;
    }

    public boolean saveRecord(LinkedList staffList) {

        if (staffList == null || staffList.isEmpty()) {
            return false;
        }

        try (PrintWriter outputFile = new PrintWriter(fileName)) {

            Staff staff = (Staff) staffList.getFirst();

            while (staff != null) {

                outputFile.println(
                        staff.getNRIC() + "," +
                        staff.getName() + "," +
                        staff.getPhoneNumber() + "," +
                        staff.getStaffID() + "," +
                        staff.getPassword() + "," +
                        staff.getRole());

                staff = (Staff) staffList.getNext();
            }

            return true;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}