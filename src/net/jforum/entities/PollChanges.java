package net.jforum.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jforum.view.forum.common.PostCommon;

/**
 * An helper class that holds changes made to the pool.
 * 
 * @author Rafael Steil
 * @version $Id: PollChanges.java,v 1.1 2005/12/02 23:49:00 rafaelsteil Exp $
 */
public class PollChanges {
	private List deletedOptions = new ArrayList();
	private List newOptions = new ArrayList();
	private List changedOptions = new ArrayList();
	
	private boolean hasChanges;
	
	private Poll first;
	private Poll second;
	
	/**
	 * @param first The "complete", most recent poll version. Usually the one
	 * that's in the database. 
	 * @param second The poll to compare with. It usually will be a poll filled
	 * by {@link PostCommon#fillPostFromRequest()}, so matches will be done againts the 
	 * existing poll and the data comming from the server. 
	 */
	public PollChanges(Poll first, Poll second) {
		this.first = first;
		this.second = second;
	}
	
	public void addChangedOption(PollOption option) {
		this.changedOptions.add(option);
		this.hasChanges = true;
	}
	
	public List getChangedOptions() {
		return this.changedOptions;
	}
	
	public void addDeletedOption(PollOption option) {
		this.deletedOptions.add(option);
		this.hasChanges = true;
	}

	public List getDeletedOptions() {
		return this.deletedOptions;
	}
	
	public void addNewOption(PollOption option) {
		this.newOptions.add(option);
		this.hasChanges = true;
	}

	public List getNewOptions() {
		return this.newOptions;
	}
	
	public boolean hasChanges() {
		this.searchForChanges();
		return this.hasChanges;
	}
	
	private void searchForChanges() {
		if (first == null || second == null) {
			return;
		}
		
		boolean isSame = first.getLabel().equals(second.getLabel());
		isSame &= first.getLength() == second.getLength();
		
		this.hasChanges = !isSame;
		
		List firstOptions = first.getOptions();
		List secondOptions = second.getOptions();
		
		int firstSize = firstOptions.size();
		int secondSize = secondOptions.size();
		int maxCount = Math.min(firstSize, secondSize);
		
		// Search for changes in existing option
		for (int i = 0; i < maxCount; i++) {
			PollOption firstOption = (PollOption)firstOptions.get(i);
			PollOption secondOption = (PollOption)secondOptions.get(i);
			
			if (firstOption.getId() == secondOption.getId()
					&& !firstOption.getText().equals(secondOption.getText())) {
				this.addChangedOption(secondOption);
			}
		}
		
		// Now check if we have more or less options
		if (firstSize > secondSize) {
			// Incoming poll has removed some options
			for (int i = firstSize - 1; i >= secondSize; i--) {
				this.addDeletedOption((PollOption)firstOptions.get(i));
			}
			
			Collections.reverse(this.deletedOptions);
		}
		else if (firstSize < secondSize) {
			// Incoming poll added options
			for (int i = firstSize; i < secondSize; i++) {
				this.addNewOption((PollOption)secondOptions.get(i));
			}
		}
	}
}