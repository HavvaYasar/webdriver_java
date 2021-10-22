package utilities.ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.Platform;

import java.io.File;
import java.io.IOException;

public class ExtentManager {

    private static ExtentReports extent;
    private static Platform platform;
    private final static String reportFileName = "lcwTestAutomationReport.html";
    private final static String macPath = System.getProperty("user.dir") + "/TestReports";
    private final static String windowsPath = System.getProperty("user.dir") + "\\TestReports";
    private final static String linuxPath = System.getProperty("user.dir") + "/TestReports/";
    private final static String macReportFileLoc = macPath + "/" + reportFileName;
    private final static String winReportFileLoc = windowsPath + "\\" + reportFileName;
    private final static String linuxReportFileLoc = linuxPath + "/" + reportFileName;
    private final static File CONF = new File("src/main/resources/spark-config.json");


    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        String fileLocation = getReportFileLocation(platform);
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileLocation);
        try {
            reporter.loadJSONConfig(CONF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;
    }

    private static String getReportFileLocation(Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
                break;
            case WINDOWS:
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);
                System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
                break;
            case LINUX:
                reportFileLocation = linuxReportFileLoc;
                System.out.println("Extent Report Path for Linux: " + linuxPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }

    private static void createReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!");
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }

    private static Platform getCurrentPlatform() {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                platform = Platform.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }
}
