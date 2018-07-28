//ver4-20170728-UTF8,增加左右滑动
var loadingPane = null;
var rangeDeltaX = 5;
var rangeDeltaY = 5;

//node.htm全局变量
var myxmlHttp = null;

function getWidthNumber(sWidth) {
    if (typeof sWidth == "number")
        return sWidth;

    var re = /\d+/i;
    var r = sWidth.match(re);
    if (r) {
        return parseInt(r);
    }
    else
        return 0;
}

function find(arr, type) {
    var tmp = arr[0];
    if (type == 1) {
        for (var loop = 0; loop < arr.length; loop++)
            if (arr[loop] > tmp)
                tmp = arr[loop];
        return tmp;
    }
    else if (type == 2) {

        for (var loop = 0; loop < arr.length; loop++)
            if (arr[loop] < tmp)
                tmp = arr[loop];
        return tmp;
    }

}

function clickmap(obj) {
    //window.open(obj.href,"newwin","toolbar=no,resizable=no,scrollbars=yes,dependent=no,width=700,height=500");
    obj.target = "_blank";
}

function DrawImage(ImgD, FitWidth, FitHeight) {
    var image = new Image();
    var scale; //缩放比例，版面图热点坐标的高度偏移量
    var ostr;
    image.src = ImgD.src;
    if (FitWidth > 0)
        scale = FitWidth / 397;
    else
        scale = ImgD.width / image.width;

    ImgD.height = image.height * scale;
    ImgD.width = FitWidth;

//FitHeight暂未启用

    //重新计算热点区域坐标
    ostr = document.getElementById("PagePicMap").innerHTML;
    var outstr = "";
    var temparray, i;
    if (ostr.indexOf("coords=\"") > 1) {
        while (ostr.indexOf("coords=\"") > 1) {
            outstr = outstr + ostr.substring(0, ostr.indexOf("coords=\"") + 8);
            ostr = ostr.substring(ostr.indexOf("coords=\"") + 8);
            temparray = ostr.substring(0, ostr.indexOf("\"")).split(",");
            for (i = 0; i < temparray.length; i++) {
                temparray[i] = parseInt(temparray[i] * scale);
            }
            outstr = outstr + temparray.join(",");
            temparray.length = 0;
            ostr = ostr.substring(ostr.indexOf("\""));
        }
    }
    else {
        while (ostr.indexOf("coords=") > 1) {
            outstr = outstr + ostr.substring(0, ostr.indexOf("coords=") + 7);
            ostr = ostr.substring(ostr.indexOf("coords=") + 7);
            temparray = ostr.substring(0, ostr.indexOf(">")).replace(/"/g, "").split(",");
            for (i = 0; i < temparray.length; i++) {
                temparray[i] = parseInt(temparray[i] * scale);
            }
            outstr = outstr + temparray.join(",");
            temparray.length = 0;
            ostr = ostr.substring(ostr.indexOf(">"));
        }
    }
    outstr = outstr + ostr;
    document.getElementById("PagePicMap").innerHTML = outstr;
}

//日历跳转，配合wdatepicker.js
function chooseday() {
    var Firstday;
    var completely_date;
    var gourl;
    var today = new Date();
    var The_Year, The_Month, The_Day;
    The_Year = $dp.cal.getP('y');
    The_Month = $dp.cal.getP('M');
    The_Day = $dp.cal.getP('d');

    if (The_Day != 0) {
        completely_date = The_Year + "-" + The_Month + "-" + The_Day;
        var temp = window.location.href;
        temp = temp.slice(temp.indexOf("/", 8));
        gourl = temp.substring(0, temp.indexOf("/", 4) + 1);
        gourl += The_Year;
        gourl = gourl + "-" + The_Month;
        gourl = gourl + "/" + The_Day + "/index.htm";

        if ((The_Year == today.getFullYear()) && (The_Month == today.getMonth() + 1) && (The_Day == today.getDate()))
            bgColor = "#FFFCCC";
        else
            bgColor = "#CCCCCC";

        if (window.XMLHttpRequest) { //非IE浏览器，创建XMLHttpRequest对象
            xmlHttp = new XMLHttpRequest();
            if (xmlHttp.overrideMimeType) {
                xmlHttp.overrideMimeType('text/xml');
            }
        }
        else if (window.ActiveXObject) { // 对于IE浏览器，创建XMLHttpRequest
            try {
                xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e) {
                try {
                    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e2) {
                    xmlHttp = null;
                }
            }
        }

        //window.location = gourl;
        debugger;
        if (xmlHttp != null) {
            try {
                xmlHttp.open("HEAD", gourl, false);
                xmlHttp.send();
                if (xmlHttp.readyState == 4) {
                    if (xmlHttp.status == 200) window.location = gourl;
                    else if (xmlHttp.status == 404) {
                        alert(completely_date + "停刊或电子版数据丢失！");
                        return false;
                    }
                }
            }
            catch (e) {
                alert("停刊或电子版数据缺失！");
                return false;
            }
        }
        else {
            alert("你的浏览器不支持ajax");
        }
        //-------
        if (xmlHttp != null) {
            xmlHttp = null;
            delete xmlHttp;
        }
    }
    else
        completely_date = "No Choose";

    xmlHttp = null;
    delete xmlHttp;
}

//移动端滑动事件
var startX = 0, startY = 0;
var IsMoved = false;
var dx = 0, dy = 0;

//touchstart事件  
function touchSatrtFunc(evt) {
    try {
        //evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
        var touch = evt.touches[0]; //获取第一个触点
        var x = Number(touch.pageX); //页面触点X坐标
        var y = Number(touch.pageY); //页面触点Y坐标
        //记录触点初始位置
        startX = x;
        startY = y;
    }
    catch (e) {
        alert('touchSatrtFunc：' + e.message);
    }
}

//touchmove事件，这个事件无法获取坐标
function touchMoveFunc(evt) {
    try {
        //evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
        if (document.body.scrollTop >= (document.body.scrollHeight - window.screen.height)) {
            evt.preventDefault();
        } //阻止触摸时浏览器的缩放、滚动条滚动等
        var touch = evt.touches[0]; //获取第一个触点
        var x = Number(touch.pageX); //页面触点X坐标
        var y = Number(touch.pageY); //页面触点Y坐标
        dx = x - startX;
        dy = y - startY;
        // if(evt&&evt.preventDefault){ window.event.returnValue = false; }
    }
    catch (e) {
        alert('touchMoveFunc：' + e.message);
    }
}

//touchend事件  
function touchEndFunc(evt) {
    try {
        //evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
        var page;
        if (dy > 100 && Math.abs(dx / dy) < 0.9) { //下滑
            page = document.getElementById("prepage");
        }
        else if (dy < -100 && Math.abs(dx / dy) < 0.9) {  //上滑
            page = document.getElementById("nextpage");
            /*
            if(page=="&nbsp;") { //末版上滑回到头版
                page=document.getElementById("prepage").innerHTML;
                page=page.substring(page.indexOf("/"),page.indexOf(">")-13)+">";
            }*/
        }
        if (dx > 100 && Math.abs(dy / dx) < 0.5) { //右滑
            page = document.getElementById("prepage");
        }
        else if (dx < -100 && Math.abs(dy / dx) < 0.5) {  //左滑
            page = document.getElementById("nextpage");
        }

        if (page) {
            // page.
            if (page.href) {
                window.location.href = page.href;
            }
            else {
                alert('没有了！');
                dx = 0;
                dy = 0;
            }
        }
        //window.event.returnValue = false;
    }
    catch (e) {
        alert('touchEndFunc：' + e.message);
    }
}

//绑定事件  
function bindEvent() {
    document.addEventListener('touchstart', touchSatrtFunc, false);
    document.addEventListener('touchmove', touchMoveFunc, false);
    document.addEventListener('touchend', touchEndFunc, false);
}

//判断是否支持触摸事件  
function isTouchDevice() {
    try {
        document.createEvent("TouchEvent");
        //alert("支持TouchEvent事件！");
        bindEvent(); //绑定事件
    }
    catch (e) {
        alert("不支持TouchEvent事件！" + e.message);
    }
}

window.onload = isTouchDevice;