package Controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import Model.BO.FileConverter_BO;
import Model.BO.TaskWorker;

import java.io.File;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Server is starting up...");

        // 1. Cập nhật trạng thái PROCESSING thành FAILED
        try {
            FileConverter_BO.failProcessingTasks();
            System.out.println("Updated PROCESSING tasks to FAILED.");
        } catch (Exception e) {
            System.err.println("Error updating tasks to FAILED: " + e.getMessage());
        }

        // 2. Dọn dẹp file tạm
        try {
            String uploadDirPath = sce.getServletContext().getRealPath("/uploads");
            FileConverter_BO.cleanupTemporaryFiles(uploadDirPath);
            System.out.println("Cleaned up temporary files.");
        } catch (Exception e) {
            System.err.println("Error cleaning up temporary files: " + e.getMessage());
        }

        // 3. Khởi động lại TaskWorker để xử lý các task PENDING
        TaskWorker.startWorker();
        System.out.println("TaskWorker restarted to process PENDING tasks.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Server is shutting down...");
    }
}