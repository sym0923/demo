function ajaxPostBlob(url,data,callback,responseType){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);        // 也可以使用POST方式，根据接口
    xhr.setRequestHeader('Content-Type', 'application/json');
    if(responseType==="blob"){
        xhr.responseType = "blob";    // 返回类型blob
    }
// 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    xhr.onload = function () {
        // 请求完成
        if (this.status === 200 && this.readyState===4) {
            callback(this.response)
        }else{
            alert("状态异常。")
        }
    };
// 发送ajax请求
    xhr.send(data);
}