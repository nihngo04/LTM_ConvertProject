package Model.BO;

import Model.Bean.Task;
import Model.DAO.FileConverter_DAO;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class FileConverter_BO {

	public static String pdfToWord(String inputFilePath) {
		return FileConverter_DAO.pdfToWord(inputFilePath);
	}

	public static void addTask(Task task) {
		FileConverter_DAO.addTask(task);
	}

	public static void updateTask(Task task) {
		FileConverter_DAO.updateTask(task);
	}

	public static Task getTask(int id) {
		return FileConverter_DAO.getTask(id);
	}

	public static List<Task> getTasksByUserId(int userId) {
		return FileConverter_DAO.getTasksByUserId(userId);
	}

	public static void failProcessingTasks() throws SQLException {
		List<Task> processingTasks = FileConverter_DAO.getTasksByStatus("PROCESSING");
		for (Task task : processingTasks) {
			task.setStatus("FAILED");
			FileConverter_DAO.updateTask(task);
		}
	}

	public static void cleanupTemporaryFiles(String uploadDirPath) {
		File uploadDir = new File(uploadDirPath);
		if (uploadDir.exists() && uploadDir.isDirectory()) {
			File[] files = uploadDir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.getName().contains("_temp_part")) {
						file.delete();
					}
				}
			}
		}
	}

}