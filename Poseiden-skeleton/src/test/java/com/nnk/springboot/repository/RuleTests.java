package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RuleTests {

	private static AutoCloseable mocks;

    private RuleName rule;

	@Mock
	private RuleNameRepository ruleNameRepository;

	@BeforeEach
	protected void setUp() {
        rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		if (null != mocks){
			mocks.close();
		}
	}

	@Test
	public void ruleSaveTest() {
        when(ruleNameRepository.save(rule)).thenAnswer(invocation -> {
            rule.setId(1);
            return rule;
        });

		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
        assertEquals("Rule Name", rule.getName());
	}

    @Test
    public void ruleUpdateTest() {
        when(ruleNameRepository.save(rule)).thenReturn(rule);

        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        assertEquals("Rule Name Update", rule.getName());
    }

    @Test
    public void ruleFindAllTest() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(rule));

        List<RuleName> listResult = ruleNameRepository.findAll();
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void ruleDeleteTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));

        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        assertFalse(ruleList.isPresent());
    }
}
