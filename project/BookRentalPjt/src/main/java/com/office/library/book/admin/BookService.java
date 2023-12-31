package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;
import com.office.library.book.HopeBookVO;
import com.office.library.book.RentalBookVO;

@Service
public class BookService {
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;//이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1;//신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1;//신규 도서 등록 실패
	
	@Autowired
	BookDAO bookDAO;

	//도서 등록
	public int registerBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] registerBookConfirm()");
		
		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());
		
		if(!isISBN) {
			int result = bookDAO.insertBook(bookVO);
			
			if(result > 0) {
				return BOOK_REGISTER_SUCCESS;
			}else {
				return BOOK_REGISTER_FAIL;
			}
		}else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
	
	//도서 검색
	public List<BookVO> searchBookConfirm(BookVO bookVO){
		System.out.println("[BookService] searchBookConfirm()");
		
		return bookDAO.selectBooksBySearch(bookVO);
	}
	
	//도서 상세 조회
	public BookVO bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		
		return bookDAO.selectBook(b_no);
	}
	//도서 수정 창 => 도서 정보 조회
	public BookVO modifyBookForm(int b_no) {
		System.out.println("[BookService] modifyBookForm()");
		
		return bookDAO.selectBook(b_no);
	}
	
	//도서 수정 기능
	public int modifyBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] modifyBookConfirm()");
		
		return bookDAO.updateBook(bookVO);
	}
	//도서 삭제 기능
	public int deleteBookConfirm(int b_no) {
		System.out.println("[BookService] deleteBookConfirm()");
		
		return bookDAO.deleteBook(b_no);
	}
	//대출도서 목록 조회
	public List<RentalBookVO> getRentalBooks(){
		System.out.println("[BookService] getRentalBooks()");
		
		return bookDAO.selectRentalBooks();
	}
	//도서 반납 처리
	public int returnBookConfirm(int b_no, int rb_no) {
		System.out.println("[BookService] returnBookConfirm()");
		
		int result = bookDAO.updateRentalBook(rb_no);
		
		if(result > 0) {
			result = bookDAO.updateBook(b_no);
		}
		return result;
	}
	//희망 도서 요청 목록보기
	public List<HopeBookVO> getHopeBooks(){
		System.out.println("[BookService] getHopeBooks()");
		
		return bookDAO.selectHopeBooks();
	}
	// 희망도서 입고처리
	public int registerHopeBookConfirm(BookVO bookVO, int hb_no) {
		System.out.println("[BookService] registerHopeBookConfirm()");
		
		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());
		
		if(!isISBN) {
			int result = bookDAO.insertBook(bookVO);
			
			if(result > 0) {
				bookDAO.updateHopeBookResult(hb_no);
				
				return BOOK_REGISTER_SUCCESS;
			}else {
				return BOOK_REGISTER_FAIL;
			}
		}else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
	
	//전체 도서 목록 조회
	public List<BookVO> getAllBooks(){
		System.out.println("[BookService] getAllBooks()");
		
		return bookDAO.selectAllBooks();
	}
}
