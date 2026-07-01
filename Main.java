public class Main {
    public static void main(String[] args) {
        PatientFileHandler patientFileHandler = new PatientFileHandler("PatientList.txt");
        LinkedList patientList = patientFileHandler.parseRecord();

        System.out.println(patientList.getFirst());

        // remove first patient and then save
        patientList.removeFromBack();
        patientFileHandler.saveRecord(patientList);
    }
}