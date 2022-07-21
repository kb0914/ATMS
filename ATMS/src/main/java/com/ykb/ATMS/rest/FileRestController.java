package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ykb.ATMS.DTO.FileRespondDTO;
import com.ykb.ATMS.DTO.FileRespondMessage;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.service.Interface.IFileDBService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class FileRestController {

	private IFileDBService fileDBService;
	
	@Autowired
	public FileRestController(IFileDBService fileDBService) {
		this.fileDBService=fileDBService;
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<FileRespondDTO>> getListFiles() {
	    		
	    return ResponseEntity.status(HttpStatus.OK).body(fileDBService.getFileList());
	}
	
	@GetMapping("/files/team/{tid}")
	public ResponseEntity<TeamDTO> getListFiles(@PathVariable long tid) {
	    		
	    return ResponseEntity.status(HttpStatus.OK).body(fileDBService.getFileListByTeamId(tid));
	}
	
	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable long id) {
	    FileDB fileDB = fileDBService.getFile(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	}
	
	@PostMapping("/upload/{tid}/{sid}")
	public ResponseEntity<FileRespondMessage> uploadFile(@RequestParam("file") MultipartFile file,
			@PathVariable long sid, @PathVariable long tid) {
	    String message = "";
	    try {
	    	fileDBService.store(file, tid, sid);
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new FileRespondMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileRespondMessage(message));
	    }
	  }
	
	@PostMapping("/uploadMainFile/{tid}/{sid}")
	public ResponseEntity<FileRespondMessage> uploadMainFile(@RequestParam("file") MultipartFile file,
			@PathVariable long sid, @PathVariable long tid) {
	    String message = "";
	    try {
	    	fileDBService.storeMainFile(file, tid, sid);
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new FileRespondMessage(message));
	    } catch (Exception e) {
	    	System.out.println(e);
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileRespondMessage(message));
	    }
	  }
	
	@PutMapping("/files/{id}")
	public void linkTaskToFile(@RequestBody List<Task> tasks, @PathVariable long id) {
		fileDBService.linkTaskToFile(id, tasks); 
		
	}
	
	@PutMapping("/files/removetask/{id}")
	public void linkTaskToFile(@RequestBody Task task, @PathVariable long id) {
		System.out.println("unlink");
		fileDBService.unlinkTaskToFile(id, task);
		
	}
	
	@DeleteMapping("/files/{id}")
	public void deleteFile(@PathVariable long id) {
	    fileDBService.deleteFile(id);
	}
}
