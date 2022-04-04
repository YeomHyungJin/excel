package com.project.common.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.project.util.ExcelUtil;

@Controller
@RequestMapping("/excelImport")
public class ExcelImportController {

	public ExcelImportController(){}
	
	// 로컬에서 서버로 파일 전송
	@RequestMapping(value = "/sendServer.do")
	public View sendServer(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException{
		
		// 화면에서 가져온 파라미터
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		
		/*****************************************************************************************/
        /*******************************엑셀파일 절대 경로에 업로드****************************************/
		
		System.out.println(">>엑셀파일 절대 경로에 업로드<<");
        
		Map<String, String> resultMap     = new HashMap<String, String>();
        Map<String, File[]> files         = dataRequest.getFiles();
        
        String targetPath = "D:\\wemb\\product\\excelTest";
        File target                       = new File(targetPath);
        
		Path directoryPath = Paths.get(targetPath);
		
		/***************************디렉토리가 없거나 존재**********************************/
		try 
		{	// 디렉토리 생성
			Files.createDirectory(directoryPath);
			System.out.println(">>디렉토리가 생성되었습니다<<");
		} 
		catch (FileAlreadyExistsException e) 
		{
			System.out.println(">>디렉토리가 이미 존재합니다<<");
		} 
		catch (NoSuchFileException e) 
		{
			System.out.println(">>디렉토리 경로가 존재하지 않습니다<<");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		/****************************************************************************/
		
        if(files != null && files.size() > 0) 
        {
            Set<Entry<String, File[]>> fileEntrySet = files.entrySet();
            
            for(Entry<String, File[]> entry : fileEntrySet) 
            {            
                // fileName : 파일명.
                String fileName = new String(dmParam.getValue("fileName"));
                File fileItem = entry.getValue()[0];
                File fileRename = new File(target.toPath() + "/" + fileName);
                fileItem.renameTo(fileRename);
            }
        }
        resultMap.put("success", "Y");
        resultMap.put("message", "업로드 성공");
        dataRequest.setResponse("dmResult", resultMap);
        
        /*****************************************************************************************/
        /****************************************엑셀 파일 읽기****************************************/
        
        System.out.println(">>엑셀 파일 읽기 시작<<");
        
        // fileName : 파일명.
        // startRow : startRow + 1 부터 읽음.
        // cellNum  : 읽어야할 column 수.
        String fileName = dmParam.getValue("fileName");
		int startRow = Integer.parseInt(dmParam.getValue("startRow"));
		int cellNum = Integer.parseInt(dmParam.getValue("cellNum"));
		
		// ExcelUtil.java 객체 생성
		ExcelUtil util = new ExcelUtil();
		
		try {
			List<Map<String, Object>> excelList = 
					util.excelImport(target.toPath() + "/" + fileName, startRow, cellNum);
			dataRequest.setResponse("dsExcelList", excelList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*****************************************************************************************/
        /****************************************서버 파일 삭제****************************************/
		
		System.out.println(">>서버 파일 삭제<<");
		
		File file = new File(target.toPath() + "/" + fileName);
		
		if(file.exists() )
		{
    		if(file.delete())
    		{
    			System.out.println(">>파일삭제 성공<<");
    		}
    		else
    		{
    			System.out.println(">>파일삭제 실패<<");
    		}
    	}
		else
		{
    		System.out.println(">>파일이 존재하지 않습니다<<");
    	}

        return new JSONDataView();
	}
}