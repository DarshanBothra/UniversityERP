package edu.univ.erp.access;

import edu.univ.erp.domain.Setting;
import edu.univ.erp.data.SettingDAO;

public class MaintenanceManager {

    public static final SettingDAO settingDAO = new SettingDAO();

    private MaintenanceManager(){}

    /**
     * Enables maintenance mode.
     * Students and instructors enter view only mode
     */
    public static boolean enableMaintenance(){
        try{
            boolean result = settingDAO.updateSetting("maintenance_on", "on");
            if (result){
                System.out.println("Maintenance mode enabled successfully.");
            }
            else{
                System.err.println("Failed to enable maintenance mode.");
            }
            return result;
        } catch (Exception e){
            System.err.println("Error enabling maintenance mode: " + e.getMessage());
            return false;
        }
    }

    /**
     * Disables maintenance mode.
     * Normal system operation resumes
     */

    public static boolean disableMaintenance(){
        try{
            boolean result = settingDAO.updateSetting("maintenance_mode", "off");
            if (result){
                System.out.println("Maintenance mode disabled successfully.");
            }
            else{
                System.err.println("Failed to disable maintenance mode.");
            }
            return result;
        } catch (Exception e){
            System.err.println("Error disabling maintenance mode: " + e.getMessage());
            return false;
        }
    }

    /**
     * Toggles maintenance based on current state
     */
    public static boolean toggleMaintenanceMode(){
        try {
            boolean isOn = AccessControl.isMaintenanceOn();
            if (isOn){
                return disableMaintenance();
            } else {
                return enableMaintenance();
            }
        } catch (Exception e){
            System.err.println("Error toggling maintenance mode.");
            return false;
        }
    }

    /**
     * returns current maintenance flag
     */

    public static boolean isMaintenanceOn(){
        return AccessControl.isMaintenanceOn();
    }
}
