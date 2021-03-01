package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;

public class TaskControllerTest {
	
	private Task todo;
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		todo = new Task();
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		
		todo.setDueDate(LocalDate.now());
		
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		
		todo.setTask("Description Text");

		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		
		todo.setTask("Description Text");
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		
		todo.setTask("Description Text");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
		Mockito.verify(taskRepo).save(todo);
	}

}
