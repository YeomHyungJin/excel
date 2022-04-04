// 공통 모듈 선언
var util = createCommonUtil();

// 우클릭 방지
document.oncontextmenu = function(e) {
	return false;
}

var file_name;
var file_object;

/*
 * Body에서 load 이벤트 발생 시 호출.
 * 앱이 최초 구성된후 최초 랜더링 직후에 발생하는 이벤트 입니다.
 */
function onBodyLoad(/* cpr.events.CEvent */ e){
	
}

/*
 * 파일 인풋에서 value-change 이벤트 발생 시 호출.
 * FileInput의 value를 변경하여 변경된 값이 저장된 후에 발생하는 이벤트.
 */
function onFi1ValueChange(/* cpr.events.CValueChangeEvent */ e){
	/** 
	 * @type cpr.controls.FileInput
	 */
	var fi1 = e.control;
	
	file_name = fi1.value;
	file_object = fi1.file;
	
	var file_split = file_name.toString().split(".");
	var file_extension = file_split[Number(file_split.length)-1];
	
	if(file_extension == "xlsx" || file_extension == "xls")
	{
		app.lookup("output").value = file_name;
	}
	else
	{
		alert("업로드 대상이 아닙니다 \n 업로드 대상 : 엑셀");
		return;
	}
}

/*
 * "파일선택" 버튼에서 click 이벤트 발생 시 호출.
 * 사용자가 컨트롤을 클릭할 때 발생하는 이벤트.
 */
function onButtonClick2(/* cpr.events.CMouseEvent */ e){
	/** 
	 * @type cpr.controls.Button
	 */
	var button = e.control;
	
	app.lookup("fi1").openFileChooser();
}


/*
 * "제출" 버튼에서 click 이벤트 발생 시 호출.
 * 사용자가 컨트롤을 클릭할 때 발생하는 이벤트.
 */
function onButtonClick(/* cpr.events.CMouseEvent */ e){
	/** 
	 * @type cpr.controls.Button
	 */
	var button = e.control;
    var sendServer     = app.lookup("sendServer");
    
    sendServer.addFileParameter(file_name, file_object);
    
    funcUpload();
}

function funcUpload(){
	
	var dmParam = app.lookup("dmParam");
	
	dmParam.setValue("fileName", file_name);
	dmParam.setValue("startRow", 5);
	dmParam.setValue("cellNum", 2);
	
	util.Submit.send(app, "sendServer", null, function(pbSuccess)
    {
    	
        if (pbSuccess)
        {
            var vsSuccess = app.lookup("dmResult").getValue("success");
            var vsMessage = app.lookup("dmResult").getValue("message");
            
            if (vsSuccess == "Y")
            {
            	alert("업로드 성공");
            }
        };
    });
}

function upload(){}

upload.prototype.run = function(){
	return "엑셀업로드 페이지입니다."
}
var tiger = new upload();

console.log(tiger.run());	

// 파일업로드 컴포넌트
///*
// * 파일 업로드에서 sendbutton-click 이벤트 발생 시 호출.
// * 파일을 전송하는 button을 클릭 시 발생하는 이벤트. 서브미션을 통해 전송 버튼에 대한 구현이 필요합니다.
// */
//function onFud1SendbuttonClick(e){
//	/** 
//	 * @type cpr.controls.FileUpload
//	 */
//	var fileUpload = e.control;
//	var vaFiles       = fileUpload.getFiles();
//    var sendServer     = app.lookup("sendServer");
//    
//    for(var cnt = 0; cnt < vaFiles.length; cnt++)
//    {
//        var row_file = vaFiles[cnt];
//        console.log(row_file);
//        sendServer.addFileParameter(row_file.name, row_file);
//    }
//    
//    funcUpload();
//}
