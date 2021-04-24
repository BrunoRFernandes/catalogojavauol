package br.com.uol.catalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.catalogo.controller.ProductController;
import br.com.uol.catalogo.domain.Product;
import br.com.uol.catalogo.domain.dto.ProductDTO;
import br.com.uol.catalogo.repositories.ProductCustomRepository;
import br.com.uol.catalogo.repositories.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());
	
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCustomRepository productCustomRepository;
	
	public List<ProductDTO> list(){
		List<Product> products = productRepository.findAll();
		List<ProductDTO> produtsDto = new ArrayList<ProductDTO>();
		
		for (Product product : products) {
			ProductDTO productDTO = new ProductDTO(product.getId(), product.getNome(), product.getDescricao(), product.getPreco());
			produtsDto.add(productDTO);
		}
		
		return produtsDto;
	}
	
	public ProductDTO findByIdProduct(Long id) {
		
		Optional<Product> obj = productRepository.findById(id);
		ProductDTO productDTO = new ProductDTO(obj.get().getId(), obj.get().getNome(), obj.get().getDescricao(), obj.get().getPreco());
		
		return productDTO;
		
	}

	public List<ProductDTO> search(Double max_price, Double min_price, String q) {
		List<Product> products = productCustomRepository.find(q, min_price, max_price);
		List<ProductDTO> produtsDto = new ArrayList<ProductDTO>();

		for (Product product : products) {
			ProductDTO productDTO = new ProductDTO(product.getId(), product.getNome(), product.getDescricao(), product.getPreco());
			produtsDto.add(productDTO);
		}

		return produtsDto;
	}

	
	public ProductDTO create(ProductDTO product) {
		Product produtSave = productRepository.save(createProductObject(product));
		return new ProductDTO(produtSave.getId(), produtSave.getNome(), produtSave.getDescricao(), produtSave.getPreco());
		
	}
	
	public ProductDTO update(ProductDTO product, Long id) {
		
		try {
			ProductDTO findByIdProduct = findByIdProduct(id);
			
			if(findByIdProduct != null) {
				Product productUpdate = createProductObject(product);
				
				productUpdate.setId(id);
				productUpdate = productRepository.save(productUpdate);
				return new ProductDTO(productUpdate.getId(), productUpdate.getNome(), productUpdate.getDescricao(), productUpdate.getPreco());
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		
		return null;
	}
	
	public boolean delete(Long id) {
		
		try {
			ProductDTO findByIdProduct = findByIdProduct(id);
			
			if(findByIdProduct != null) {
				Product productDelete = createProductObject(findByIdProduct);
				
				productDelete.setId(id);
				productRepository.delete(productDelete);
				
				return true;
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		return false;
	}
	
	//CRIAR O OBJETO PRODUT:
	private Product createProductObject(ProductDTO productDTO) {
		return new Product(productDTO.getNome(), productDTO.getDescricao(), productDTO.getPreco());
	}


}
