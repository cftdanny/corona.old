<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Inject Parameter Map</title>
	</head>
	<body>
		<h1 id="caption">Parameter Map</h1>
		
		<div>Order NO: </div>
		<div id="no">${no!}</div>
		<div>Qty: </div>
		<div id="qty">${qty!}</div>
		<div>Count: </div>
		<div id="count"><#if total != null>${total.count}</#if></div>
		
		
	</body>
</html>