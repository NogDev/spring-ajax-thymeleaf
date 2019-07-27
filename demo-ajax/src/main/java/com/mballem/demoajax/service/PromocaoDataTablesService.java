/**
 * 
 */
package com.mballem.demoajax.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.mballem.demoajax.domain.Promocao;
import com.mballem.demoajax.repository.PromocaoRepository;

/**
 * @author andersonnogueira
 * @since Jul 26, 2019
 */
public class PromocaoDataTablesService {
	private String[] cols = {
			"id", "titulo", "site", "linkPromocao", "descricao", "linkImagem", 
			"preco", "likes", "dtCadastro", "categoria"
	};
	
	public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		int current = currentPage(start, length);
		
		String column = columnName(request);
		
		Sort.Direction direction = orderBy(request);
		
		String search = searchBy(request);
		
		Pageable pageable = PageRequest.of(current, length, direction, column);
		
		Page<Promocao> page = queryBy(search, repository, pageable);
		
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());
		return json;
	}

	
	/**
	 * @param request
	 */
	private String searchBy(HttpServletRequest request) {
		
		return request.getParameter("search[value]").isEmpty()
				? ""
				:request.getParameter("search[value]");
	}


	/**
	 * @param repository
	 * @param pageable
	 * @return
	 */
	private Page<Promocao> queryBy(String search,PromocaoRepository repository, Pageable pageable) {
		
		if (search.isEmpty()) {
			
			return repository.findAll(pageable);
		}
		
		return repository.findByTituloOrSiteOrCategoria(search, pageable);
	}


	/**
	 * @param request
	 * @return sort
	 */
	private Direction orderBy(HttpServletRequest request) {
		
		String order = request.getParameter("order[0][dir]"); 
		Sort.Direction sort = Sort.Direction.ASC;
		
		if (order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		
		return sort;
	}


	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return cols[iCol];
	}

	/**
	 * @param start
	 * @param length
	 * @return start/length
	 */
	private int currentPage(int start, int length) {
		return start / length;
	}
}
