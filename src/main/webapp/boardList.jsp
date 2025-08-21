<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
</head>
<body>


	<h2>게시판 리스트</h2>
	<hr>
	<table border=1 cellspacing="0" cellpadding ="0">
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>글쓴이</th>
			<th>조회수</th>
			<th>글등록일</th>
		</tr>
		
		<c:forEach items="${boardDtos }" var="boardDto">
			<tr>
				<td>${boardDto.bnum }</td>
				<td>${boardDto.btitle }</td>
				<td>${boardDto.memberid }</td>
				<td>${boardDto.bhit }</td>
				<td>${boardDto.bdate }</td>
				
			</tr>
		</c:forEach>

		
	</table>
	<hr>
	<c:forEach begin="1" end="${totalPage}" var="i">  <!-- totalpage : request객체서 가져온거 -->
		<c:choose>
			<c:when test="${i==currentPage}">
				<a href="boardlist?page=${i }"> <b style="color:red;"> ${i }페이지 </b> | </a>
			</c:when>
			<c:otherwise>
				<a href="boardlist?page=${i }"> ${i }페이지 | </a>
			</c:otherwise>
			</c:choose>
		
	</c:forEach>
</body>
</html>