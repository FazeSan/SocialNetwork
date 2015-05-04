<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
		<link href="https://code.jquery.com/jquery-2.1.4.min.js" rel="stylesheet">
		<link rel="stylesheet" href="../styles/social.css" type="text/css" media="screen">
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<title>My Social Network - Home</title>
	</head>
	<script src="//code.jquery.com/jquery-1.11.2.min.js">
	$(document).ready(function(){
		$('#updateUsername').submit(function(){
			$.ajax({
				url: 'http://snenv-epitech-t3xdqsth8a.elasticbeanstalk.com/login',
				type: 'POST',
				dataType: 'json',
				date: $('#login').seralize(),
				sucess: function(data) {
					if(data.isValid) {
						$('#displayName').html('Votre login est : ' + data.username);
						$('#displayName').slideDown(500);
						}
					else {
						alter('Please enter a valid username');
						}
					}
			});
			return false;
		});
	});
	</script>
<body>
	<div class="row">
		<div class="col-lg-12 top-bar">My Social Network</div>
	</div>
	<div class="row spacing"></div>
	<div class="row">
		<div class="col-lg-offset-4 col-lg-4">
			<form id="login" class="home_form">
				<table class="home_table_connection">
				<tr><td>Identifiant</td><td><input type="text" placeholder="Votre identifiant" id="username" name="username"></td></tr>
				<tr><td>Mot de passe</td><td><input type="password" placeholder="Votre mot de passe" id="password" name="password"></td></tr>
				<tr><td colspan="2"><input type="submit" class="home_table_submit" value="Se Connecter"></td></tr>
				</table>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-offset-4 col-lg-4">
			<p id="displayName"></p>
		</div>
	</div>
</body>
</html>