package fr.vds.expenses.bo;

public class Refund extends LineDetail {

    private Participant participantToBeRefund;

    public Refund() {
        super();
    }

    public Refund(Participant participantToBeRefund, int idLineDetail , int value) {
        super(idLineDetail, value);
        this.participantToBeRefund = participantToBeRefund;
    }

    public Participant getParticipantToBeRefund() {
        return participantToBeRefund;
    }

    public void setParticipantToBeRefund(Participant participantToBeRefund) {
        this.participantToBeRefund = participantToBeRefund;
    }
}