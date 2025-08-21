<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<link rel="stylesheet" href="css/board.css">
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


<!-- 페이지네이션 -->

<div class="pagination">

	<!-- 첫번째 페이지로 이동 화살표 (1페이지로 이동)-->
	<c:if test="${currentPage > 1 }">
		<a href="boardlist?page=1">◀◀</a>
	</c:if>
	
	<!-- 페이지 그룹이동 -->
	<c:if test="${startPage > 1}">
		<a href="boardlist?page=${startPage - 1}"> ◀ </a>
	</c:if>
	
 <c:forEach begin="${startPage}" end="${endPage}" var="i">
		<c:choose>
			<c:when test="${i == currentPage}">
				<a href="boardlist?page=${i}" class="active">${i}</a>
			</c:when>
			<c:otherwise>
				<a href="boardlist?page=${i}">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
  
   <!--그룹 이동-->
  <c:if test="${endPage < totalPage}">
  	<a href="boardlist?page=${endPage + 1}">▶</a>
  </c:if>
  <!-- 마지막 페이지로 이동 화살표-->
  <c:if test="${currentPage < totalPage}">
  	<a href="boardlist?page=${totalPage}">▶▶</a>
  </c:if>
  
</div>
</body>
</html>