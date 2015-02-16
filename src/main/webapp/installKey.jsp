<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	String pkcs7ca = "sPKCS7ca=\"\"\r\n"; 
	//读取
	BufferedReader br=new BufferedReader(new FileReader(new File("E:\\mywork\\ec-cas\\test2\\root-cert.pem")));
	String s = br.readLine();
	while (StringUtils.isNotBlank(s)) {   
		pkcs7ca += "sPKCS7ca=sPKCS7ca & \"" + s + "\" & vbcrlf\r\n";
        s = br.readLine();   
    }
	br.close();
	String a=request.getHeader("X-Forwarded-For");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>CA CERT</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<OBJECT id="XEnroll" classid="clsid:127698e4-e730-4e5c-a2b1-21490a70c8a1" codebase="xenroll.dll"></OBJECT>      
     
<script type="text/vbscript">      
    ON ERROR resume next      
    <%=pkcs7ca%>   
    XEnroll.InstallPKCS7 sPKCS7ca      
    If err.number = 438 then      
        msgbox err.description & err.number      
    Elseif err.number <> 0 then      
        msgbox err.description & err.number      
    Else      
        msgbox "根证书安装成功"     
    End If      
</script>
  </head>
  
  <body>
    
  </body>
</html>
