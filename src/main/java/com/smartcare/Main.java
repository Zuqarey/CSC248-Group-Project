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

		while (!logout) {
			if (loggedInPerson instanceof Patient) {
				Patient patient = (Patient) loggedInPerson;

				Object[] options = { "Check Medical Record", "Book Appointment", "Logout"};
				int chosenOption = JOptionPane.showOptionDialog(null, "Please choose your option", "Patient Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (chosenOption == 0) {
					medicalRecord(patient);
				} else if (chosenOption == 1) {
					bookAppointment(appointmentQueue, patient);
				} else {
					logout = true;
				}
			} else {

			}
		}


		patientFileHandler.saveRecord(patientList);
		staffFileHandler.saveRecord(staffList);
	}

	public static Person login(LinkedList patientList, LinkedList staffList) {
		boolean exit = false;
		boolean patientLogin = false;
		boolean staffLogin = false;

		while (!exit) {
			String nric = JOptionPane.showInputDialog("Enter your NRIC");
			if (nric == null) {
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
						JOptionPane.showMessageDialog(null, "Welcome Dr.");
						return s;
					}
					s = (Staff) staffList.getNext();
				}
			}
		}

		return null;
	}

	public static void medicalRecord(Patient patient) {
		JOptionPane.showMessageDialog(null, patient.toString());
	}

	public static void bookAppointment(Queue appointmentQueue, Patient patient) {
		/* TODO: Tarikh
		*	sakit
		*/

		String year = JOptionPane.showInputDialog("Enter your appointment date (YYYY-MM-DD)");
		String sickness = JOptionPane.showInputDialog("Enter your sickness");

		appointmentQueue.enqueue(new Appointment(year, sickness));
	}
}