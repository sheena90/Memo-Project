<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">   
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    
    <link rel="stylesheet" href="/static/css/style.css" type="text/css">

</head>
<body>
	<div id="wrap">
		<c:import url="/WEB-INF/jsp/include/header.jsp" />
		<section class="d-flex justify-content-center">
			<div class="join-box my-5">
				<h1 class="text-center">회원 가입</h1>
				<!-- 아이디, 패스워드, 패스워드 확인, 이름, 이메일 -->
				<input type="text" id="loginIdInput" class="form-control mt-3" placeholder="아이디">
				<input type="password" id="passwordInput" class="form-control mt-3" placeholder="비밀번호">
				<input type="password" id="passwordConfirmInput" class="form-control mt-3" placeholder="비밀번호 확인">
				<input type="text" id="nameInput" class="form-control mt-3" placeholder="이름">
				<input type="text" id="emailInput" class="form-control mt-3" placeholder="이메일">
				
				<button type="button" id="joinBtn" class="btn btn-info btn-block mt-3">회원가입</button>
			</div>
		</section>
		<c:import url="/WEB-INF/jsp/include/footer.jsp" />
	
	
	</div>
	
	<script>
		$(document).ready(function() {
			$("#joinBtn").on("click", function() {
				let loginId = $("#loginIdInput").val();
				let password = $("#passwordInput").val();
				let passwordConfirm = $("#passwordConfirmInput").val();
				let name = $("#nameInput").val();
				let email = $("#emailInput").val();
				
				if(loginId == "") {
					alert("아이디를 입력하세요.");
					return;
				}
				
				if(password == "") {
					alert("비밀번호를 입력하세요.");
					return;
				}
				
				if(password != passwordConfirm) {
					alert("비밀번호가 일치하지 않습니다.");
					return;
				}
				
				if(name == "") {
					alert("이름을 입력하세요.");
					return;
				}
				
				if(email == "") {
					alert("메일주소를 입력하세요.");
					return;
				}
				
				$.ajax({
					type:"post",
					url:"/user/sign_up",
					data:{"loginId":loginId, "password":password, "name":name, "email":email},
					success:function(data) {
						if(data.result == "success") {
							// 회원가입 성공
							location.href="/user/signin_view";
						} else {
							alert("회원가입 실패");
						}
						
					},
					error:function() {
						alert("회원가입 에러");
					}
				});
			});
		});
	</script>

</body>
</html>