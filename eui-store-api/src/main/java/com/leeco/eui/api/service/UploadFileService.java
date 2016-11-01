/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Bin Gong
 *
 */
@Service
public class UploadFileService {

	public static final String ROOT = "upload-dir";
	private static final String MESSAGE = "message";
		
	public String uploadInfo(Model model) throws IOException {
		return "lezone/update/uploadForm";
	}
	
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute(MESSAGE, 
					"The file " + file.getOriginalFilename() + " is empty.");
		}
		else {
			try {
				Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
				redirectAttributes.addFlashAttribute(MESSAGE, 
						file.getOriginalFilename() + " has been uploaded successfully!");
			}
			catch (IOException | RuntimeException e) {
				redirectAttributes.addFlashAttribute(MESSAGE, 
						"Failed to upload the file " + file.getOriginalFilename() + ". " + e.getMessage());
			}
		}
		return "redirect:/lezone/update/upload";
	}
}
