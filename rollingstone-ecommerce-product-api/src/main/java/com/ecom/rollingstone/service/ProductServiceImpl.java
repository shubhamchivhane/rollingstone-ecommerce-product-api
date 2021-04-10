package com.ecom.rollingstone.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.rollingstone.exception.Http404Exception;
import com.ecom.rollingstone.model.Category;
import com.ecom.rollingstone.model.Product;
import com.ecom.rollingstone.repository.ProductRepository;
import com.ecom.rollingstone.service.feign.CategoryFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductServiceImpl implements ProductService {

	final static org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryFeignClient categoryFeignClient;

	@Override
	@HystrixCommand(fallbackMethod = "saveProductWithoutValidation")
	public Product save(Product product) {
		Category category = null;
		Category parentCategory = null;

		if (product.getCategory() == null) {
			logger.info(" product category can not be null");
			throw new Http404Exception("Bad Request as Product category can not be null");
		} else {
			logger.info("Product Category is not null  {} :" + product.getCategory());

			try {
				category = categoryFeignClient.get(product.getCategory().getId());
			} catch (Exception e) {
				logger.info(" product category does not exist");
				throw new Http404Exception("Bad Request as the Category Provided is an Invalid one");
			}
		}

		if (product.getParentCategory() == null) {
			logger.info("parent category can not be null");
			throw new Http404Exception("Bad Request as Product parent category can not be null");
		} else {
			logger.info("Product parent Category is not null {} :" + product.getParentCategory());

			try {
				parentCategory = categoryFeignClient.get(product.getParentCategory().getId());
			} catch (Exception e) {
				logger.info(" product parent category does not exists");
				throw new Http404Exception("Bad Request as the Parent Category Provided is an Invalid one");
			}

		}

		return productRepository.save(product);
	}

	public Product saveProductWithoutValidation(Product product) {
		logger.info("[fallback methods] Product parent category doesnt exists "+product.getParentCategory().getId());
		return productRepository.save(product);
	}

	@Override
	public Optional<Product> getProduct(Long productId) {

		return productRepository.findById(productId);
	}

	@Override
	public void deleteProduct(Long productId) {

		productRepository.deleteById(productId);

	}

}
