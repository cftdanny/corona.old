<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cookie Test</title>
	</head>
	<body>
		<h1 id="caption">Cookie</h1>
		
		<div>Hello: </div>
		<div id="hello">${hello!}</div>
		<div>World: </div>
		<div id="world">${world!}</div>
		<p>
		
		<form action="cookie.html" method="post">
			<label>Hello: </label> <input type="text" id="inputHello" name="hello"> <p>
			<label>World: </label> <input type="text" id="inputWorld" name="world"> <p>
			<input id="submit" type="submit" value="Submit" />
		</form>
	</body>
</html>