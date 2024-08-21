//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.RuleName;
//import com.nnk.springboot.repositories.RuleNameRepository;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RuleTests {
//
//	private static AutoCloseable mocks;
//
//	@InjectMocks
//	private RuleNameRepository ruleNameRepository;
//
//	@BeforeEach
//	protected void setUp() {
//		mocks = MockitoAnnotations.openMocks(this);
//	}
//
//	@AfterAll
//	public static void tearDown() throws Exception {
//		if (null != mocks){
//			mocks.close();
//		}
//	}
//
//	@Test
//	public void ruleTest() {
//		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
//
//		// Save
//		rule = ruleNameRepository.save(rule);
//		assertNotNull(rule.getId());
//		assertTrue(rule.getName().equals("Rule Name"));
//
//		// Update
//		rule.setName("Rule Name Update");
//		rule = ruleNameRepository.save(rule);
//		assertTrue(rule.getName().equals("Rule Name Update"));
//
//		// Find
//		List<RuleName> listResult = ruleNameRepository.findAll();
//		assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = rule.getId();
//		ruleNameRepository.delete(rule);
//		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
//		assertFalse(ruleList.isPresent());
//	}
//}
