package com.pm.company.dto.result;

import java.util.Date;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class VisitResultDTO {
    private Long visitId;

    private Date start;

    private Date end;

    private boolean active;

    private int status;

    public VisitResultDTO() {
		super();
	}

	public VisitResultDTO(Long visitId, Date start, Date end, boolean active, int status) {
		super();
		this.visitId = visitId;
		this.start = start;
		this.end = end;
		this.active = active;
		this.status = status;
	}

	public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
