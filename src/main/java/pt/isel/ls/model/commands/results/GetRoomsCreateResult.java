package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;

import java.util.List;

public class GetRoomsCreateResult implements CommandResult {

    private Iterable<Label> labels;

    private boolean wasError = false;
    private String previousName = "";
    private String previousDescription = "";
    private String previousLocation = "";
    private String previousCapacity = "";
    private List<String> previousLabels;


    public Iterable<Label> getLabels() {
        return labels;
    }

    public void setLabels(Iterable<Label> labels) {
        this.labels = labels;
    }

    public void setError() {
        wasError = true;
    }

    public void setPreviousName(String previousName) {
        if (previousName != null) {
            this.previousName = previousName;
        }
    }

    public void setPreviousDescription(String previousDescription) {
        if (previousDescription != null) {
            this.previousDescription = previousDescription;
        }
    }

    public void setPreviousLocation(String previousLocation) {
        if (previousLocation != null) {
            this.previousLocation = previousLocation;
        }
    }

    public void setPreviousCapacity(String previousCapacity) {
        if (previousCapacity != null) {
            this.previousCapacity = previousCapacity;
        }
    }

    public void setPreviousLabels(List<String> previousLabels) {
        this.previousLabels = previousLabels;
    }

    public boolean wasError() {
        return wasError;
    }

    public String getPreviousName() {
        return previousName;
    }

    public String getPreviousDescription() {
        return previousDescription;
    }

    public String getPreviousLocation() {
        return previousLocation;
    }

    public String getPreviousCapacity() {
        return previousCapacity;
    }

    public List<String> getPreviousLabels() {
        return previousLabels;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetRoomsCreate;
    }
}
