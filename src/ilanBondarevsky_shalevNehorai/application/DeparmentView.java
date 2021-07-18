package ilanBondarevsky_shalevNehorai.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeparmentView {
	private String deprmentName;
	
	private ObservableList<RoleView> roleList;
	
	public DeparmentView(String name) {
		this.deprmentName = name;
		
		roleList = FXCollections.observableArrayList();
	}
	
	public String getName() {
		return deprmentName;
	}
	
	public void addRoleId(int id) {
		roleList.add(new RoleView(id, deprmentName));
	}
	
	public ObservableList<RoleView> getRoleList() {
		return roleList;
	}
}
