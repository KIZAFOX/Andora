package fr.kizafox.andora.tools.starter;

public enum StatusPlugin {

     LOAD, IN_USE;

     private static StatusPlugin status;

    public static void setStatus(StatusPlugin status) {
        StatusPlugin.status = status;
    }

    public static boolean isStatus(StatusPlugin status) {
        return StatusPlugin.status == status;
    }

    public static StatusPlugin getStatus() {
        return status;
    }
}
