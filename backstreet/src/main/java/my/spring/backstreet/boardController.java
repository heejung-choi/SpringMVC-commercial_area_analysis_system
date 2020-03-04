package my.spring.backstreet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.BoardDAO;
import vo.BoardVO;
@Controller
public class BoardController {
	@Autowired
	BoardDAO dao;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(BoardVO vo){
		ModelAndView mav = new ModelAndView();
		List<BoardVO> list = dao.listAll();
		mav.addObject("list", list);
		mav.setViewName("board");
		return mav;
	}
	
	@RequestMapping(value = "/listOne", method = RequestMethod.GET)
	public ModelAndView listone(BoardVO vo, String board_id){
		
		ModelAndView mav = new ModelAndView();
		BoardVO listOne = dao.listOne(Integer.parseInt(board_id));
		System.out.println("listone 입니당!!!"+ listOne);
		System.out.println("---------------------------");
		//System.out.print("id는?"+board_id);
		List<BoardVO> list = dao.listAll();
		mav.addObject("listOne", listOne);
		mav.addObject("list", list);				
		mav.setViewName("board");
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(BoardVO vo, int board_id){
		ModelAndView mav = new ModelAndView();
		dao.delete(board_id);
		List<BoardVO> list = dao.listAll();
		mav.addObject("list", list);
		mav.setViewName("board");
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(BoardVO vo, String keyword, String searchType){
		ModelAndView mav = new ModelAndView();
		List<BoardVO> list = dao.search(keyword, searchType);
		mav.addObject("list", list);
		mav.setViewName("board");
		return mav;
	}
	
	@RequestMapping(value = "/writer", method = RequestMethod.GET)
	public ModelAndView writer(BoardVO vo, String writer){
		ModelAndView mav = new ModelAndView();
		List<BoardVO> list = dao.listWriter(writer);
		mav.addObject("list", list);
		mav.setViewName("board");
		return mav;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(BoardVO vo){
		ModelAndView mav = new ModelAndView();
		String writer = vo.getWriter();
		boolean result = dao.insert(vo);
		if (result) { mav.addObject("msg", writer + "님의 글이 성공적으로 입력되었습니다."); } else {
		mav.addObject("msg", writer + "님의 글이 입력되지 않았습니다."); }
		List<BoardVO> list = dao.listAll();
		mav.addObject("list", list);				
		mav.setViewName("board");
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(BoardVO vo, @RequestParam("id") int id){
		System.out.println("vo는?:"+vo);	
		ModelAndView mav = new ModelAndView();
		String writer = vo.getWriter();
		System.out.println("----ddddd---"+id);
		BoardVO vo1 = vo;
		vo1.setBoard_id(id);
		boolean result = dao.update(vo1);
		System.out.println("result는?:"+result);		
		if (result) {	
			mav.addObject("msg", writer + "님의 글이 성공적으로 수정되었습니다.");	
		} else {
			mav.addObject("msg", writer + "님의 글이 수정되지 않았습니다.");
		}
		List<BoardVO> list = dao.listAll();
		mav.addObject("list", list);
		mav.setViewName("board");
		return mav;
	}
}
