/*function confirmDelete_备用(uid) {
        var result = confirm("是否删除？");
        if (result) {
            console.log("x?uid="+uid);
            window.location.href = "x?uid="+uid;//携带uid参数 
        }
    }
*/
//************************************添加【只读禁止光标】（初始化）： */
window.onload=function readonly_cursor(){
    // 获取所有具有 readonly 属性的 input 元素
    var readonlyInputs = document.querySelectorAll('input[readonly]');
    
//    // 移除之前可能添加的所有 read-only css类
//    var allInputs = document.querySelectorAll('input.read-only');
//    allInputs.forEach(function(input) {
//        input.classList.remove('read-only');
//    });

    // 为当前所有 readonly input 元素添加 read-only css类
    readonlyInputs.forEach(function(input) {
        input.classList.add('read-only');
    });
}
//	//获取所有具有.readonly类的input元素
//	var readonlyInputs = document.querySelectorAll('input[readonly]');
//	
//	console.log('readonlyInputs='+readonlyInputs.length);
//	
//	readonlyInputs.forEach(function(input) {
//	input.addEventListener('mouseover', function() {
//	// console.log('鼠标移入了');
//	 this.classList.add('read-only'); // 添加read-only类
//});
//	// console.log('鼠标又出来了');
//	input.addEventListener('mouseout', function() {
//	 this.classList.remove('read-only'); // 移除read-only类
//	});
//});
//	
//}



//*************************************************【修改按钮】模块：  

//function enableEditing(cell) {//点击按钮后，取消只读
//    var inputs = cell.getElementsByClassName('editing');
//    for (var i = 0; i < inputs.length; i++) {
//        inputs[i].removeAttribute('readonly');
//        inputs[i].focus();
//    }
//}

//点击【修改按钮】触发该函数：
function enableEditing(row) {//row=td,td子节点为input
console.log('点击了修改按钮。');
//console.log('row是个什么东西？'+row+row.tagName);
	//【login】取消只读：
      var tds = row.querySelectorAll('td');
      tds.forEach(function(td) {
		var input=td.getElementsByTagName('input')[0];
		if(input)
			if(input.name == 'uid' || input.name=='urole' || input.name == 'upsw' ){
			//不能改的，pass
			}else{
				input.readOnly = false; // 取消只读
				input.classList.remove('read-only');//取消光标css
			}
        
    });
    //【提交按钮】改变：
      var button = row.getElementsByTagName('button')[0];
      button.innerHTML = '提交'; //改为提交按钮
      button.setAttribute('onclick', 'submitChanges(this.parentNode.parentNode)');
}



//点击【提交按钮】触发该函数：
function submitChanges(row) {
console.log('点击了提交按钮。');
    // 【提交数据到服务器：（使用 fetch API）】
//    var inputs = row.querySelectorAll('input');
//    var formData = new FormData();
//    inputs.forEach(function(input) {
//        formData.append(input.name, input.value);
//		input.classList.add('read-only');//取消光标css
//    });
    var inputs = row.querySelectorAll('input');
    var data = new URLSearchParams(); // 使用 URLSearchParams 来构建键值对
    inputs.forEach(function(input) {
        data.append(input.name, input.value);
		input.classList.add('read-only');//取消光标css
    });
    
//    fetch('UpdateUserServlet.do', {
//        method: 'POST',
//        body: formData
//    })
    // 使用 fetch 发送数据
    fetch('UpdateUserServlet.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded' // 设置内容类型为 x-www-form-urlencoded
        },
        body: data // URLSearchParams 对象可以直接作为 body 发送
    })
    .then(response => response.text()) // 或者 response.json() 如果服务器返回JSON
    .then(result => console.log(result))
    .then(data => {
        console.log('服务器发来的响应数据：', data);
        
    })
    .catch(error => {
		alert('请求失败'+error);
    })
    .finally(()=>{
		// 重置【input】为只读
        inputs.forEach(function(input) {
            input.readOnly = true;
        })
        // 重置【修改按钮】
        var button = row.getElementsByClassName('updateBtn')[0];
        button.innerText = '修改';
        button.onclick = function() { enableEditing(row); };
	});
}





    
//********************************************************【删除按钮】模块： */    
    
    function confirmDelete(uid) {
        var isDelete = confirm("您确定要删除吗？");
        if (isDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "DeleteUserServlet.do?uid="+uid  , true);// 配置请求类型、URL及异步处理方式
            xhr.onload = function () {
                if (xhr.status === 200) {
                    alert('删除成功');
                    window.location.href="QueryAllUserServlet.do";
                } else {
                    alert('操作失败');
                }
            };
            xhr.send();
        }
    }
    
    
