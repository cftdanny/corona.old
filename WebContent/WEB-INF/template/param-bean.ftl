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
		<div id="count">${(total.count)!}</div>
		<div>Sum: </div>
		<div id="sum">${(total.sum)!}</div>
		
		<div>Mail: </div>
		<div id="mail">${mails!}</div>
		
		<div>Items:</div>
		<#list items as item>
		<div id="item:${item.name!}">${item.price}</div>
		</#list>
		
		<div>Lines:</div>
		<#list (items[0].lines)! as line>
			<div id="line:${line.to!}">${line.notes}</div>
		</#list>
		<#list (items[1].lines)! as line>
			<div id="line:${line.to!}">${line.notes}</div>
		</#list>
	</body>
</html>