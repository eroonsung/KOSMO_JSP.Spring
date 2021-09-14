package com.naver.erp;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class BoardServiceImpl implements BoardService{
	//----------------------------------------------------------------------------
	@Autowired
	private BoardDAO boardDAO;

	//String uploadDir = "C:\\eroonsung\\workspace_sboot_04_withFileUpload\\prj01\\src\\main\\resources\\static\\resources\\img\\";
	String uploadDir = Info.board_pic_dir;
	
	//----------------------------------------------------------------------------
	/*
	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegCnt;
	}
	*/
	/*
	public int insertBoard(BoardDTO boardDTO) {
		if(boardDTO.getB_no()>0) {
			int updatePrintNoCnt = this.boardDAO.updatePrintNo(boardDTO);
		}
		
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegCnt;
	}
	*/
	
	public int insertBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception {
		
		FileUpload fileUpload = new FileUpload(multi);
		
		boardDTO.setPic(fileUpload.getNewFileName());
		
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		if(boardDTO.getB_no()>0) {
			int updatePrintNoCnt = this.boardDAO.updatePrintNo(boardDTO);
		}
		
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		
		fileUpload.uploadFile(uploadDir);
		
		return boardRegCnt;
	}
	
	
	//----------------------------------------------------------------------------
	public BoardDTO getBoard(int b_no) {
		int updateCnt = this.boardDAO.updateReadcount(b_no);
		if(updateCnt==0) {return null;}
		
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		return boardDTO;
	}
	
	//----------------------------------------------------------------------------
	
	public int updateBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception {
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt == 0) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt == 0) {return -2;}
		
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		
		FileUpload fileUpload = new FileUpload(multi);
		
		String newFileName = null;
		String is_del = boardDTO.getIs_del();
		
		if(is_del==null) {
			newFileName = fileUpload.getNewFileName();
			boardDTO.setPic(newFileName);
		}
		
		//newFileName = fileUpload.getNewFileName(is_del==null);
		//boardDTO.setPic(newFileName);
		
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		String pic = this.boardDAO.getPic(boardDTO);
		
		int updateCnt = this.boardDAO.updateBoard(boardDTO);
		
		//dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
		
		if(is_del==null) {
			if(multi!=null && multi.isEmpty()==false) {
				if(pic!=null && pic.length()>0) {
					fileUpload.deleteFile(uploadDir+pic);
				}
				//fileUpload.deleteFile(uploadDir+pic, multi!=null && multi.isEmpty()==false && pic!=null && pic.length()>0);
				
				fileUpload.uploadFile(uploadDir);
			}
		}else {
			if(pic!=null&&pic.length()>0) {
				fileUpload.deleteFile(uploadDir+pic);
			}
		}
		
		return updateCnt;
	}
	//----------------------------------------------------------------------------
	public int deleteBoard(BoardDTO boardDTO) {
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt==0) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt==0) {return -2;}
		
		int childrenCnt = this.boardDAO.getChildrenCnt(boardDTO);
		if(childrenCnt>0) {return -3;}
		
		String pic = this.boardDAO.getPic(boardDTO);
		
		int downPrintNo = this.boardDAO.downPrintNo(boardDTO);
		
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);
		if(pic!=null&&pic.length()>0) {
			File file = new File(uploadDir+pic);
			file.delete();
		}
		
		return deleteCnt;
		
	}
}

