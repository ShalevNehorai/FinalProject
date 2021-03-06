package ilanBondarevsky_shalevNehorai.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

//class to make the ListView unselectable
public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public ObservableList<T> getSelectedItems() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public void selectAll() {
		
	}

	@Override
	public void selectFirst() {
		
	}

	@Override
	public void selectIndices(int arg0, int... arg1) {
		
	}

	@Override
	public void selectLast() {
		
	}

	@Override
	public void clearAndSelect(int arg0) {
		
	}

	@Override
	public void clearSelection() {
		
	}

	@Override
	public void clearSelection(int arg0) {
		
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isSelected(int arg0) {
		return false;
	}

	@Override
	public void select(int arg0) {
		
	}

	@Override
	public void select(T arg0) {
		
	}

	@Override
	public void selectNext() {
		
	}

	@Override
	public void selectPrevious() {
		
	}

}
