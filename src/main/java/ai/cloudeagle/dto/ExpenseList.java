package ai.cloudeagle.dto;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {

	public List<Expense> expenses;

	public ExpenseList() {
		expenses = new ArrayList<>();
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

}
