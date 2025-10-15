package com.lostnfound.model;

import java.sql.Timestamp;

public class Claim {
    private int claimId;
    private int itemId;
    private String itemTitle;        // populated via JOIN in getPendingClaims()
    private String claimantName;
    private String claimantContact;
    private String proofDescription;
    private String status;
    private Timestamp claimedAt;     // maps to claimed_at in DB
    private Integer reviewedBy;
    private Timestamp reviewedAt;

    public Claim() {}

    public int getClaimId() { return claimId; }
    public void setClaimId(int claimId) { this.claimId = claimId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemTitle() { return itemTitle; }
    public void setItemTitle(String itemTitle) { this.itemTitle = itemTitle; }

    public String getClaimantName() { return claimantName; }
    public void setClaimantName(String claimantName) { this.claimantName = claimantName; }

    public String getClaimantContact() { return claimantContact; }
    public void setClaimantContact(String claimantContact) { this.claimantContact = claimantContact; }

    public String getProofDescription() { return proofDescription; }
    public void setProofDescription(String proofDescription) { this.proofDescription = proofDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getClaimedAt() { return claimedAt; }
    public void setClaimedAt(Timestamp claimedAt) { this.claimedAt = claimedAt; }

    public Integer getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(Integer reviewedBy) { this.reviewedBy = reviewedBy; }

    public Timestamp getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(Timestamp reviewedAt) { this.reviewedAt = reviewedAt; }
}
