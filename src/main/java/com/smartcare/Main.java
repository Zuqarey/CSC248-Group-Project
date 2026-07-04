package com.smartcare;

import javax.swing.JOptionPane;

import com.smartcare.io.PatientFileHandler;
import com.smartcare.io.StaffFileHandler;
import com.smartcare.util.LinkedList;
import com.smartcare.util.Queue;

public class Main {
	/** Main Program
	 * @author Zufar
	 */
	public static void main(String[] args) {
		PatientFileHandler patientFileHandler = new PatientFileHandler("patients.txt");
		LinkedList patientList = patientFileHandler.parseRecord();

		StaffFileHandler staffFileHandler = new StaffFileHandler("staffs.txt");
		LinkedList staffList = staffFileHandler.parseRecord();

		Queue appointmentQueue = new Queue();

		if (patientList == null && staffList == null) {
			return;
		}

		Person loggedInPerson = login(patientList, staffList);

		boolean logout = false;

		while (!logout && loggedInPerson != null) {

			// Paitent Menu
			if (loggedInPerson instanceof Patient) {
				Patient patient = (Patient) loggedInPerson;

				Object[] options = { "Check Medical Record", "Book Appointment", "Logout"};
				int chosenOption = JOptionPane.showOptionDialog(null, "Please choose your option", "Patient Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (chosenOption == 0) {
					JOptionPane.showMessageDialog(null, patient.toString());
				} else if (chosenOption == 1) {
					bookAppointment(appointmentQueue, patient);
				} else {
					logout = true;
				}
			// Staff Menu after Login
			} else if (loggedInPerson instanceof Staff) {
				// TODO: 1.0 Search Patient Medical Record (based on ic)
				// TODO: 2.0 Search previous Appointment that happened 
				// TODO: 3.0 Manage New Apppointment
				// TODO: 3.1 Traverse New Apppointment

				// TODO: 3.1 Traverse New Apppointment - done by Fareed
				System.out.println("\n--- Patient Registry & Appointments Traversal ---");

				if (patientMasterList.isEmpty()) {
					System.out.println("The registry is currently empty. No appointments found.");
				} else {
					int recordCount = 1;

					for (Object currentObj = patientMasterList.getFirst(); currentObj != null; currentObj = patientMasterList.getNext()) {
						
						Patient currentPatient = (Patient) currentObj;

						System.out.println("\n[Record " + recordCount + "]");
						System.out.println(currentPatient.toString());
						System.out.println("--------------------------------------------------");

						recordCount++;
					}
					
					System.out.println("End of registry traversal. Total records found: " + (recordCount - 1));
				}

				// TODO: 3.2 Update New Apppointment - done by Fareed
				System.out.println("\n--- Update Existing Appointment ---");
				System.out.print("Enter the Patient ID to update (e.g., P1001): ");
				String searchId = scan.nextLine();

				boolean isFound = false;

				for (Object currentObj = patientMasterList.getFirst(); currentObj != null; currentObj = patientMasterList.getNext()) {
					
					Patient currentPatient = (Patient) currentObj;
					
					if (currentPatient.getPatientID().equalsIgnoreCase(searchId)) {
						isFound = true;
						System.out.println("\nPatient found: " + currentPatient.getName());
						System.out.println("Current Appointment Details: " + currentPatient.getAppointment().toString());
						
						System.out.print("Enter New Appointment Date (YYYY-MM-DD) or press Enter to keep current: ");
						String newDate = scan.nextLine();
						if (!newDate.trim().isEmpty()) {
							currentPatient.getAppointment().setDate(newDate);
						}
						
						System.out.print("Enter New Appointment Time (HH:MM AM/PM) or press Enter to keep current: ");
						String newTime = scan.nextLine();
						if (!newTime.trim().isEmpty()) {
							currentPatient.getAppointment().setTime(newTime);
						}
						
						System.out.println("\nAppointment successfully updated!");
						System.out.println("Updated Info: " + currentPatient.getAppointment().toString());
						
						break; 
					}
				}

				if (!isFound) {
					System.out.println("Error: Patient ID '" + searchId + "' not found in the active registry.");
				}

				// TODO: 3.2.1 Update Assigned doctor
				// TODO: 3.3 Delete New Apppointment (Deceased/MIA)
			}
		}

		patientFileHandler.saveRecord(patientList);
		staffFileHandler.saveRecord(staffList);
	}

	/** Menu for Login
	 * @author Zufar
	 */
	public static Person login(LinkedList patientList, LinkedList staffList) {
		boolean exit = false;
		boolean patientLogin = false;
		boolean staffLogin = false;

		while (!exit) {
			String nric = JOptionPane.showInputDialog("Enter your NRIC");
			if (nric == null || nric.isEmpty()) {
				return null;
			}

			// Check for patients
			Patient p = (Patient) patientList.getFirst();
			while (p != null) {
				if (p.login(nric)) {
					patientLogin = true;
					JOptionPane.showMessageDialog(null, "Welcome " + p.getName());
					return p;
				}
				p = (Patient) patientList.getNext();
			}

			// Check for doctor/staff
			if (!patientLogin) {
				String idStr = JOptionPane.showInputDialog("Enter your ID");
				int id = Integer.parseInt(idStr);
				String password = JOptionPane.showInputDialog("Enter your password");

				Staff s = (Staff) staffList.getFirst();
				while (s != null) {
					if (s.login(id, password)) {
						staffLogin = true;
						JOptionPane.showMessageDialog(null, "Welcome "+s.getName());
						return s;
					}
					s = (Staff) staffList.getNext();
				}
			}

			if (!patientLogin && !staffLogin) {
				/*TODO: 4.0 Add new patient(
				 nric
				 name
				 phone number
				 TODO 0.1 :buang patient id []
				 
				 medical reccord (penyakit)
					careType = hospital faculty (patient)
					details =  condition        (patient)
					medicalFee // Doctor
					isCritical patient
				 Appointment patient
				 	Date patient
					Time
				 */
			}
		}

		return null;
	}

	// For Patient
	public static void bookAppointment(Queue appointmentQueue, Patient patient) {
		// TODO: Implement bookAppointment
		Queue tempQueue = new Queue();
		 for (int i = 0; i < 5; i++) {
			String date = JOptionPane.showInputDialog("Enter appointment date (YYYY-MM-DD):");
			String time = JOptionPane.showInputDialog("Enter appointment time (HH:MM (P/M)):");

			Appointment appointment = new Appointment(date, time);
			patient.setAppointment(appointment);
		 }
	}
}