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
	
	// ���ÿ��� ������ ���� ����
	@RequestMapping(value = "/sendServer.do")
	public View sendServer(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException{
		
		// ȭ�鿡�� ������ �Ķ����
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		
		/*****************************************************************************************/
        /*******************************�������� ���� ��ο� ���ε�****************************************/
		
		System.out.println(">>�������� ���� ��ο� ���ε�<<");
        
		Map<String, String> resultMap     = new HashMap<String, String>();
        Map<String, File[]> files         = dataRequest.getFiles();
        
        String targetPath = "D:\\wemb\\product\\excelTest";
        File target                       = new File(targetPath);
        
		Path directoryPath = Paths.get(targetPath);
		
		/***************************���丮�� ���ų� ����**********************************/
		try 
		{	// ���丮 ����
			Files.createDirectory(directoryPath);
			System.out.println(">>���丮�� �����Ǿ����ϴ�<<");
		} 
		catch (FileAlreadyExistsException e) 
		{
			System.out.println(">>���丮�� �̹� �����մϴ�<<");
		} 
		catch (NoSuchFileException e) 
		{
			System.out.println(">>���丮 ��ΰ� �������� �ʽ��ϴ�<<");
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
                // fileName : ���ϸ�.
                String fileName = new String(dmParam.getValue("fileName"));
                File fileItem = entry.getValue()[0];
                File fileRename = new File(target.toPath() + "/" + fileName);
                fileItem.renameTo(fileRename);
            }
        }
        resultMap.put("success", "Y");
        resultMap.put("message", "���ε� ����");
        dataRequest.setResponse("dmResult", resultMap);
        
        /*****************************************************************************************/
        /****************************************���� ���� �б�****************************************/
        
        System.out.println(">>���� ���� �б� ����<<");
        
        // fileName : ���ϸ�.
        // startRow : startRow + 1 ���� ����.
        // cellNum  : �о���� column ��.
        String fileName = dmParam.getValue("fileName");
		int startRow = Integer.parseInt(dmParam.getValue("startRow"));
		int cellNum = Integer.parseInt(dmParam.getValue("cellNum"));
		
		// ExcelUtil.java ��ü ����
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
        /****************************************���� ���� ����****************************************/
		
		System.out.println(">>���� ���� ����<<");
		
		File file = new File(target.toPath() + "/" + fileName);
		
		if(file.exists() )
		{
    		if(file.delete())
    		{
    			System.out.println(">>���ϻ��� ����<<");
    		}
    		else
    		{
    			System.out.println(">>���ϻ��� ����<<");
    		}
    	}
		else
		{
    		System.out.println(">>������ �������� �ʽ��ϴ�<<");
    	}

        return new JSONDataView();
	}
}