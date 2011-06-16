<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Inject Parameter by Simple Data</title>
	</head>
	<body>
		<h1 id="caption">Inject Parameter by Simple Data</h1>
		
		<div>A: </div>
		<div id="a">${a!}</div>
		
		<div>B: </div>
		<div id="b">${(b.toString())!}</div>

		<div>C: </div>
		<div id="c"><#list c! as cd>${cd}</#list></div>
${dad}
		<div>B: </div>
		<div id="d">${(d.toString())!}</div>
	</body>
</html>