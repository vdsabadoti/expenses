package fr.vds.expenses.bo;

public class Debt extends LineDetail {

    private Participant participantHasADebt;

    public Debt() {
        super();
    }

    public Debt(Participant participantHasADebt, int idLineDetail, int value) {
        super(idLineDetail, value);
        this.participantHasADebt = participantHasADebt;
    }

    public Participant getParticipantHasADebt() {
        return participantHasADebt;
    }

    public void setParticipantHasADebt(Participant participantHasADebt) {
        this.participantHasADebt = participantHasADebt;
    }
}