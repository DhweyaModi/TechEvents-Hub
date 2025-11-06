package ui;


import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {

        //PHASE 3 CODE. 
        SwingUtilities.invokeLater(() -> { // SwingUtilities closes program on gui window exit. (EDT: thread)
            TechEventsHubGUI gui = new TechEventsHubGUI();
            gui.setVisible(true); // so that changes are actually visible. 
        });


        
        //PHASE 2 CODE. 
        /* System.out.println(" ");
        System.out.println("==========================");
        System.out.println("Welcome to my project!");
        System.out.println(" ");
    
        TechEventsAppConsoleRunner appConsoleRunner = new TechEventsAppConsoleRunner();

        appConsoleRunner.loadPreviousInstance();

        */

        //TechEventsHubAppRunner savedProgress = appConsoleRunner.saveProgress(); //PHASE 1. 

        
    }
}
