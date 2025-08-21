package com.kmii.board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oracle.net.aso.r;

import java.io.IOException;
import java.util.List;
/**
 * Servlet implementation class BoardController
 */
@WebServlet("/boardlist")   // 보드리스트 요청만 구현
public class BoardController extends HttpServlet {
	private static final int PAGE_GROUP_SIZE=10;
	//게시판 하단에 표시될 현재 글의 갯수로 만들어진 전체 페이지수 
	//[1] [2] [3] [4]	
	private static final long serialVersionUID = 1L;
	
	BoardDao boardDao = new BoardDao();   
   
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int page = 1; 
		//게시판에 페이지 번호 없이 게시판 링크로 접근한 경우 무조건 1페이지의 내용이 출력되어야 함
		//처음에 보여질 페이지의 번호의 초기값을 1로 초기화
		
		int totalContent = boardDao.countBoard();
		
		
		if(request.getParameter("page") == null) { //참이면 링크타고 게시판으로 들어온 경우
			page = 1;
		} else { //유저가 보고 싶은 페이지 번호를 클릭한 경우
			page = Integer.parseInt(request.getParameter("page"));
			//유저가 클릭한 유저가 보고 싶어하는 페이지의 번호
		}
		
		List<BoardDto> boardDtos = boardDao.boardList(page);
		
		int totalPage = (int) Math.ceil((double)totalContent / BoardDao.PAGE_SIZE );
		
		int startPage = (((page-1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE) +1 ;
		int endPage = startPage + PAGE_GROUP_SIZE -1;
		// int endPage = startPage + PAGE_GROUP_SIZE -1; 이런경우 글이 없어도 여러 페이지가 찍힌다
				
		// 개선한 ednapage값 : startPage+9 실제 마지막 페이지 값보다 작으면 마지막 페이지값으로 endpage값을 대체
		if(endPage > totalPage) {  
			endPage = totalPage ;
		} 
				
		
		request.setAttribute("boardDtos", boardDtos);  // 유저가 선택한 페이지에 해당하는 글(10개씩)
		request.setAttribute("currentPage", page);  // 유저가 현재 선택한 페이지
		
		// 총글의 개수로 표현될 페이지 수 (23개면 3개 전달)
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("boardList.jsp");		
		dispatcher.forward(request, response);		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}