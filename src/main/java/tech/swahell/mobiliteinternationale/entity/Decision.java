package tech.swahell.mobiliteinternationale.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Decision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate decisionDate;

    private String mention;

    @Enumerated(EnumType.STRING)
    private DecisionVerdict verdict;

    private String pvPath;

    private String attestationPath; // ✅ Nouveau champ

    private String madeBy;

    @Enumerated(EnumType.STRING)
    private CommissionRole madeByRole;

    private String comment;

    @OneToOne
    @JoinColumn(name = "mobility_id", unique = true)
    private Mobility mobility;

    // Constructors
    public Decision() {}

    public Decision(LocalDate decisionDate, String mention, DecisionVerdict verdict, String pvPath, String attestationPath, Mobility mobility) {
        this.decisionDate = decisionDate;
        this.mention = mention;
        this.verdict = verdict;
        this.pvPath = pvPath;
        this.attestationPath = attestationPath;
        this.mobility = mobility;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public DecisionVerdict getVerdict() {
        return verdict;
    }

    public void setVerdict(DecisionVerdict verdict) {
        this.verdict = verdict;
    }

    public String getPvPath() {
        return pvPath;
    }

    public void setPvPath(String pvPath) {
        this.pvPath = pvPath;
    }

    public String getAttestationPath() {
        return attestationPath;
    }

    public void setAttestationPath(String attestationPath) {
        this.attestationPath = attestationPath;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public CommissionRole getMadeByRole() {
        return madeByRole;
    }

    public void setMadeByRole(CommissionRole madeByRole) {
        this.madeByRole = madeByRole;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    @Override
    public String toString() {
        return "Decision{" +
                "id=" + id +
                ", decisionDate=" + decisionDate +
                ", mention='" + mention + '\'' +
                ", verdict=" + verdict +
                ", pvPath='" + pvPath + '\'' +
                ", attestationPath='" + attestationPath + '\'' + // ✅ toString mis à jour
                ", madeBy='" + madeBy + '\'' +
                ", madeByRole=" + madeByRole +
                ", comment='" + comment + '\'' +
                '}';
    }
}
