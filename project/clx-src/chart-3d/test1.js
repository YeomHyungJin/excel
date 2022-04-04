/************************************************
 * test1.js
 * Created at 2022. 3. 22. 오후 3:25:21.
 *
 * @author 염형진
 ************************************************/

var util = createCommonUtil();


/*
 * 임베디드 페이지에서 load 이벤트 발생 시 호출.
 * 페이지의 Load가 완료되었을 때 호출되는 Event.
 */
function onEp1Load(/* cpr.events.CEvent */ e){
	/** 
	 * @type cpr.controls.EmbeddedPage
	 */
	var ep1 = e.control;
	
	getList1();
}

/*
 * 임베디드 페이지에서 load 이벤트 발생 시 호출.
 * 페이지의 Load가 완료되었을 때 호출되는 Event.
 */
function onEp2Load(/* cpr.events.CEvent */ e){
	/** 
	 * @type cpr.controls.EmbeddedPage
	 */
	var ep2 = app.lookup("ep2");
	
	getList2();
}

function getList1() {
   
//    util.Submit.send(app, "subEp1", function(pbSuccess) {
//        if (pbSuccess) {
            var dsList = app.lookup("dsList");
            
            var jsonArray = new Array();
            
            var date = "";
            var mathScore = "";
            var engScore = "";
            var koreanScore = "";
            
            var jsonObj = {};
            
            if(dsList.getRowCount() == 0){
                jsonObj.date = dsList.getValue(0, "");
                jsonObj.mathScore = dsList.getValue(0, 0);
                jsonObj.engScore = dsList.getValue(0, 0);
                jsonObj.koreanScore = dsList.getValue(0, 0);
                jsonArray.push(jsonObj);
            }
           
            for (var i = 0; i < dsList.getRowCount(); i++) {
                var jsonObj = {};
                jsonObj.date = dsList.getValue(i, "date");
                jsonObj.mathScore = dsList.getValue(i, "mathScore");
                jsonObj.engScore = dsList.getValue(i, "engScore");
                jsonObj.koreanScore = dsList.getValue(i, "koreanScore");
                jsonArray.push(jsonObj);
            }
            setDrawChart1(jsonArray);
//        }
//    });
}

function setDrawChart1(jsonArray) {
	
    var ep1 = app.lookup("ep1");
    
    ep1.callPageMethod("sendData", jsonArray);
}

function getList2() {
   
//    util.Submit.send(app, "subEp1", function(pbSuccess) {
//        if (pbSuccess) {
            var dsList2 = app.lookup("dsList2");
            
            var jsonArray = new Array();
            
            var date = "";
            var mathScore = "";
            var engScore = "";
            var koreanScore = "";
            
            var jsonObj = {};
            var grd1 = app.lookup("grd1");
            
            if(dsList2.getRowCount() == 0){
                jsonObj.date = dsList2.getValue(0, "");
                jsonObj.mathScore = dsList2.getValue(0, 0);
                jsonObj.engScore = dsList2.getValue(0, 0);
                jsonObj.koreanScore = dsList2.getValue(0, 0);
                jsonArray.push(jsonObj);
            }
           
            for (var i = 0; i < dsList2.getRowCount(); i++) {
                var jsonObj = {};
                jsonObj.date = dsList2.getValue(i, "date");
                jsonObj.mathScore = dsList2.getValue(i, "mathScore");
                jsonObj.engScore = dsList2.getValue(i, "engScore");
                jsonObj.koreanScore = dsList2.getValue(i, "koreanScore");
                jsonArray.push(jsonObj);
            }
            setDrawChart2(jsonArray);
//        }
//    });
}

function setDrawChart2(jsonArray) {
	
    var ep2 = app.lookup("ep2");
    
    ep2.callPageMethod("sendData", jsonArray);
}

