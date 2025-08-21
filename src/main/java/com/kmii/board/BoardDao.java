package com.kmii.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kmii.board.BoardDto;

public class BoardDao {
	
	// DB 커넥션 준비
	private String driverName = "com.mysql.jdbc.Driver";   // MYSQL JDBC 드라이버 이름 - mysql사용시 fix값 고정
	private String url = "jdbc:mysql://localhost:3306/jspdb";   // MYSQL이 설치된 서버의 주소(ip)와 연결할 db(스키마) 이름
	private String userName = "root";
	private String password = "12345";	
			
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
		
	static final int PAGE_SIZE = 10;
	
	public List<BoardDto> boardList(int page) { //게시판 모든 글 리스트를 가져와서 반환하는 메서드
		// page값의 페이지에 해당하는 글 번호 계산
		
		int offset = (page-1) * PAGE_SIZE;
		
		String sql = "SELECT * FROM board ORDER BY bnum DESC LIMIT ? OFFSET ?";
		
		
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
	
			try {
				Class.forName(driverName); 
				conn = DriverManager.getConnection(url, userName, password);
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, PAGE_SIZE);
				pstmt.setInt(2, offset);
				rs = pstmt.executeQuery(); //모든 글 리스트(모든 레코드) 반환
				
				while(rs.next()) {
					int bnum = rs.getInt("bnum");
					String btitle = rs.getString("btitle");
					String bcontent = rs.getString("bcontent");
					String memberid = rs.getString("memberid");
					int bhit = rs.getInt("bhit");
					String bdate = rs.getString("bdate");
					
					
					BoardDto bDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);				
					bDtos.add(bDto);
					
				}	
				
			} catch (Exception e) {
				System.out.println("DB 에러 발생! 게시판 목록 가져오기 실패!");
				e.printStackTrace(); //에러 내용 출력
			} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
				try {
					if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
						rs.close();
					}				
					if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
						pstmt.close();
					}				
					if(conn != null) { //Connection이 null 이 아닐 때만 닫기
						conn.close();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			return bDtos; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
		}
	
	
	public int countBoard() {  // 게시판 모든 글의 갯수를 반환하는 메서드
		
		String sql = "SELECT * FROM board ";
		int count=0;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);	
			pstmt = conn.prepareStatement(sql);
		    rs = pstmt.executeQuery();  // 모든 글 리스트(레코드) 반환
			
			
			while(rs.next()) {   // 글이하나가 아니고 여러개라 while로 돌려줌
				
				count++;
				
				}
		
		} catch (Exception e) {
			System.out.println("게시판 목록 가져오기 실패"); 
			e.printStackTrace();  //에러 내용 출력
			
		} finally {  // finally : 에러 유무와 상관없이 무조건 실행할 내용 입력 -> 여기선 에러와 상관없이 커넥션 닫기
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null){  
					pstmt.close();
				}
				
				if(conn != null) {  
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
					
		return count;  

	  }
		
	}