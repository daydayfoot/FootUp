<%@ page language="java" pageEncoding="UTF-8"%>
<div class="wx_header">
	<div class="wx_header_content">
		<a class="index_link" href="javascript:void(0);" onclick="gotoHome()"></a>
		<a class="head_back" href="javaScript:gotoHome();"></a> <span
			class="wx_btn header_link" style="display: none;"><< 风险画像</span> <span
			class="user_photo"
			onClick="lookDetail('<shiro:principal property="id"/>')"></span> <span
			class="user_name"
			onClick="lookDetail('<shiro:principal property="id"/>')"> <shiro:principal
				property="account" />
		</span> <span class="lock"
			onClick="updatepw('<shiro:principal property="id"/>')"></span> <span
			class="log_out" onclick="dropOut()"></span>

	</div>
</div>


<div class="pop_form pop1" id="tipMsgDiv">
	<form action="">
		<div class="form_item">
			<p class="pop_warning clear_content" id="tipMsgHtml"></p>
		</div>
		<div class="form_submit">
			<span class="wx_btn close_pop">确定</span>
		</div>
	</form>
</div>


<div class="pop_form pop1" id="pwUpdateDiv">
	<form action="" id="pwEditForm">
		<div class="form_item">
			<label for="" class="wx_label">原密码<i style="color: #dec478">*</i>:
			</label> <span class="form_right rel"> <input type="password"
				id="oldPasswordTop" name="oldPasswordTop" placeholder="请输入原密码"
				maxlength="20" class="wx_input clear_content" autocomplete="false">
				<p class="error_tips" id="oldPasswordTop_error"></p>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">新密码<i style="color: #dec478">*</i>:
			</label> <span class="form_right rel"> <input type="password"
				id="newPasswordTop" name="newPasswordTop" maxlength="20"
				placeholder="请输入登录密码" class="wx_input clear_content"
				autocomplete="false">
				<p class="error_tips" id="newPasswordTop_error"></p>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">确认密码<i style="color: #dec478">*</i>:
			</label> <span class="form_right rel"> <input type="password"
				id="newPasswordTop2" name="newPasswordTop2" maxlength="20"
				placeholder="请再次输入密码" class="wx_input clear_content"
				autocomplete="false">
				<p class="error_tips" id="newPasswordTop2_error"></p>
			</span>
		</div>
		<div class="form_submit">
			<span class="wx_btn " onclick="saveNewPassword()">保存</span> <span
				class="wx_btn close_pop">取消</span>
		</div>
	</form>
</div>


<!-- 用户详情Div -->
<div class="pop_form pop1" id="userDetailDiv">
	<form action="">
		<div class="form_item">
			<label for="" class="wx_label">用户名:</label> <span class="form_right">
				<span class="plain_text clear_content" id="unameHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">姓名:</label> <span class="form_right">
				<span class="plain_text clear_content" id="nameHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">角色:</label> <span class="form_right">
				<span class="plain_text clear_content" id="roleNamesHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">身份证号:</label> <span class="form_right">
				<span class="plain_text clear_content" id="cidHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">手机号:</label> <span class="form_right">
				<span class="plain_text clear_content" id="mobileHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">邮箱:</label> <span class="form_right">
				<span class="plain_text clear_content" id="emailHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">状态:</label> <span class="form_right">
				<span class="plain_text clear_content" id="statusHead"></span>
			</span>
		</div>
		<div class="form_item">
			<label for="" class="wx_label">创建日期:</label> <span class="form_right">
				<span class="plain_text clear_content" id="createTimeHead"></span>
			</span>
		</div>
		<div class="form_submit">
			<span class="wx_btn close_pop">确定</span>
			<!--  <input class="wx_btn close_pop" type="submit" value="保存设置"> -->
		</div>
	</form>
</div>

<script>
var  headPwFlag="false";
var  idValue="0";
$(function() {
    $("#oldPasswordTop").focus(function() {  
    	var oldPasswordTop=$("#oldPasswordTop").val();
		$("#oldPasswordTop_error").html("");
    });
    $("#newPasswordTop").focus(function() {  
    	var newPasswordTop=$("#newPasswordTop").val();
		$("#newPasswordTop_error").html("");
    });
    $("#newPasswordTop2").focus(function() {  
    	var newPasswordTop2=$("#newPasswordTop2").val();
		$("#newPasswordTop2_error").html("");
    });
    $("#newPasswordTop").blur(function(){
		$("#newPasswordTop_error").html("");
		$("#newPasswordTop_error").show();
		var newPasswordTop=$("#newPasswordTop").val();
    	if(!notNull(newPasswordTop)){
			$("#newPasswordTop_error").html("必填");
 		}else{
	    	var passport = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]{7,19}$/;
			if(!passport.test(newPasswordTop)){
				$("#newPasswordTop_error").html("8-20位字母,数字或符号组合");
			}else{
				$("#newPasswordTop_error").html("");
			}
 		}
    });
    $("#newPasswordTop2").blur(function(){
		$("#newPasswordTop2_error").html("");
		$("#newPasswordTop2_error").show();
		var newPasswordTop2=$("#newPasswordTop2").val();
		if(!notNull(newPasswordTop2)){
			$("#newPasswordTop2_error").html("必填");
 		}else{
			var newPasswordTop=$("#newPasswordTop").val();
 			if(notNull(newPasswordTop)){
				if(newPasswordTop2 != newPasswordTop){
					$("#newPasswordTop2_error").html("两次密码输入不一致");
				}else{
					$("#newPasswordTop2_error").html("");
				}
 			}
 		}
    });
	$("#oldPasswordTop").blur(function(){
		headPwFlag="false";
		var oldpw=$("#oldPasswordTop").val();
		if(!notNull(oldpw)){
			$("#oldPasswordTop_error").html("必填");
			return;
 		}else{
 			$("#oldPasswordTop_error").html("");
 		}
		$.ajax({
	  		url :"${ctx }/userManger/selectByIdPw",
	  		data:{
		    	"password":oldpw,
		    	"id":idValue,
		    },
		    datatype: 'json',  
		    type: 'post',
		    cache: true,
		    async : true,
			success : function(data) {
				$("#oldPasswordTop_error").html(""); 
				if (data.code  == "0000" )  {
					headPwFlag="true";
				}else{
					headPwFlag="false";
					$("#oldPasswordTop_error").show();
					$("#oldPasswordTop_error").html("请输入正确密码"); 
				}
			}
		});
	});
		
		
	});
function dropOut(){
	 window.location.href = "${ctx}/dropOut";
}
function updatepw(id){
	$("#oldPasswordTop").val("");
	if(null != id && "" != id){
		$.ajax({
	 		url :"${ctx }/userManger/selectById", 
	    	data:{
	    		"id":id,
	   		 },
	    	datatype: 'json',  
	    	type: 'post',
	    	cache: true,
		    async : true,
			success : function(data) {
				if (data.code=="0000") {
					popDiv= popModel('#pwUpdateDiv',"修改密码");
					$("#newPasswordTop_error").html("");
					$("#newPasswordTop2_error").html("");
					idValue=id;
				}
			}
		});
	}	
}
function saveNewPassword(){
		var oldPasswordTop=$("#oldPasswordTop").val();
		var flag=0;
		if(!notNull(oldPasswordTop)){
			$("#oldPasswordTop_error").html("");
 			$("#oldPasswordTop_error").show();
			$("#oldPasswordTop_error").html("必填");
			flag +=1;
 		}
		var newPasswordTop=$("#newPasswordTop").val();
		var newPasswordTop2=$("#newPasswordTop2").val();
		
		if(!notNull(newPasswordTop)){
			$("#newPasswordTop_error").html("");
 			$("#newPasswordTop_error").show();
			$("#newPasswordTop_error").html("必填");
			flag +=1;
 		}
		if(!notNull(newPasswordTop2)){
			$("#newPasswordTop2_error").html("");
 			$("#newPasswordTop2_error").show();
			$("#newPasswordTop2_error").html("必填");
			flag +=1;
 		}
		if(flag>0){
			return;
		}
		var passport = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]{7,19}$/;
		if(!passport.test(newPasswordTop)){
			$("#newPasswordTop_error").html("");
 			$("#newPasswordTop_error").show();
			$("#newPasswordTop_error").html("8-20位字母,数字或符号组合");
			flag +=1;
 		}
		if( !newPasswordTop == newPasswordTop2){
			$("#newPasswordTop2_error").html("");
 			$("#newPasswordTop2_error").show();
			$("#newPasswordTop2_error").html("两次密码输入不一致");
			flag +=1;
 		}
		if(flag>0){
			return;
		}
	if(headPwFlag=="true"){
		$.ajax({
	  		url :"${ctx }/userManger/updatePW",
				data : {
					"password" : newPasswordTop2,
					"id":idValue
				},
				datatype : 'json',
				type : 'post',
				cache : true,
				async : true,
				success : function(data) {
					popDiv.close();
					if (data.code == "0000") {
						popMsgModel("密码修改成功");
					} else {
						popMsgModel("密码修改失败！");
					}
				}
			});
		}
}

function lookDetail(id){
	$.ajax({
 		url :"${ctx }/userManger/selectById", 
    	data:{
    		"id":id,
   		 },
    	datatype: 'json',  
    	type: 'post',
    	cache: true,
	    async : true,
		success : function(data) {
			if (data.code=="0000") {
				popModel('#userDetailDiv',"用户详情");
				
				$("#unameHead").html(data.uname); 
				$("#nameHead").html(data.name); 
				$("#cidHead").html(data.cid); 
				$("#emailHead").html(data.email); 
				$("#mobileHead").html(data.mobile); 
				$("#roleNamesHead").html(data.roleNames); 
				$("#createTimeHead").html(data.createTime); 
				var status=data.status;
				if("undefined" == typeof(status)){ status = " "; }
				if(status==0){
					status="正常"; 
				}else if(status==1){
					status="冻结";
				}else if(status==2){
					status="注销"; 
				}else if(status==3){ 
					status="锁定"; 
				}else{
					status="未知"; 
				}
				$("#statusHead").html(status); 
			}
		}
	});
}


function validatePhone(phone){
	return phone.match("^1[3|4|5|7|8][0-9]{9}$");
}

function refresh(){
	location.reload() ;
}
function goBackHistory(){
	window.history.back();
}

//身份证脱密
function cidDegassing(cid){
	if(notNull(cid)){
		if(cid.length == 18){
			cid = cid.substr(0,10)+"XXXX"+cid.substr(14);
		}else if(cid.length == 15){
			cid = cid.substr(0,8)+"XXXX"+cid.substr(12);
		}
	}
	return cid;
}
//手机号脱密
function phoneDegassing(phone){
	if(notNull(phone)){
		if(phone.length == 11){
			phone = phone.substr(0,3)+"XXXX"+phone.substr(7);
		}
	}
	return phone;
}
//不为空
function notNull(data){
	if(null!=data && ""!=data && "undefined"!=typeof(data)){
		return true;
	}else{
		return false;
	}
}
// div title 弹框
var popDiv;
function popModel(divID,divTitle){
	$(divID).show();
	popDiv = new wx_pop({
        html:$(divID),title:divTitle
    });
    return popDiv;
}
function popMsgModel(msgConuext){
	$("#tipMsgHtml").html(msgConuext); 
	$("#tipMsgDiv").show();
	popModel("#tipMsgDiv","提示信息");
}
function gotoHome(){
	window.location.href='${ctx}/home'
}
//全局ajax session 过期 跳转登陆	
jQuery(function($){  
    // 备份jquery的ajax方法    
	$.ajaxSetup({
		dataFilter : function(response) {
			if (response.indexOf('短信验证码') !== -1) {
				//如果返回的文本包含"登陆页面"，就跳转到登陆页面  
				window.location.href = "${ctx}/index";
				//一定要返回一个字符串不能不返回或者不给返回值，否则会进入success方法  
				return "";
			} else {
				//如果没有超时直接返回  
				return response;
			}
		}
	});
 
});
</script>
