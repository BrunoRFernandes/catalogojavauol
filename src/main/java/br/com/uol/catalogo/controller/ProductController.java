package br.com.uol.catalogo.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.catalogo.domain.dto.ErrorMessage;
import br.com.uol.catalogo.domain.dto.ProductDTO;
import br.com.uol.catalogo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());
	
	@Autowired
	private ProductService productSerice;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody ProductDTO product) {
		LOGGER.log(Level.INFO, "Cadastra novo produto.");
		
		if(product != null) {
			ProductDTO newProduct = productSerice.create(product);
			return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
		}

		return new ResponseEntity<>(new ErrorMessage(400, "Erro ao cadastrar o produto: " + product.getNome()), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProductDTO>> listProduts() {
		LOGGER.log(Level.INFO, "Lista produtos.");
		
		return ResponseEntity.ok(productSerice.list());
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<ProductDTO>> search(
			@RequestParam(value = "min_price", required = false) Double min_price,
			@RequestParam(value = "max_price", required = false) Double max_price,
			@RequestParam(value = "q", required = false) String q
	){
		return ResponseEntity.ok(productSerice.search(max_price, min_price, q));
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findByIdProduct(@PathVariable Long id){
		LOGGER.log(Level.INFO, "Busca produto por ID: " + id);
		
		try {
			ProductDTO resultProduct = productSerice.findByIdProduct(id);
			
			if(resultProduct != null) {
				return new ResponseEntity<>(resultProduct, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		return new ResponseEntity<>(new String("Produto não foi localizado!"), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public  ResponseEntity<?> update(@Valid @RequestBody ProductDTO product, @PathVariable Long id) {
		LOGGER.log(Level.INFO, "Atualizando o produto: " + product.getNome());
		
		ProductDTO productUpdate = productSerice.update(product, id);
		
		if(productUpdate != null) {
			return new ResponseEntity<>(productUpdate, HttpStatus.OK);
		}
			
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public  ResponseEntity<?> delete(@PathVariable Long id) {
		LOGGER.log(Level.INFO, "Deletando o produto com o id: " + id);
		
		if(productSerice.delete(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
			
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
