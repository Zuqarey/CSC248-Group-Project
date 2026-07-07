package com.smartcare;

import javax.swing.JOptionPane;

import com.smartcare.io.PatientFileHandler;
import com.smartcare.io.StaffFileHandler;
import com.smartcare.util.LinkedList;
import com.smartcare.util.Queue;

public class Main {
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

				Object[] options = { "Check Medical Record", "Book Appointment","Change Appointment", "Logout"};
				int chosenOption = JOptionPane.showOptionDialog(null, "Please choose your option", "Patient Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (chosenOption == 0) {
					JOptionPane.showMessageDialog(null, patient.toString());
				} else if (chosenOption == 1) {
					bookAppointment(appointmentQueue, patient);
				} else if (chosenOption == 2) {
					// done by fareed
						changeAppointment(patientList);
				} else{
					logout = true;
				}

				patientFileHandler.saveRecord(patientList);
				staffFileHandler.saveRecord(staffList);

			// Staff Menu after Login
			} else if (loggedInPerson instanceof Staff) {
				Object[] options = { "Check Medical Record", "View Appointments","New Appointment","Update Medical Record","Remove Patient","View New Patient","Logout"};
				int chosenOption = JOptionPane.showOptionDialog(null, "Please choose your option", "Staff Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (chosenOption == 0) {
					SearchPatient(patientList);					// done by fareed
				} else if (chosenOption == 1) {
					// TODO: 1 Search previous Appointment that happened to view appointment record
					viewAppointmentRecord(patientList);
				} else if (chosenOption == 2) {
					bookAppointment(appointmentQueue);
				} else if (chosenOption == 3) {
					// TODO: 2 Update New Apppointment Staff to update appointment record as a staff
					updateMedicalRecord(patientList);
				} else if (chosenOption == 4) {
					// TODO: 3 Delete Apppointment (if Deceased/MIA)
					RemovePatient(patientList);
				} else if (chosenOption == 5) {
					// TODO: 4 View (Traverse) new patient for Staff to view patients who are visiting for the first time
					viewNewPatient(patientList);
				} else {
					logout = true;// keluar dari menu staff
				}
		
				patientFileHandler.saveRecord(patientList);
				staffFileHandler.saveRecord(staffList);
			} else {
				
			}
		}
	}

	/** Menu for Login
	 * @author Zufar
	 */
	public static Person login(LinkedList patientList, LinkedList staffList) {
		boolean exit = false;
		boolean patientLogin = false;
		boolean staffLogin = false;

		while (!exit) {
			// Ask person if he/she is new patient if no then proceed with patien login and staff login.

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

			// New Patient
			if (!patientLogin && !staffLogin) {
				/*TODO: 5 Add new patient for first time becasue they dont have medical record
				 Insert:
				 1. Patient
						nric
				  		name
				  		phone number
				 2. medical record (penyakit) 
						careType = hospital faculty
						details =  condition
						medicalFee // staff yang akan isi yang lain semua patient yang isi
						isCritical patient
				 3. Appointment patient
				 		Date patient
						Time
				 4. Add new patient to the patientList

				 # nanti patient masuk kan medical record baru.
				staff check medical record yang patient buat baru tadi
				staff kena isi meidcal fee patient
				dalam masa yang sama staff boleh overide data2 lain kalau nak
				 */
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
		}

		return null;
	}

	// For Patient
	public static void bookAppointment(Queue appointmentQueue, Patient patient) {
		 for (int i = 0; i < 5; i++) {
			String date = JOptionPane.showInputDialog("Enter appointment date (YYYY-MM-DD):");
			String time = JOptionPane.showInputDialog("Enter appointment time (HH:MM (P/M)):");

			Appointment appointment = new Appointment(date, time);
			patient.setAppointment(appointment);
			appointmentQueue.enqueue(patient);

			JOptionPane.showMessageDialog(null, "Appointment booked for " + patient.getName() + " on " + date + " at " + time +"\nYour Appointment is Booked");
		 }
	}

	public static void bookAppointment(Queue appointmentQueue) {
		Queue tempQueue = new Queue();
		while(!appointmentQueue.isEmpty()){
			Patient patient = (Patient) appointmentQueue.dequeue();
			tempQueue.enqueue(patient);
			JOptionPane.showMessageDialog(null, patient.toString());
		}

		while(!tempQueue.isEmpty()){
			appointmentQueue.enqueue(tempQueue.dequeue());
		}
	}

	public static void SearchPatient(LinkedList patientList) {
		String searchId = "something";
		boolean isFound = false;
		while(true) {
			for (Object currentObj = patientList.getFirst(); currentObj != null || isFound; currentObj = patientList.getNext()) {
				searchId = JOptionPane.showInputDialog("Enter the Patient NRIC to search (e.g., 999999999999):");

				if (searchId == null || searchId.isEmpty()) {
					return;
				}

				Patient currentPatient = (Patient) currentObj;

				if (currentPatient.getNRIC().equalsIgnoreCase(searchId)) {
					isFound = true;
					JOptionPane.showMessageDialog(null, "Patient found: " + currentPatient.toString()); 
				} else {
					JOptionPane.showMessageDialog(null, "Error: Patient ID '" + searchId + "' not found in the active registry.");
				}
			}
		}
	}

	public static void changeAppointment(LinkedList patientList) {
		String searchId = JOptionPane.showInputDialog("Enter the Patient NRIC to update (e.g., 999999999999):");
		boolean isFound = false;

		for (Object currentObj = patientList.getFirst(); currentObj != null; currentObj = patientList.getNext()) {
			Patient currentPatient = (Patient) currentObj;

			if (currentPatient.getNRIC().equalsIgnoreCase(searchId)) {
				isFound = true;
				JOptionPane.showMessageDialog(null, "Patient found: " + currentPatient.getName() + "\nCurrent Appointment Details: " + currentPatient.getAppointment().toString());

				String newDate = JOptionPane.showInputDialog("Enter New Appointment Date (YYYY-MM-DD) or press Enter to keep current:");
				if (!newDate.trim().isEmpty()) {
					currentPatient.getAppointment().setDate(newDate);
				}

				String newTime = JOptionPane.showInputDialog("Enter New Appointment Time (HH:MM AM/PM) or press Enter to keep current:");
				if (!newTime.trim().isEmpty()) {
					currentPatient.getAppointment().setTime(newTime);
				}

				JOptionPane.showMessageDialog(null, "Appointment successfully updated!\nUpdated Info: " + currentPatient.getAppointment().toString());
				break; 
			}
		}

		if (!isFound) {
			JOptionPane.showMessageDialog(null, "Error: Patient ID '" + searchId + "' not found in the active registry.");
		}
	}

	// method to untuk tengok appointment record
	public static void viewAppointmentRecord(LinkedList patientList){
		Patient patient = (Patient) patientList.getFirst();// casting to patient so that we can get 
		Appointment appointment = patient.getAppointment();// casting to get the appointment from patient
		
	}

	//Methode Untuk remove patient dari linked list
	public static void RemovePatient(LinkedList PatientList) {
		// sebab mati atau MIA

	}

	//Menthode untuk staff tengok patient yang first time buat appointment
	public static void viewNewPatient(LinkedList patientList) {
	}

	public static void updateMedicalRecord( LinkedList patientList){

	}
		
}