# KOSMO_JSP.Spring

- 20210819 목
  - 로그인 화면 들어가기
  - 비동기방식으로 서버에 접속하여 아이디, 암호 존재 개수 얻기 


- 20210820 금
  - 아이디, 암호 검색하기(DB연동하기)


- 20210823 월
  - 파라미터 꺼내는 방법  
    ① HttpServletRequest request  
      request.getParameter( "login_id" );  
    ② @RequestParam( value="login_id") String login_id  
    ③ BoardDTO boardDTO  
  - 게시판 목록 화면 만들기
  - 새글쓰기 화면 만들기
  - 새 글 유효성 검사하기

- 20210824 화
  - 서비스 층 구현하기(@Service, @Transactional)
  - DAO 층 구현하기(@Repository)
  - xml 파일에 오라클 쿼리문 작성하기

- 20210825 수
  - 검색하기 
    <참고>
      - 검색결과
        -  1행 1열 : String, int, double
        -  1행 m열 : HashMap, XxxDTO
        -  n행 1열 : ArrayList, LinkedList, String[]
        -  n행 m열 : List<Map<String,String>> (  ArrayList<HashMap<String,String>>  ), List<XxxDTO>
  - 게시글 상세보기
  
- 20210826 목
  - 상세보기 화면 : update 후 화면 보이기
  - 수정/삭제 화면
  
- 20210827 금
   - 수정하기
   - 삭제하기

- 20210830 월
   - 댓글쓰기
   - 검색하기

- 20210831 화
   - 게시판 검색 총 개수 출력
   - 페이징처리1 (화면에 행을 잘라서 출력)
  
 - 20210901 수
   - 페이징처리2 (화면에 행의 개수만큼 출력, 페이지 선택)
   - boardList.jsp내 자바 코드 정리
   - 엔터키:동기->비동기 바꾸기
   - 페이징처리3 (페이지 10개 이상 넘어갈 때 다음,이전 추가)
