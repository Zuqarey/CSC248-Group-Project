package com.smartcare;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.smartcare.io.StaffFileHandler;
import com.smartcare.util.LinkedList;

/**
 * Unit test for simple App.
 */
public class AppTest {
    // Write a test for the StaffFileHandler class
    @Test
    public void testStaffFileHandler() {
        // Create a StaffFileHandler object
        StaffFileHandler staffFileHandler = new StaffFileHandler("staffs.txt");

        // Parse the staff records
        LinkedList staffList = staffFileHandler.parseRecord();

        // Check if the staff list is not empty
        assertTrue(staffList.getFirst() != null);
    }
}
