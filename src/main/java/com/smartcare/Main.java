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

		PatientFileHandler appointmentHandler = new PatientFileHandler("QueueNewAppointment.txt");
		LinkedList appointmentList = appointmentHandler.parseRecord();
		Queue appointmentQueue = new Queue();

			for (Object obj = appointmentList.getFirst();
     			obj != null;
    			 obj = appointmentList.getNext()) {

   				 appointmentQueue.enqueue(obj);
				}

		if (patientList == null && staffList == null) {
			return;
		}
		Person loggedInPerson = login(patientList, staffList, patientFileHandler);
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
					SearchPatient(patientList);					// done by @fareed
				} else if (chosenOption == 1) {
					// TODO: 1 Search previous Appointment that happened to view appointment record
					viewAppointmentRecord(patientList);
				} else if (chosenOption == 2) {
					bookAppointment(appointmentQueue);  // done by @Zufar
				} else if (chosenOption == 3) {
					// TODO: 2 Update new Apppointment Staff to update appointment record as a staff
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
	public static Person login(LinkedList patientList, LinkedList staffList, PatientFileHandler patientFileHandler) {
		boolean exit = false;
		boolean patientLogin = false;
		boolean staffLogin = false;

		while (!exit) {
			// Ask person if he/she is new patient if no then proceed with patien login and staff login.
        // ASK THE USER IF THEY ARE A NEW PATIENT FIRST
        	int selection = JOptionPane.showConfirmDialog(null, "Are you a new patient?", "Registration", 
            	JOptionPane.YES_NO_CANCEL_OPTION
        	);

		    if (selection == JOptionPane.CANCEL_OPTION || selection == JOptionPane.CLOSED_OPTION) {
            return null;}
			
			if (selection == JOptionPane.YES_OPTION) {

    			boolean reg = false;
			// NRIC
			String nric = "";

			while (true) {
				nric = JOptionPane.showInputDialog(null, "Enter Your NRIC:");

				if (nric == null) {
					reg = true;
					break;
				}
				if (!nric.trim().isEmpty())
					break;
				JOptionPane.showMessageDialog(null, "NRIC cannot be empty.");
			}
			if (reg)
				continue;

			// Check duplicate NRIC
			boolean exists = false;
			Patient temp = (Patient) patientList.getFirst();

			while (temp != null) {
				if (temp.getNRIC().equals(nric)) {
					exists = true;
					break;
				}
				temp = (Patient) patientList.getNext();
			}
			if (exists) {
				JOptionPane.showMessageDialog(null,"This NRIC is already registered.");
				continue;
			}

			// NAME
			String name = "";
			while (true) {
				name = JOptionPane.showInputDialog("Enter Full Name:");

				if (name == null) {
					reg = true;
					break;
				}
				if (!name.trim().isEmpty())
					break;

				JOptionPane.showMessageDialog(null,
						"Name cannot be empty.");
			}
			if (reg)
				continue;

			// PHONE
			String phone = "";
			while (true) {
				phone = JOptionPane.showInputDialog("Enter Phone Number:");

				if (phone == null) {
					reg = true;
					break;
				}
				if (!phone.trim().isEmpty())
					break;

				JOptionPane.showMessageDialog(null,"Phone number cannot be empty.");
			}
			if (reg)
				continue;

			// CARE TYPE
			String careType = "";

			while (true) {
				careType = JOptionPane.showInputDialog(
						"Enter Care Type / Hospital Faculty:");
				if (careType == null) {
					reg = true;
					break;
				}
				if (!careType.trim().isEmpty())
					break;

				JOptionPane.showMessageDialog(null,"Care Type cannot be empty.");
			}
			if (reg)
				continue;

			// CONDITION DETAILS
			String details = "";
			while (true) {
				details = JOptionPane.showInputDialog("Enter Condition Details:");
				if (details == null) {
					reg = true;
					break;
				}
				if (!details.trim().isEmpty())
					break;
				JOptionPane.showMessageDialog(null,"Condition Details cannot be empty.");
			}
			if (reg)
				continue;

			// CRITICAL
			int criticalChoice = JOptionPane.showConfirmDialog(null,"Is this condition critical?","Critical Condition",JOptionPane.YES_NO_OPTION);
			if (criticalChoice == JOptionPane.CLOSED_OPTION)
				continue;
			boolean isCritical = (criticalChoice == JOptionPane.YES_OPTION);

			// Staff will update later
			double medicalFee = 0.0;
			// APPOINTMENT DATE
			String date = "";

			while (true) {

				date = JOptionPane.showInputDialog("Enter Appointment Date (YYYY-MM-DD):");

				if (date == null) {
					reg = true;
					break;
				}

				if (!date.trim().isEmpty())
					break;

				JOptionPane.showMessageDialog(null,"Appointment Date cannot be empty.");
			}
			if (reg)
				continue;

			// APPOINTMENT TIME
			String time = "";

			while (true) {
				time = JOptionPane.showInputDialog("Enter Appointment Time (HH:MM AM/PM):");
				if (time == null) {
					reg = true;
					break;
				}
				if (!time.trim().isEmpty())
					break;

				JOptionPane.showMessageDialog(null,
						"Appointment Time cannot be empty.");
			}
				if (reg)
				continue;

				// CREATE OBJECTS
                MedicalRecord record = new MedicalRecord(careType, details, medicalFee, isCritical);
                Appointment appointment = new Appointment(date, time);
                Patient newPatient = new Patient(nric, name, phone, record, appointment);

                // SAVE INTO SYSTEM MASTER REGISTRY
                patientList.insertAtBack(newPatient);
                patientFileHandler.saveRecord(patientList);

                // 2. SAVE INTO THE NEW PATIENT QUEUE FILE (QueueNewPatient.txt)
                PatientFileHandler newPatientFileHandler = new PatientFileHandler("QueueNewPatient.txt");
                LinkedList newPatientQueueList = newPatientFileHandler.parseRecord();
                if (newPatientQueueList == null) {
                    newPatientQueueList = new LinkedList();
                }
                newPatientQueueList.insertAtBack(newPatient);
                newPatientFileHandler.saveRecord(newPatientQueueList);

                JOptionPane.showMessageDialog(null, "Registration Successful!\n\n" + "Name : " + newPatient.getName() + "\nNRIC : " + newPatient.getNRIC()
                                                            + "\nPhone : " + newPatient.getPhoneNumber() + "\nAppointment : " + date + " at " + time + "\n\nWelcome to SmartCare!");
                return newPatient;
		}



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
		}

		return null;
	}


	// For Patient
	public static void bookAppointment(Queue appointmentQueue, Patient patient) {
		 
			boolean reg = false;
			String date = "";
				while (true) {
					date = JOptionPane.showInputDialog("Enter New Appointment Date (YYYY-MM-DD):");
					if (date == null) { reg = true; break; }
					if (!date.trim().isEmpty()) break;
					JOptionPane.showMessageDialog(null, "Appointment date cannot be empty. Please re-enter.");	
				}
				if (date == null){
						return;
				}
				String time = "";
				while (true) {
					time = JOptionPane.showInputDialog("Enter New Appointment Time (HH:MM AM/PM):");
					if (time == null) { reg = true; break; }
					if (!time.trim().isEmpty()) break;
					JOptionPane.showMessageDialog(null, "Appointment time cannot be empty. Please re-enter.");
				}
				if (time == null){
						return;
				}
				

				Appointment appointment = new Appointment(date, time);
        			patient.setAppointment(appointment);
        			appointmentQueue.enqueue(patient);

       			// 3. CONVERT QUEUE STATE BACK TO LINKEDLIST AND WRITE TO QueueNewAppointment.txt
        			PatientFileHandler appointmentFileHandler = new PatientFileHandler("QueueNewAppointment.txt");
        			LinkedList tempLinkList = new LinkedList();
       				Queue backupQueue = new Queue();

        		// Drain current queue to populate file list safely without erasing other queue entries
        		while (!appointmentQueue.isEmpty()) {
           		Patient p = (Patient) appointmentQueue.dequeue();
           		tempLinkList.insertAtBack(p);
           		backupQueue.enqueue(p);
        		}

       			 // Restore our primary queue reference structure back to life
        		while (!backupQueue.isEmpty()) {
            		appointmentQueue.enqueue(backupQueue.dequeue());
        		}

        		// Save the updated complete list back to the text file
        		appointmentFileHandler.saveRecord(tempLinkList);

        		JOptionPane.showMessageDialog(null, "Appointment booked for " + patient.getName() + " on " + date + " at " + time + "\nYour Appointment is Booked");
	}

	//for staff
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
		

		for (Object currentObj = patientList.getFirst(); currentObj != null; currentObj = patientList.getNext()) {
			Patient currentPatient = (Patient) currentObj;

				JOptionPane.showMessageDialog(null, "Patient Name: " + currentPatient.getName() + "\nCurrent Appointment Details: " + currentPatient.getAppointment().toString());

				String newDate = JOptionPane.showInputDialog("Enter New Appointment Date (YYYY-MM-DD) or press Enter to keep current:");
				
				if(newDate == null){
					return;
				}
				if (!newDate.trim().isEmpty()) {
					currentPatient.getAppointment().setDate(newDate);
				}

				String newTime = JOptionPane.showInputDialog("Enter New Appointment Time (HH:MM AM/PM) or press Enter to keep current:");
				
				if(newTime == null){
					return;
				}
				if (!newTime.trim().isEmpty()) {
					currentPatient.getAppointment().setTime(newTime);
				}

				JOptionPane.showMessageDialog(null, "Appointment successfully updated!\nUpdated Info: " + currentPatient.getAppointment().toString());
				break; 
			
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