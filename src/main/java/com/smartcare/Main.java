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
				// TODO: 3.2 Update New Apppointment
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
				 TODO 0.1 :buang patient id
				 
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
	}
}