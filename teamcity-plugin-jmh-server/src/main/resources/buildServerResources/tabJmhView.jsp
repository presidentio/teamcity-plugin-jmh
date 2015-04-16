<!--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>-->
<!--<%@ taglib prefix="tt" tagdir="/WEB-INF/tags/tests" %>-->
<!--<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"%>-->
<html>
<meta charset="utf-8">
<title>Benchmarks</title>
<style>
    <style type="text/css">
    .table-benchmark {
    	border: 1px solid #DFDFDF;
    	background-color: #F9F9F9;
    	width: 100%;
    	-moz-border-radius: 3px;
    	-webkit-border-radius: 3px;
    	border-radius: 3px;
    	font-family: Arial,"Bitstream Vera Sans",Helvetica,Verdana,sans-serif;
    	color: #333;
    }
    .table-benchmark td, #table-3 th {
    	border-top-color: white;
    	border-bottom: 1px solid #DFDFDF;
    	color: #555;
    }
    .table-benchmark th {
    	text-shadow: rgba(255, 255, 255, 0.796875) 0px 1px 0px;
    	font-family: Georgia,"Times New Roman","Bitstream Charter",Times,serif;
    	font-weight: normal;
    	padding: 7px 7px 8px;
    	text-align: left;
    	line-height: 1.3em;
    	font-size: 14px;
    }
    .table-benchmark td {
    	font-size: 12px;
    	padding: 4px 7px 2px;
    	vertical-align: top;
    }
    </style>
    <script>
            var data = ${data};
            for(var i = 0; i < data.length; i++){
                document.write("<tr>");
                document.write("<td>" + data[i].benchmark + "</td>");
                document.write("<td>" + data[i].primaryMetric.score + "</td>");
                document.write("</tr>");
            }
        </script>
</style>
<body>
    <table class="table-benchmark">
        <tr>
            <th>Name</th>
            <th>Time</th>
        </tr>
    <table>
</body>
</html>
