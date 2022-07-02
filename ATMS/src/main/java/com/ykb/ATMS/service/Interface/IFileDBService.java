package com.ykb.ATMS.service.Interface;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ykb.ATMS.DTO.FileReceiveDTO;
import com.ykb.ATMS.DTO.FileRespondDTO;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Task;

public interface IFileDBService {

	List<FileDB>  getAllFiles();

	FileDB getFile(long id);

	FileDB store(MultipartFile file, long tid, long sid) throws IOException;

	void linkTaskToFile(long id, List<Task> tasks);

	void unlinkTaskToFile(long id, Task task);

	List<FileRespondDTO> getFileList();

	FileRespondDTO getTaskProveFile(long id);

	void deleteFile(long id);

	void storeMainFile(MultipartFile file, long tid, long sid) throws IOException;

	List<FileRespondDTO> getFileListByTeamId(long tid);

}
