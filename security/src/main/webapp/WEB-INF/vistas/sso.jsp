<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Single SignOn</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body style="background-color: #fafafa;">
		
		<div style="display: flex; align-items: center;	 justify-content: center;">
			<div style="padding-top:100px;">
				<h1>Single Sign On</h1>
				<div style="border: 1px solid #dadada; padding: 10px;">
					
					<form id="login" action="http://localhost:8080/api/security/login" method="POST">
					  <div class="form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control" id="username" name="username" placeholder="Username">
					  </div>
					  <div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" id="password" name="password" placeholder="Password">
					  </div>
					  <button type="submit" class="btn btn-primary">Login</button>
					</form>
					<p class="text text-danger" id="error"></p>
				</div>
			</div>
		</div>
		
		
		
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
			$("#login").submit(function(event) {
			event.preventDefault();

			  /* stop form from submitting normally */
			  event.preventDefault();

			  /* get the action attribute from the <form action=""> element */
			  var $form = $( this ),
				  url = $form.attr( 'action' );
			  
			  document.getElementById("error").innerHTML = "";
			  
			  $.ajax({
				  url:'http://localhost:8080/api/security/login',
				  type:"POST",
				  data:JSON.stringify({ username: $('#username').val(), password: $('#password').val() }),
				  contentType:"application/json",
				  dataType:"json",
				  success: function(e){
					if(e.ok){
						const urlParams = new URLSearchParams(window.location.search);
						const redirect = urlParams.get('redirect');
						window.location.replace(redirect + "?token="+e.body.token);
					}else{
						document.getElementById("error").innerHTML = e.message;
					}
				  }
				})
			});
		</script>

	</body>
</html>