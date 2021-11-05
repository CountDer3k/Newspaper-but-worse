package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
	
	UserRepository userRepository;
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Mock
	KeyHolder keyHolder;
	
	@Before
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
		keyHolder = new GeneratedKeyHolder();
	}
	
	
	@Test
	public void testGetUserByUsername() {
		String username = "";
	}
	
	public void mockKeyHolder(int key) {
		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class), Mockito.any(GeneratedKeyHolder.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Map<String, Object> keyMap = new HashMap<String, Object>();
				keyMap.put("", key);
				((GeneratedKeyHolder)args[2]).getKeyList().add(keyMap);
				return key;
			}
		}).thenReturn(1);
	}
	
}
